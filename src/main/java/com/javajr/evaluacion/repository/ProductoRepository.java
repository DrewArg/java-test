package com.javajr.evaluacion.repository;

import com.javajr.evaluacion.model.ProductoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel,Integer> {
    @Query("SELECT producto FROM ProductoModel producto WHERE producto.nombre = :nombre")

    Page<ProductoModel> buscarPorNombre(String nombre, Pageable pageable);

}
