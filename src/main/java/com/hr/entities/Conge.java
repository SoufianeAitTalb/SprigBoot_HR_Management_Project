package com.hr.entities;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "conge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConge;

    private String etat="En cours De traitement" ;

    private String raison;

    private Date dateDebut;

    private Date dateFin;


    @ManyToOne
    @JoinColumn(name = "fk_employee")
    private Employee employe;


	public Conge(  String raison, Date dateDebut, Date dateFin, Employee employe) {
		super();
	
		
		this.raison = raison;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.employe = employe;
	}
    
    
    

}
