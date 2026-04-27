package co.edu.unbosque.proyectomodulofirst.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.unbosque.proyectomodulofirst.dto.EmpleadoConductorDTO;
import co.edu.unbosque.proyectomodulofirst.service.EmpleadoConductorService;

@RestController
@RequestMapping("/conductor")
@CrossOrigin(origins = {"http://localhost:8081", "*"})
public class EmpleadoConductorController {

    @Autowired
    private EmpleadoConductorService empleadoConductorServ;

    @PostMapping("/crearconductor")
    public ResponseEntity<String> crearConductor(@RequestParam String nombre, @RequestParam int edad,
            @RequestParam LocalDateTime fechaInicio, @RequestParam String tipoVehiculo) {
        EmpleadoConductorDTO nuevo = new EmpleadoConductorDTO(nombre, edad, fechaInicio, tipoVehiculo);
        int status = empleadoConductorServ.create(nuevo);
        if (status == 0) {
            return new ResponseEntity<>("Conductor creado correctamente", HttpStatus.CREATED);
        } else if (status == 1) {
            return new ResponseEntity<>("La fecha de inicio es obligatoria", HttpStatus.BAD_REQUEST);
        } else if (status == 2) {
            return new ResponseEntity<>("El nombre ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else if (status == 3) {
            return new ResponseEntity<>("La edad ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 4) {
            return new ResponseEntity<>("El tipo de vehiculo ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Error al crear conductor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mostrartodo")
    public ResponseEntity<List<EmpleadoConductorDTO>> obtenerTodo() {
        List<EmpleadoConductorDTO> lista = empleadoConductorServ.getAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

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
            return new ResponseEntity<>("Conductor actualizado con exito", HttpStatus.OK);
        } else if (status == 1) {
            return new ResponseEntity<>("Conductor no encontrado con id: " + id, HttpStatus.NOT_FOUND);
        } else if (status == 2) {
            return new ResponseEntity<>("La fecha de inicio es obligatoria", HttpStatus.BAD_REQUEST);
        } else if (status == 3) {
            return new ResponseEntity<>("El nombre ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else if (status == 4) {
            return new ResponseEntity<>("La edad ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 5) {
            return new ResponseEntity<>("El tipo de vehiculo ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Error al actualizar conductor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarconductor")
    public ResponseEntity<String> eliminarConductor(@RequestParam Long id) {
        int status = empleadoConductorServ.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<>("Conductor eliminado con exito", HttpStatus.OK);
        } else if (status == 1) {
            return new ResponseEntity<>("No se puede eliminar el conductor porque tiene paquetes asignados", HttpStatus.BAD_REQUEST);
        } else if (status == 2) {
            return new ResponseEntity<>("Conductor no encontrado con id: " + id, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Error al eliminar conductor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
