package com.example.demo.ui.contact;

import com.example.demo.domain.Contact;
import com.example.demo.services.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class ContactsView extends SplitLayout {

    private final ContactDataProvider contactDataProvider;
    private final Grid<Contact> contactGrid;
    private final ContactEditor contactForm;

    public ContactsView(ContactService contactService) {
        this.contactDataProvider = new ContactDataProvider(contactService);

        contactGrid = new Grid<>(contactDataProvider);
        contactGrid.addColumn(Contact::getLastName).setHeader("Last name");
        contactGrid.addColumn(Contact::getFirstName).setHeader("First name");
        contactGrid.addColumn(Contact::getBirthDate).setHeader("Birth date");
        contactGrid.addColumn(Contact::getEmail).setHeader("Email");
        contactGrid.addSelectionListener(e -> {
            if (e.isFromClient()) {
                e.getFirstSelectedItem().ifPresentOrElse(this::select, this::deselectAll);
            }
        });

        var refresh = new Button("Refresh", VaadinIcon.REFRESH.create(), e -> refreshEntireGrid());
        var add = new Button("Add Contact", VaadinIcon.PLUS_CIRCLE.create(), e -> add());

        var buttons = new HorizontalLayout(add, refresh);
        var contactGridLayout = new VerticalLayout(contactGrid, buttons);

        contactForm = new ContactEditor(contactService, this::refreshInGrid, this::removeFromGrid);

        addToPrimary(contactGridLayout);
        addToSecondary(contactForm);
        setSplitterPosition(80f);
        setSizeFull();
    }

    private void select(Contact contact) {
        contactForm.edit(contact);
    }

    private void deselectAll() {
        contactForm.clear();
    }

    private void add() {
        contactGrid.deselectAll();
        contactForm.createNew();
    }

    private void refreshEntireGrid() {
        justRefreshGrid();
        contactGrid.deselectAll();
    }

    private void refreshInGrid(Contact contact, boolean isNew) {
        if (isNew) {
            contactDataProvider.refreshAll();
        } else {
            contactDataProvider.refreshItem(contact);
        }
        contactGrid.select(contact);
    }

    private void removeFromGrid(Contact contact) {
        contactDataProvider.refreshItem(contact);
        contactGrid.deselectAll();
    }

    private void justRefreshGrid() {
        contactDataProvider.refreshAll();
    }
}
