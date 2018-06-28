package com.ipiecoles.java.mdd050.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.service.CommercialService;
import com.ipiecoles.java.mdd050.service.EmployeService;

@RestController
@RequestMapping("/employes")
public class EmployeController {
	
	@Autowired
	private EmployeService employeService;
	
	@Autowired
	private CommercialService commercialService;
	
	@GetMapping("/count")
	public Long getEmploye() {
		 return employeService.countAllEmploye();
	}
	
	@GetMapping("/{id}")
	public Employe getEmploye(@PathVariable("id") Long id) {
		
		Employe emp = employeService.findById(id);
		if (emp == null) {
			throw new EntityNotFoundException("L'employe numéro " + id + " n'existe pas");
		}
		else return emp;
	}
	
	@GetMapping(value = "", params = "matricule")
	public Employe getEmployeByMatricule(@RequestParam("matricule") String mat){
		Employe emp = employeService.findMyMatricule(mat);
		if (emp == null) {
			throw new EntityNotFoundException("L'employe avec le matricule " + mat + " n'existe pas");
		}
		else return emp;
	}
	
	@GetMapping("")
	public Page<Employe> getAllEmploye(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size,
			@RequestParam("sortProperty") String sortProperty,
			@RequestParam("sortDirection") String sortDirection){
		return employeService.findAllEmployes(page, size, sortProperty, sortDirection);
	}
	
	@RequestMapping (value="", consumes="application/json")
	public <T extends Employe> T creerEmployer(@RequestBody T emp){
		if (emp.getClass()==Commercial.class) {
			
		}
		return null;
		//return new ResponseEntity<Employe>(HttpStatus.CREATED);
	}

}
