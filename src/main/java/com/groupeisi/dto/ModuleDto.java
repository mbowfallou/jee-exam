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
    private String professor_name;
    private int filiere_id;
    private String filiere_name;
}
