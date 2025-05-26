package com.lluc.backend.shopapp.shopapp.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDTO {
    private Long id;
    private Long userId; // ID del usuario que creó el reporte
    private String comment; // Comentario del reporte
}