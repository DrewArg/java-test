package com.javajr.evaluacion.service;

import com.javajr.evaluacion.exceptions.ExcepcionNegocio;
import com.javajr.evaluacion.model.ProductoModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductoService {
    ProductoModel crear(final ProductoModel productoModel) throws ExcepcionNegocio;
    ProductoModel actualizar(final ProductoModel productoModel) throws ExcepcionNegocio;
    void borrar(final Integer id);
    ProductoModel buscarPorId(final Integer id) throws ExcepcionNegocio;
    Page<ProductoModel> buscarPorNombre(final String nombre,Pageable pageable) throws ExcepcionNegocio;
    Page<ProductoModel> listarTodos(Pageable pageable);

    List<ProductoModel> mostrarTodosOrdenadosAscendentemente();
    List<ProductoModel> mostrarTodosOrdenadosDescendentemente();
}
