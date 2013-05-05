package com.metal.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent class for the admin database table.
 */
@Entity
@Table(name = "admin")
@DiscriminatorValue("ADMIN")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
		@NamedQuery(name = "Admin.findByName", query = "SELECT a FROM Admin a WHERE a.name = :name"),
		@NamedQuery(name = "Admin.findByUsername", query = "SELECT a FROM Admin a WHERE a.username = :username") })
public class Admin extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Size(max = 50)
	@Column(nullable = false, length = 100)
	private String description;

	@Size(min = 5, max = 50)
	@Column(nullable = false, length = 100)
	private String name;

	public Admin() {
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}