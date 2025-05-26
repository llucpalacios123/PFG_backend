package com.lluc.backend.shopapp.shopapp.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private Long id;
    private Long userId; // ID del usuario que creó la review
    private int rating; // Calificación
    private String comment; // Comentario
}