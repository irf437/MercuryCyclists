package InventoryControllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import InventoryDomainModels.Part;
import InventoryDomainModels.PartModelAssembler;
import domainModels.Supplier;
import InventoryExceptionHandlers.PartNotFoundException;
import exceptionHandlers.SupplierNotFoundException;
import InventoryRepos.PartRepository;
import repos.SupplierRepository;

@RestController
public class PartController{
	
	private final SupplierRepository suppRepository;
	
	private final PartRepository repository;
	private final PartModelAssembler assembler;
	
	PartController(SupplierRepository suppRepository, PartRepository repository, PartModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
		this.suppRepository = suppRepository;
		
	}
	
	@GetMapping("/parts")
	public CollectionModel<EntityModel<Part>> all() {
		List<EntityModel<Part>> parts = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		
		return CollectionModel.of(parts, linkTo(methodOn(PartController.class).all()).withSelfRel());
	}
	
	@PostMapping("/parts")
	ResponseEntity<?> newPart(@RequestBody Part newPart) {
		Supplier newSupp = suppRepository.findById(newPart.getCompanyName()).orElseThrow(() -> new SupplierNotFoundException(newPart.getCompanyName()));
		newPart.setSupplier(newSupp);
		
//		suppRepository.findById(newContact.getCompanyName()).orElseThrow(() -> new SupplierNotFoundException(newContact.getCompanyName())).pushContact(newContact);
//		newSupp.pushContact(newContact);
		
		EntityModel<Part> entityModel = assembler.toModel(repository.save(newPart));		
		
		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(EntityModel.of(entityModel));
	}	
	
	@GetMapping("/parts/{partId}")
	public EntityModel<Part> one(@PathVariable String partId) {
		Part part = repository.findById(partId) //
				.orElseThrow(() -> new PartNotFoundException(partId));
		return assembler.toModel(part);
	}
	
	@PutMapping("/parts/{partId}")
	ResponseEntity<?> replacePart(@RequestBody Part newPart, @PathVariable String partId) {
		Part updatedPart = repository.findById(partId) //
				.map(part -> {
					part.setPartId(newPart.getpartId());
					part.setName(newPart.getName());
					part.setDescription(newPart.getDescription());					
					part.setCompanyName(newPart.getCompanyName());
					return repository.save(part);
				}) //
				.orElseGet(() -> {
					newPart.setPartId(partId);
					return repository.save(newPart);
				});
		EntityModel<Part> entityModel = assembler.toModel(updatedPart);
		
		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}
	
	@DeleteMapping("/parts/{PartId}")
	ResponseEntity<?> deletePart(@PathVariable String PartId) {
		repository.deleteById(PartId);
		return ResponseEntity.noContent().build();
	}
}