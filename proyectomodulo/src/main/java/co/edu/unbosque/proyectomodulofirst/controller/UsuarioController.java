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
import co.edu.unbosque.proyectomodulofirst.dto.UsuarioDTO;
import co.edu.unbosque.proyectomodulofirst.enums.TipoDeUsuario;
import co.edu.unbosque.proyectomodulofirst.service.UsuarioService;

/**
 * Controlador REST para gestionar los usuarios del sistema.
 * Expone endpoints para crear, obtener, actualizar y eliminar usuarios.
 */
@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = {"http://localhost:8081", "*"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioServ;

    /**
     * Crea un nuevo usuario con los datos proporcionados.
     *
     * @param nombre nombre del usuario
     * @param tipo tipo de usuario según el enum TipoDeUsuario
     * @param ciudad ciudad de residencia del usuario
     * @param direccion dirección del usuario
     * @param telefono número de teléfono del usuario
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(@RequestParam String nombre, @RequestParam TipoDeUsuario tipo,
            @RequestParam String ciudad, @RequestParam String direccion, @RequestParam long telefono) {
        UsuarioDTO nuevo = new UsuarioDTO(nombre, tipo, ciudad, direccion, telefono);
        int status = usuarioServ.create(nuevo);
        if (status == 0) {
            return new ResponseEntity<String>("Usuario creado con exito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Error al crear usuario", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene la lista de todos los usuarios registrados.
     *
     * @return lista de usuarios con estado HTTP correspondiente
     */
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<UsuarioDTO>> obtenerTodo() {
        List<UsuarioDTO> usuarioLista = usuarioServ.getAll();
        if (usuarioLista.isEmpty()) {
            return new ResponseEntity<List<UsuarioDTO>>(usuarioLista, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<UsuarioDTO>>(usuarioLista, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Actualiza los datos de un usuario existente según su ID.
     *
     * @param id identificador del usuario a actualizar
     * @param nombre nuevo nombre del usuario
     * @param tipo nuevo tipo de usuario según el enum TipoDeUsuario
     * @param ciudad nueva ciudad de residencia del usuario
     * @param direccion nueva dirección del usuario
     * @param telefono nuevo número de teléfono del usuario
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarUsuario(@RequestParam Long id, @RequestParam String nombre,
            @RequestParam TipoDeUsuario tipo, @RequestParam String ciudad,
            @RequestParam String direccion, @RequestParam long telefono) {
        UsuarioDTO nuevo = new UsuarioDTO();
        nuevo.setNombre(nombre);
        nuevo.setTipo(tipo);
        nuevo.setCiudad(ciudad);
        nuevo.setDireccion(direccion);
        nuevo.setTelefono(telefono);
        int status = usuarioServ.updateById(id, nuevo);
        if (status == 0) {
            return new ResponseEntity<String>("Usuario actualizado con exito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Error al actualizar usuario", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un usuario según su ID.
     *
     * @param id identificador del usuario a eliminar
     * @return respuesta con mensaje de éxito o error según el resultado
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarUsuario(@RequestParam Long id) {
        int status = usuarioServ.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<String>("Usuario eliminado con exito", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<String>("Error al eliminar usuario", HttpStatus.BAD_REQUEST);
        }
    }
}