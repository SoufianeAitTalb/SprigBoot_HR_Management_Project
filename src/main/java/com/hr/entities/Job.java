package com.hr.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "metier")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@ToString
public class Job {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJob;
    
    private String titre;
    
    private double salaire;
    
    @OneToMany(mappedBy = "metier",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Employee> listEmployes;

	public Job(String titre, double salaire) {
		super();
		this.titre = titre;
		this.salaire = salaire;
	}

    
    
}
