package com.javajr.evaluacion.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoInsertRequest {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer cantidad;

}
