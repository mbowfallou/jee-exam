package com.groupeisi.mapping;

import com.groupeisi.dto.Filiere;
import com.groupeisi.entities.FiliereEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FiliereMapper {
    Filiere toFiliere(FiliereEntity filiereEntity);
    FiliereEntity fromFiliere(Filiere filiere);
}
