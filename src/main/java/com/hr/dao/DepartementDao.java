package com.hr.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.entities.Departement;
public interface DepartementDao extends JpaRepository<Departement, Long> {

	Departement findByNomDep(String departement);

	
}
