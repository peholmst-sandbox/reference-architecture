package com.example.demo.services;

import com.example.demo.domain.Contact;
import com.example.demo.domain.ContactRepository;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAll(int offset, int limit) {
        return contactRepository.findAllBy(ScrollPosition.offset(offset),
                Sort.by(Contact.PROP_LAST_NAME, Contact.PROP_FIRST_NAME, Contact.PROP_ID),
                Limit.of(limit)).toList();
    }

    @Override
    public int countAll() {
        return (int) contactRepository.count();
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }
}
