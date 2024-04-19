package com.javajr.evaluacion.service;

import com.javajr.evaluacion.exceptions.ExcepcionNegocio;
import com.javajr.evaluacion.model.ProductoModel;
import com.javajr.evaluacion.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImplementacion implements ProductoService{
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoServiceImplementacion.class);
    private final ProductoRepository productoRepository;
    public ProductoServiceImplementacion(final ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    @Override
    public ProductoModel crear(final ProductoModel productoModel) throws ExcepcionNegocio{
        LOGGER.debug("Creando el producto: " + productoModel.toString());
        if(productoModel.getIdProducto() == null){
            ProductoModel productoCreado = productoRepository.save(productoModel);
            if (productoCreado.getIdProducto() != null) {
                return productoCreado;
            } else {
                throw new ExcepcionNegocio("No se pudo crear el producto");
            }
        }
        throw new ExcepcionNegocio("No se puede crear el producto");
    }

    @Override
    public ProductoModel actualizar(final ProductoModel productoModel) throws ExcepcionNegocio {
    LOGGER.debug("Actualizacion del producto: " + productoModel.toString());
    if(productoModel.getIdProducto() != null){
        return productoRepository.save(productoModel);
    }
    throw new ExcepcionNegocio("No se puede modificar un producto que aún no existe");
    }

    @Override
    public void borrar(final Integer id) {
    LOGGER.debug("Borrando el producto con id: " + id);
    productoRepository.deleteById(id);
    }

    @Override
    public ProductoModel buscarPorId(Integer id) throws ExcepcionNegocio {
       LOGGER.debug("Buscando el producto por id: " + id);
        Optional<ProductoModel> productoModelOptional = productoRepository.findById(id);
        if(productoModelOptional.isPresent()){
            return productoModelOptional.get();
        }
        throw new ExcepcionNegocio("No se encontro el producto con el id: " + id);
    }

    @Override
    public Page<ProductoModel> buscarPorNombre(String nombre,Pageable pageable) throws ExcepcionNegocio {
        LOGGER.debug("Buscando el producto por nombre: " + nombre);
        LOGGER.debug("Pageable offset: " + pageable.getOffset()+ " pagesize: " + pageable.getPageSize()+" and pageNumber: " + pageable.getPageNumber());

        Page<ProductoModel> productos = productoRepository.buscarPorNombre(nombre,pageable);
        if(!productos.isEmpty()){
            return productos;
        }
        throw new ExcepcionNegocio("No se encontraron productos con el nombre: " + nombre);
    }


    @Override
    public Page<ProductoModel> listarTodos(Pageable pageable) {
        LOGGER.debug("Listado de todos los productos por paginas");
        LOGGER.debug("Pageable offset: " + pageable.getOffset()+ " pagesize: " + pageable.getPageSize()+" and pageNumber: " + pageable.getPageNumber());
        return productoRepository.findAll(pageable);

    }


    @Override
   public List<ProductoModel> mostrarTodosOrdenadosAscendentemente(){
        List<ProductoModel> productos = productoRepository.findAll();
        productos.sort((Comparator.comparing(ProductoModel::getPrecio)));
        return productos;
}
    @Override
    public List<ProductoModel> mostrarTodosOrdenadosDescendentemente(){
        List<ProductoModel> productos = productoRepository.findAll();
        productos.sort((Comparator.comparing(ProductoModel::getPrecio).reversed()));
        return productos;
    }

}
