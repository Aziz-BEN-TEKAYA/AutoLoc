package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import tn.esprit.autoloc.web.mapper.VehiculeMapper;

import java.util.HashSet;
import java.util.List;
@Service
@RequiredArgsConstructor
public class VehiculeService implements IvehiculeService {
    final VehiculeRepository vRepo;
    final AgenceRepository agenceRepo;
    final EquipementRepository equipementRepo;
    final VehiculeMapper vehiculeMapper;

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
    @Transactional
    public Vehicule affecterVehicule(Long idVehicule, Long idAgence) {
        Vehicule vehicule = vRepo.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));

        Agence agence = agenceRepo.findById(idAgence)
                .orElseThrow(() -> new RuntimeException("Agence introuvable"));

        vehicule.setAgence(agence);
        return vRepo.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vRepo.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));

        Equipement equipement = equipementRepo.findById(idEquipement)
                .orElseThrow(() -> new RuntimeException("Équipement introuvable"));

        if (vehicule.getEquipements() == null) {
            vehicule.setEquipements(new HashSet<>());
        }
        vehicule.getEquipements().add(equipement); // Set : pas de doublon

        return vRepo.save(vehicule);
    }


    @Override
    @Transactional
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vRepo.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));

        Equipement equipement = equipementRepo.findById(idEquipement)
                .orElseThrow(() -> new RuntimeException("Équipement introuvable"));

        if (vehicule.getEquipements() != null) {
            vehicule.getEquipements().remove(equipement);
        }

        return vRepo.save(vehicule);
    }
    public List<Vehicule> vehiculesParMarque(String marque) {
        return vRepo.findByMarque(marque);
    }

    public List<Vehicule> vehiculesParCategorie(CategorieVehicule categorie) {
        return vRepo.findByCategorie(categorie);
    }


    public List<Vehicule> vehiculesDisponibles() {
        return vRepo.findByStatut(StatutVehicule.DISPONIBLE);
    }


    public List<Vehicule> vehiculesMarqueContient(String fragment) {
        return vRepo.findByMarqueContainingIgnoreCase(fragment);
    }
    public List<Vehicule> getVehiculesByAgence(Long idAgence) {
        return vRepo.getVehiculesByAgence(idAgence);
    }

    // =====================================================================
    //  Méthodes DTO
    // =====================================================================

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> retrieveAllVehiculesDTO() {
        return vehiculeMapper.toDTOList(retrieveAllVehicules());
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO retrieveVehiculeDTO(Long idVehicule) {
        Vehicule vehicule = vRepo.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));
        return vehiculeMapper.toDTO(vehicule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> vehiculesDisponiblesDTO() {
        return vehiculeMapper.toDTOList(vehiculesDisponibles());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> vehiculesByAgenceDTO(Long idAgence) {
        return vehiculeMapper.toDTOList(getVehiculesByAgence(idAgence));
    }

}
