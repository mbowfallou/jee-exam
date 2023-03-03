package com.groupeisi.controller;

import com.groupeisi.dto.Student;
import com.groupeisi.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentRestController {
    @Autowired
    private StudentService studentService;

    // Get All Students
    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    // Get One Student By his ID
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") int id){
        return studentService.getStudent(id);
    }

    // Get One Student By his Firstname
    @GetMapping("/prenom/{prenom}")
    public List<Student> getStudentByFirstname(@PathVariable("prenom") String firstname){
        return studentService.getStudentByFirstname(firstname);
    }

    // Get One Student By his Lastname
    @GetMapping("/nom/{nom}")
    public List<Student> getStudentByLastname(@PathVariable("nom") String lastname){
        return studentService.getStudentByLastname(lastname);
    }

    // Get One Student By his Email
    @GetMapping("/email/{email}")
    public Student getStudentByEmail(@PathVariable("email") String email){
        return studentService.getStudentByEmail(email);
    }

    // Get One Student By First and Last Name
    @GetMapping("/nomcomplet/{prenom}/{nom}")
    public List<Student> getStudentByPrenomAndNom(@PathVariable("prenom") String firstname, @PathVariable("nom") String lastname){
        return studentService.getStudentByPrenomAndNom(firstname, lastname);
    }

    // Get One Student By Email and Password
    @GetMapping("/account/{email}/{password}")
    public Student getStudentByEmailAndPassword(@PathVariable("email") String email, @PathVariable(value = "", name = "password") String password){
        return studentService.getStudentByEmailAndPassword(email, password);
    }

    // Save A User
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Student createStudent(@Valid @RequestBody Student appUser){
        return studentService.createStudent(appUser);
    }

    // Update A Student By his ID
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") int id, @Valid @RequestBody Student appUser){
        return studentService.updateStudent(id, appUser);
    }

    // Remove A Student By his ID
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") int id){
        studentService.deleteStudent(id);
    }
}
