package com.example.demo;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchitectureVerificationTest {

    private static final String PKG_APPLICATION = "com.example.demo";
    private static final String PKG_UI = PKG_APPLICATION + ".ui..";
    private static final String PKG_DOMAIN = PKG_APPLICATION + ".domain..";
    private static final String PKG_SERVICES = PKG_APPLICATION + ".services..";
    private static final String PKG_SERVICES_SPI = PKG_APPLICATION + ".services.spi..";
    private static final String PKG_INTEGRATIONS = PKG_APPLICATION + ".integrations..";
    private final static JavaClasses PROJECT_CLASSES = new ClassFileImporter().importPackages(PKG_APPLICATION);

    @Test
    void domain_must_not_depend_on_services() {
        noClasses().that().resideInAPackage(PKG_DOMAIN)
                .should().dependOnClassesThat().resideInAPackage(PKG_SERVICES)
                .check(PROJECT_CLASSES);
    }

    @Test
    void domain_must_not_depend_on_ui() {
        noClasses().that().resideInAPackage(PKG_DOMAIN)
                .should().dependOnClassesThat().resideInAPackage(PKG_UI)
                .check(PROJECT_CLASSES);
    }

    @Test
    void domain_must_not_depend_on_integrations() {
        noClasses().that().resideInAPackage(PKG_DOMAIN)
                .should().dependOnClassesThat().resideInAPackage(PKG_INTEGRATIONS)
                .check(PROJECT_CLASSES);
    }

    @Test
    void services_must_not_depend_on_ui() {
        noClasses().that().resideInAPackage(PKG_SERVICES)
                .should().dependOnClassesThat().resideInAPackage(PKG_UI)
                .check(PROJECT_CLASSES);
    }

    @Test
    void services_must_not_depend_on_integrations() {
        noClasses().that().resideInAPackage(PKG_SERVICES)
                .should().dependOnClassesThat().resideInAPackage(PKG_INTEGRATIONS)
                .check(PROJECT_CLASSES);
    }

    @Test
    void service_implementations_must_be_package_private() {
        classes().that().resideInAPackage(PKG_SERVICES).and().areAnnotatedWith(Service.class)
                .should().bePackagePrivate()
                .check(PROJECT_CLASSES);
    }

    @Test
    void ui_must_not_depend_on_integrations() {
        noClasses().that().resideInAPackage(PKG_UI)
                .should().dependOnClassesThat().resideInAPackage(PKG_INTEGRATIONS)
                .check(PROJECT_CLASSES);
    }

    @Test
    void ui_must_not_depend_on_repositories() {
        noClasses().that().resideInAnyPackage(PKG_UI)
                .should().dependOnClassesThat().areAssignableTo(Repository.class)
                .check(PROJECT_CLASSES);
    }

    @Test
    void integrations_must_not_depend_on_ui() {
        noClasses().that().resideInAnyPackage(PKG_INTEGRATIONS)
                .should().dependOnClassesThat().resideInAPackage(PKG_UI)
                .check(PROJECT_CLASSES);
    }

    @Test
    void integration_implementations_must_be_package_private() {
        classes().that().resideInAPackage(PKG_INTEGRATIONS).and().implement(JavaClass.Predicates.resideInAnyPackage(PKG_SERVICES_SPI))
                .should().bePackagePrivate()
                .check(PROJECT_CLASSES    );
    }
}
