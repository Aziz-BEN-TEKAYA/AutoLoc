package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
        return retrieveAllVehicules().stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO retrieveVehiculeDTO(Long idVehicule) {
        Vehicule vehicule = vRepo.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));
        return toDTO(vehicule);
    }

    @Override
    @Transactional
    public VehiculeDTO addVehiculeDTO(VehiculeDTO dto) {
        Vehicule vehicule = new Vehicule();
        copyToEntity(dto, vehicule);
        return toDTO(vRepo.save(vehicule));
    }

    @Override
    @Transactional
    public VehiculeDTO updateVehiculeDTO(VehiculeDTO dto) {
        if (dto.getIdVehicule() == null) {
            throw new RuntimeException("idVehicule obligatoire pour la modification");
        }
        // On charge l'entité existante pour conserver équipements et réservations
        Vehicule vehicule = vRepo.findById(dto.getIdVehicule())
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));
        copyToEntity(dto, vehicule);
        return toDTO(vRepo.save(vehicule));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> vehiculesDisponiblesDTO() {
        return vehiculesDisponibles().stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> vehiculesByAgenceDTO(Long idAgence) {
        return getVehiculesByAgence(idAgence).stream().map(this::toDTO).toList();
    }

    // ---- Mapping Entité <-> DTO ----

    private VehiculeDTO toDTO(Vehicule v) {
        Agence agence = v.getAgence();
        Set<String> equipements = v.getEquipements() == null
                ? Set.of()
                : v.getEquipements().stream().map(Equipement::getLibelle).collect(Collectors.toSet());

        return VehiculeDTO.builder()
                .idVehicule(v.getIdVehicule())
                .immatriculation(v.getImmatriculation())
                .marque(v.getMarque())
                .modele(v.getModele())
                .categorie(v.getCategorie())
                .tarifJournalier(v.getTarifJournalier())
                .statut(v.getStatut())
                .idAgence(agence != null ? agence.getId() : null)
                .nomAgence(agence != null ? agence.getNom() : null)
                .equipements(equipements)
                .build();
    }

    private void copyToEntity(VehiculeDTO dto, Vehicule v) {
        v.setImmatriculation(dto.getImmatriculation());
        v.setMarque(dto.getMarque());
        v.setModele(dto.getModele());
        v.setCategorie(dto.getCategorie());
        v.setTarifJournalier(dto.getTarifJournalier());
        v.setStatut(dto.getStatut());

        if (dto.getIdAgence() != null) {
            Agence agence = agenceRepo.findById(dto.getIdAgence())
                    .orElseThrow(() -> new RuntimeException("Agence introuvable"));
            v.setAgence(agence);
        } else {
            v.setAgence(null);
        }
    }

}
