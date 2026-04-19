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
    // 1 - Nombre invalido
    // 2 - Edad invalida
    // 3 - Fecha inicio obligatoria
    // 4 - Tipo vehiculo invalido
    @Override
    public int create(EmpleadoConductorDTO data) {

        try {
            LanzadorDeExcepcion.verificarNombre(data.getNombre());
            LanzadorDeExcepcion.verificarEdad(data.getEdad());

            if (data.getFechaInicio() == null) {
                return 3;
            }

            LanzadorDeExcepcion.verificarTipoVehiculo(data.getTipoVehiculo());

        } catch (NombreException e) {
            return 1;
        } catch (EdadException e) {
            return 2;
        } catch (TipoVehiculoException e) {
            return 4;
        }

        empleadoConductorRepo.save(mapper.map(data, EmpleadoConductor.class));
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
    // 1 - No encontrado
    // 2 - Tiene paquetes asignados
    // 3 - Id invalido
    @Override
    public int deleteById(Long id) {

        try {
            LanzadorDeExcepcion.verificarIdNegativo(id);
        } catch (InvalidDataException e) {
            return 3;
        }

        Optional<EmpleadoConductor> encontrado = empleadoConductorRepo.findById(id);

        if (encontrado.isPresent()) {

            List<Paquete> paquetes = (List<Paquete>) paqueteRepo.findAll();

            for (Paquete p : paquetes) {
                if (p.getIdConductor() == id) {
                    return 2;
                }
            }

            empleadoConductorRepo.delete(encontrado.get());
            return 0;
        }

        return 1;
    }

    // 0 - Actualizado exitosamente
    // 1 - No encontrado
    // 2 - Nombre invalido
    // 3 - Edad invalida
    // 4 - Fecha inicio obligatoria
    // 5 - Tipo vehiculo invalido
    // 6 - Id invalido
    @Override
    public int updateById(Long id, EmpleadoConductorDTO newData) {

        try {
            LanzadorDeExcepcion.verificarIdNegativo(id);
        } catch (InvalidDataException e) {
            return 6;
        }

        Optional<EmpleadoConductor> encontrado = empleadoConductorRepo.findById(id);

        if (encontrado.isPresent()) {

            try {
                LanzadorDeExcepcion.verificarNombre(newData.getNombre());
                LanzadorDeExcepcion.verificarEdad(newData.getEdad());

                if (newData.getFechaInicio() == null) {
                    return 4;
                }

                LanzadorDeExcepcion.verificarTipoVehiculo(newData.getTipoVehiculo());

            } catch (NombreException e) {
                return 2;
            } catch (EdadException e) {
                return 3;
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

        return 1;
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