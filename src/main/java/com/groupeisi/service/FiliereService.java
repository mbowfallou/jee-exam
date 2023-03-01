package com.groupeisi.service;

import com.groupeisi.dao.IFiliereRepository;
import com.groupeisi.dao.IModuleRepository;
import com.groupeisi.dto.Filiere;
import com.groupeisi.entities.FiliereEntity;
import com.groupeisi.exception.EntityAlreadyExistsException;
import com.groupeisi.exception.EntityNotFoundException;
import com.groupeisi.exception.RequestException;
import com.groupeisi.mapping.FiliereMapper;
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
public class FiliereService {
    private IFiliereRepository iFiliereRepository;
    private FiliereMapper filiereMapper;
    private MessageSource messageSource;

    // Get All Filieres
    @Transactional(readOnly = true)
    public List<Filiere> getFilieres() {
        int id = 0;
        return StreamSupport.stream(Optional.ofNullable(iFiliereRepository.findAll()).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("filiere.notfound", new Object[]{id}, Locale.getDefault())))
                        .spliterator(), false)
                .map(filiereMapper::toFiliere)
                .collect(Collectors.toList());
    }

    // Get One Filiere By its ID
    @Transactional(readOnly = true)
    public Filiere getFiliere(Integer id) {
        return filiereMapper.toFiliere(iFiliereRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("filiereId.notfound", new Object[]{id},
                                Locale.getDefault()))));
    }

    // Get One FiliereEntity By its ID
    @Transactional(readOnly = true)
    public FiliereEntity getFiliereEntity(Integer id) {
        return iFiliereRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("filiereId.notfound", new Object[]{id},
                                Locale.getDefault())));
    }

    // Get One Filiere By its name
    @Transactional(readOnly = true)
    public Filiere getFiliereByName(String name) {
        return filiereMapper.toFiliere(Optional.ofNullable(iFiliereRepository.findByName(name))
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("filiereName.notfound", new Object[]{name},
                                Locale.getDefault()))));

    }

    @Transactional
    public Filiere createFiliere(Filiere filiere) {
        if(iFiliereRepository.findByName(filiere.getName()) != null) {
            throw new EntityAlreadyExistsException(messageSource.getMessage("filiereName.exists", new Object[]{filiere.getName()},
                    Locale.getDefault()));
        } else {
            return filiereMapper.toFiliere(iFiliereRepository.save(filiereMapper.fromFiliere(filiere)));
        }
    }

    @Transactional
    public Filiere updateFiliere(Integer id, Filiere filiere) {
        if(iFiliereRepository.findByName(filiere.getName()) != null) {
            throw new EntityAlreadyExistsException(messageSource.getMessage("filiereName.exists", new Object[]{filiere.getName()},
                    Locale.getDefault()));
        } else {
            return iFiliereRepository.findById(id)
                    .map(entity -> {
                        filiere.setId(id);
                        return filiereMapper.toFiliere(iFiliereRepository.save(filiereMapper.fromFiliere(filiere)));
                    })
                    .orElseThrow(
                            () -> new EntityNotFoundException(messageSource.getMessage("filiere.notfound", new Object[]{id}, Locale.getDefault()))
                    );
        }
    }

    @Transactional
    public void deleteFiliere(Integer id) {
        try {
            iFiliereRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("filiere.errordeletion", new Object[] {id},
                    Locale.getDefault()), HttpStatus.CONFLICT);
        }
    }
}
