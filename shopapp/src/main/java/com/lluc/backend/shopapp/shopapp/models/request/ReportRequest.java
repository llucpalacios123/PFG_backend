package com.lluc.backend.shopapp.shopapp.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequest {
    private Long productId; // ID del producto
    private String comment; // Detalles adicionales del reporte
}