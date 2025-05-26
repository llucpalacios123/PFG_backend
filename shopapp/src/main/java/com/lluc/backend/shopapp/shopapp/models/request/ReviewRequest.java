package com.lluc.backend.shopapp.shopapp.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Long productId; // ID del producto
    private int rating; // Calificación del producto (1-5)
    private String comment; // Comentario del usuario
}