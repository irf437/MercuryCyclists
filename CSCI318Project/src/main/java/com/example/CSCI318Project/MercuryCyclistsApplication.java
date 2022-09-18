package com.example.CSCI318Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import controllers.*;
import domainModels.*;
import exceptionHandlers.*;
import repos.*;


@SpringBootApplication
@ComponentScan(basePackageClasses = {SupplierController.class, ContactController.class, PartController.class, PartModelAssembler.class, ContactModelAssembler.class, SupplierModelAssembler.class})
@EnableJpaRepositories(basePackageClasses = {PartRepository.class, ContactRepository.class, SupplierRepository.class})
@EntityScan(basePackageClasses = {Part.class, Contact.class, Supplier.class})
public class MercuryCyclistsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercuryCyclistsApplication.class, args);
	}

}
