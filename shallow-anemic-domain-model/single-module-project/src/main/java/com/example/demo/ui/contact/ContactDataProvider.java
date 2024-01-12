package com.example.demo.ui.contact;

import com.example.demo.domain.Contact;
import com.example.demo.services.ContactService;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;

import java.util.stream.Stream;

class ContactDataProvider extends AbstractBackEndDataProvider<Contact, Void> {

    private final ContactService contactService;

    ContactDataProvider(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    protected Stream<Contact> fetchFromBackEnd(Query<Contact, Void> query) {
        return contactService.findAll(query.getOffset(), query.getLimit()).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<Contact, Void> query) {
        return contactService.countAll();
    }

    @Override
    public Object getId(Contact item) {
        return item.getId();
    }
}
