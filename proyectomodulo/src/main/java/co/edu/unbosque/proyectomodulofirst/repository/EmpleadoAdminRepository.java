package co.edu.unbosque.proyectomodulofirst.repository;

import org.springframework.data.repository.CrudRepository;
import co.edu.unbosque.proyectomodulofirst.entity.EmpleadoAdmin;

/**
 * Repositorio encargado de la gestión de operaciones CRUD
 * para la entidad EmpleadoAdmin.
 */
public interface EmpleadoAdminRepository extends CrudRepository<EmpleadoAdmin, Long>{

}