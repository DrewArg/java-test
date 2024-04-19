package com.javajr.evaluacion.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponse {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer cantidad;


}
