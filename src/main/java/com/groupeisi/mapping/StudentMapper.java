package com.groupeisi.mapping;

import com.groupeisi.dto.Student;
import com.groupeisi.entities.StudentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toStudent(StudentEntity studentEntity);
    StudentEntity fromStudent(Student student);
}
