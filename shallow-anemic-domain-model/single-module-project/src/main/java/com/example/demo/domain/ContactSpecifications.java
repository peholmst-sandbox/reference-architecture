package com.example.demo.domain;

import org.springframework.data.jpa.domain.Specification;

import java.time.MonthDay;

public final class ContactSpecifications {
    private ContactSpecifications() {
    }

    public static Specification<Contact> withBirthDay(MonthDay birthDay) {
        return (root, query, builder) -> {
            var birthDate = root.get(Contact_.birthDate);
            var day = builder.function("DAY", Integer.class, birthDate);
            var month = builder.function("MONTH", Integer.class, birthDate);

            return builder.and(
                    builder.equal(month, birthDay.getMonthValue()),
                    builder.equal(day, birthDay.getDayOfMonth())
            );
        };
    }
}
