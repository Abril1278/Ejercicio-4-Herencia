public class MaquinaPalomitas extends Maquina {

    private int porcionesPorHora;
    private boolean tieneCarrito;

    public MaquinaPalomitas(String codigo, String marca, String modelo, double tarifa,
                             int porcionesPorHora, boolean tieneCarrito) {
        super(codigo, marca, modelo, tarifa);
        if (porcionesPorHora <= 0) {
            throw new IllegalArgumentException("Las porciones por hora deben ser mayores que cero.");
        }
        this.porcionesPorHora = porcionesPorHora;
        this.tieneCarrito = tieneCarrito;
    }

    public double calcularCosto(int dias) {
        double costo = getTarifaDiaria() * dias;
        if (tieneCarrito) {
            costo += 40 * dias;
        }
        return costo;
    }

    public int getPorcionesPorHora() {
        return porcionesPorHora;
    }

    public boolean isTieneCarrito() {
        return tieneCarrito;
    }
}
