import java.util.ArrayList;

/**
 * Guarda y busca las máquinas, y controla las operaciones que afectan
 * al negocio (cotizar, confirmar alquiler, registrar devolución).
 */
public class Inventario {

    private ArrayList<Maquina> maquinas;
    private double ingresosAcumulados;

    public Inventario() {
        this.maquinas = new ArrayList<Maquina>();
        this.ingresosAcumulados = 0;
    }

    /**
     * Agrega una máquina si su código no existe todavía.
     */
    public boolean registrarMaquina(Maquina maquina) {
        if (maquina == null) {
            return false;
        }
        if (buscarPorCodigo(maquina.getCodigoInventario()) != null) {
            return false;
        }
        return maquinas.add(maquina);
    }

    public Maquina buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (Maquina m : maquinas) {
            if (m.getCodigoInventario().equalsIgnoreCase(codigo.trim())) {
                return m;
            }
        }
        return null;
    }

    /**
     * Calcula el precio de un alquiler sin cambiar disponibilidad ni ingresos.
     */
    public double cotizar(String codigo, int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días deben ser un número entero positivo.");
        }
        Maquina m = buscarPorCodigo(codigo);
        if (m == null) {
            throw new IllegalArgumentException("No existe una máquina con ese código.");
        }
        return m.calcularCosto(dias);
    }

    /**
     * Alquila una máquina disponible y registra el ingreso.
     * Si la máquina no existe, no está disponible, o los días son
     * inválidos, no se cambia ningún dato.
     */
    public boolean confirmarAlquiler(String codigo, int dias) {
        if (dias <= 0) {
            return false;
        }
        Maquina m = buscarPorCodigo(codigo);
        if (m == null) {
            return false;
        }
        if (!m.isDisponible()) {
            return false;
        }
        double costo = m.calcularCosto(dias);
        if (!m.alquilar()) {
            return false;
        }
        ingresosAcumulados += costo;
        return true;
    }

    /**
     * Marca como disponible una máquina alquilada.
     */
    public boolean registrarDevolucion(String codigo) {
        Maquina m = buscarPorCodigo(codigo);
        if (m == null) {
            return false;
        }
        return m.devolver();
    }

    public double getIngresosAcumulados() {
        return ingresosAcumulados;
    }

    /**
     * Nota: este método no aparece en el diagrama UML original, pero es
     * indispensable para que Aplicacion pueda "consultar el inventario"
     * y generar el reporte por categorías (operaciones que sí pide el
     * documento de requisitos). Devuelve la lista real, no una copia.
     */
    public ArrayList<Maquina> getMaquinas() {
        return maquinas;
    }
}
