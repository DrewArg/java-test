package com.javajr.evaluacion.mapper;
import com.javajr.evaluacion.controller.request.ProductoInsertRequest;
import com.javajr.evaluacion.controller.request.ProductoUpdateRequest;
import com.javajr.evaluacion.controller.response.ProductoResponse;
import com.javajr.evaluacion.model.ProductoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ProductoMapper {
    ProductoMapper instancia = Mappers.getMapper(ProductoMapper.class);
    ProductoResponse mapearAProductoResponse(ProductoModel productoModel);
    ProductoModel mapearAProductoModel(ProductoInsertRequest productoDto);
    ProductoModel mapearAProductoModel(ProductoUpdateRequest productoDto);
    List<ProductoResponse> mapearAListaProductoResponse(Collection<ProductoModel> productos);
}
