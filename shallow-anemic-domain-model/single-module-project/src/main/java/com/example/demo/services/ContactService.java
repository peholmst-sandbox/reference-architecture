package com.example.demo.services;

import com.example.demo.domain.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAll();

    Contact save(Contact contact);

    void delete(Contact contact);
}
