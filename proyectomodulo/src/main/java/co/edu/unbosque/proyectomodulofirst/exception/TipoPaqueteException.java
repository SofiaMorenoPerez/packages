package co.edu.unbosque.proyectomodulofirst.exception;

/**
 * Excepción personalizada para errores relacionados con el tipo de paquete.
 */
public class TipoPaqueteException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje de error.
     * 
     * @param mensaje descripción del error
     */
    public TipoPaqueteException(String mensaje) {
        super(mensaje);
    }
}