package com.hr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hr.entities.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {
	
	public Employee findByEmailAndPassword(String email,String password);
	
	public Employee findByEmail(String email);
	
	@Query("SELECT d.nomDep, COUNT(e.idEmp) FROM Employee e RIGHT JOIN e.departement d GROUP BY d.nomDep ORDER BY COUNT(e.idEmp) DESC")
	List<Object[]> findDepartmentCount();

	@Query("SELECT  j.titre, j.salaire ,COUNT(e.idEmp) FROM Employee e RIGHT JOIN e.metier j GROUP BY j.titre ORDER BY COUNT(e.idEmp) DESC")
	List<Object[]> findJobCount();
	
	
	
	

}
