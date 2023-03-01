package com.groupeisi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto {
    private Integer id;
    private String name;
    private String jours;
    private int credit;
    private int quantum;
    private String docs;
    private int professor_id;
    private int filiere_id;
}
