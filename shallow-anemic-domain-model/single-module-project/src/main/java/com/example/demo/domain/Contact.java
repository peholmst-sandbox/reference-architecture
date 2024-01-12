package com.example.demo.domain;


import com.example.demo.domain.converters.ZoneIdAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDate;
import java.time.ZoneId;

@Entity
public class Contact extends AbstractPersistable<Long> {

    public static final String PROP_FIRST_NAME = "firstName";
    public static final String PROP_LAST_NAME = "lastName";
    public static final String PROP_ID = "id";
    public static final int FIRST_NAME_MAX_LENGTH = 300;
    public static final int LAST_NAME_MAX_LENGTH = 300;
    public static final int EMAIL_MAX_LENGTH = 320;

    @Column(nullable = false, length = FIRST_NAME_MAX_LENGTH)
    @NotBlank(message = "Please enter a first name")
    @Size(max = FIRST_NAME_MAX_LENGTH)
    private String firstName;
    @Column(nullable = false, length = LAST_NAME_MAX_LENGTH)
    @NotBlank(message = "Please enter a last name")
    @Size(max = LAST_NAME_MAX_LENGTH)
    private String lastName;
    @Convert(converter = ZoneIdAttributeConverter.class)
    @NotNull(message = "Please select a time zone")
    private ZoneId timeZone;
    @PastOrPresent(message = "Birth date cannot be in the future")
    private LocalDate birthDate;
    @Column(length = EMAIL_MAX_LENGTH)
    @Size(max = EMAIL_MAX_LENGTH)
    @Email
    private String email;

    public Contact() {
    }

    public Contact(Contact original) {
        setId(original.getId());
        setFirstName(original.getFirstName());
        setLastName(original.getLastName());
        setTimeZone(original.getTimeZone());
        setBirthDate(original.getBirthDate());
        setEmail(original.getEmail());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
