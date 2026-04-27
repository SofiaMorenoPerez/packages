package co.edu.unbosque.proyectomodulofirst.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectomodulofirst.dto.EmpleadoManipuladorDTO;
import co.edu.unbosque.proyectomodulofirst.entity.EmpleadoManipulador;
import co.edu.unbosque.proyectomodulofirst.entity.Paquete;
import co.edu.unbosque.proyectomodulofirst.exception.EdadException;
import co.edu.unbosque.proyectomodulofirst.exception.LanzadorDeExcepcion;
import co.edu.unbosque.proyectomodulofirst.exception.NombreException;
import co.edu.unbosque.proyectomodulofirst.exception.TipoPaqueteException;
import co.edu.unbosque.proyectomodulofirst.repository.EmpleadoManipuladorRepository;
import co.edu.unbosque.proyectomodulofirst.repository.PaqueteRepository;

@Service
public class EmpleadoManipuladorService implements CRUDOperation<EmpleadoManipuladorDTO> {

    @Autowired
    private EmpleadoManipuladorRepository empleadoManipuladorRepo;

    @Autowired
    private PaqueteRepository paqueteRepo;

    @Autowired
    private ModelMapper mapper;

    public EmpleadoManipuladorService() {
    }

    private boolean estaEnProceso(Paquete p) {
        if (p.getFechaEnvio() == null) return false;
        LocalDateTime fin = p.getFechaEnvio().plusHours(p.getMaxHoras());
        return LocalDateTime.now().isBefore(fin);
    }

    // 0 - Creado exitosamente
    // 1 - Fecha de inicio nula
    // 2 - Nombre invalido
    // 3 - Edad invalida
    // 4 - Tipo de paquete invalido
    @Override
    public int create(EmpleadoManipuladorDTO data) {
        if (data.getFechaInicio() == null) return 1;
        try {
            LanzadorDeExcepcion.verificarNombre(data.getNombre());
            LanzadorDeExcepcion.verificarEdad(data.getEdad());
            LanzadorDeExcepcion.verificarTipoPaqueteTexto(data.getTipoDePaquete());
        } catch (NombreException e) {
            return 2;
        } catch (EdadException e) {
            return 3;
        } catch (TipoPaqueteException e) {
            return 4;
        }
        EmpleadoManipulador entity = new EmpleadoManipulador(data.getNombre(), data.getEdad(),
                data.getFechaInicio(), data.getTipoDePaquete());
        empleadoManipuladorRepo.save(entity);
        return 0;
    }

    @Override
    public List<EmpleadoManipuladorDTO> getAll() {
        List<EmpleadoManipulador> entityList = (List<EmpleadoManipulador>) empleadoManipuladorRepo.findAll();
        List<EmpleadoManipuladorDTO> dtoList = new ArrayList<>();
        entityList.forEach((entidad) -> {
            EmpleadoManipuladorDTO dto = mapper.map(entidad, EmpleadoManipuladorDTO.class);
            dtoList.add(dto);
        });
        return dtoList;
    }

    // 0 - Eliminado exitosamente
    // 1 - Tiene paquetes activos en proceso
    // 2 - No encontrado
    @Override
    public int deleteById(Long id) {
        Optional<EmpleadoManipulador> encontrado = empleadoManipuladorRepo.findById(id);
        if (!encontrado.isPresent()) return 2;
        List<Paquete> paquetes = (List<Paquete>) paqueteRepo.findAll();
        for (Paquete p : paquetes) {
            if (p.getIdManipulador() == id && estaEnProceso(p)) return 1;
        }
        empleadoManipuladorRepo.delete(encontrado.get());
        return 0;
    }

    // 0 - Actualizado exitosamente
    // 1 - No encontrado
    // 2 - Fecha de inicio nula
    // 3 - Nombre invalido
    // 4 - Edad invalida
    // 5 - Tipo de paquete invalido
    @Override
    public int updateById(Long id, EmpleadoManipuladorDTO newData) {
        Optional<EmpleadoManipulador> encontrado = empleadoManipuladorRepo.findById(id);
        if (!encontrado.isPresent()) return 1;
        if (newData.getFechaInicio() == null) return 2;
        try {
            LanzadorDeExcepcion.verificarNombre(newData.getNombre());
            LanzadorDeExcepcion.verificarEdad(newData.getEdad());
            LanzadorDeExcepcion.verificarTipoPaqueteTexto(newData.getTipoDePaquete());
        } catch (NombreException e) {
            return 3;
        } catch (EdadException e) {
            return 4;
        } catch (TipoPaqueteException e) {
            return 5;
        }
        EmpleadoManipulador temp = encontrado.get();
        temp.setNombre(newData.getNombre());
        temp.setEdad(newData.getEdad());
        temp.setFechaInicio(newData.getFechaInicio());
        temp.setTipoDePaquete(newData.getTipoDePaquete());
        empleadoManipuladorRepo.save(temp);
        return 0;
    }

    @Override
    public long count() {
        return empleadoManipuladorRepo.count();
    }

    @Override
    public boolean exist(Long id) {
        return empleadoManipuladorRepo.existsById(id);
    }
}