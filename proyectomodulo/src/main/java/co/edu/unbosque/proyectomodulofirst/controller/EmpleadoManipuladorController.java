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

@RestController
@RequestMapping("/manipulador")
@CrossOrigin(origins = {"http://localhost:8081", "*"})
public class EmpleadoManipuladorController {

    @Autowired
    private EmpleadoManipuladorService empleadoManipuladorServ;

    @PostMapping("/crear")
    public ResponseEntity<String> crearManipulador(
            @RequestParam String nombre,
            @RequestParam int edad,
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam String tipoPaquete) {

        EmpleadoManipuladorDTO nuevo =
                new EmpleadoManipuladorDTO(nombre, edad, fechaInicio, tipoPaquete);

        int status = empleadoManipuladorServ.create(nuevo);

        if (status == 0) {
            return new ResponseEntity<>("Manipulador creado con exito",
                    HttpStatus.CREATED);
        } else if (status == 1) {
            return new ResponseEntity<>("El nombre ingresado no es valido",
                    HttpStatus.BAD_REQUEST);
        } else if (status == 2) {
            return new ResponseEntity<>("La edad ingresada no es valida",
                    HttpStatus.BAD_REQUEST);
        } else if (status == 3) {
            return new ResponseEntity<>("La fecha de inicio es obligatoria",
                    HttpStatus.BAD_REQUEST);
        } else if (status == 4) {
            return new ResponseEntity<>("El tipo de paquete es obligatorio",
                    HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Error al crear manipulador",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mostrartodo")
    public ResponseEntity<List<EmpleadoManipuladorDTO>> obtenerTodo() {

        List<EmpleadoManipuladorDTO> lista =
                empleadoManipuladorServ.getAll();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PutMapping("/actualizarmanipulador")
    public ResponseEntity<String> actualizarManipulador(
            @RequestParam Long id,
            @RequestParam String nombre,
            @RequestParam int edad,
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam String tipoPaquete) {

        EmpleadoManipuladorDTO nuevo = new EmpleadoManipuladorDTO();
        nuevo.setNombre(nombre);
        nuevo.setEdad(edad);
        nuevo.setFechaInicio(fechaInicio);
        nuevo.setTipoDePaquete(tipoPaquete);

        int status = empleadoManipuladorServ.updateById(id, nuevo);

        if (status == 0) {
            return new ResponseEntity<>("Manipulador actualizado con exito",
                    HttpStatus.OK);
        } else if (status == 1) {
            return new ResponseEntity<>("Manipulador no encontrado con id: " + id,
                    HttpStatus.NOT_FOUND);
        } else if (status == 2) {
            return new ResponseEntity<>("El nombre ingresado no es valido",
                    HttpStatus.BAD_REQUEST);
        } else if (status == 3) {
            return new ResponseEntity<>("La edad ingresada no es valida",
                    HttpStatus.BAD_REQUEST);
        } else if (status == 4) {
            return new ResponseEntity<>("La fecha de inicio es obligatoria",
                    HttpStatus.BAD_REQUEST);
        } else if (status == 5) {
            return new ResponseEntity<>("El tipo de paquete es obligatorio",
                    HttpStatus.BAD_REQUEST);
        } else if (status == 6) {
            return new ResponseEntity<>("El id ingresado no es valido",
                    HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Error al actualizar manipulador",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarmanipulador")
    public ResponseEntity<String> eliminarManipulador(
            @RequestParam Long id) {

        int status = empleadoManipuladorServ.deleteById(id);

        if (status == 0) {
            return new ResponseEntity<>("Manipulador eliminado con exito",
                    HttpStatus.OK);
        } else if (status == 1) {
            return new ResponseEntity<>("Manipulador no encontrado con id: " + id,
                    HttpStatus.NOT_FOUND);
        } else if (status == 2) {
            return new ResponseEntity<>(
                    "No se puede eliminar porque tiene paquetes asignados",
                    HttpStatus.BAD_REQUEST);
        } else if (status == 3) {
            return new ResponseEntity<>("El id ingresado no es valido",
                    HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Error al eliminar manipulador",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}