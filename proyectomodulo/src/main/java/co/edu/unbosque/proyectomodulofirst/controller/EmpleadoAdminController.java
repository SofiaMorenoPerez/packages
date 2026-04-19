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
import co.edu.unbosque.proyectomodulofirst.dto.EmpleadoAdminDTO;
import co.edu.unbosque.proyectomodulofirst.service.EmpleadoAdminService;

/**
 * Controlador REST para gestionar los empleados administrativos.
 * Expone endpoints para crear, obtener, actualizar y eliminar administrativos.
 */
@RestController
@RequestMapping("/administrativo")
@CrossOrigin(origins = {"http://localhost:8081", "*"})
public class EmpleadoAdminController {

    @Autowired
    private EmpleadoAdminService empleadoAdminServ;

    /**
     * Crea un nuevo empleado administrativo con los datos proporcionados.
     *
     * @param nombre nombre del administrativo
     * @param edad edad del administrativo
     * @param fechaInicio fecha de inicio del administrativo
     * @param zonaAsignada zona asignada al administrativo
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @PostMapping("/crearadministrativo")
    public ResponseEntity<String> crearAdmin(@RequestParam String nombre, @RequestParam int edad,
            @RequestParam LocalDateTime fechaInicio, @RequestParam String zonaAsignada) {
        EmpleadoAdminDTO nuevo = new EmpleadoAdminDTO(nombre, edad, fechaInicio, zonaAsignada);
        int status = empleadoAdminServ.create(nuevo);
        if (status == 0) {
            return new ResponseEntity<>("Administrativo creado correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("No se pudo crear el administrativo ", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene la lista de todos los empleados administrativos registrados.
     *
     * @return lista de administrativos con estado HTTP correspondiente
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<EmpleadoAdminDTO>> obtenerTodo() {
        List<EmpleadoAdminDTO> lista = empleadoAdminServ.getAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<List<EmpleadoAdminDTO>>(lista, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<EmpleadoAdminDTO>>(lista, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Actualiza los datos de un empleado administrativo existente según su ID.
     *
     * @param id identificador del administrativo a actualizar
     * @param nombre nuevo nombre del administrativo
     * @param edad nueva edad del administrativo
     * @param fechaInicio nueva fecha de inicio del administrativo
     * @param zonaAsignada nueva zona asignada al administrativo
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarAdmin(@RequestParam Long id, @RequestParam String nombre,
            @RequestParam int edad, @RequestParam LocalDateTime fechaInicio,
            @RequestParam String zonaAsignada) {
        EmpleadoAdminDTO nuevo = new EmpleadoAdminDTO();
        nuevo.setNombre(nombre);
        nuevo.setEdad(edad);
        nuevo.setFechaInicio(fechaInicio);
        nuevo.setZonaAsignada(zonaAsignada);
        int status = empleadoAdminServ.updateById(id, nuevo);
        if (status == 0) {
            return new ResponseEntity<String>("Administrativo actualizado con exito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Error al actualizar administrativo", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un empleado administrativo según su ID.
     *
     * @param id identificador del administrativo a eliminar
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarAdmin(@RequestParam Long id) {
        int status = empleadoAdminServ.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<String>("Administrativo eliminado con exito", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<String>("Error al eliminar administrativo", HttpStatus.BAD_REQUEST);
        }
    }
}