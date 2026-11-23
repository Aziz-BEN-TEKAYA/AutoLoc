package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IvehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeDTO;


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

    @PutMapping("/affecterVehicule/{idVehicule}/{idAgence}")
    public Vehicule affecterVehicule(@PathVariable Long idVehicule, @PathVariable Long idAgence){
        return VehiculeService.affecterVehicule(idVehicule, idAgence);
    }

    @PutMapping("/affecterEquipement/{idVehicule}/{idEquipement}")
    public Vehicule affecterEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement){
        return VehiculeService.affecterEquipement(idVehicule, idEquipement);
    }

    @PutMapping("/desaffecterEquipement/{idVehicule}/{idEquipement}")
    public Vehicule desaffecterEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement){
        return VehiculeService.desaffecterEquipement(idVehicule, idEquipement);
    }

    // ===================== Endpoints DTO =====================

    @GetMapping("/dto/getVehicules")
    public List<VehiculeDTO> getAllVehiculesDTO(){
        return VehiculeService.retrieveAllVehiculesDTO();
    }

    @GetMapping("/dto/getVehiculeId/{idVehicule}")
    public VehiculeDTO getVehiculeDTOById(@PathVariable Long idVehicule){
        return VehiculeService.retrieveVehiculeDTO(idVehicule);
    }

    @PostMapping("/dto/addVehicule")
    public VehiculeDTO addVehiculeDTO(@RequestBody VehiculeDTO dto){
        return VehiculeService.addVehiculeDTO(dto);
    }

    @PutMapping("/dto/updateVehicule")
    public VehiculeDTO updateVehiculeDTO(@RequestBody VehiculeDTO dto){
        return VehiculeService.updateVehiculeDTO(dto);
    }

    @GetMapping("/dto/disponibles")
    public List<VehiculeDTO> getVehiculesDisponiblesDTO(){
        return VehiculeService.vehiculesDisponiblesDTO();
    }

    @GetMapping("/dto/agence/{idAgence}")
    public List<VehiculeDTO> getVehiculesByAgenceDTO(@PathVariable Long idAgence){
        return VehiculeService.vehiculesByAgenceDTO(idAgence);
    }
}
