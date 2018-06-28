package com.ipiecoles.java.mdd050.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ipiecoles.java.mdd050.exception.EmployeException;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    public Employe findById(Long id){
        return employeRepository.findOne(id);
    }

    public Long countAllEmploye() {
        return employeRepository.count();
    }

    public void deleteEmploye(Long id) throws EmployeException {
        try {
            employeRepository.delete(id);
        } catch (DataIntegrityViolationException e){
            //Cas particulier de la suppression d'un manager avec equipe
            if(e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException myException = (ConstraintViolationException) e.getCause();
                if (myException.getSQLException().getSQLState().equals("23000")) {
                    throw new EmployeException("Pour supprimer un manager, il faut que son équipe soit vide.");
                }
            }
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public <T extends Employe> T creerEmploye(T e) {
        return employeRepository.save(e);
    }

    public <T extends Employe> T updateEmploye(Long id, T employe) throws EmployeException {
        return employeRepository.save(employe);
    }

    public Page<Employe> findAllEmployes(Integer page, Integer size, String sortProperty, String sortDirection) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.fromString(sortDirection),sortProperty));
        Pageable pageable = new PageRequest(page,size,sort);
        return employeRepository.findAll(pageable);
    }

    public Employe findMyMatricule(String matricule) {
       return this.employeRepository.findByMatricule(matricule);
    }
}
