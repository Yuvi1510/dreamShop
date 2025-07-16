package com.yuvraj.main.models;

import java.util.Collection;
import java.util.HashSet;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role {
	@Id
	private Long id;
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private Collection<User> user = new HashSet<>();
	
	public Role(Long id , String name) {
		this.id = id;
		this.name = name;
	}

	
}
