package com.hr.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.entities.Job;
public interface JobDao extends JpaRepository<Job, Long> {

	Job findByTitre(String job);

	
}
