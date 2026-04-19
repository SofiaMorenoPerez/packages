package co.edu.unbosque.proyectomodulofirst.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.proyectomodulofirst.dto.EmpleadoManipuladorDTO;
import co.edu.unbosque.proyectomodulofirst.service.EmpleadoManipuladorService;

/**
 * Controlador REST para gestionar los empleados manipuladores.
 * Expone endpoints para crear, obtener, actualizar y eliminar manipuladores.
 */
@RestController
@RequestMapping("/manipulador")
@CrossOrigin(origins = {"http://localhost:8081", "*"})
public class EmpleadoManipuladorController {

    @Autowired
    private EmpleadoManipuladorService empleadoManipuladorServ;

    /**
     * Crea un nuevo empleado manipulador con los datos proporcionados.
     *
     * @param nombre nombre del manipulador
     * @param edad edad del manipulador
     * @param fechaInicio fecha de inicio del manipulador
     * @param tipoPaquete tipo de paquete que maneja el manipulador
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crearManipulador(@RequestParam String nombre, @RequestParam int edad,
            @RequestParam LocalDateTime fechaInicio, @RequestParam String tipoPaquete) {
        EmpleadoManipuladorDTO nuevo = new EmpleadoManipuladorDTO(nombre, edad, fechaInicio, tipoPaquete);
        int status = empleadoManipuladorServ.create(nuevo);
        if (status == 0) {
            return new ResponseEntity<String>("Manipulador creado con exito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Error al crear manipulador", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene la lista de todos los empleados manipuladores registrados.
     *
     * @return lista de manipuladores con estado HTTP correspondiente
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<EmpleadoManipuladorDTO>> obtenerTodo() {
        List<EmpleadoManipuladorDTO> lista = empleadoManipuladorServ.getAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<List<EmpleadoManipuladorDTO>>(lista, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<EmpleadoManipuladorDTO>>(lista, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Actualiza los datos de un empleado manipulador existente según su ID.
     *
     * @param id identificador del manipulador a actualizar
     * @param nombre nuevo nombre del manipulador
     * @param edad nueva edad del manipulador
     * @param fechaInicio nueva fecha de inicio del manipulador
     * @param tipoPaquete nuevo tipo de paquete asignado al manipulador
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @PutMapping("/actualizarmanipulador")
    public ResponseEntity<String> actualizarManipulador(@RequestParam Long id, @RequestParam String nombre,
            @RequestParam int edad, @RequestParam LocalDateTime fechaInicio,
            @RequestParam String tipoPaquete) {
        EmpleadoManipuladorDTO nuevo = new EmpleadoManipuladorDTO();
        nuevo.setNombre(nombre);
        nuevo.setEdad(edad);
        nuevo.setFechaInicio(fechaInicio);
        nuevo.setTipoDePaquete(tipoPaquete);
        int status = empleadoManipuladorServ.updateById(id, nuevo);
        if (status == 0) {
            return new ResponseEntity<String>("Manipulador actualizado con exito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Error al actualizar manipulador", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un empleado manipulador según su ID.
     *
     * @param id identificador del manipulador a eliminar
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @DeleteMapping("/eliminarmanipulador")
    public ResponseEntity<String> eliminarManipulador(@RequestParam Long id) {
        int status = empleadoManipuladorServ.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<String>("Manipulador eliminado con exito", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<String>("Error al eliminar manipulador", HttpStatus.BAD_REQUEST);
        }
    }
}