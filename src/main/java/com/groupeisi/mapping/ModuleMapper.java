package com.groupeisi.mapping;

import com.groupeisi.dto.ModuleDto;
import com.groupeisi.entities.ModuleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModuleMapper {
    //ModuleMapper MAPPER = Mappers.getMapper(ModuleMapper.class);
    //@Mapping(target="professor", source = "professorEntity")
    ModuleDto toModule(ModuleEntity moduleEntity);
    //@Mapping(target="professorEntity", source="professor")
    ModuleEntity fromModule(ModuleDto module);
}
