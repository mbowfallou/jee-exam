package com.groupeisi.controller;

import com.groupeisi.dto.Filiere;
import com.groupeisi.service.FiliereService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/filieres")
@AllArgsConstructor
public class FiliereRestController {
    @Autowired
    private FiliereService filiereService;

    // Get All Modules
    @GetMapping
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Filiere> getModules(){
        return filiereService.getFilieres();
    }

    // Get One Filiere By its ID
    @GetMapping("/{id}")
    public Filiere getFiliere(@PathVariable("id") int id){
        return filiereService.getFiliere(id);
    }

    // Get One Filiere By its Name
    @GetMapping("/nom/{nom}")
    public Filiere getFiliereByName(@PathVariable("nom") String name){
        return filiereService.getFiliereByName(name);
    }

    // Save A Filiere
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Filiere createFiliere(@Valid @RequestBody Filiere filiere){
        return filiereService.createFiliere(filiere);
    }

    // Update A Filiere By its ID
    @PutMapping("/{id}")
    public Filiere updateFiliere(@PathVariable("id") int id, @Valid @RequestBody Filiere filiere){
        return filiereService.updateFiliere(id, filiere);
    }

    // Remove A Filiere By its ID
    @DeleteMapping("/{id}")
    public Filiere deleteFiliere(@PathVariable("id") int id){
        Filiere f = filiereService.getFiliere(id);
        try{
            filiereService.deleteFiliere(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
}
