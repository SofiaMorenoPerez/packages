package co.edu.unbosque.proyectomodulofirst.service;

import java.time.LocalDateTime;
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

    private boolean estaEnProceso(Paquete p) {
        if (p.getFechaEnvio() == null) return false;
        LocalDateTime fin = p.getFechaEnvio().plusHours(p.getMaxHoras());
        return LocalDateTime.now().isBefore(fin);
    }

    // 0 - Creado exitosamente
    // 1 - Tipo de usuario nulo
    // 2 - Nombre invalido
    // 3 - Ciudad invalida
    // 4 - Direccion invalida
    // 5 - Telefono invalido
    @Override
    public int create(UsuarioDTO data) {
        if (data.getTipo() == null) return 1;
        try {
            LanzadorDeExcepcion.verificarNombre(data.getNombre());
            LanzadorDeExcepcion.verificarCiudad(data.getCiudad(), "ciudad");
            LanzadorDeExcepcion.verificarDireccion(data.getDireccion(), "direccion");
            LanzadorDeExcepcion.verificarTelefono(data.getTelefono());
        } catch (NombreException e) {
            return 2;
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
    // 1 - Tiene paquetes activos (en proceso)
    // 2 - No encontrado
    @Override
    public int deleteById(Long id) {
        Optional<Usuario> encontrado = usuarioRepo.findById(id);
        if (encontrado.isPresent()) {
            List<Paquete> paquetes = (List<Paquete>) paqueteRepo.findAll();
            for (Paquete p : paquetes) {
                if (p.getIdUsuario() == id && estaEnProceso(p)) return 1;
            }
            usuarioRepo.delete(encontrado.get());
            return 0;
        }
        return 2;
    }

    // 0 - Actualizado exitosamente
    // 1 - No encontrado
    // 2 - Tipo nulo
    // 3 - Nombre invalido
    // 4 - Ciudad invalida
    // 5 - Direccion invalida
    // 6 - Telefono invalido
    @Override
    public int updateById(Long id, UsuarioDTO newData) {
        Optional<Usuario> encontrado = usuarioRepo.findById(id);
        if (!encontrado.isPresent()) return 1;
        if (newData.getTipo() == null) return 2;
        try {
            LanzadorDeExcepcion.verificarNombre(newData.getNombre());
            LanzadorDeExcepcion.verificarCiudad(newData.getCiudad(), "ciudad");
            LanzadorDeExcepcion.verificarDireccion(newData.getDireccion(), "direccion");
            LanzadorDeExcepcion.verificarTelefono(newData.getTelefono());
        } catch (NombreException e) {
            return 3;
        } catch (CiudadException e) {
            return 4;
        } catch (DireccionException e) {
            return 5;
        } catch (TelefonoException e) {
            return 6;
        }
        Usuario usuario = encontrado.get();
        usuario.setNombre(newData.getNombre());
        usuario.setTipo(newData.getTipo());
        usuario.setTarifa(newData.getTipo().getTarifa());
        usuario.setCiudad(newData.getCiudad());
        usuario.setDireccion(newData.getDireccion());
        usuario.setTelefono(newData.getTelefono());
        usuarioRepo.save(usuario);
        return 0;
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