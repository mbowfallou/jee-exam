package com.groupeisi.controller;

import com.groupeisi.dto.ModuleDto;
import com.groupeisi.dto.Student;
import com.groupeisi.service.ModuleService;
import com.groupeisi.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/modules")
@AllArgsConstructor
public class ModuleRestController {
    @Autowired
    private ModuleService moduleService;

    // Get All Modules
    @GetMapping
    public List<ModuleDto> getModules(){
        return moduleService.getModules();
    }

    // Get One Module By its ID
    @GetMapping("/{id}")
    public ModuleDto getModule(@PathVariable("id") int id){
        return moduleService.getModule(id);
    }

    // Get One Module By its Name
    @GetMapping("/nom/{nom}")
    public List<ModuleDto> getModuleByName(@PathVariable("nom") String name){
        return moduleService.getModuleByName(name);
    }

    // Save A Module
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ModuleDto createModule(@Valid @RequestBody ModuleDto module){
        return moduleService.createModule(module);
    }

    // Update A Module By its ID
    @PutMapping("/{id}")
    public ModuleDto updateModule(@PathVariable("id") int id, @Valid @RequestBody ModuleDto module){
        return moduleService.updateModule(id, module);
    }

    // Remove A Module By its ID
    @DeleteMapping("/{id}")
    public void deleteModule(@PathVariable("id") int id){
        moduleService.deleteModule(id);
    }
}
