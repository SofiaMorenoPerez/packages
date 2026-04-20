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
import co.edu.unbosque.proyectomodulofirst.exception.LanzadorDeExcepcion;
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

    // 0 - Creado exitosamente
    // 1 - Fecha de inicio nula
    // 2 - Nombre invalido
    // 3 - Edad invalida
    // 4 - Zona asignada invalida
    @Override
    public int create(EmpleadoAdminDTO data) {
        if (data.getFechaInicio() == null) return 1;
        try {
            LanzadorDeExcepcion.verificarNombre(data.getNombre());
            LanzadorDeExcepcion.verificarEdad(data.getEdad());
            LanzadorDeExcepcion.verificarZonaAsignada(data.getZonaAsignada());
        } catch (NombreException e) {
            return 2;
        } catch (EdadException e) {
            return 3;
        } catch (ZonaAsignadaException e) {
            return 4;
        }
        EmpleadoAdmin entity = new EmpleadoAdmin(data.getNombre(), data.getEdad(),
                data.getFechaInicio(), data.getZonaAsignada());
        empleadoAdminRepo.save(entity);
        return 0;
    }

    @Override
    public List<EmpleadoAdminDTO> getAll() {
        List<EmpleadoAdmin> entityList = (List<EmpleadoAdmin>) empleadoAdminRepo.findAll();
        List<EmpleadoAdminDTO> dtoList = new ArrayList<>();
        entityList.forEach((entidad) -> {
            EmpleadoAdminDTO dto = mapper.map(entidad, EmpleadoAdminDTO.class);
            dtoList.add(dto);
        });
        return dtoList;
    }

    // 0 - Eliminado exitosamente
    // 1 - No encontrado
    @Override
    public int deleteById(Long id) {
        Optional<EmpleadoAdmin> encontrado = empleadoAdminRepo.findById(id);
        if (!encontrado.isPresent()) return 1;
        empleadoAdminRepo.delete(encontrado.get());
        return 0;
    }

    // 0 - Actualizado exitosamente
    // 1 - No encontrado
    // 2 - Fecha de inicio nula
    // 3 - Nombre invalido
    // 4 - Edad invalida
    // 5 - Zona asignada invalida
    @Override
    public int updateById(Long id, EmpleadoAdminDTO newData) {
        Optional<EmpleadoAdmin> encontrado = empleadoAdminRepo.findById(id);
        if (!encontrado.isPresent()) return 1;
        if (newData.getFechaInicio() == null) return 2;
        try {
            LanzadorDeExcepcion.verificarNombre(newData.getNombre());
            LanzadorDeExcepcion.verificarEdad(newData.getEdad());
            LanzadorDeExcepcion.verificarZonaAsignada(newData.getZonaAsignada());
        } catch (NombreException e) {
            return 3;
        } catch (EdadException e) {
            return 4;
        } catch (ZonaAsignadaException e) {
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
