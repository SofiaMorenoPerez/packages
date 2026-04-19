package co.edu.unbosque.proyectomodulofirst.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectomodulofirst.dto.PaqueteDTO;
import co.edu.unbosque.proyectomodulofirst.entity.Paquete;
import co.edu.unbosque.proyectomodulofirst.exception.CiudadException;
import co.edu.unbosque.proyectomodulofirst.exception.DireccionException;
import co.edu.unbosque.proyectomodulofirst.exception.InvalidDataException;
import co.edu.unbosque.proyectomodulofirst.exception.PesoException;
import co.edu.unbosque.proyectomodulofirst.exception.ResourceNotFoundException;
import co.edu.unbosque.proyectomodulofirst.repository.PaqueteRepository;


/**
 * Servicio encargado de la lógica de negocio para la entidad Paquete.
 * Permite realizar operaciones CRUD, validaciones y control de asignación de recursos.
 */
@Service 
public class PaqueteService implements CRUDOperation<PaqueteDTO> {
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EmpleadoConductorService conductorService;


	@Autowired
	private EmpleadoManipuladorService manipuladorService;
	
	@Autowired
	private PaqueteRepository paqueteRepo;
	
	@Autowired 
	private ModelMapper mapper;

	public PaqueteService() {
	}

	/**
	 * Crea un nuevo paquete validando datos, relaciones y disponibilidad de recursos.
	 * 
	 * @param newData datos del paquete
	 * @return 0 si se crea correctamente
	 */
	@Override
	public int create(PaqueteDTO newData) { 
			
		    if (newData.getTipo() == null) {
		        throw new InvalidDataException("El tipo de paquete es obligatorio");
		    }
	
		    if (newData.getPeso() <= 0) {
		        throw new InvalidDataException("El peso debe ser mayor a 0");
		    }
	
		    if (newData.getCiudadDeOrigen() == null || newData.getCiudadDeOrigen().isEmpty()) {
		        throw new InvalidDataException("La ciudad de origen es obligatoria");
		    }
	
		    if (newData.getCiudadDeDestino() == null || newData.getCiudadDeDestino().isEmpty()) {
		        throw new InvalidDataException("La ciudad de destino es obligatoria");
		    }
	
		    if (newData.getDireccionDeOrigen() == null || newData.getDireccionDeOrigen().isEmpty()) {
		        throw new InvalidDataException("La dirección de origen es obligatoria");
		    }
	
		    if (newData.getDireccionDeDestino() == null || newData.getDireccionDeDestino().isEmpty()) {
		        throw new InvalidDataException("La dirección de destino es obligatoria");
		    }
		    
		    if (!newData.getCiudadDeOrigen().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
		        throw new CiudadException("La ciudad de origen solo puede contener letras");
		    }
		    
		    if (!newData.getCiudadDeDestino().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
		        throw new CiudadException("La ciudad de destino solo puede contener letras");
		    }
		    
		    if (!newData.getDireccionDeOrigen().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9 #\\-]+$")) {
		        throw new DireccionException("La dirección de origen debe tener letras y números");
		    }
		    
		    if (!newData.getDireccionDeDestino().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9 #\\-]+$")) {
		        throw new DireccionException("La dirección de destino debe tener letras y números");
		    }
		    
		    if (newData.getPeso() <= 0) {
		        throw new PesoException("El peso debe ser mayor a 0");
		    }
	
		    // VALIDAR EXISTENCIA DE RELACIONES
		    if (!usuarioService.exist(newData.getIdUsuario())) {
		        throw new ResourceNotFoundException("Usuario no existe");
		    }
	
		    if (!conductorService.exist(newData.getIdConductor())) {
		        throw new ResourceNotFoundException("Conductor no existe");
		    }
	
		    if (!manipuladorService.exist(newData.getIdManipulador())) {
		        throw new ResourceNotFoundException("Manipulador no existe");
		    }
	
		 
		    List<Paquete> paquetes = (List<Paquete>) paqueteRepo.findAll();
	
		    for (Paquete p : paquetes) {
		        if (p.getIdConductor() == newData.getIdConductor()
		            && p.getTiempoRestanteMinutos() > 0) {
		            throw new InvalidDataException("El conductor ya tiene un paquete en proceso");
		        }
	
		        if (p.getIdManipulador() == newData.getIdManipulador()
		            && p.getTiempoRestanteMinutos() > 0) {
		            throw new InvalidDataException("El manipulador ya tiene un paquete en proceso");
		        }
		    }
		    
			newData.setFechaEnvio(LocalDateTime.now());
			newData.setMaxHoras(newData.getTipo().getMaxHoras());
			newData.setTiempo(newData.getMaxHoras());
			
			paqueteRepo.save(mapper.map(newData, Paquete.class)); 
			return 0;
		}

