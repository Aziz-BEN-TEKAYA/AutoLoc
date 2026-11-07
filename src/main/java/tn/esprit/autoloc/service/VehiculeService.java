package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class VehiculeService implements IvehiculeService {
    final VehiculeRepository vRepo;
    final AgenceRepository agenceRepo;
    final EquipementRepository equipementRepo;
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

    @Override
    public Vehicule affecterVehicule(Long idVehicule, Long idAgence) {

        Vehicule vehicule = vRepo.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));

        Agence agence = agenceRepo.findById(idAgence)
                .orElseThrow(() -> new RuntimeException("Agence introuvable"));

        vehicule.setAgence(agence);
        return vRepo.save(vehicule);
    }
    @Override
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {

        Vehicule vehicule = vRepo.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));

        Equipement equipement = equipementRepo.findById(idEquipement)
                .orElseThrow(() -> new RuntimeException("Équipement introuvable"));

        if (!vehicule.getEquipements().contains(equipement)) {
            vehicule.getEquipements().add(equipement);
        }

        return vRepo.save(vehicule);
    }
    @Override
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {

        Vehicule vehicule = vRepo.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));

        Equipement equipement = equipementRepo.findById(idEquipement)
                .orElseThrow(() -> new RuntimeException("Équipement introuvable"));

        vehicule.getEquipements().remove(equipement);

        return vRepo.save(vehicule);
    }
}
