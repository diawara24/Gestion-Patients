package org.sid.springmvc.controller;

import org.sid.springmvc.dao.PatientRepository;
import org.sid.springmvc.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
public class PatientController {
    @Autowired
    PatientRepository patientRepository;

    @GetMapping(path = "/patients")
    public String list(Model modele,
           @RequestParam(name="page", defaultValue = "0") int page,
           @RequestParam(name="size", defaultValue = "5") int size,
           @RequestParam(name = "keyword", defaultValue = "") String mc){
        Page<Patient> pagePatients = patientRepository.findByNameContains(mc, PageRequest.of(page, size));
        modele.addAttribute("patients", pagePatients.getContent());
        modele.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        modele.addAttribute("currentPage", page);
        modele.addAttribute("size", size);
        modele.addAttribute("keyword", mc);
        return "patients";
    }

    @GetMapping(path = "/deletePatient")
    public String deletePatient(Long id, String keyword, int page, int size){
        patientRepository.deleteById(id);
        return "redirect:/patients?page="+page+"&size="+size+"&keyword="+keyword;
    }

    @GetMapping(path = "/formPatient")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        model.addAttribute("mode","new");
        return "formPatient";
    }

    @PostMapping("/savePatient")
    @Transactional
    public String ajoutPatient(@Valid  Patient patient, BindingResult bindingResult, Model model){
        //Verifier si le formulaire est correct
        if (bindingResult.hasErrors()) return "formPatient";

        patientRepository.save(patient);
        model.addAttribute("patient", patient);
        return "confirmation";
    }
    @GetMapping("/editPatient")
    public String editPatient(Long id, Model model){
        Patient patient = patientRepository.findById(id).get();
        model.addAttribute("patient", patient);
        model.addAttribute("mode","update");
        return "formPatient";
    }

}
