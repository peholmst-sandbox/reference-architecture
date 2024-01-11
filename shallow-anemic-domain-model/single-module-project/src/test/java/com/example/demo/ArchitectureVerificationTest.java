package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ArchitectureVerificationTest {

    @Test
    void verifyApplicationStructure() {
        ApplicationModules.of(DemoApplication.class).verify();
        System.out.println(ApplicationModules.of(DemoApplication.class));
    }
}
