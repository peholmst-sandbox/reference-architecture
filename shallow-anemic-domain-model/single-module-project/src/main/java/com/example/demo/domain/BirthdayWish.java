package com.example.demo.domain;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"contact_id", "[year]"}))
public class BirthdayWish extends AbstractPersistable<Long> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "contact_id")
    private Contact contact;
    @Column(nullable = false, name = "[year]")
    private int year;

    protected BirthdayWish() {
    }

    public BirthdayWish(Contact contact, int year) {
        this.contact = contact;
        this.year = year;
    }
}
