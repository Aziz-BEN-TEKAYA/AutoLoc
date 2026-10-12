package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IvehiculeService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicule")
public class VehiculeController {
    private final IvehiculeService VehiculeService;
    @PostMapping("/addVehicule")
    public Vehicule addVehicule(@RequestBody Vehicule c){
        return VehiculeService.addVehicule(c);
    }
    @GetMapping("/getVehicules")
    public List<Vehicule> getAllVehicules(){
        return VehiculeService.retrieveAllVehicules();
    }
    @GetMapping("/getVehiculeId/{idc}")
    public Vehicule getVehiculeById(@PathVariable long idc){
        return VehiculeService.retrieveVehicule(idc);
    }
    @DeleteMapping("/deleteVehicule/{idc}")
    public void deleteVehicule(@PathVariable long idc){
        VehiculeService.removeVehicule(idc);
    }

    @PutMapping("/updateVehicule")
    public Vehicule updateVehicule(@RequestBody Vehicule c){
        return VehiculeService.updateVehicule(c);
    }
}
