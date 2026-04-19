package co.edu.unbosque.proyectomodulofirst.service;

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
import co.edu.unbosque.proyectomodulofirst.exception.InvalidDataException;
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

    // 0 - Creado exitosamente
    // 1 - Nombre invalido
    // 2 - Edad invalida
    // 3 - Fecha inicio obligatoria
    // 4 - Tipo paquete invalido
    @Override
    public int create(EmpleadoManipuladorDTO data) {

        try {
            LanzadorDeExcepcion.verificarNombre(data.getNombre());
            LanzadorDeExcepcion.verificarEdad(data.getEdad());

            if (data.getFechaInicio() == null) {
                return 3;
            }

            LanzadorDeExcepcion.verificarTipoPaquete(data.getTipoDePaquete());

        } catch (NombreException e) {
            return 1;
        } catch (EdadException e) {
            return 2;
        } catch (TipoPaqueteException e) {
            return 4;
        }

        empleadoManipuladorRepo.save(mapper.map(data, EmpleadoManipulador.class));
        return 0;
    }

    @Override
    public List<EmpleadoManipuladorDTO> getAll() {
        List<EmpleadoManipulador> entityList =
                (List<EmpleadoManipulador>) empleadoManipuladorRepo.findAll();

        List<EmpleadoManipuladorDTO> dtoList = new ArrayList<>();

        entityList.forEach((entidad) -> {
            EmpleadoManipuladorDTO dto =
                    mapper.map(entidad, EmpleadoManipuladorDTO.class);
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

        Optional<EmpleadoManipulador> encontrado =
                empleadoManipuladorRepo.findById(id);

        if (encontrado.isPresent()) {

            List<Paquete> paquetes = (List<Paquete>) paqueteRepo.findAll();

            for (Paquete p : paquetes) {
                if (p.getIdManipulador() == id) {
                    return 2;
                }
            }

            empleadoManipuladorRepo.delete(encontrado.get());
            return 0;
        }

        return 1;
    }

    // 0 - Actualizado exitosamente
    // 1 - No encontrado
    // 2 - Nombre invalido
    // 3 - Edad invalida
    // 4 - Fecha inicio obligatoria
    // 5 - Tipo paquete invalido
    // 6 - Id invalido
    @Override
    public int updateById(Long id, EmpleadoManipuladorDTO newData) {

        try {
            LanzadorDeExcepcion.verificarIdNegativo(id);
        } catch (InvalidDataException e) {
            return 6;
        }

        Optional<EmpleadoManipulador> encontrado =
                empleadoManipuladorRepo.findById(id);

        if (encontrado.isPresent()) {

            try {
                LanzadorDeExcepcion.verificarNombre(newData.getNombre());
                LanzadorDeExcepcion.verificarEdad(newData.getEdad());

                if (newData.getFechaInicio() == null) {
                    return 4;
                }

                LanzadorDeExcepcion.verificarTipoPaquete(
                        newData.getTipoDePaquete());

            } catch (NombreException e) {
                return 2;
            } catch (EdadException e) {
                return 3;
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

        return 1;
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