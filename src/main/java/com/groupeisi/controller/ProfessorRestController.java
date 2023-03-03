package com.groupeisi.controller;

import com.groupeisi.dto.Professor;
import com.groupeisi.service.ProfessorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/profs")
@AllArgsConstructor
public class ProfessorRestController {
    @Autowired
    private ProfessorService professorService;

    // Get All Students
    @GetMapping
    public List<Professor> getProfessors(){
        return professorService.getProfessors();
    }

    // Get One Professor By his ID
    @GetMapping("/{id}")
    public Professor getProfessor(@PathVariable("id") int id){
        return professorService.getProfessor(id);
    }

    // Get One Professor By his Firstname
    @GetMapping("/prenom/{prenom}")
    public List<Professor> getProfessorByFirstname(@PathVariable("prenom") String firstname){
        return professorService.getProfessorByFirstname(firstname);
    }

    // Get One Professor By his Lastname
    @GetMapping("/nom/{nom}")
    public List<Professor> getProfessorByLastname(@PathVariable("nom") String lastname){
        return professorService.getProfessorByLastname(lastname);
    }

    // Get One Professor By his Email
    @GetMapping("/email/{email}")
    public Professor getProfessorByEmail(@PathVariable("email") String email){
        return professorService.getProfessorByEmail(email);
    }

    // Get One Professor By First and Last Name
    @GetMapping("/nomcomplet/{prenom}/{nom}")
    public List<Professor> getProfessorByPrenomAndNom(@PathVariable("prenom") String firstname, @PathVariable("nom") String lastname){
        return professorService.getProfessorByPrenomAndNom(firstname, lastname);
    }

    // Get One Professor By Email and Password
    @GetMapping("/account/{email}/{password}")
    public Professor getProfessorByEmailAndPassword(@PathVariable("email") String email, @PathVariable(value = "", name = "password") String password){
        return professorService.getProfessorByEmailAndPassword(email, password);
    }

    // Save A User
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Professor createProfessor(@Valid @RequestBody Professor appUser){
        return professorService.createProfessor(appUser);
    }

    // Update A Professor By his ID
    @PutMapping("/{id}")
    public Professor updateProfessor(@PathVariable("id") int id, @Valid @RequestBody Professor appUser){
        return professorService.updateProfessor(id, appUser);
    }

    // Remove A Professor By his ID
    @DeleteMapping("/{id}")
    public void deleteProfessor(@PathVariable("id") int id){
        professorService.deleteProfessor(id);
    }
}
