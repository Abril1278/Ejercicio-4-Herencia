public class FuenteChocolate extends Maquina {

    private double capacidadKg;

    public FuenteChocolate(String codigo, String marca, String modelo, double tarifa, double capacidadKg) {
        super(codigo, marca, modelo, tarifa);
        if (capacidadKg <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        this.capacidadKg = capacidadKg;
    }

    public double calcularCosto(int dias) {
        double costo = getTarifaDiaria() * dias;
        costo += 20 * capacidadKg * dias;
        return costo;
    }

    public double getCapacidadKg() {
        return capacidadKg;
    }
}
