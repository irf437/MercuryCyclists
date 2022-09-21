package InventoryDomainModels;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import domainModels.Supplier;

@Entity
@Table(name = "parts")
public class Part {
	
	private @Id String partId;
	private String name;
	private String description;
	private String companyName;
	
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "supplier_companyName", nullable = false)
	private Supplier supplier;
	
	Part() {}
	
	Part(String partId, String name, String description, String companyName){
		this.partId = partId;
		this.name = name;
		this.description = description;		
		this.companyName = companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyName() {
		return this.companyName;
	}
	
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	public Supplier getSupplier() {
		return this.supplier;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getpartId() {
		return this.partId;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPartId(String partId) {
		this.partId = partId;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}	
	
	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
	    if (!(o instanceof Part))
	    	return false;
	    Part part = (Part) o;
	    return Objects.equals(this.partId, part.partId) && 
	    		Objects.equals(this.name, part.name) &&
	    		Objects.equals(this.description,  part.description) &&
	    		Objects.equals(this.supplier, part.supplier);
	  }

	@Override
	public int hashCode() {
		return Objects.hash(this.partId, this.name, this.description, this.supplier);
	}
}