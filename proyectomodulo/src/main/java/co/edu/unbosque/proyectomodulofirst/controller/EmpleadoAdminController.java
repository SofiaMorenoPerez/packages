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
    public ResponseEntity<String> crearAdmin(@RequestParam String nombre,
            @RequestParam int edad,
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam String zonaAsignada) {

        EmpleadoAdminDTO nuevo = new EmpleadoAdminDTO(nombre, edad, fechaInicio, zonaAsignada);

        int status = empleadoAdminServ.create(nuevo);

        if (status == 0)
            return new ResponseEntity<>("Administrativo creado correctamente", HttpStatus.CREATED);

        return new ResponseEntity<>("Error al crear administrativo", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/mostrartodo")
    public ResponseEntity<List<EmpleadoAdminDTO>> obtenerTodo() {

        List<EmpleadoAdminDTO> lista = empleadoAdminServ.getAll();

        if (lista.isEmpty())
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarAdmin(@RequestParam Long id,
            @RequestParam String nombre,
            @RequestParam int edad,
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam String zonaAsignada) {

        EmpleadoAdminDTO nuevo = new EmpleadoAdminDTO();
        nuevo.setNombre(nombre);
        nuevo.setEdad(edad);
        nuevo.setFechaInicio(fechaInicio);
        nuevo.setZonaAsignada(zonaAsignada);

        int status = empleadoAdminServ.updateById(id, nuevo);

        if (status == 0)
            return new ResponseEntity<>("Administrativo actualizado con exito", HttpStatus.CREATED);

        return new ResponseEntity<>("Error al actualizar administrativo", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarAdmin(@RequestParam Long id) {

        int status = empleadoAdminServ.deleteById(id);

        if (status == 0)
            return new ResponseEntity<>("Administrativo eliminado con exito", HttpStatus.ACCEPTED);

        return new ResponseEntity<>("Error al eliminar administrativo", HttpStatus.BAD_REQUEST);
    }
}