package com.example.cqrs.hexagonal;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchitectureTest {
    JavaClasses projectClasses;

    @BeforeEach
    void setup() {
        projectClasses = new ClassFileImporter().importPackages("com.example.cqrs");
    }

    @Test
    void should_domain_never_be_linked_with_frameworks() {
        var ruleNoFramework = noClasses().that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("..springframework..")
                .orShould().dependOnClassesThat().resideInAPackage("javax..")
                .orShould().dependOnClassesThat().resideInAPackage("jakarta..");

        ruleNoFramework.check(projectClasses);
    }

    @Test
    void should_respect_hexagonal_architecture() {
        var ruleLayerAccess = layeredArchitecture().consideringAllDependencies()
                .layer("domain").definedBy("..domain..")
                .layer("infra").definedBy("..infra..")
                .layer("application").definedBy("..application..")
                .layer("adapter").definedBy("..adapter..")
                .whereLayer("domain").mayOnlyBeAccessedByLayers("infra","application","adapter");

        ruleLayerAccess.check(projectClasses);
        var ruleNoFramework = noClasses().that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("infra..")
                .orShould().dependOnClassesThat().resideInAPackage("kernel..")
                .orShould().dependOnClassesThat().resideInAPackage("application..")
                .orShould().dependOnClassesThat().resideInAPackage("adapter..");

        ruleNoFramework.check(projectClasses);
    }
}