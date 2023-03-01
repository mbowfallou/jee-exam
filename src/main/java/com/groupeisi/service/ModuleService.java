package com.groupeisi.service;

import com.groupeisi.dao.IFiliereRepository;
import com.groupeisi.dao.IModuleRepository;
import com.groupeisi.dto.ModuleDto;
import com.groupeisi.dto.Professor;
import com.groupeisi.entities.FiliereEntity;
import com.groupeisi.entities.ModuleEntity;
import com.groupeisi.entities.ProfessorEntity;
import com.groupeisi.exception.EntityAlreadyExistsException;
import com.groupeisi.exception.EntityNotFoundException;
import com.groupeisi.exception.RequestException;
import com.groupeisi.mapping.ModuleMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ModuleService {
    private IModuleRepository iModuleRepository;
    private ModuleMapper moduleMapper;
    private FiliereService filiereService;
    private ProfessorService professorService;
    private MessageSource messageSource;

    // Get All Modules
    @Transactional(readOnly = true)
    public List<ModuleDto> getModules() {
        int id = 0;
        return StreamSupport.stream(Optional.ofNullable(iModuleRepository.findAll()).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("module.notfound", new Object[]{id}, Locale.getDefault())))
                        .spliterator(), false)
                .map(moduleEntity -> {
                    ModuleDto mod = moduleMapper.toModule(moduleEntity);
                    mod.setProfessor_id(moduleEntity.getProfessor().getId());
                    mod.setFiliere_id(moduleEntity.getFiliere().getId());
                    return mod;
                })
                .collect(Collectors.toList());
    }

    // Get One Module By its ID
    @Transactional(readOnly = true)
    public ModuleDto getModule(Integer id) {
        return moduleMapper.toModule(iModuleRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("moduleId.notfound", new Object[]{id},
                                Locale.getDefault()))));
    }

    // Get One Module By its name
    @Transactional(readOnly = true)
    public List<ModuleDto> getModuleByName(String name) {
        return StreamSupport.stream(Optional.ofNullable(iModuleRepository.findByName(name)).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("moduleName.notfound", new Object[]{name}, Locale.getDefault())))
                        .spliterator(), false)
                .map(moduleMapper::toModule)
                .collect(Collectors.toList());

    }

    @Transactional
    public ModuleDto createModule(ModuleDto module) {
        int filiere_id = module.getFiliere_id();
        int prof_id = module.getProfessor_id();
        FiliereEntity fil = filiereService.getFiliereEntity(filiere_id);
        ProfessorEntity pro = professorService.getProfessorEntity(prof_id);

        List<ModuleEntity> mod = iModuleRepository.findByName(module.getName())
                .stream().filter(moduleEntity -> moduleEntity.getFiliere().getId() == filiere_id)
                .collect(Collectors.toList());
        try {
            if(mod.isEmpty()) {
                System.out.println("Module est NULL\n");
                ModuleEntity modEnt = moduleMapper.fromModule(module);

                modEnt.setFiliere(fil);
                modEnt.setProfessor(pro);

                modEnt = iModuleRepository.save(modEnt);

                module.setFiliere_id(modEnt.getFiliere().getId());
                module.setProfessor_id(modEnt.getProfessor().getId());
                module.setId(modEnt.getId());

                System.out.println(module);
            } else {
                throw new EntityAlreadyExistsException(messageSource.getMessage("moduleName.exists", new Object[]{module.getName()},
                        Locale.getDefault()));
            }
        } catch (Exception e) {
            throw new EntityAlreadyExistsException(messageSource.getMessage("moduleName.exists", new Object[]{module.getName()},
                    Locale.getDefault()));
        }

        return module;
    }

    @Transactional
    public ModuleDto updateModule(Integer id, ModuleDto module) {
        int filiere_id = module.getFiliere_id();
        int prof_id = module.getProfessor_id();
        FiliereEntity fil = filiereService.getFiliereEntity(filiere_id);
        ProfessorEntity pro = professorService.getProfessorEntity(prof_id);

        List<ModuleEntity> mod = iModuleRepository.findByName(module.getName())
                .stream().filter(moduleEntity -> moduleEntity.getFiliere().getId() == filiere_id)
                .collect(Collectors.toList());
        try {
            if(mod.isEmpty()) {
                ModuleEntity modEnt = moduleMapper.fromModule(module);

                modEnt.setFiliere(fil);
                modEnt.setProfessor(pro);

                //modEnt = iModuleRepository.save(modEnt);
                ModuleEntity t = iModuleRepository.findById(id)
                        .map(entity -> {
                            modEnt.setId(id);
                            return iModuleRepository.save(modEnt);
                        })
                        .orElseThrow(
                                () -> new EntityNotFoundException(messageSource.getMessage("moduleId.notfound", new Object[]{id}, Locale.getDefault()))
                        );

                module.setFiliere_id(t.getFiliere().getId());
                module.setProfessor_id(t.getProfessor().getId());
                module.setId(t.getId());
            } else {
                throw new EntityAlreadyExistsException(messageSource.getMessage("moduleName.exists", new Object[]{module.getName()},
                        Locale.getDefault()));
            }
        } catch (Exception e) {
            throw new EntityAlreadyExistsException(messageSource.getMessage("moduleName.exists", new Object[]{module.getName()},
                    Locale.getDefault()));
        }

        return module;
    }

    @Transactional
    public void deleteModule(Integer id) {
        try {
            iModuleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("module.errordeletion", new Object[] {id},
                    Locale.getDefault()), HttpStatus.CONFLICT);
        }
    }
}
