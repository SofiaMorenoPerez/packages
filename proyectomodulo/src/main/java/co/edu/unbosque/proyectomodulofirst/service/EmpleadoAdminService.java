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
import co.edu.unbosque.proyectomodulofirst.exception.ResourceNotFoundException;
import co.edu.unbosque.proyectomodulofirst.exception.ZonaAsignadaException;
import co.edu.unbosque.proyectomodulofirst.repository.EmpleadoAdminRepository;

/**
 * Servicio encargado de la lógica de negocio para EmpleadoAdmin.
 * Permite realizar operaciones CRUD y validaciones de datos.
 */
@Service
public class EmpleadoAdminService implements CRUDOperation<EmpleadoAdminDTO>{
	
	@Autowired
	private EmpleadoAdminRepository empleadoAdminRepo;

	@Autowired
	private ModelMapper mapper;
	
	public EmpleadoAdminService() {

	}

	/**
	 * Crea un nuevo administrador con validaciones.
	 * 
	 * @param data datos del administrador
	 * @return resultado de la operación
	 */
	@Override
	public int create(EmpleadoAdminDTO data) {	
		if (data == null) {
	        throw new InvalidDataException("Los datos del administrador no pueden ser nulos");
	    }

	    if (data.getNombre() == null || data.getNombre().isEmpty()) {
	        throw new InvalidDataException("El nombre es obligatorio");
	    }

	    if (data.getEdad() <= 0) {
	        throw new InvalidDataException("La edad debe ser válida");
	    }

	    if (data.getFechaInicio() == null) {
	        throw new InvalidDataException("La fecha de inicio es obligatoria");
	    }

	    if (data.getZonaAsignada() == null || data.getZonaAsignada().isEmpty()) {
	        throw new InvalidDataException("La zona asignada es obligatoria");
	    }
	    
	    if (!data.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new NombreException("El nombre solo puede contener letras y espacios");
	    }
	    
	    if (data.getEdad() < 0 || data.getEdad() > 120) {
	        throw new EdadException("La edad debe estar entre 0 y 120 años");
	    }
	    
	    if (!data.getZonaAsignada().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new ZonaAsignadaException("La zona asignada solo puede contener letras y espacios");
	    }

		EmpleadoAdmin entity = new EmpleadoAdmin(data.getNombre(), data.getEdad(), data.getFechaInicio(), data.getZonaAsignada());
		empleadoAdminRepo.save(entity);
		return 0;
	}

	/**
	 * Obtiene todos los administradores registrados.
	 * 
	 * @return lista de administradores
	 */
	@Override
	public List<EmpleadoAdminDTO> getAll() {
		List<EmpleadoAdmin> entityList = (List<EmpleadoAdmin>) empleadoAdminRepo.findAll();
        
		if (entityList.isEmpty()) {
	        throw new ResourceNotFoundException("No hay administradores registrados");
	    }
		
		List<EmpleadoAdminDTO> dtoList = new ArrayList<>();

        entityList.forEach((entidad) -> {
            EmpleadoAdminDTO dto = mapper.map(entidad, EmpleadoAdminDTO.class);
            dtoList.add(dto);
        });

        return dtoList;
	}

	/**
	 * Elimina un administrador por id.
	 * 
	 * @param id identificador del administrador
	 * @return resultado de la operación
	 */
	@Override
	public int deleteById(Long id) {
		Optional<EmpleadoAdmin> encontrado = empleadoAdminRepo.findById(id);

	    if (!encontrado.isPresent()) {
	    	throw new ResourceNotFoundException("Administrador con id " + id + " no encontrado");
	    }
	    empleadoAdminRepo.delete(encontrado.get());
	    return 0;
	}

	/**
	 * Actualiza un administrador por id.
	 * 
	 * @param id identificador del administrador
	 * @param newData nuevos datos
	 * @return resultado de la operación
	 */
	@Override
	public int updateById(Long id, EmpleadoAdminDTO newData) {
		Optional<EmpleadoAdmin> encontrado = empleadoAdminRepo.findById(id);
		
		if (!encontrado.isPresent()) {
	        throw new ResourceNotFoundException("Administrador con id " + id + " no encontrado");
	    }

	    if (newData == null) {
	        throw new InvalidDataException("Los datos a actualizar no pueden ser nulos");
	    }

	    if (newData.getNombre() == null || newData.getNombre().isEmpty()) {
	        throw new InvalidDataException("El nombre es obligatorio");
	    }

	    if (newData.getEdad() <= 0) {
	        throw new InvalidDataException("La edad debe ser válida");
	    }

	    if (newData.getFechaInicio() == null) {
	        throw new InvalidDataException("La fecha de inicio es obligatoria");
	    }

	    if (newData.getZonaAsignada() == null || newData.getZonaAsignada().isEmpty()) {
	        throw new InvalidDataException("La zona asignada es obligatoria");
	    }
	    
	    if (!newData.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new NombreException("El nombre solo puede contener letras y espacios");
	    }
	    
	    if (newData.getEdad() < 0 || newData.getEdad() > 120) {
	        throw new EdadException("La edad debe estar entre 0 y 120 años");
	    }
	    
	    if (!newData.getZonaAsignada().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new ZonaAsignadaException("La zona asignada solo puede contener letras y espacios");
	    }

        EmpleadoAdmin temp = encontrado.get();

        temp.setNombre(newData.getNombre());
        temp.setEdad(newData.getEdad());
        temp.setFechaInicio(newData.getFechaInicio());
        temp.setZonaAsignada(newData.getZonaAsignada());

        empleadoAdminRepo.save(temp);
        return 0;
	}

	/**
	 * Cuenta los administradores registrados.
	 * 
	 * @return cantidad de administradores
	 */
	@Override
	public long count() {
		return empleadoAdminRepo.count();
	}

	/**
	 * Verifica si un administrador existe.
	 * 
	 * @param id identificador del administrador
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return empleadoAdminRepo.existsById(id) ? true : false;
	}
}