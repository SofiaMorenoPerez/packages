package co.edu.unbosque.proyectomodulofirst.exception;

/**
 * Excepción personalizada para errores relacionados con la edad.
 */
public class EdadException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje de error.
     * 
     * @param mensaje descripción del error
     */
    public EdadException(String mensaje) {
        super(mensaje);
    }
}