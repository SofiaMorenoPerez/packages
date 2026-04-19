package co.edu.unbosque.proyectomodulofirst.repository;

import org.springframework.data.repository.CrudRepository;
import co.edu.unbosque.proyectomodulofirst.entity.Empleado;

/**
 * Repositorio encargado de la gestión de operaciones CRUD
 * para la entidad Empleado.
 */
public interface EmpleadoRepository extends CrudRepository<Empleado, Long>{

}