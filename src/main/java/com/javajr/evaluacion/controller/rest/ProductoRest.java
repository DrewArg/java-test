package com.javajr.evaluacion.controller.rest;

import com.javajr.evaluacion.controller.request.ProductoInsertRequest;
import com.javajr.evaluacion.controller.request.ProductoUpdateRequest;
import com.javajr.evaluacion.controller.response.ProductoResponse;
import com.javajr.evaluacion.exceptions.ExcepcionNegocio;
import com.javajr.evaluacion.mapper.ProductoMapper;
import com.javajr.evaluacion.model.ProductoModel;
import com.javajr.evaluacion.service.ProductoService;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api")
public class ProductoRest {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoRest.class);

    private final ProductoService productoService;

    @Autowired
    public ProductoRest(ProductoService productoService) {
        this.productoService = productoService;
    }

    private final ProductoMapper productoMapper = ProductoMapper.instancia;

     /**
     * Listar todos los productos con paginado
     */

    @GetMapping(path = "/productos")
    public ResponseEntity<Page<ProductoResponse>> mostrarTodos(Pageable pageable){
        LOGGER.info("listar todos los productos paginados");
        LOGGER.info("Pageable: " + pageable);
        Page<ProductoResponse> productoResponse = null;
        Page<ProductoModel> productos = null;

        try{
            productos = productoService.listarTodos(pageable);
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        try{
            productoResponse = productos.map(productoMapper::mapearAProductoResponse);

        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(productoResponse, HttpStatus.OK);
    }

    /**
     * Buscar producto por nombre
     * @param nombre nombre del producto
     * @return retorna el producto
     */
    @GetMapping(path = "/productos/nombres/{nombre}")
    public ResponseEntity<Page<ProductoResponse>> mostrarProductosPorNombre(@PathVariable String nombre, Pageable pageable) {
        LOGGER.info("Mostrar productos por nombre");
        LOGGER.debug("Nombre: {}", nombre);
        LOGGER.debug("Pageable: {}", pageable);

        try {
            Page<ProductoModel> productos = productoService.buscarPorNombre(nombre, pageable);
            Page<ProductoResponse> productoResponse = productos.map(productoMapper::mapearAProductoResponse);
            return new ResponseEntity<>(productoResponse, HttpStatus.OK);

        } catch (ExcepcionNegocio e) {
            LOGGER.error("Error al buscar productos por nombre", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error("Error interno al buscar productos por nombre", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Buscar producto por id
     * @param id identificador del producto
     * @return retorna el producto
     */
    @GetMapping(path = "/productos/{id}")
    public ResponseEntity<Object> getProducto(@PathVariable Integer id) {
        LOGGER.info("Mostrar producto por ID: {}", id);

        try {
            ProductoModel productoModel = productoService.buscarPorId(id);
            ProductoResponse productoResponse = productoMapper.mapearAProductoResponse(productoModel);
            return new ResponseEntity<>(productoResponse, HttpStatus.OK);

        } catch (ExcepcionNegocio e) {
            LOGGER.error("Error al buscar producto por ID: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            LOGGER.error("Error interno al buscar producto por ID: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Mostrar todos los productos ordenados por precio ascendente
     */
@GetMapping(path = "/productos/precio/ascendente")
public ResponseEntity<List<ProductoResponse>> mostrarProductosPrecioAscendente(){
    LOGGER.info("Mostrar todos los productos ordenados por precio ascendete");
    return crearRespuesta(productoService.mostrarTodosOrdenadosAscendentemente());
}

    /**
     * Mostrar todos los productos ordenados por precio descendente
     */
    @GetMapping(path = "/productos/precio/descendente")
    public ResponseEntity<List<ProductoResponse>> mostrarProductosPrecioDescendente(){
        LOGGER.info("Mostrar todos los productos ordenados por precio descendente");
        return crearRespuesta(productoService.mostrarTodosOrdenadosDescendentemente());
    }
    /**
     * Guardar un nuevo producto
     * @param datosProducto son los datos para un nuevo producto
     * @return un producto nuevo
     */
    @PostMapping(path = "/productos")
    public ResponseEntity<ProductoResponse> crearProducto(@RequestBody ProductoInsertRequest datosProducto) {
        LOGGER.info("Crear nuevo producto: {}", datosProducto);

        try {
            ProductoModel productoModel = productoMapper.mapearAProductoModel(datosProducto);
            productoModel = productoService.crear(productoModel);
            ProductoResponse productoResponse = productoMapper.mapearAProductoResponse(productoModel);
            LOGGER.debug("Producto creado: {}", productoResponse);
            return new ResponseEntity<>(productoResponse, HttpStatus.CREATED);

        } catch (Exception e) {
            LOGGER.error("Error al crear nuevo producto", e);
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Modificar los datos de un producto
     *
     * @param id identificador de un producto
     * @param datosProducto datos a modificar del producto
     * @return los datos de un producto modificado
     */
    @PutMapping("/productos/{id}")
    public ResponseEntity<Object> actualizarProducto(@PathVariable("id") Integer id, @RequestBody ProductoUpdateRequest datosProducto){
        LOGGER.info("Actualizar producto con ID: {}", id);

        try{
            ProductoModel productoNuevo = productoMapper.mapearAProductoModel(datosProducto);
            ProductoModel productoAModificar = productoService.buscarPorId(id);

            if (Objects.isNull(productoAModificar)) {
                LOGGER.error("Producto a modificar es nulo");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            productoAModificar.setNombre(productoNuevo.getNombre());
            productoAModificar.setDescripcion(productoNuevo.getDescripcion());
            productoAModificar.setPrecio(productoNuevo.getPrecio());
            productoAModificar.setCantidad(productoNuevo.getCantidad());

            productoAModificar = productoService.actualizar(productoAModificar);
            ProductoResponse productoResponse = productoMapper.mapearAProductoResponse(productoAModificar);
            return new ResponseEntity<>(productoResponse, HttpStatus.CREATED);

        } catch (ExcepcionNegocio e) {
            LOGGER.error("Error al actualizar producto con ID: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);

        } catch (Exception e) {
            LOGGER.error("Error interno al actualizar producto con ID: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
    /**
     * Borrado de un producto
     * @param id identificador de un producto
     *
     */
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<HttpStatus> borrarProducto(@PathVariable("id") Integer id){
        try{
            productoService.borrar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    private ResponseEntity<List<ProductoResponse>> crearRespuesta(List<ProductoModel> productos){
        List<ProductoResponse> productoResponse = productos.stream()
                .map(productoMapper::mapearAProductoResponse)
                .toList();

        return new ResponseEntity<>(productoResponse,HttpStatus.OK);
    }
}

