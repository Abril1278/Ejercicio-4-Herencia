public class MaquinaAlgodonAzucar extends Maquina {

    private int potenciaVatios;

    public MaquinaAlgodonAzucar(String codigo, String marca, String modelo, double tarifa, int potenciaVatios) {
        super(codigo, marca, modelo, tarifa);
        if (potenciaVatios <= 0) {
            throw new IllegalArgumentException("La potencia debe ser mayor que cero.");
        }
        this.potenciaVatios = potenciaVatios;
    }

    public double calcularCosto(int dias) {
        double costo = getTarifaDiaria() * dias;
        if (potenciaVatios > 1000) {
            costo += 60;
        }
        return costo;
    }

    public int getPotenciaVatios() {
        return potenciaVatios;
    }
}
