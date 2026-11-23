package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

import java.util.List;

public interface IvehiculeService {
    List<Vehicule> retrieveAllVehicules();
    Vehicule addVehicule( Vehicule c);
    Vehicule updateVehicule( Vehicule c);
    Vehicule retrieveVehicule(Long idVehicule);
    void removeVehicule(Long idVehicule);
    List< Vehicule> addVehicules (List< Vehicule>  Vehicules);
    public Vehicule affecterVehicule(Long idVehicule, Long idAgence);
    Vehicule affecterEquipement(Long idVehicule, Long idEquipement);

    Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement);

    // ---- Méthodes basées sur le DTO ----
    List<VehiculeDTO> retrieveAllVehiculesDTO();
    VehiculeDTO retrieveVehiculeDTO(Long idVehicule);
    VehiculeDTO addVehiculeDTO(VehiculeDTO dto);
    VehiculeDTO updateVehiculeDTO(VehiculeDTO dto);
    List<VehiculeDTO> vehiculesDisponiblesDTO();
    List<VehiculeDTO> vehiculesByAgenceDTO(Long idAgence);
}
