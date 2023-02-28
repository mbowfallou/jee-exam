package com.groupeisi.service;

import com.groupeisi.dao.IStudentRepository;
import com.groupeisi.dto.Student;
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
    private MessageSource messageSource;

    // Get All Students
    @Transactional(readOnly = true)
    public List<Student> getStudents() {
        int id = 0;
        return StreamSupport.stream(Optional.ofNullable(iStudentRepository.findAll()).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("appUser.notfound", new Object[]{id}, Locale.getDefault())))
                        .spliterator(), false)
                .map(studentMapper::toStudent)
                .collect(Collectors.toList());
    }

    // Get One Student By his ID(matricule)
    @Transactional(readOnly = true)
    public Student getStudent(Integer id) {
        return studentMapper.toStudent(iStudentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("appUserId.notfound", new Object[]{id},
                                Locale.getDefault()))));
    }

    // Get One Student By his Email
    @Transactional(readOnly = true)
    public Student getStudentByEmail(String email) {
        return studentMapper.toStudent(Optional.ofNullable(iStudentRepository.findByEmail(email))
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("appUserEmail.notfound", new Object[]{email},
                                Locale.getDefault()))));

    }

    // Get Many Students By a Lastname
    @Transactional(readOnly = true)
    public List<Student> getStudentByLastname(String lastname) {
        return StreamSupport.stream(Optional.ofNullable(iStudentRepository.findByNom(lastname)).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("appUserNom.notfound", new Object[]{lastname}, Locale.getDefault())))
                        .spliterator(), false)
                .map(studentMapper::toStudent)
                .collect(Collectors.toList());
    }

    // Get Many Students By a Firstname
    @Transactional(readOnly = true)
    public List<Student> getStudentByFirstname(String firstname) {
        return StreamSupport.stream(Optional.ofNullable(iStudentRepository.findByPrenom(firstname)).orElseThrow(() ->
                                new EntityNotFoundException(messageSource.getMessage("appUserPrenom.notfound", new Object[]{firstname}, Locale.getDefault())))
                        .spliterator(), false)
                .map(studentMapper::toStudent)
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
                .map(studentMapper::toStudent)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Student getStudentByEmailAndPassword(String email, String password) {
        return studentMapper.toStudent(Optional.ofNullable(iStudentRepository.findByEmailAndPassword(email, password))
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("appUserEmail.notfound", new Object[]{email},
                                Locale.getDefault()
                        )
                        )
                )
        );
    }

    @Transactional
    public Student createStudent(Student Student) {
        return studentMapper.toStudent(iStudentRepository.save(studentMapper.fromStudent(Student)));
    }

    @Transactional
    public Student updateStudent(Integer id, Student Student) {
        return iStudentRepository.findById(id)
                .map(entity -> {
                    Student.setId(id);
                    return studentMapper.toStudent(iStudentRepository.save(studentMapper.fromStudent(Student)));
                })
                .orElseThrow(
                        () -> new EntityNotFoundException(messageSource.getMessage("appUser.notfound", new Object[]{id}, Locale.getDefault()))
                );
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
