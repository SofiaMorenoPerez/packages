package co.edu.unbosque.proyectomodulofirst.repository;

import org.springframework.data.repository.CrudRepository;
import co.edu.unbosque.proyectomodulofirst.entity.EmpleadoConductor;

/**
 * Repositorio encargado de la gestión de operaciones CRUD
 * para la entidad EmpleadoConductor.
 */
public interface EmpleadoConductorRepository extends CrudRepository<EmpleadoConductor, Long>{

}