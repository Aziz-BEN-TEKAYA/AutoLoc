package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IemployeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employe")
public class EmployeController {
    private final IemployeService employeService;
    @PostMapping("/addEmploye")
    public Employe addEmploye(@RequestBody Employe c){
        return employeService.addEmploye(c);
    }
    @GetMapping("/getEmployes")
    public List<Employe> getAllEmployes(){
        return employeService.retrieveAllEmployes();
    }
    @GetMapping("/getEmployeId/{idc}")
    public Employe getEmployeById(@PathVariable long idc){
        return employeService.retrieveEmploye(idc);
    }
    @DeleteMapping("/deleteEmploye/{idc}")
    public void deleteEmploye(@PathVariable long idc){
        employeService.removeEmploye(idc);
    }

    @PutMapping("/updateEmploye")
    public Employe updateEmploye(@RequestBody Employe c){
        return employeService.updateEmploye(c);
    }
}
