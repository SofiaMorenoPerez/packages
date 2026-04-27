package co.edu.unbosque.proyectomodulofirst.service;

import java.util.List;

/**
 * Interfaz que define las operaciones básicas CRUD.
 * Permite crear, consultar, eliminar, actualizar y validar datos.
 */
public interface CRUDOperation <T>{
	
	/**
	 * Crea un nuevo registro.
	 * 
	 * @param data datos a crear
	 * @return resultado de la operación
	 */
	public int create(T data);  

	/**
	 * Obtiene todos los registros.
	 * 
	 * @return lista de datos
	 */
	public List <T> getAll();      

	/**
	 * Elimina un registro por su id.
	 * 
	 * @param id identificador del dato
	 * @return resultado de la operación
	 */
	public int deleteById(Long id);  

	/**
	 * Actualiza un registro por su id.
	 * 
	 * @param id identificador del dato
	 * @param newData nuevos datos
	 * @return resultado de la operación
	 */
	public int updateById(Long id, T newData);

	/**
	 * Cuenta la cantidad de registros.
	 * 
	 * @return cantidad de datos
	 */
	public long count();

	/**
	 * Verifica si un registro existe.
	 * 
	 * @param id identificador del dato
	 * @return true si existe, false si no
	 */
	public boolean exist(Long id);  

}