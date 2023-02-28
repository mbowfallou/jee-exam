package com.groupeisi.mapping;

import com.groupeisi.dto.Professor;
import com.groupeisi.entities.ProfessorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    Professor toProfessor(ProfessorEntity professorEntity);
    ProfessorEntity fromProfessor(Professor professor);
}
