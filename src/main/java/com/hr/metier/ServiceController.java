package com.hr.metier;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hr.dao.CongeDao;
import com.hr.dao.DepartementDao;
import com.hr.dao.EmployeeDao;
import com.hr.dao.JobDao;
import com.hr.entities.Conge;
import com.hr.entities.Employee;
import com.hr.entities.Departement;
import com.hr.entities.Job;

@Controller
class MainController {

	
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CongeDao congeDao;
	
	@Autowired
	private JobDao jobDao;
	@Autowired
	private DepartementDao departementDao;
	
	
	private static Employee employe = new Employee();
	
	@RequestMapping (value="/SupDep{nom}")
	public String deleteDep(Model m, String nom) {
	Departement d = departementDao.findByNomDep(nom);
	departementDao.delete(d);
	return hello(m);
	}
	
	@RequestMapping (value="/SupJob{titre}")
	public String deleteJob(Model m, String titre) {
	Job j = jobDao.findByTitre(titre);
	jobDao.delete(j);
	return job(m);
	}
	
	@RequestMapping (value="/accept{raison}")
	public String accept(Model m, String raison) {
		Conge c = congeDao.findByRaison(raison);
		c.setEtat("Accepte");
		congeDao.save(c);
		return demande(m);
	}
	
	@RequestMapping (value="/refuse{raison}")
	public String refuse(Model m, String raison) {
		Conge c = congeDao.findByRaison(raison);
		c.setEtat("Refuse");
		congeDao.save(c);
		return demande(m);
	}
	
	@RequestMapping(value="/addDepartement", method=RequestMethod.POST)
	public String save(Model m, String nomDep) {
		Departement d = new Departement(nomDep);
		
		departementDao.save(d);
	return departement(m);}
	
	@RequestMapping(value="/addJob", method=RequestMethod.POST)
	public String save(Model m, String titre, double salaire) {
		Job j = new Job(titre,salaire);
		
		jobDao.save(j);
	return job(m);}
	

	@RequestMapping(value="/sEmployee", method=RequestMethod.POST)
	public String save(Model m, String nom,String prenom, String sexe, String adresse,
			int tel,String email, String password, String departement, String metier) {
		Job j = jobDao.findByTitre(metier);
		Departement d = departementDao.findByNomDep(departement);
		Employee e = new Employee();
		e.setNom(nom);
		e.setPrenom(prenom);
		e.setMetier(j);
		e.setDepartement(d);
		e.setAdresse(adresse);
		e.setTel(tel);
		e.setEmail(email);
		e.setPassword(password);
		e.setSexe(sexe);

		employeeDao.save(e);
	return addEmployee(m);}
	
	
	@GetMapping("/addEmployee")
    public String addEmployee(Model m) {
    	m.addAttribute("listjob",jobDao.findAll()); 
    	m.addAttribute("listDep",departementDao.findAll()); 

        return "addEmployee";
    }
	
	
	@GetMapping("/job")
    public String job(Model m) {
       	m.addAttribute("lst",employeeDao.findJobCount()); 
        return "job";
    }
	
	@GetMapping("/demande")
    public String demande(Model m) {
 
       	m.addAttribute("list",congeDao.findDemande()); 

    	
       	m.addAttribute("lst",congeDao.findDemand()); 
        return "demande";
    }
	
	@GetMapping("/departement")
    public String departement(Model m) {
    	
    	
    	m.addAttribute("list",employeeDao.findDepartmentCount()); 
        return "departement";
    }
	
	@RequestMapping(value="/")
	public String accueil(Model m) {
	//List<Employee> list = new ArrayList<Employee>();
	//Employee e = new Employee();
	//e.setNom("hello");
	//e.setPrenom("hello");
	//e.setEmail("email");
	//list.add(e);
	//m.addAttribute("list",list);
	return "login"; }
	
	@RequestMapping(value="/index")
	public String index(Model m) {
	
	m.addAttribute("list",employeeDao.findAll());
	m.addAttribute("list",employeeDao.findAll());
	m.addAttribute("listjob",jobDao.findAll()); 
	m.addAttribute("listdep",departementDao.findAll()); 
	return "index";
	}
	
	@RequestMapping(value="/login")
	public String login(Model m) {
		
		return "login";
	}
	@RequestMapping(value="/validerLogin")
	public String save(Model m ,String email,String password) {
		Employee employee= employeeDao.findByEmailAndPassword(email, password);
		employe = employee;
		if(employee==null) {
			boolean error=true;
			m.addAttribute("error",error);
			
			return "login";
		}
		
		
    if(employee.getIsHr()) {
	m.addAttribute("employee", employee);
	m.addAttribute("list",employeeDao.findAll());
	return index(m);
	}
    
    
    else {
    	List<Employee> list = new ArrayList<Employee>();
    	list.add(employee);
    
    m.addAttribute("list",list);
    return "hello";}
    

	}
	
	
	@RequestMapping(value="/demandeConge")
	
	public String demandeConge(Model m) {
		
		List<Employee> list=new ArrayList<Employee>();
		list.add(employe);
		
		m.addAttribute("list", list);
		
		
		return "demandeConge";
	}
	
  @RequestMapping(value="/validerDemandeConge",method=RequestMethod.POST)
	
	public String validerDemandeConge(Model m,String email,String dateDebut,String dateFin,String raison) throws ParseException {
	  
	  Employee employee = employeeDao.findByEmail(email);
	  
	  Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateDebut);
	  Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(dateFin); 
	  
	  

	 
	  
	  
	  
	  Conge conge = new Conge( raison, date1, date2, employee);
	  congeDao.save(conge);
	  
	  employee.getListConges().add(conge);
	  
	  
	  
	  boolean success=true;
	  m.addAttribute("error",success);
	  
		
		return demandeConge(m);
	}
	
	@RequestMapping (value="/SupEmp{email}")
	public String delete(Model m, String email) {
	Employee e = employeeDao.findByEmail(email);
	employeeDao.delete(e);
	return index(m);
	} 
	
	@RequestMapping(value="/updEmp{email}")
	public String update(Model m,String email) {
	
		return "index";
	}

	
	
    @GetMapping("/hello")
    public String hello(Model m) {
    	
    	List<Employee> list = new ArrayList<Employee>();
    	list.add(employe);
    
       m.addAttribute("list",list);
    	
        return "hello";
    }
    
    
    @GetMapping("/listDemandeConges")
    
    public String listDemandeConges(Model m) {
    	
    	m.addAttribute("listConge",congeDao.findByEmploye(employe));
    	
    	return "listDemandeConges";
    }
    
    @RequestMapping(value="/changement_emp",method=RequestMethod.POST)
   	public String changement_emp(Model m,String Nom,String Prenom,String Job,String Departement,String Adresse,int Tel, String Email) {
   	Employee e=employeeDao.findByEmail(Email);
   	Job j = jobDao.findByTitre(Job);
   	Departement d = departementDao.findByNomDep(Departement);
   	e.setNom(Nom);
   	e.setPrenom(Prenom);
   	e.setMetier(j);
   	e.setDepartement(d);
   	e.setAdresse(Adresse);
   	e.setTel(Tel);
   	e.setEmail(Email);
   	employeeDao.save(e);
   	return index(m);}
    
    
}