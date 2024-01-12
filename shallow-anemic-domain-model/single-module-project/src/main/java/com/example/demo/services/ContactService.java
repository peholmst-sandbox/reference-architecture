package com.example.demo.services;

import com.example.demo.domain.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAll(int offset, int limit);

    int countAll();

    Contact save(Contact contact);

    void delete(Contact contact);
}
