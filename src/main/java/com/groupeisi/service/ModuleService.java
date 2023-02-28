package com.groupeisi.service;

import com.groupeisi.dao.IModuleRepository;
import com.groupeisi.dto.ModuleDto;
import com.groupeisi.dto.Professor;
import com.groupeisi.entities.ModuleEntity;
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
    private MessageSource messageSource;

    // Get All Modules
    @Transactional(readOnly = true)
    public List<ModuleDto> getModules() {
        int id = 0;
        return StreamSupport.stream(Optional.ofNullable(iModuleRepository.findAll()).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("module.notfound", new Object[]{id}, Locale.getDefault())))
                        .spliterator(), false)
                .map(moduleMapper::toModule)
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
    public ModuleDto getModuleByName(String name) {
        return moduleMapper.toModule(Optional.ofNullable(iModuleRepository.findByName(name))
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("moduleName.notfound", new Object[]{name},
                                Locale.getDefault()))));

    }

    @Transactional
    public ModuleDto createModule(ModuleDto module) {
        System.out.println(module);
        ModuleEntity mod = moduleMapper.fromModule(module);
        return moduleMapper.toModule(iModuleRepository.save(moduleMapper.fromModule(module)));
    }

    @Transactional
    public ModuleDto updateModule(Integer id, ModuleDto module) {
        return iModuleRepository.findById(id)
                .map(entity -> {
                    module.setId(id);
                    return moduleMapper.toModule(iModuleRepository.save(moduleMapper.fromModule(module)));
                })
                .orElseThrow(
                        () -> new EntityNotFoundException(messageSource.getMessage("moduleId.notfound", new Object[]{id}, Locale.getDefault()))
                );
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
