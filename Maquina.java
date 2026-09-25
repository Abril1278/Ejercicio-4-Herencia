public abstract class Maquina {

    private String codigoInventario;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private boolean disponible;


    protected Maquina(String codigo, String marca, String modelo, double tarifa) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        if (tarifa <= 0) {
            throw new IllegalArgumentException("La tarifa diaria debe ser mayor que cero.");
        }
        this.codigoInventario = codigo.trim();
        this.marca = marca;
        this.modelo = modelo;
        this.tarifaDiaria = tarifa;
        this.disponible = true;
    }


    public abstract double calcularCosto(int dias);

    public boolean alquilar() {
        if (!disponible) {
            return false;
        }
        disponible = false;
        return true;
    }

    public boolean devolver() {
        if (disponible) {
            return false;
        }
        disponible = true;
        return true;
    }

    public String getCodigoInventario() {
        return codigoInventario;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public boolean isDisponible() {
        return disponible;
    }
}
