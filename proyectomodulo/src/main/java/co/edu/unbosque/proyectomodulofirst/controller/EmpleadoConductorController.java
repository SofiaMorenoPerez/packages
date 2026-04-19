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
import co.edu.unbosque.proyectomodulofirst.dto.EmpleadoConductorDTO;
import co.edu.unbosque.proyectomodulofirst.service.EmpleadoConductorService;

/**
 * Controlador REST para gestionar los empleados conductores.
 * Expone endpoints para crear, obtener, actualizar y eliminar conductores.
 */
@RestController
@RequestMapping("/conductor")
@CrossOrigin(origins = {"http://localhost:8081", "*"})
public class EmpleadoConductorController {

    @Autowired
    private EmpleadoConductorService empleadoConductorServ;

    /**
     * Crea un nuevo empleado conductor con los datos proporcionados.
     *
     * @param nombre nombre del conductor
     * @param edad edad del conductor
     * @param fechaInicio fecha de inicio del conductor
     * @param tipoVehiculo tipo de vehículo asignado al conductor
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @PostMapping("/crearconductor")
    public ResponseEntity<String> crearConductor(@RequestParam String nombre, @RequestParam int edad,
            @RequestParam LocalDateTime fechaInicio, @RequestParam String tipoVehiculo) {
        EmpleadoConductorDTO nuevo = new EmpleadoConductorDTO(nombre, edad, fechaInicio, tipoVehiculo);
        int status = empleadoConductorServ.create(nuevo);
        if (status == 0) {
            return new ResponseEntity<>("Conductor creado correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("No se pudo crear el conductor ", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene la lista de todos los empleados conductores registrados.
     *
     * @return lista de conductores con estado HTTP correspondiente
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<EmpleadoConductorDTO>> obtenerTodo() {
        List<EmpleadoConductorDTO> lista = empleadoConductorServ.getAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<List<EmpleadoConductorDTO>>(lista, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<EmpleadoConductorDTO>>(lista, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Actualiza los datos de un empleado conductor existente según su ID.
     *
     * @param id identificador del conductor a actualizar
     * @param nombre nuevo nombre del conductor
     * @param edad nueva edad del conductor
     * @param fechaInicio nueva fecha de inicio del conductor
     * @param tipoVehiculo nuevo tipo de vehículo asignado al conductor
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @PutMapping("/actualizarconductor")
    public ResponseEntity<String> actualizarConductor(@RequestParam Long id, @RequestParam String nombre,
            @RequestParam int edad, @RequestParam LocalDateTime fechaInicio,
            @RequestParam String tipoVehiculo) {
        EmpleadoConductorDTO nuevo = new EmpleadoConductorDTO();
        nuevo.setNombre(nombre);
        nuevo.setEdad(edad);
        nuevo.setFechaInicio(fechaInicio);
        nuevo.setTipoVehiculo(tipoVehiculo);
        int status = empleadoConductorServ.updateById(id, nuevo);
        if (status == 0) {
            return new ResponseEntity<String>("Conductor actualizado con exito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Error al actualizar conductor", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un empleado conductor según su ID.
     *
     * @param id identificador del conductor a eliminar
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @DeleteMapping("/eliminarconductor")
    public ResponseEntity<String> eliminarConductor(@RequestParam Long id) {
        int status = empleadoConductorServ.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<String>("Conductor eliminado con exito", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<String>("Error al eliminar conductor", HttpStatus.BAD_REQUEST);
        }
    }
}