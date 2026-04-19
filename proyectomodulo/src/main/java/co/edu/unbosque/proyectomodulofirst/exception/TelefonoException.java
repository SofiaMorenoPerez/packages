package co.edu.unbosque.proyectomodulofirst.exception;

/**
 * Excepción personalizada para errores relacionados con el teléfono.
 */
public class TelefonoException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje de error.
     * 
     * @param mensaje descripción del error
     */
    public TelefonoException(String mensaje) {
        super(mensaje);
    }
}