	/**
	 * Obtiene todos los paquetes registrados.
	 * 
	 * @return lista de paquetes en formato DTO
	 */
	@Override
	public List<PaqueteDTO> getAll() {
		List<Paquete> entityList = (List<Paquete>) paqueteRepo.findAll();
		
		 if (entityList.isEmpty()) {
		        throw new ResourceNotFoundException("No hay paquetes registrados");
		    }

		
		List<PaqueteDTO> dtoList = new ArrayList<>();
		
		entityList.forEach((entidad)->{
			PaqueteDTO dto = mapper.map(entidad, PaqueteDTO.class);
			dto.setTiempo((int) entidad.getTiempoRestanteMinutos());
			dtoList.add(dto);		
		});
		return dtoList;
	}
	
	/**
	 * Elimina un paquete por su identificador.
	 * 
	 * @param id identificador del paquete
	 * @return 0 si se elimina correctamente
	 */
	@Override
	public int deleteById(Long id) {
		
		Optional<Paquete > encontrado = paqueteRepo.findById(id); 
		if (encontrado.isPresent()){
			paqueteRepo.delete(encontrado.get());
			return 0;
			
		}
		throw new ResourceNotFoundException("Paquete con id " + id + " no encontrado");
	}

	/**
	 * Actualiza datos específicos de un paquete.
	 * 
	 * @param id identificador del paquete
	 * @param newData nuevos datos del paquete
	 * @return 0 si se actualiza correctamente
	 */
	@Override
	public int updateById(Long id, PaqueteDTO newData) {


	    if (id == null || id <= 0) {
	        throw new InvalidDataException("El id del paquete es inválido");
	    }

	    Optional<Paquete> encontrado = paqueteRepo.findById(id);

	    if (encontrado.isPresent()) {

	   
	        if (newData.getCiudadDeDestino() == null || newData.getCiudadDeDestino().isEmpty()) {
	            throw new CiudadException("La ciudad de destino es obligatoria");
	        }
	        if (!newData.getCiudadDeDestino().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
	            throw new CiudadException("La ciudad de destino solo puede contener letras");
	        }

	      
	        if (newData.getDireccionDeDestino() == null || newData.getDireccionDeDestino().isEmpty()) {
	            throw new DireccionException("La dirección de destino es obligatoria");
	        }
	        if (!newData.getDireccionDeDestino().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9 #\\-]+$")) {
	            throw new DireccionException("La dirección de destino debe tener letras y números");
	        }

	        Paquete temp = encontrado.get();
	        temp.setCiudadDeDestino(newData.getCiudadDeDestino());
	        temp.setDireccionDeDestino(newData.getDireccionDeDestino());

	        paqueteRepo.save(temp);

	        return 0;
	    }

	    throw new ResourceNotFoundException("Paquete no encontrado con id: " + id);
	}
	
	/**
	 * Cuenta la cantidad de paquetes registrados.
	 * 
	 * @return número de paquetes
	 */
	@Override
	public long count() {
		
		return paqueteRepo.count();
	}

	/**
	 * Verifica si existe un paquete por su id.
	 * 
	 * @param id identificador del paquete
	 * @return true si existe, false en caso contrario
	 */
	@Override
    public boolean exist(Long id) {
        return paqueteRepo.existsById(id) ? true : false;
    }
	
	
}