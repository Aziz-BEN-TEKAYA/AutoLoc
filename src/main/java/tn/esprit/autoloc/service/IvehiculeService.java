package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

public interface IvehiculeService {
    List<Vehicule> retrieveAllVehicules();
    Vehicule addVehicule( Vehicule c);
    Vehicule updateVehicule( Vehicule c);
    Vehicule retrieveVehicule(Long idVehicule);
    void removeVehicule(Long idVehicule);
    List< Vehicule> addVehicules (List< Vehicule>  Vehicules);
}
