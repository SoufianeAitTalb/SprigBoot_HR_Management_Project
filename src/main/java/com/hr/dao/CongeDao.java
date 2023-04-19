package com.hr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hr.entities.Conge;
import com.hr.entities.Employee;

public interface CongeDao extends JpaRepository<Conge, Long> {
	
	
	
	@Query("SELECT e.nom, e.prenom, c.dateDebut, c.dateFin, c.raison, c.etat, c.idConge FROM Conge c LEFT JOIN Employee e ON c.employe.idEmp = e.idEmp WHERE c.etat like 'En cours De traitement' ORDER BY e.nom")
	List<Object[]> findDemande();
	
	@Query("SELECT e.nom, e.prenom, c.dateDebut, c.dateFin, c.raison, c.etat FROM Conge c LEFT JOIN Employee e ON c.employe.idEmp = e.idEmp WHERE c.etat like 'Accepte' OR c.etat like 'Refuse' ORDER BY e.nom")
	List<Object[]> findDemand();
	
	Conge findByRaison(String raison);
	
	
	public List<Conge>  findByEmploye(Employee employe);
	
	


}
