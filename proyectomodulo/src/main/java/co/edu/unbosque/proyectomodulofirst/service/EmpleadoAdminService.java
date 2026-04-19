package co.edu.unbosque.proyectomodulofirst.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectomodulofirst.dto.EmpleadoAdminDTO;
import co.edu.unbosque.proyectomodulofirst.entity.EmpleadoAdmin;
import co.edu.unbosque.proyectomodulofirst.exception.EdadException;
import co.edu.unbosque.proyectomodulofirst.exception.InvalidDataException;
import co.edu.unbosque.proyectomodulofirst.exception.NombreException;
import co.edu.unbosque.proyectomodulofirst.exception.ZonaAsignadaException;
import co.edu.unbosque.proyectomodulofirst.repository.EmpleadoAdminRepository;

@Service
public class EmpleadoAdminService implements CRUDOperation<EmpleadoAdminDTO> {

    @Autowired
    private EmpleadoAdminRepository empleadoAdminRepo;

    @Autowired
    private ModelMapper mapper;

    public EmpleadoAdminService() {
    }

    @Override
    public int create(EmpleadoAdminDTO data) {

        try {
            if (data == null)
                throw new InvalidDataException("");

            if (data.getNombre() == null || data.getNombre().isEmpty())
                throw new InvalidDataException("");

            if (data.getEdad() <= 0)
                throw new InvalidDataException("");

            if (data.getFechaInicio() == null)
                throw new InvalidDataException("");

            if (data.getZonaAsignada() == null || data.getZonaAsignada().isEmpty())
                throw new InvalidDataException("");

            if (!data.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$"))
                throw new NombreException("");

            if (data.getEdad() < 0 || data.getEdad() > 120)
                throw new EdadException("");

            if (!data.getZonaAsignada().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$"))
                throw new ZonaAsignadaException("");

        } catch (NombreException e) {
            return 1;
        } catch (EdadException e) {
            return 2;
        } catch (ZonaAsignadaException e) {
            return 3;
        } catch (InvalidDataException e) {
            return 4;
        }

        empleadoAdminRepo.save(mapper.map(data, EmpleadoAdmin.class));
        return 0;
    }

    @Override
    public List<EmpleadoAdminDTO> getAll() {
        List<EmpleadoAdmin> entityList = (List<EmpleadoAdmin>) empleadoAdminRepo.findAll();
        List<EmpleadoAdminDTO> dtoList = new ArrayList<>();

        entityList.forEach(entidad -> {
            EmpleadoAdminDTO dto = mapper.map(entidad, EmpleadoAdminDTO.class);
            dtoList.add(dto);
        });

        return dtoList;
    }

    @Override
    public int deleteById(Long id) {
        Optional<EmpleadoAdmin> encontrado = empleadoAdminRepo.findById(id);

        if (encontrado.isPresent()) {
            empleadoAdminRepo.delete(encontrado.get());
            return 0;
        }

        return 1;
    }

    @Override
    public int updateById(Long id, EmpleadoAdminDTO newData) {

        Optional<EmpleadoAdmin> encontrado = empleadoAdminRepo.findById(id);

        if (!encontrado.isPresent())
            return 1;

        try {
            if (newData.getNombre() == null || newData.getNombre().isEmpty())
                throw new InvalidDataException("");

            if (newData.getEdad() <= 0)
                throw new InvalidDataException("");

            if (newData.getFechaInicio() == null)
                throw new InvalidDataException("");

            if (newData.getZonaAsignada() == null || newData.getZonaAsignada().isEmpty())
                throw new InvalidDataException("");

            if (!newData.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$"))
                throw new NombreException("");

            if (newData.getEdad() < 0 || newData.getEdad() > 120)
                throw new EdadException("");

            if (!newData.getZonaAsignada().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$"))
                throw new ZonaAsignadaException("");

        } catch (NombreException e) {
            return 2;
        } catch (EdadException e) {
            return 3;
        } catch (ZonaAsignadaException e) {
            return 4;
        } catch (InvalidDataException e) {
            return 5;
        }

        EmpleadoAdmin temp = encontrado.get();

        temp.setNombre(newData.getNombre());
        temp.setEdad(newData.getEdad());
        temp.setFechaInicio(newData.getFechaInicio());
        temp.setZonaAsignada(newData.getZonaAsignada());

        empleadoAdminRepo.save(temp);
        return 0;
    }

    @Override
    public long count() {
        return empleadoAdminRepo.count();
    }

    @Override
    public boolean exist(Long id) {
        return empleadoAdminRepo.existsById(id);
    }
}