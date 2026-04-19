package co.edu.unbosque.proyectomodulofirst.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.proyectomodulofirst.entity.Usuario;

/**
 * Repositorio encargado de la gestión de operaciones CRUD
 * para la entidad Usuario.
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

}