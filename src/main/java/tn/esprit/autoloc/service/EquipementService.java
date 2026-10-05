package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.EquipementRepository;

import java.util.List;

public class EquipementService implements IequipementService {
    EquipementRepository eqRepo;
    @Override
    public List<Equipement> retrieveAllEquipements() {
        return (List<Equipement>) eqRepo.findAll();
    }

    @Override
    public Equipement addEquipement(Equipement c) {
        return eqRepo.save(c);
    }

    @Override
    public Equipement updateEquipement(Equipement c) {
        return eqRepo.save(c);
    }

    @Override
    public Equipement retrieveEquipement(Long idEquipement) {

        return eqRepo.findById(idEquipement).orElse(null);

    }

    @Override
    public void removeEquipement(Long idEquipement) {
        eqRepo.deleteById(idEquipement);

    }

    @Override
    public List<Equipement> addEquipements(List<Equipement> Equipements) {
        return (List<Equipement>) eqRepo.saveAll(Equipements);
    }
}
