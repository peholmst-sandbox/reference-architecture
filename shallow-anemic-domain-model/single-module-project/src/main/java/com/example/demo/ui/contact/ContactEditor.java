package com.example.demo.ui.contact;

import com.example.demo.domain.Contact;
import com.example.demo.services.ContactService;
import com.example.demo.ui.converters.StringToZoneIdConverter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.time.ZoneId;

public class ContactEditor extends VerticalLayout {
    private final ContactService contactService;
    private final OnSaveCallback onSaveCallback;
    private final OnDeleteCallback onDeleteCallback;
    private final Binder<Contact> binder;
    private final Div noContact;
    private final FormLayout formLayout;
    private final HorizontalLayout buttons;
    private final H1 header;
    private final Button delete;

    public ContactEditor(ContactService contactService,
                         OnSaveCallback onSaveCallback,
                         OnDeleteCallback onDeleteCallback) {
        this.contactService = contactService;
        this.onSaveCallback = onSaveCallback;
        this.onDeleteCallback = onDeleteCallback;
        binder = new BeanValidationBinder<>(Contact.class);

        var firstName = new TextField("First name");
        firstName.setMaxLength(Contact.FIRST_NAME_MAX_LENGTH);
        var lastName = new TextField("Last name");
        lastName.setMaxLength(Contact.LAST_NAME_MAX_LENGTH);
        var timeZone = new ComboBox<>("Time zone", ZoneId.getAvailableZoneIds());
        var birthDate = new DatePicker("Birth date");
        var email = new EmailField("Email");

        binder.forField(firstName).bind("firstName");
        binder.forField(lastName).bind("lastName");
        binder.forField(timeZone).withConverter(new StringToZoneIdConverter()).bind("timeZone");
        binder.forField(birthDate).bind("birthDate");
        binder.forField(email).bind("email");

        var createNew = new Button("create", event -> createNew());
        createNew.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

        header = new H1();
        noContact = new Div(new Span("Please select a contact or "), createNew, new Span(" a new one."));
        formLayout = new FormLayout(firstName, lastName, timeZone, birthDate, email);

        var save = new Button("Save", event -> save());
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        delete = new Button("Delete", VaadinIcon.TRASH.create(), event -> delete());
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        buttons = new HorizontalLayout(save, delete);

        add(header, noContact, formLayout, buttons);

        clear();
    }

    public void createNew() {
        setBean(new Contact());
    }

    public void edit(Contact contact) {
        setBean(new Contact(contact));
    }

    public void clear() {
        setBean(null);
    }

    private void setBean(Contact bean) {
        binder.setBean(bean);
        header.setVisible(bean != null);
        formLayout.setVisible(bean != null);
        buttons.setVisible(bean != null);
        noContact.setVisible(bean == null);
        delete.setVisible(bean != null && !bean.isNew());

        if (bean != null) {
            if (bean.isNew()) {
                header.setText("Add contact");
            } else {
                header.setText("Edit contact");
            }
        }
    }

    private void save() {
        if (binder.validate().isOk()) {
            var contact = contactService.save(binder.getBean());
            setBean(contact);
            onSaveCallback.contactSaved(contact);
        }
    }

    private void delete() {
        var contact = binder.getBean();
        contactService.delete(contact);
        setBean(null);
        onDeleteCallback.contactDeleted(contact);
    }

    @FunctionalInterface
    public interface OnSaveCallback {
        void contactSaved(Contact contact);
    }

    @FunctionalInterface
    public interface OnDeleteCallback {
        void contactDeleted(Contact contact);
    }
}
