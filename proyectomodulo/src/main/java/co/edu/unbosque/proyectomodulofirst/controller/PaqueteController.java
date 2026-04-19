package co.edu.unbosque.proyectomodulofirst.controller;

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
import co.edu.unbosque.proyectomodulofirst.dto.PaqueteDTO;
import co.edu.unbosque.proyectomodulofirst.enums.TipoDePaquete;
import co.edu.unbosque.proyectomodulofirst.service.PaqueteService;

@RestController
@RequestMapping("/paquete")
@CrossOrigin(origins = {"http://localhost:8081", "*"})
public class PaqueteController {

    @Autowired
    private PaqueteService paqueteServ;

    @PostMapping("/crear")
    public ResponseEntity<String> crearPaquete(
            @RequestParam long idUsuario,
            @RequestParam long idConductor,
            @RequestParam long idManipulador,
            @RequestParam String ciudadDeOrigen,
            @RequestParam String ciudadDeDestino,
            @RequestParam String direccionDeOrigen,
            @RequestParam String direccionDeDestino,
            @RequestParam TipoDePaquete tipo,
            @RequestParam double peso) {

        PaqueteDTO nuevo = new PaqueteDTO(idUsuario, idConductor, idManipulador,
                ciudadDeOrigen, ciudadDeDestino, direccionDeOrigen, direccionDeDestino, tipo, peso);

        int status = paqueteServ.create(nuevo);

        if (status == 0) {
            return new ResponseEntity<>("Paquete creado con exito", HttpStatus.CREATED);
        } else if (status == 1) {
            return new ResponseEntity<>("El tipo de paquete es obligatorio", HttpStatus.BAD_REQUEST);
        } else if (status == 2) {
            return new ResponseEntity<>("El peso debe ser mayor a 0", HttpStatus.BAD_REQUEST);
        } else if (status == 3) {
            return new ResponseEntity<>("La ciudad ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 4) {
            return new ResponseEntity<>("La ciudad de destino no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 5) {
            return new ResponseEntity<>("La direccion ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 6) {
            return new ResponseEntity<>("La direccion de destino no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 7) {
            return new ResponseEntity<>("El id ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else if (status == 8) {
            return new ResponseEntity<>("El usuario no existe", HttpStatus.NOT_FOUND);
        } else if (status == 9) {
            return new ResponseEntity<>("El conductor no existe", HttpStatus.NOT_FOUND);
        } else if (status == 10) {
            return new ResponseEntity<>("El manipulador no existe", HttpStatus.NOT_FOUND);
        } else if (status == 11) {
            return new ResponseEntity<>("El conductor ya tiene un paquete en proceso", HttpStatus.BAD_REQUEST);
        } else if (status == 12) {
            return new ResponseEntity<>("El manipulador ya tiene un paquete en proceso", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Error al crear paquete", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mostrartodo")
    public ResponseEntity<List<PaqueteDTO>> obtenerTodo() {
        List<PaqueteDTO> paqueteLista = paqueteServ.getAll();
        if (paqueteLista.isEmpty()) {
            return new ResponseEntity<>(paqueteLista, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paqueteLista, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarPaquete(
            @RequestParam Long id,
            @RequestParam String ciudadDeDestino,
            @RequestParam String direccionDeDestino) {

        PaqueteDTO nuevo = new PaqueteDTO();
        nuevo.setCiudadDeDestino(ciudadDeDestino);
        nuevo.setDireccionDeDestino(direccionDeDestino);

        int status = paqueteServ.updateById(id, nuevo);

        if (status == 0) {
            return new ResponseEntity<>("Paquete actualizado con exito", HttpStatus.OK);
        } else if (status == 1) {
            return new ResponseEntity<>("El id del paquete es invalido", HttpStatus.BAD_REQUEST);
        } else if (status == 2) {
            return new ResponseEntity<>("La ciudad de destino no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 3) {
            return new ResponseEntity<>("La direccion de destino no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 4) {
            return new ResponseEntity<>("Paquete no encontrado con id: " + id, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Error al actualizar paquete", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarpaquete")
    public ResponseEntity<String> eliminarPaquete(@RequestParam Long id) {
        int status = paqueteServ.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<>("Paquete eliminado con exito", HttpStatus.OK);
        } else if (status == 1) {
            return new ResponseEntity<>("Paquete con id " + id + " no encontrado", HttpStatus.NOT_FOUND);
        } else if (status == 2) {
            return new ResponseEntity<>("El id ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Error al eliminar paquete", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}