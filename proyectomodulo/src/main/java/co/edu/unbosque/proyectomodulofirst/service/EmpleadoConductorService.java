package co.edu.unbosque.proyectomodulofirst.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectomodulofirst.dto.EmpleadoConductorDTO;
import co.edu.unbosque.proyectomodulofirst.entity.EmpleadoConductor;
import co.edu.unbosque.proyectomodulofirst.entity.Paquete;
import co.edu.unbosque.proyectomodulofirst.exception.EdadException;
import co.edu.unbosque.proyectomodulofirst.exception.InvalidDataException;
import co.edu.unbosque.proyectomodulofirst.exception.LanzadorDeExcepcion;
import co.edu.unbosque.proyectomodulofirst.exception.NombreException;
import co.edu.unbosque.proyectomodulofirst.exception.TipoVehiculoException;
import co.edu.unbosque.proyectomodulofirst.repository.EmpleadoConductorRepository;
import co.edu.unbosque.proyectomodulofirst.repository.PaqueteRepository;

@Service
public class EmpleadoConductorService implements CRUDOperation<EmpleadoConductorDTO> {

    @Autowired
    private EmpleadoConductorRepository empleadoConductorRepo;

    @Autowired
    private PaqueteRepository paqueteRepo;

    @Autowired
    private ModelMapper mapper;

    public EmpleadoConductorService() {
    }

    // 0 - Creado exitosamente
    // 1 - Fecha de inicio nula
    // 2 - Nombre invalido
    // 3 - Edad invalida
    // 4 - Tipo de vehiculo invalido
    @Override
    public int create(EmpleadoConductorDTO data) {
        if (data.getFechaInicio() == null) return 1;
        try {
            LanzadorDeExcepcion.verificarNombre(data.getNombre());
            LanzadorDeExcepcion.verificarEdad(data.getEdad());
            LanzadorDeExcepcion.verificarTipoVehiculo(data.getTipoVehiculo());
        } catch (NombreException e) {
            return 2;
        } catch (EdadException e) {
            return 3;
        } catch (TipoVehiculoException e) {
            return 4;
        }
        EmpleadoConductor conductor = new EmpleadoConductor();
        conductor.setNombre(data.getNombre());
        conductor.setEdad(data.getEdad());
        conductor.setFechaInicio(data.getFechaInicio());
        conductor.setTipoVehiculo(data.getTipoVehiculo());
        empleadoConductorRepo.save(conductor);
        return 0;
    }

    @Override
    public List<EmpleadoConductorDTO> getAll() {
        List<EmpleadoConductor> entityList = (List<EmpleadoConductor>) empleadoConductorRepo.findAll();
        List<EmpleadoConductorDTO> dtoList = new ArrayList<>();
        entityList.forEach((entidad) -> {
            EmpleadoConductorDTO dto = mapper.map(entidad, EmpleadoConductorDTO.class);
            dtoList.add(dto);
        });
        return dtoList;
    }

    // 0 - Eliminado exitosamente
    // 1 - Tiene paquetes asignados
    // 2 - No encontrado
    @Override
    public int deleteById(Long id) {
        Optional<EmpleadoConductor> encontrado = empleadoConductorRepo.findById(id);
        if (!encontrado.isPresent()) return 2;
        List<Paquete> paquetes = (List<Paquete>) paqueteRepo.findAll();
        for (Paquete p : paquetes) {
            if (p.getIdConductor() == id) return 1;
        }
        empleadoConductorRepo.delete(encontrado.get());
        return 0;
    }

    // 0 - Actualizado exitosamente
    // 1 - No encontrado
    // 2 - Fecha de inicio nula
    // 3 - Nombre invalido
    // 4 - Edad invalida
    // 5 - Tipo de vehiculo invalido
    @Override
    public int updateById(Long id, EmpleadoConductorDTO newData) {
        Optional<EmpleadoConductor> encontrado = empleadoConductorRepo.findById(id);
        if (!encontrado.isPresent()) return 1;
        if (newData.getFechaInicio() == null) return 2;
        try {
            LanzadorDeExcepcion.verificarNombre(newData.getNombre());
            LanzadorDeExcepcion.verificarEdad(newData.getEdad());
            LanzadorDeExcepcion.verificarTipoVehiculo(newData.getTipoVehiculo());
        } catch (NombreException e) {
            return 3;
        } catch (EdadException e) {
            return 4;
        } catch (TipoVehiculoException e) {
            return 5;
        }
        EmpleadoConductor temp = encontrado.get();
        temp.setNombre(newData.getNombre());
        temp.setEdad(newData.getEdad());
        temp.setFechaInicio(newData.getFechaInicio());
        temp.setTipoVehiculo(newData.getTipoVehiculo());
        empleadoConductorRepo.save(temp);
        return 0;
    }

    @Override
    public long count() {
        return empleadoConductorRepo.count();
    }

    @Override
    public boolean exist(Long id) {
        return empleadoConductorRepo.existsById(id);
    }
}
