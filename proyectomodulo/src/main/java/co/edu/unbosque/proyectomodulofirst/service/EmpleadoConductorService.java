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
import co.edu.unbosque.proyectomodulofirst.exception.NombreException;
import co.edu.unbosque.proyectomodulofirst.exception.ResourceNotFoundException;
import co.edu.unbosque.proyectomodulofirst.exception.TipoVehiculoException;
import co.edu.unbosque.proyectomodulofirst.repository.EmpleadoConductorRepository;
import co.edu.unbosque.proyectomodulofirst.repository.PaqueteRepository;

/**
 * Servicio encargado de la lógica de negocio para EmpleadoConductor.
 * Permite realizar operaciones CRUD, validaciones y control de paquetes asignados.
 */
@Service
public class EmpleadoConductorService implements CRUDOperation<EmpleadoConductorDTO>{
	
	@Autowired
	private EmpleadoConductorRepository empleadoConductorRepo;
	
	@Autowired
	private PaqueteRepository paqueteRepo;
	
	@Autowired
	private ModelMapper mapper;

	public EmpleadoConductorService() {

	}

	/**
	 * Crea un nuevo conductor con validaciones.
	 * 
	 * @param data datos del conductor
	 * @return resultado de la operación
	 */
	@Override
	public int create(EmpleadoConductorDTO data) {
		
		if (data == null) {
	        throw new InvalidDataException("Los datos del conductor no pueden ser nulos");
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

	    if (data.getTipoVehiculo() == null || data.getTipoVehiculo().isEmpty()) {
	        throw new InvalidDataException("El tipo de vehículo es obligatorio");
	    }
	    
	    if (!data.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new NombreException("El nombre solo puede contener letras y espacios");
	    }
	    
	    if (data.getEdad() < 0 || data.getEdad() > 120) {
	        throw new EdadException("La edad debe estar entre 0 y 120 años");
	    }
	    
	    if (!data.getTipoVehiculo().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new TipoVehiculoException("El tipo de vehículo solo puede contener letras y espacios");
	    }

	    EmpleadoConductor conductor = new EmpleadoConductor();

	    conductor.setNombre(data.getNombre());
	    conductor.setEdad(data.getEdad());
	    conductor.setFechaInicio(data.getFechaInicio());
	    conductor.setTipoVehiculo(data.getTipoVehiculo());

	    empleadoConductorRepo.save(conductor);

	    return 0;
	}

	/**
	 * Obtiene todos los conductores registrados.
	 * 
	 * @return lista de conductores
	 */
	@Override
	public List<EmpleadoConductorDTO> getAll() {
		List<EmpleadoConductor> entityList = (List<EmpleadoConductor>) empleadoConductorRepo.findAll();
		
		if (entityList.isEmpty()) {
	        throw new ResourceNotFoundException("No hay administradores registrados");
	    }
		
        List<EmpleadoConductorDTO> dtoList = new ArrayList<>();

        entityList.forEach((entidad) -> {
            EmpleadoConductorDTO dto = mapper.map(entidad, EmpleadoConductorDTO.class);
            dtoList.add(dto);
        });

        return dtoList;
	}

	/**
	 * Elimina un conductor validando que no tenga paquetes asignados.
	 * 
	 * @param id identificador del conductor
	 * @return resultado de la operación
	 */
	@Override
	public int deleteById(Long id) {
		Optional<EmpleadoConductor> encontrado = empleadoConductorRepo.findById(id);

	    if (!encontrado.isPresent()) {
	    	throw new ResourceNotFoundException("Usuario con id " + id + " no encontrado");
	    }

	    List<Paquete> paquetes = (List<Paquete>) paqueteRepo.findAll();

    	for (Paquete p : paquetes) {
    	    if (p.getIdConductor() == id) {
    	    	throw new InvalidDataException(
    	                "No se puede eliminar el conductor porque tiene paquetes asignados");
    	    }
    	}

	    empleadoConductorRepo.delete(encontrado.get());
	    return 0;
	}

	/**
	 * Actualiza un conductor por id.
	 * 
	 * @param id identificador del conductor
	 * @param newData nuevos datos
	 * @return resultado de la operación
	 */
	@Override
	public int updateById(Long id, EmpleadoConductorDTO newData) {
		Optional<EmpleadoConductor> encontrado = empleadoConductorRepo.findById(id);
		
		if (!encontrado.isPresent()) {
	        throw new ResourceNotFoundException("Conductor con id " + id + " no encontrado");
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

	    if (newData.getTipoVehiculo() == null || newData.getTipoVehiculo().isEmpty()) {
	        throw new InvalidDataException("El tipo de vehículo es obligatorio");
	    }
	    
	    if (!newData.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new NombreException("El nombre solo puede contener letras y espacios");
	    }
	    
	    if (newData.getEdad() < 0 || newData.getEdad() > 120) {
	        throw new EdadException("La edad debe estar entre 0 y 120 años");
	    }
	    
	    if (!newData.getTipoVehiculo().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new TipoVehiculoException("El tipo de vehículo solo puede contener letras y espacios");
	    }

        EmpleadoConductor temp = encontrado.get();

        temp.setNombre(newData.getNombre());
        temp.setEdad(newData.getEdad());
        temp.setFechaInicio(newData.getFechaInicio());
        temp.setTipoVehiculo(newData.getTipoVehiculo());

        empleadoConductorRepo.save(temp);
        return 0;
	}

	/**
	 * Cuenta los conductores registrados.
	 * 
	 * @return cantidad de conductores
	 */
	@Override
	public long count() {
		return empleadoConductorRepo.count();
	}

	/**
	 * Verifica si un conductor existe.
	 * 
	 * @param id identificador del conductor
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return empleadoConductorRepo.existsById(id) ? true : false;
	}
}