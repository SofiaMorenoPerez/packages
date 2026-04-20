package co.edu.unbosque.proyectomodulofirst.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.unbosque.proyectomodulofirst.dto.EmpleadoAdminDTO;
import co.edu.unbosque.proyectomodulofirst.service.EmpleadoAdminService;

@RestController
@RequestMapping("/administrativo")
@CrossOrigin(origins = {"http://localhost:8081", "*"})
public class EmpleadoAdminController {

    @Autowired
    private EmpleadoAdminService empleadoAdminServ;

    @PostMapping("/crearadministrativo")
    public ResponseEntity<String> crearAdmin(@RequestParam String nombre, @RequestParam int edad,
            @RequestParam LocalDateTime fechaInicio, @RequestParam String zonaAsignada) {
        EmpleadoAdminDTO nuevo = new EmpleadoAdminDTO(nombre, edad, fechaInicio, zonaAsignada);
        int status = empleadoAdminServ.create(nuevo);
        if (status == 0) {
            return new ResponseEntity<>("Administrativo creado correctamente", HttpStatus.CREATED);
        } else if (status == 1) {
            return new ResponseEntity<>("La fecha de inicio es obligatoria", HttpStatus.BAD_REQUEST);
        } else if (status == 2) {
            return new ResponseEntity<>("El nombre ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else if (status == 3) {
            return new ResponseEntity<>("La edad ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 4) {
            return new ResponseEntity<>("La zona asignada ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Error al crear administrativo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mostrartodo")
    public ResponseEntity<List<EmpleadoAdminDTO>> obtenerTodo() {
        List<EmpleadoAdminDTO> lista = empleadoAdminServ.getAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

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
            return new ResponseEntity<>("Administrativo actualizado con exito", HttpStatus.OK);
        } else if (status == 1) {
            return new ResponseEntity<>("Administrativo no encontrado con id: " + id, HttpStatus.NOT_FOUND);
        } else if (status == 2) {
            return new ResponseEntity<>("La fecha de inicio es obligatoria", HttpStatus.BAD_REQUEST);
        } else if (status == 3) {
            return new ResponseEntity<>("El nombre ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else if (status == 4) {
            return new ResponseEntity<>("La edad ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 5) {
            return new ResponseEntity<>("La zona asignada ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Error al actualizar administrativo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarAdmin(@RequestParam Long id) {
        int status = empleadoAdminServ.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<>("Administrativo eliminado con exito", HttpStatus.OK);
        } else if (status == 1) {
            return new ResponseEntity<>("Administrativo no encontrado con id: " + id, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Error al eliminar administrativo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
