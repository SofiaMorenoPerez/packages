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
import co.edu.unbosque.proyectomodulofirst.exception.NombreException;
import co.edu.unbosque.proyectomodulofirst.exception.ResourceNotFoundException;
import co.edu.unbosque.proyectomodulofirst.exception.TipoPaqueteException;
import co.edu.unbosque.proyectomodulofirst.repository.EmpleadoManipuladorRepository;
import co.edu.unbosque.proyectomodulofirst.repository.PaqueteRepository;

/**
 * Servicio encargado de la lógica de negocio para EmpleadoManipulador.
 * Permite realizar operaciones CRUD, validaciones y control de paquetes asignados.
 */
@Service
public class EmpleadoManipuladorService implements CRUDOperation<EmpleadoManipuladorDTO>{
	
	@Autowired
	private EmpleadoManipuladorRepository empleadoManipuladorRepo;

	@Autowired
	private PaqueteRepository paqueteRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	public EmpleadoManipuladorService() {

	}

	/**
	 * Crea un nuevo manipulador con validaciones.
	 * 
	 * @param data datos del manipulador
	 * @return resultado de la operación
	 */
	@Override
	public int create(EmpleadoManipuladorDTO data) {
		
		if (data == null) {
	        throw new InvalidDataException("Los datos del manipulador no pueden ser nulos");
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

	    if (data.getTipoDePaquete() == null || data.getTipoDePaquete().isEmpty()) {
	        throw new InvalidDataException("El tipo de paquete es obligatorio");
	    }

	    if (!data.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new NombreException("El nombre solo puede contener letras y espacios");
	    }
	    
	    if (data.getEdad() < 0 || data.getEdad() > 120) {
	        throw new EdadException("La edad debe estar entre 0 y 120 años");
	    }
	    
	    if (!data.getTipoDePaquete().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new TipoPaqueteException("El tipo de paquete solo puede contener letras y espacios");
	    }
	    
		EmpleadoManipulador entity = new EmpleadoManipulador(data.getNombre(), data.getEdad(), data.getFechaInicio(), data.getTipoDePaquete());
		empleadoManipuladorRepo.save(entity);
		return 0;
	}

	/**
	 * Obtiene todos los manipuladores registrados.
	 * 
	 * @return lista de manipuladores
	 */
	@Override
	public List<EmpleadoManipuladorDTO> getAll() {
		List<EmpleadoManipulador> entityList = (List<EmpleadoManipulador>) empleadoManipuladorRepo.findAll();
        
		if (entityList.isEmpty()) {
	        throw new ResourceNotFoundException("No hay administradores registrados");
	    }
		
		List<EmpleadoManipuladorDTO> dtoList = new ArrayList<>();

        entityList.forEach((entidad) -> {
            EmpleadoManipuladorDTO dto = mapper.map(entidad, EmpleadoManipuladorDTO.class);
            dtoList.add(dto);
        });

        return dtoList;
	}

	/**
	 * Elimina un manipulador validando que no tenga paquetes asignados.
	 * 
	 * @param id identificador del manipulador
	 * @return resultado de la operación
	 */
	@Override
	public int deleteById(Long id) {

	    Optional<EmpleadoManipulador> encontrado = empleadoManipuladorRepo.findById(id);

	    if (!encontrado.isPresent()) {
	        throw new ResourceNotFoundException("Manipulador con id " + id + " no encontrado");
	    }

	    List<Paquete> paquetes = (List<Paquete>) paqueteRepo.findAll();

	    for (Paquete p : paquetes) {
	    	if (p.getIdManipulador() == id) {
	            throw new InvalidDataException(
	                "No se puede eliminar el manipulador porque tiene paquetes asignados");
	        }
	    }

	    empleadoManipuladorRepo.delete(encontrado.get());
	    return 0;
	}

	/**
	 * Actualiza un manipulador por id.
	 * 
	 * @param id identificador del manipulador
	 * @param newData nuevos datos
	 * @return resultado de la operación
	 */
	@Override
	public int updateById(Long id, EmpleadoManipuladorDTO newData) {
		Optional<EmpleadoManipulador> encontrado = empleadoManipuladorRepo.findById(id);
		
		if (!encontrado.isPresent()) {
	        throw new ResourceNotFoundException("Manipulador con id " + id + " no encontrado");
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

	    if (newData.getTipoDePaquete() == null || newData.getTipoDePaquete().isEmpty()) {
	        throw new InvalidDataException("El tipo de paquete es obligatorio");
	    }
	    
	    if (!newData.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new NombreException("El nombre solo puede contener letras y espacios");
	    }
	    
	    if (newData.getEdad() < 0 || newData.getEdad() > 120) {
	        throw new EdadException("La edad debe estar entre 0 y 120 años");
	    }
	    
	    if (!newData.getTipoDePaquete().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	        throw new TipoPaqueteException("El tipo de paquete solo puede contener letras y espacios");
	    }

        EmpleadoManipulador temp = encontrado.get();

        temp.setNombre(newData.getNombre());

        empleadoManipuladorRepo.save(temp);
        return 0;
	}

	/**
	 * Cuenta los manipuladores registrados.
	 * 
	 * @return cantidad de manipuladores
	 */
	@Override
	public long count() {
		return empleadoManipuladorRepo.count();
	}

	/**
	 * Verifica si un manipulador existe.
	 * 
	 * @param id identificador del manipulador
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return empleadoManipuladorRepo.existsById(id) ? true : false;
	}
}