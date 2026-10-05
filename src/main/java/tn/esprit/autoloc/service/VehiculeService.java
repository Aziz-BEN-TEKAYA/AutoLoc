package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;

public class VehiculeService implements IvehiculeService {
    VehiculeRepository vRepo;
    @Override
    public List<Vehicule> retrieveAllVehicules() {
        return (List<Vehicule>) vRepo.findAll();
    }

    @Override
    public Vehicule addVehicule(Vehicule c) {
        return vRepo.save(c);
    }

    @Override
    public Vehicule updateVehicule(Vehicule c) {
        return vRepo.save(c);
    }

    @Override
    public Vehicule retrieveVehicule(Long idVehicule) {

        return vRepo.findById(idVehicule).orElse(null);

    }

    @Override
    public void removeVehicule(Long idVehicule) {
        vRepo.deleteById(idVehicule);

    }

    @Override
    public List<Vehicule> addVehicules(List<Vehicule> Vehicules) {
        return (List<Vehicule>) vRepo.saveAll(Vehicules);
    }
}
