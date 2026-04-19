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

/**
 * Controlador REST para gestionar los paquetes del sistema.
 * Expone endpoints para crear, obtener, actualizar y eliminar paquetes.
 */
@RestController
@RequestMapping("/paquete")
@CrossOrigin(origins = {"http://localhost:8081", "*"})
public class PaqueteController {

    @Autowired
    private PaqueteService paqueteServ;

    /**
     * Crea un nuevo paquete con los datos proporcionados.
     *
     * @param idUsuario identificador del usuario que envía el paquete
     * @param idConductor identificador del conductor asignado al paquete
     * @param idManipulador identificador del manipulador asignado al paquete
     * @param ciudadDeOrigen ciudad de origen del paquete
     * @param ciudadDeDestino ciudad de destino del paquete
     * @param direccionDeOrigen dirección de origen del paquete
     * @param direccionDeDestino dirección de destino del paquete
     * @param tipo tipo de paquete según el enum TipoDePaquete
     * @param peso peso del paquete en kilogramos
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crearPaquete(@RequestParam long idUsuario, @RequestParam long idConductor,
            @RequestParam long idManipulador, @RequestParam String ciudadDeOrigen,
            @RequestParam String ciudadDeDestino, @RequestParam String direccionDeOrigen,
            @RequestParam String direccionDeDestino, @RequestParam TipoDePaquete tipo,
            @RequestParam double peso) {
        PaqueteDTO nuevo = new PaqueteDTO(idUsuario, idConductor, idManipulador, ciudadDeOrigen,
                ciudadDeDestino, direccionDeDestino, direccionDeDestino, tipo, peso);
        int status = paqueteServ.create(nuevo);
        if (status == 0) {
            return new ResponseEntity<String>("Paquete creado con exito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Error al crear paquete", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene la lista de todos los paquetes registrados.
     *
     * @return lista de paquetes con estado HTTP correspondiente
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<PaqueteDTO>> obtenerTodo() {
        List<PaqueteDTO> paqueteLista = paqueteServ.getAll();
        if (paqueteLista.isEmpty()) {
            return new ResponseEntity<List<PaqueteDTO>>(paqueteLista, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<PaqueteDTO>>(paqueteLista, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Actualiza la ciudad y dirección de destino de un paquete existente según su ID.
     *
     * @param id identificador del paquete a actualizar
     * @param ciudadDeDestino nueva ciudad de destino del paquete
     * @param direccionDeDestino nueva dirección de destino del paquete
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarPaquete(@RequestParam Long id,
            @RequestParam String ciudadDeDestino, @RequestParam String direccionDeDestino) {
        PaqueteDTO nuevo = new PaqueteDTO();
        nuevo.setCiudadDeDestino(ciudadDeDestino);
        nuevo.setDireccionDeDestino(direccionDeDestino);
        int status = paqueteServ.updateById(id, nuevo);
        if (status == 0) {
            return new ResponseEntity<String>("Paquete actualizado con exito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Error al actualizar paquete", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un paquete según su ID.
     *
     * @param id identificador del paquete a eliminar
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @DeleteMapping("/eliminarpaquete")
    public ResponseEntity<String> eliminarPaquete(@RequestParam Long id) {
        int status = paqueteServ.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<String>("Paquete eliminado con exito", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<String>("Error al eliminar paquete", HttpStatus.BAD_REQUEST);
        }
    }
}