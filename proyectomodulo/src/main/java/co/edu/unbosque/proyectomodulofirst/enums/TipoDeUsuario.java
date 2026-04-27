package co.edu.unbosque.proyectomodulofirst.enums;

/**
 * Enumeración que representa los tipos de usuario disponibles en el sistema.
 * Cada tipo tiene asociada una tarifa de envío diferente.
 */
public enum TipoDeUsuario {

    NORMAL(3000),
    PREMIUM(4000),
    CONCURRENTE(5000);

    private final double tarifa;

    /**
     * Constructor que asigna la tarifa correspondiente al tipo de usuario.
     *
     * @param tarifa valor de la tarifa asociada al tipo de usuario
     */
    TipoDeUsuario(double tarifa) {
        this.tarifa = tarifa;
    }

    public double getTarifa() {
        return tarifa;
    }
}