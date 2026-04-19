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
import co.edu.unbosque.proyectomodulofirst.exception.LanzadorDeExcepcion;
import co.edu.unbosque.proyectomodulofirst.exception.NombreException;
import co.edu.unbosque.proyectomodulofirst.exception.TelefonoException;
import co.edu.unbosque.proyectomodulofirst.repository.PaqueteRepository;
import co.edu.unbosque.proyectomodulofirst.repository.UsuarioRepository;

@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PaqueteRepository paqueteRepo;

    @Autowired
    private ModelMapper mapper;

    public UsuarioService() {
    }

    // 0 - Creado exitosamente
    // 1 - Nombre invalido
    // 2 - Tipo de usuario obligatorio
    // 3 - Ciudad invalida
    // 4 - Direccion invalida
    // 5 - Telefono invalido
    @Override
    public int create(UsuarioDTO data) {

        try {
            LanzadorDeExcepcion.verificarNombre(data.getNombre());

            if (data.getTipo() == null) {
                return 2;
            }

            LanzadorDeExcepcion.verificarCiudad(data.getCiudad(), "ciudad");
            LanzadorDeExcepcion.verificarDireccion(data.getDireccion(), "direccion");
            LanzadorDeExcepcion.verificarTelefono(data.getTelefono());

        } catch (NombreException e) {
            return 1;
        } catch (CiudadException e) {
            return 3;
        } catch (DireccionException e) {
            return 4;
        } catch (TelefonoException e) {
            return 5;
        }

        data.setTarifa(data.getTipo().getTarifa());
        usuarioRepo.save(mapper.map(data, Usuario.class));
        return 0;
    }

    @Override
    public List<UsuarioDTO> getAll() {

        List<Usuario> entityList = (List<Usuario>) usuarioRepo.findAll();
        List<UsuarioDTO> dtoList = new ArrayList<>();

        entityList.forEach((entidad) -> {
            UsuarioDTO dto = mapper.map(entidad, UsuarioDTO.class);
            dtoList.add(dto);
        });

        return dtoList;
    }

    // 0 - Eliminado exitosamente
    // 1 - No encontrado
    // 2 - Id invalido
    // 3 - Usuario con paquetes asociados
    @Override
    public int deleteById(Long id) {

        try {
            LanzadorDeExcepcion.verificarIdNegativo(id);
        } catch (InvalidDataException e) {
            return 2;
        }

        Optional<Usuario> encontrado = usuarioRepo.findById(id);

        if (encontrado.isPresent()) {

            List<Paquete> paquetes = (List<Paquete>) paqueteRepo.findAll();

            for (Paquete p : paquetes) {
                if (p.getIdUsuario() == id) {
                    return 3;
                }
            }

            usuarioRepo.delete(encontrado.get());
            return 0;
        }

        return 1;
    }

    // 0 - Actualizado exitosamente
    // 1 - Id invalido
    // 2 - No encontrado
    // 3 - Nombre invalido
    // 4 - Tipo obligatorio
    // 5 - Ciudad invalida
    // 6 - Direccion invalida
    // 7 - Telefono invalido
    @Override
    public int updateById(Long id, UsuarioDTO newData) {

        try {
            LanzadorDeExcepcion.verificarIdNegativo(id);
        } catch (InvalidDataException e) {
            return 1;
        }

        Optional<Usuario> encontrado = usuarioRepo.findById(id);

        if (encontrado.isPresent()) {

            try {

                LanzadorDeExcepcion.verificarNombre(newData.getNombre());

                if (newData.getTipo() == null) {
                    return 4;
                }

                LanzadorDeExcepcion.verificarCiudad(newData.getCiudad(), "ciudad");
                LanzadorDeExcepcion.verificarDireccion(newData.getDireccion(), "direccion");
                LanzadorDeExcepcion.verificarTelefono(newData.getTelefono());

            } catch (NombreException e) {
                return 3;
            } catch (CiudadException e) {
                return 5;
            } catch (DireccionException e) {
                return 6;
            } catch (TelefonoException e) {
                return 7;
            }

            Usuario temp = encontrado.get();

            temp.setNombre(newData.getNombre());
            temp.setTipo(newData.getTipo());
            temp.setTarifa(newData.getTipo().getTarifa());
            temp.setCiudad(newData.getCiudad());
            temp.setDireccion(newData.getDireccion());
            temp.setTelefono(newData.getTelefono());

            usuarioRepo.save(temp);
            return 0;
        }

        return 2;
    }

    @Override
    public long count() {
        return usuarioRepo.count();
    }

    @Override
    public boolean exist(Long id) {
        return usuarioRepo.existsById(id);
    }
}