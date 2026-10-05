package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;

public class VehiculeService implements IvehiculeService {
    VehiculeRepository clRepo;
    @Override
    public List<Vehicule> retrieveAllVehicules() {
        return (List<Vehicule>) clRepo.findAll();
    }

    @Override
    public Vehicule addVehicule(Vehicule c) {
        return clRepo.save(c);
    }

    @Override
    public Vehicule updateVehicule(Vehicule c) {
        return clRepo.save(c);
    }

    @Override
    public Vehicule retrieveVehicule(Long idVehicule) {

        return clRepo.findById(idVehicule).orElse(null);

    }

    @Override
    public void removeVehicule(Long idVehicule) {
        clRepo.deleteById(idVehicule);

    }

    @Override
    public List<Vehicule> addVehicules(List<Vehicule> Vehicules) {
        return (List<Vehicule>) clRepo.saveAll(Vehicules);
    }
}
