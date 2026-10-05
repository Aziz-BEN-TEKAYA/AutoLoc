package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.EmployeRepository;
import tn.esprit.autoloc.repository.EmployeRepository;

import java.util.List;

public class EmployeService implements IemployeService{
    EmployeRepository empRepo;
    @Override
    public List<Employe> retrieveAllEmployes() {
        return (List<Employe>) empRepo.findAll();
    }

    @Override
    public Employe addEmploye(Employe c) {
        return empRepo.save(c);
    }

    @Override
    public Employe updateEmploye(Employe c) {
        return empRepo.save(c);
    }

    @Override
    public Employe retrieveEmploye(Long idEmploye) {

        return empRepo.findById(idEmploye).orElse(null);

    }

    @Override
    public void removeEmploye(Long idEmploye) {
        empRepo.deleteById(idEmploye);

    }

    @Override
    public List<Employe> addEmployes(List<Employe> Employes) {
        return (List<Employe>) empRepo.saveAll(Employes);
    }
}
