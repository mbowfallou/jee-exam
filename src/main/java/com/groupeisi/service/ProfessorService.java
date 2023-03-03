package com.groupeisi.service;

import com.groupeisi.dao.IProfessorRepository;
import com.groupeisi.dto.Professor;
import com.groupeisi.entities.ProfessorEntity;
import com.groupeisi.exception.EntityNotFoundException;
import com.groupeisi.exception.RequestException;
import com.groupeisi.mapping.ProfessorMapper;
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
public class ProfessorService {
    private IProfessorRepository iProfessorRepository;
    private ProfessorMapper professorMapper;
    private FiliereService filiereService;
    private MessageSource messageSource;

    // Get All Professors
    @Transactional(readOnly = true)
    public List<Professor> getProfessors() {
        int id = 0;
        return StreamSupport.stream(Optional.ofNullable(iProfessorRepository.findAll()).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("prof.notfound", new Object[]{id}, Locale.getDefault())))
                        .spliterator(), false)
                .map(professorMapper::toProfessor)
                .collect(Collectors.toList());
    }

    // Get One Professor By his ID(matricule)
    @Transactional(readOnly = true)
    public Professor getProfessor(Integer id) {
        return professorMapper.toProfessor(iProfessorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("profId.notfound", new Object[]{id},
                                Locale.getDefault()))));
    }

    // Get One ProfessorEntity By his ID(matricule)
    @Transactional(readOnly = true)
    public ProfessorEntity getProfessorEntity(Integer id) {
        return iProfessorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("profId.notfound", new Object[]{id},
                                Locale.getDefault())));
    }

    // Get One Professor By his Email
    @Transactional(readOnly = true)
    public Professor getProfessorByEmail(String email) {
        return professorMapper.toProfessor(Optional.ofNullable(iProfessorRepository.findByEmail(email))
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("profEmail.notfound", new Object[]{email},
                                Locale.getDefault()))));

    }

    // Get Many Professors By a Lastname
    @Transactional(readOnly = true)
    public List<Professor> getProfessorByLastname(String lastname) {
        return StreamSupport.stream(Optional.ofNullable(iProfessorRepository.findByNom(lastname)).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("profNom.notfound", new Object[]{lastname}, Locale.getDefault())))
                        .spliterator(), false)
                .map(professorMapper::toProfessor)
                .collect(Collectors.toList());
    }

    // Get Many Professors By a Firstname
    @Transactional(readOnly = true)
    public List<Professor> getProfessorByFirstname(String firstname) {
        return StreamSupport.stream(Optional.ofNullable(iProfessorRepository.findByPrenom(firstname)).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("profPrenom.notfound", new Object[]{firstname}, Locale.getDefault())))
                        .spliterator(), false)
                .map(professorMapper::toProfessor)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Professor> getProfessorByPrenomAndNom(String firstname, String lastname) {
        return StreamSupport.stream(Optional.ofNullable(iProfessorRepository.findByPrenomAndNom(firstname, lastname))
                        .orElseThrow(() -> new EntityNotFoundException(
                                        messageSource.getMessage("profPrenomNom.notfound", new Object[]{firstname, lastname}, Locale.getDefault())
                                )
                        )
                        .spliterator(), false)
                .map(professorMapper::toProfessor)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Professor getProfessorByEmailAndPassword(String email, String password) {
        return professorMapper.toProfessor(Optional.ofNullable(iProfessorRepository.findByEmailAndPassword(email, password))
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("profEmail.notfound", new Object[]{email},
                                Locale.getDefault()
                        )
                        )
                )
        );
    }

    @Transactional
    public Professor createProfessor(Professor Professor) {
        return professorMapper.toProfessor(iProfessorRepository.save(professorMapper.fromProfessor(Professor)));
    }

    @Transactional
    public Professor updateProfessor(Integer id, Professor Professor) {
        return iProfessorRepository.findById(id)
                .map(entity -> {
                    Professor.setId(id);
                    return professorMapper.toProfessor(iProfessorRepository.save(professorMapper.fromProfessor(Professor)));
                })
                .orElseThrow(
                        () -> new EntityNotFoundException(messageSource.getMessage("prof.notfound", new Object[]{id}, Locale.getDefault()))
                );
    }

    @Transactional
    public void deleteProfessor(Integer id) {
        try {
            iProfessorRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("prof.errordeletion", new Object[] {id},
                    Locale.getDefault()), HttpStatus.CONFLICT);
        }
    }
}
