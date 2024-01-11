package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.Optional;

public interface BirthdayWishRepository extends JpaRepository<BirthdayWish, Long> {

    boolean existsByContactAndYear(Contact contact, int year);
}
