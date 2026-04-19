package co.edu.unbosque.proyectomodulofirst.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectomodulofirst.dto.UsuarioDTO;
import co.edu.unbosque.proyectomodulofirst.entity.Paquete;
import co.edu.unbosque.proyectomodulofirst.entity.Usuario;
import co.edu.unbosque.proyectomodulofirst.exception.CiudadException;
import co.edu.unbosque.proyectomodulofirst.exception.DireccionException;
import co.edu.unbosque.proyectomodulofirst.exception.InvalidDataException;
import co.edu.unbosque.proyectomodulofirst.exception.NombreException;
import co.edu.unbosque.proyectomodulofirst.exception.ResourceNotFoundException;
import co.edu.unbosque.proyectomodulofirst.exception.TelefonoException;
import co.edu.unbosque.proyectomodulofirst.repository.PaqueteRepository;
import co.edu.unbosque.proyectomodulofirst.repository.UsuarioRepository;

/**
 * Servicio encargado de la lógica de negocio para la entidad Usuario.
 * Implementa operaciones CRUD con validaciones de negocio.
 */
@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PaqueteRepository paqueteRepo;

    @Autowired
    private ModelMapper mapper;

    /**
     * Constructor vacío del servicio.
     */
    public UsuarioService() { 
        
    }

    /**
     * Crea un nuevo usuario en el sistema validando sus datos.
     * 
     * @param data datos del usuario a registrar
     * @return código de estado (0 si fue exitoso)
     */
    @Override
    public int create(UsuarioDTO data) {

        if (data.getNombre() == null || data.getNombre().isEmpty()) {
            throw new InvalidDataException("El nombre es obligatorio");
        }

        if (data.getTipo() == null) {
            throw new InvalidDataException("El tipo de usuario es obligatorio");
        }

        if (data.getCiudad() == null || data.getCiudad().isEmpty()) {
            throw new InvalidDataException("La ciudad es obligatoria");
        }

        if (data.getDireccion() == null || data.getDireccion().isEmpty()) {
            throw new InvalidDataException("La dirección es obligatoria");
        }

        if (data.getTelefono() <= 0) {
            throw new InvalidDataException("El teléfono debe ser válido");
        }

        if (!data.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            throw new NombreException("El nombre solo puede contener letras y espacios");
        }

        if (!data.getCiudad().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            throw new CiudadException("La ciudad solo puede contener letras y espacios");
        }

        if (!data.getDireccion().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9 #\\-]+$")) {
            throw new DireccionException(
                "La dirección debe contener letras y números y no tener caracteres especiales"
            );
        }

        if (!String.valueOf(data.getTelefono()).matches("^\\d{10}$")) {
            throw new TelefonoException("El teléfono debe tener exactamente 10 números");
        }

        data.setTarifa(data.getTipo().getTarifa());
        usuarioRepo.save((mapper.map(data, Usuario.class)));
        return 0;
    }

    /**
     * Obtiene todos los usuarios registrados.
     * 
     * @return lista de usuarios en formato DTO
     */
    @Override
    public List<UsuarioDTO> getAll() {
        List<Usuario> entityList = (List<Usuario>) usuarioRepo.findAll();

        if (entityList.isEmpty()) {
            throw new ResourceNotFoundException("No hay usuarios registrados");
        }

        List<UsuarioDTO> dtoList = new ArrayList<>();

        entityList.forEach((entidad) -> {
            UsuarioDTO dto = mapper.map(entidad, UsuarioDTO.class);
            dtoList.add(dto);
        });

        return dtoList;
    }

    /**
     * Elimina un usuario por su ID, validando que no tenga paquetes asociados.
     * 
     * @param id identificador del usuario
     * @return código de estado (0 si fue exitoso)
     */
    @Override
    public int deleteById(Long id) {
        Optional<Usuario> encontrado = usuarioRepo.findById(id);

        if (encontrado.isPresent()) {

            List<Paquete> paquetes = (List<Paquete>) paqueteRepo.findAll();

            for (Paquete p : paquetes) {
                if (p.getIdUsuario() == id) {
                    throw new InvalidDataException(
                        "No se puede eliminar el usuario porque tiene paquetes asociados");
                }
            }

            usuarioRepo.delete(encontrado.get());
            return 0;
        }

        throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
    }

    /**
     * Actualiza los datos de un usuario existente.
     * 
     * @param id identificador del usuario
     * @param newData nuevos datos del usuario
     * @return código de estado (0 si fue exitoso)
     */
    @Override
    public int updateById(Long id, UsuarioDTO newData) {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        // VALIDACIONES
        if (newData.getNombre() == null || newData.getNombre().isEmpty()) {
            throw new InvalidDataException("El nombre es obligatorio");
        }

        if (newData.getTipo() == null) {
            throw new InvalidDataException("El tipo de usuario es obligatorio");
        }

        if (newData.getCiudad() == null || newData.getCiudad().isEmpty()) {
            throw new InvalidDataException("La ciudad es obligatoria");
        }

        if (newData.getDireccion() == null || newData.getDireccion().isEmpty()) {
            throw new InvalidDataException("La dirección es obligatoria");
        }

        if (newData.getTelefono() <= 0) {
            throw new InvalidDataException("El teléfono debe ser válido");
        }

        if (!newData.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            throw new NombreException("El nombre solo puede contener letras y espacios");
        }

        if (!newData.getCiudad().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            throw new CiudadException("La ciudad solo puede contener letras y espacios");
        }

        if (!newData.getDireccion().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9 #\\-]+$")) {
            throw new DireccionException(
                "La dirección debe contener letras y números y no tener caracteres especiales"
            );
        }

        if (!String.valueOf(newData.getTelefono()).matches("^\\d{10}$")) {
            throw new TelefonoException("El teléfono debe tener exactamente 10 números");
        }

        usuario.setNombre(newData.getNombre());
        usuario.setTipo(newData.getTipo());
        usuario.setTarifa(newData.getTipo().getTarifa());
        usuario.setCiudad(newData.getCiudad());
        usuario.setDireccion(newData.getDireccion());
        usuario.setTelefono(newData.getTelefono());

        usuarioRepo.save(usuario);

        return 0;
    }

    /**
     * Cuenta la cantidad total de usuarios registrados.
     * 
     * @return número de usuarios
     */
    @Override
    public long count() {
        return usuarioRepo.count();
    }

    /**
     * Verifica si un usuario existe por su ID.
     * 
     * @param id identificador del usuario
     * @return true si existe, false en caso contrario
     */
    @Override
    public boolean exist(Long id) {
        return usuarioRepo.existsById(id) ? true : false;
    }
}