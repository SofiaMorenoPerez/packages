package co.edu.unbosque.proyectomodulofirst.enums;

/**
 * Enumeración que representa los tipos de paquete disponibles en el sistema.
 * Cada tipo tiene asociado un máximo de horas permitido para su entrega.
 */
public enum TipoDePaquete {

    ALIMENTICIO(6),
    NO_ALIMENTICIO(24),
    CARTA(72);

    private final int maxHoras;

    /**
     * Constructor que asigna el máximo de horas de entrega al tipo de paquete.
     *
     * @param maxHoras número máximo de horas permitido para entregar el paquete
     */
    TipoDePaquete(int maxHoras) {
        this.maxHoras = maxHoras;
    }

    public int getMaxHoras() {
        return maxHoras;
    }
}