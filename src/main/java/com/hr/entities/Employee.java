package com.hr.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "employe")
@Data
@NoArgsConstructor 
@AllArgsConstructor 
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmp;

    private String nom;

    public Employee(Long idEmp, String nom, String prenom, String sexe, String adresse, int tel, String email,Job job) {
		this.idEmp = idEmp;
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
		this.adresse = adresse;
		this.tel = tel;
		this.email = email;
		this.metier=job;
	}

	private String prenom;
    
    private String sexe;

    private String adresse;
    
    private int tel;

    private String email;

    private String password;

    private Boolean isHr= false;

    @ManyToOne
    @JoinColumn(name = "fk_departement")
    private Departement departement;
    
    @ManyToOne
    @JoinColumn(name = "fk_metier")
    private Job metier;
    
    @OneToMany(mappedBy = "employe",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Conge> listConges;

 
    
    
}
