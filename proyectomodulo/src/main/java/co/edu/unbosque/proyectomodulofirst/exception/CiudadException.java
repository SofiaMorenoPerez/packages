package co.edu.unbosque.proyectomodulofirst.exception;

/**
 * Excepción personalizada para errores relacionados con la ciudad.
 */
public class CiudadException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje de error.
     * 
     * @param mensaje descripción del error
     */
    public CiudadException(String mensaje) {
        super(mensaje);
    }
}