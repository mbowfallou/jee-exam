package com.groupeisi.service;

import com.groupeisi.dao.IStudentRepository;
import com.groupeisi.dto.Professor;
import com.groupeisi.dto.Student;
import com.groupeisi.entities.*;
import com.groupeisi.exception.EntityNotFoundException;
import com.groupeisi.exception.RequestException;
import com.groupeisi.mapping.StudentMapper;
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
public class StudentService {
    private IStudentRepository iStudentRepository;
    private StudentMapper studentMapper;
    private FiliereService filiereService;
    private MessageSource messageSource;

    // Get All Students
    @Transactional(readOnly = true)
    public List<Student> getStudents() {
        int id = 0;
        return StreamSupport.stream(Optional.ofNullable(iStudentRepository.findAll()).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("appUser.notfound", new Object[]{id}, Locale.getDefault())))
                        .spliterator(), false)
                .map(studentEntity -> {
                    Student std = studentMapper.toStudent(studentEntity);
                    std.setFiliere_id(studentEntity.getFiliere().getId());
                    std.setFiliere_name(studentEntity.getFiliere().getName());
                    return std;
                })
                .collect(Collectors.toList());
    }

    // Get One Student By his ID(matricule)
    @Transactional(readOnly = true)
    public Student getStudent(Integer id) {
        StudentEntity stdEnt = iStudentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("appUserId.notfound", new Object[]{id},
                                Locale.getDefault())));

        Student stdDto = studentMapper.toStudent(stdEnt);
        stdDto.setFiliere_id(stdEnt.getFiliere().getId());
        stdDto.setFiliere_name(stdEnt.getFiliere().getName());

        return stdDto;
    }

    // Get One Student By his Email
    @Transactional(readOnly = true)
    public Student getStudentByEmail(String email) {
        StudentEntity stdEnt = Optional.ofNullable(iStudentRepository.findByEmail(email))
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("appUserEmail.notfound", new Object[]{email},
                                Locale.getDefault())));

        Student stdDto = studentMapper.toStudent(stdEnt);
        stdDto.setFiliere_id(stdEnt.getFiliere().getId());
        stdDto.setFiliere_name(stdEnt.getFiliere().getName());

        return stdDto;
    }

    // Get Many Students By a Lastname
    @Transactional(readOnly = true)
    public List<Student> getStudentByLastname(String lastname) {
        return StreamSupport.stream(Optional.ofNullable(iStudentRepository.findByNom(lastname)).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("appUserNom.notfound", new Object[]{lastname}, Locale.getDefault())))
                        .spliterator(), false)
                .map(studentEntity -> {
                    Student std = studentMapper.toStudent(studentEntity);
                    std.setFiliere_id(studentEntity.getFiliere().getId());
                    std.setFiliere_name(studentEntity.getFiliere().getName());
                    return std;
                })
                .collect(Collectors.toList());
    }

    // Get Many Students By a Firstname
    @Transactional(readOnly = true)
    public List<Student> getStudentByFirstname(String firstname) {
        return StreamSupport.stream(Optional.ofNullable(iStudentRepository.findByPrenom(firstname)).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("appUserPrenom.notfound", new Object[]{firstname}, Locale.getDefault())))
                        .spliterator(), false)
                .map(studentEntity -> {
                    Student std = studentMapper.toStudent(studentEntity);
                    std.setFiliere_id(studentEntity.getFiliere().getId());
                    std.setFiliere_name(studentEntity.getFiliere().getName());
                    return std;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Student> getStudentByPrenomAndNom(String firstname, String lastname) {
        return StreamSupport.stream(Optional.ofNullable(iStudentRepository.findByPrenomAndNom(firstname, lastname))
                        .orElseThrow(() -> new EntityNotFoundException(
                                        messageSource.getMessage("appUserPrenomNom.notfound", new Object[]{firstname, lastname}, Locale.getDefault())
                                )
                        )
                        .spliterator(), false)
                .map(studentEntity -> {
                    Student std = studentMapper.toStudent(studentEntity);
                    std.setFiliere_id(studentEntity.getFiliere().getId());
                    std.setFiliere_name(studentEntity.getFiliere().getName());
                    return std;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Student getStudentByEmailAndPassword(String email, String password) {
        return Optional.ofNullable(iStudentRepository.findByEmailAndPassword(email, password))
                .map(studentEntity -> {
                    Student etd = studentMapper.toStudent(studentEntity);
                    //etd.setAppRoles(studentEntity.getAppRoles().getNom());
                    return etd;
                })
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("appUserEmail.notfound", new Object[]{email},
                                Locale.getDefault()
                        )
                        )
                );
    }

    @Transactional
    public Student createStudent(Student student) {
        FiliereEntity fil = filiereService.getFiliereEntity(student.getFiliere_id());
        StudentEntity stdEnt = studentMapper.fromStudent(student);
        stdEnt.setFiliere(fil);
        // T O  C H A N G E
        stdEnt.setClasse(Classe.Premiere);

        stdEnt = iStudentRepository.save(stdEnt);
        student = studentMapper.toStudent(stdEnt);
        student.setFiliere_id(stdEnt.getFiliere().getId());

        return student;
    }

    @Transactional
    public Student updateStudent(Integer id, Student student) {
        FiliereEntity fil = filiereService.getFiliereEntity(student.getFiliere_id());
        StudentEntity stdEnt = studentMapper.fromStudent(student);
        stdEnt.setFiliere(fil);
        // T O  C H A N G E
        stdEnt.setClasse(Classe.Premiere);

        StudentEntity finalStdEnt = stdEnt;
        stdEnt = iStudentRepository.findById(id)
                .map(entity -> {
                    finalStdEnt.setId(id);
                    return iStudentRepository.save(finalStdEnt);
                })
                .orElseThrow(
                        () -> new EntityNotFoundException(messageSource.getMessage("appUser.notfound", new Object[]{id}, Locale.getDefault()))
                );

        student = studentMapper.toStudent(stdEnt);
        student.setFiliere_id(stdEnt.getFiliere().getId());

        return student;
    }

    @Transactional
    public void deleteStudent(Integer id) {
        try {
            iStudentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("appUser.errordeletion", new Object[] {id},
                    Locale.getDefault()), HttpStatus.CONFLICT);
        }
    }
}
