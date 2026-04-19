package co.edu.unbosque.proyectomodulofirst.exception;

/**
 * Excepción personalizada para errores relacionados con la dirección.
 */
public class DireccionException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje de error.
     * 
     * @param mensaje descripción del error
     */
    public DireccionException(String mensaje) {
        super(mensaje);
    }
}