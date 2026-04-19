package co.edu.unbosque.proyectomodulofirst.repository;

import org.springframework.data.repository.CrudRepository;
import co.edu.unbosque.proyectomodulofirst.entity.EmpleadoManipulador;

/**
 * Repositorio encargado de la gestión de operaciones CRUD
 * para la entidad EmpleadoManipulador.
 */
public interface EmpleadoManipuladorRepository extends CrudRepository<EmpleadoManipulador, Long>{

}