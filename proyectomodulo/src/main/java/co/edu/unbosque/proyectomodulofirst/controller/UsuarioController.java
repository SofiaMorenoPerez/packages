package co.edu.unbosque.proyectomodulofirst.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.unbosque.proyectomodulofirst.dto.UsuarioDTO;
import co.edu.unbosque.proyectomodulofirst.enums.TipoDeUsuario;
import co.edu.unbosque.proyectomodulofirst.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = {"http://localhost:8081", "*"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioServ;

    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(@RequestParam String nombre, @RequestParam TipoDeUsuario tipo,
            @RequestParam String ciudad, @RequestParam String direccion, @RequestParam long telefono) {
        UsuarioDTO nuevo = new UsuarioDTO(nombre, tipo, ciudad, direccion, telefono);
        int status = usuarioServ.create(nuevo);
        if (status == 0) {
            return new ResponseEntity<>("Usuario creado con exito", HttpStatus.CREATED);
        } else if (status == 1) {
            return new ResponseEntity<>("El tipo de usuario es obligatorio", HttpStatus.BAD_REQUEST);
        } else if (status == 2) {
            return new ResponseEntity<>("El nombre ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else if (status == 3) {
            return new ResponseEntity<>("La ciudad ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 4) {
            return new ResponseEntity<>("La direccion ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 5) {
            return new ResponseEntity<>("El telefono ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Error al crear usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mostrartodo")
    public ResponseEntity<List<UsuarioDTO>> obtenerTodo() {
        List<UsuarioDTO> lista = usuarioServ.getAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

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
            return new ResponseEntity<>("Usuario actualizado con exito", HttpStatus.OK);
        } else if (status == 1) {
            return new ResponseEntity<>("Usuario no encontrado con id: " + id, HttpStatus.NOT_FOUND);
        } else if (status == 2) {
            return new ResponseEntity<>("El tipo de usuario es obligatorio", HttpStatus.BAD_REQUEST);
        } else if (status == 3) {
            return new ResponseEntity<>("El nombre ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else if (status == 4) {
            return new ResponseEntity<>("La ciudad ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 5) {
            return new ResponseEntity<>("La direccion ingresada no es valida", HttpStatus.BAD_REQUEST);
        } else if (status == 6) {
            return new ResponseEntity<>("El telefono ingresado no es valido", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Error al actualizar usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarUsuario(@RequestParam Long id) {
        int status = usuarioServ.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<>("Usuario eliminado con exito", HttpStatus.OK);
        } else if (status == 1) {
            return new ResponseEntity<>("No se puede eliminar el usuario porque tiene paquetes asociados", HttpStatus.BAD_REQUEST);
        } else if (status == 2) {
            return new ResponseEntity<>("Usuario no encontrado con id: " + id, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Error al eliminar usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
