package co.edu.unbosque.proyectomodulofirst.exception;

/**
 * Excepción personalizada para errores relacionados con datos inválidos.
 */
public class InvalidDataException extends RuntimeException{
	
    /**
     * Constructor que recibe el mensaje de error.
     * 
     * @param mensaje descripción del error
     */
    public InvalidDataException(String mensaje) {
        super(mensaje);
    }
}