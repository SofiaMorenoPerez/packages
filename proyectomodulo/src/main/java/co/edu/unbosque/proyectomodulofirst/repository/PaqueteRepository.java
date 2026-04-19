package co.edu.unbosque.proyectomodulofirst.repository;

import org.springframework.data.repository.CrudRepository;
import co.edu.unbosque.proyectomodulofirst.entity.Paquete;

/**
 * Repositorio encargado de la gestión de operaciones CRUD
 * para la entidad Paquete.
 */
public interface PaqueteRepository extends CrudRepository<Paquete, Long>{

}