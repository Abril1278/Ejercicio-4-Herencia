import java.util.ArrayList;
import java.util.Scanner;

public class Aplicacion {

    private static Scanner sc = new Scanner(System.in);
    private static Inventario inventario = new Inventario();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1:
                    registrarMaquina();
                    break;
                case 2:
                    consultarInventario();
                    break;
                case 3:
                    buscarMaquina();
                    break;
                case 4:
                    cotizarAlquiler();
                    break;
                case 5:
                    confirmarAlquiler();
                    break;
                case 6:
                    registrarDevolucion();
                    break;
                case 7:
                    mostrarReporte();
                    break;
                case 8:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
            System.out.println();
        } while (opcion != 8);
        sc.close();
    }

    private static void mostrarMenu() {

        System.out.println("   DULCE ESTACIÓN - Sistema de Alquiler");
        System.out.println();
        System.out.println("1. Registrar máquina");
        System.out.println("2. Consultar inventario");
        System.out.println("3. Buscar máquina por código");
        System.out.println("4. Cotizar alquiler");
        System.out.println("5. Confirmar alquiler");
        System.out.println("6. Registrar devolución");
        System.out.println("7. Ver reporte");
        System.out.println("8. Salir");
    }

    private static void registrarMaquina() {
        System.out.println("--- Registrar máquina ---");
        System.out.println("Tipo de máquina:");
        System.out.println("1. Máquina de palomitas");
        System.out.println("2. Máquina de algodón de azúcar");
        System.out.println("3. Fuente de chocolate");
        int tipo = leerEntero("Seleccione el tipo: ");
        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo inválido. No se registró la máquina.");
            return;
        }

        System.out.print("Código de inventario: ");
        String codigo = sc.nextLine();
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();
        double tarifa = leerDouble("Tarifa diaria (Q): ");

        try {
            Maquina maquina;
            switch (tipo) {
                case 1:
                    int porciones = leerEntero("Porciones por hora: ");
                    boolean carrito = leerBooleano("¿Tiene carrito? (s/n): ");
                    maquina = new MaquinaPalomitas(codigo, marca, modelo, tarifa, porciones, carrito);
                    break;
                case 2:
                    int potencia = leerEntero("Potencia (vatios): ");
                    maquina = new MaquinaAlgodonAzucar(codigo, marca, modelo, tarifa, potencia);
                    break;
                default:
                    double capacidad = leerDouble("Capacidad (kg): ");
                    maquina = new FuenteChocolate(codigo, marca, modelo, tarifa, capacidad);
                    break;
            }

            if (inventario.registrarMaquina(maquina)) {
                System.out.println("Máquina registrada correctamente.");
            } else {
                System.out.println("No se pudo registrar: el código ya existe.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo registrar la máquina: " + e.getMessage());
        }
    }

    private static void consultarInventario() {
        System.out.println("--- Inventario ---");
        ArrayList<Maquina> maquinas = inventario.getMaquinas();
        if (maquinas.isEmpty()) {
            System.out.println("No hay máquinas registradas.");
            return;
        }
        for (Maquina m : maquinas) {
            System.out.println(descripcion(m));
        }
    }

    private static void buscarMaquina() {
        System.out.print("Código a buscar: ");
        String codigo = sc.nextLine();
        Maquina m = inventario.buscarPorCodigo(codigo);
        if (m == null) {
            System.out.println("No se encontró una máquina con ese código.");
        } else {
            System.out.println(descripcion(m));
        }
    }

    private static void cotizarAlquiler() {
        System.out.print("Código de la máquina: ");
        String codigo = sc.nextLine();
        int dias = leerEntero("Días de alquiler: ");
        try {
            double total = inventario.cotizar(codigo, dias);
            System.out.printf("Costo estimado: Q%.2f%n", total);
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo cotizar: " + e.getMessage());
        }
    }

    private static void confirmarAlquiler() {
        System.out.print("Código de la máquina: ");
        String codigo = sc.nextLine();
        int dias = leerEntero("Días de alquiler: ");

        double total;
        try {
            total = inventario.cotizar(codigo, dias);
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo cotizar: " + e.getMessage());
            return;
        }

        System.out.printf("El total a pagar es Q%.2f%n", total);
        boolean confirma = leerBooleano("¿Confirmar alquiler? (s/n): ");
        if (!confirma) {
            System.out.println("Alquiler cancelado. No se realizaron cambios.");
            return;
        }

        if (inventario.confirmarAlquiler(codigo, dias)) {
            System.out.println("Alquiler confirmado.");
        } else {
            System.out.println("No se pudo confirmar el alquiler (la máquina ya no está disponible).");
        }
    }

    private static void registrarDevolucion() {
        System.out.print("Código de la máquina: ");
        String codigo = sc.nextLine();
        if (inventario.registrarDevolucion(codigo)) {
            System.out.println("Devolución registrada. La máquina ya está disponible.");
        } else {
            System.out.println("No se pudo registrar la devolución (código inexistente o la máquina ya estaba disponible).");
        }
    }

    private static void mostrarReporte() {
        ArrayList<Maquina> maquinas = inventario.getMaquinas();

        int totalPalomitas = 0, dispPalomitas = 0, alqPalomitas = 0;
        int totalAlgodon = 0, dispAlgodon = 0, alqAlgodon = 0;
        int totalChocolate = 0, dispChocolate = 0, alqChocolate = 0;

        for (Maquina m : maquinas) {
            if (m instanceof MaquinaPalomitas) {
                totalPalomitas++;
                if (m.isDisponible()) dispPalomitas++; else alqPalomitas++;
            } else if (m instanceof MaquinaAlgodonAzucar) {
                totalAlgodon++;
                if (m.isDisponible()) dispAlgodon++; else alqAlgodon++;
            } else if (m instanceof FuenteChocolate) {
                totalChocolate++;
                if (m.isDisponible()) dispChocolate++; else alqChocolate++;
            }
        }

        int total = maquinas.size();
        int disponibles = dispPalomitas + dispAlgodon + dispChocolate;
        int alquiladas = alqPalomitas + alqAlgodon + alqChocolate;

        System.out.println("--- Reporte de Dulce Estación ---");
        System.out.println("Total de máquinas: " + total);
        System.out.println("Disponibles: " + disponibles);
        System.out.println("Alquiladas: " + alquiladas);
        System.out.println();
        System.out.println("Por categoría:");
        System.out.println("  Palomitas            -> Total: " + totalPalomitas
                + ", Disponibles: " + dispPalomitas + ", Alquiladas: " + alqPalomitas);
        System.out.println("  Algodón de azúcar    -> Total: " + totalAlgodon
                + ", Disponibles: " + dispAlgodon + ", Alquiladas: " + alqAlgodon);
        System.out.println("  Fuente de chocolate  -> Total: " + totalChocolate
                + ", Disponibles: " + dispChocolate + ", Alquiladas: " + alqChocolate);
        System.out.println();
        System.out.printf("Ingresos acumulados: Q%.2f%n", inventario.getIngresosAcumulados());
    }

    private static String descripcion(Maquina m) {
        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(m.getCodigoInventario());
        sb.append(" | Marca: ").append(m.getMarca());
        sb.append(" | Modelo: ").append(m.getModelo());
        sb.append(" | Tarifa diaria: Q").append(m.getTarifaDiaria());
        sb.append(" | Disponible: ").append(m.isDisponible() ? "Sí" : "No");

        if (m instanceof MaquinaPalomitas) {
            MaquinaPalomitas p = (MaquinaPalomitas) m;
            sb.append(" | Tipo: Palomitas | Porciones/hora: ").append(p.getPorcionesPorHora());
            sb.append(" | Carrito: ").append(p.isTieneCarrito() ? "Sí" : "No");
        } else if (m instanceof MaquinaAlgodonAzucar) {
            MaquinaAlgodonAzucar a = (MaquinaAlgodonAzucar) m;
            sb.append(" | Tipo: Algodón de azúcar | Potencia: ").append(a.getPotenciaVatios()).append("W");
        } else if (m instanceof FuenteChocolate) {
            FuenteChocolate f = (FuenteChocolate) m;
            sb.append(" | Tipo: Fuente de chocolate | Capacidad: ").append(f.getCapacidadKg()).append("kg");
        }
        return sb.toString();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = sc.nextLine();
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = sc.nextLine();
                return Double.parseDouble(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número.");
            }
        }
    }

    private static boolean leerBooleano(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim().toLowerCase();
            if (linea.equals("s") || linea.equals("si") || linea.equals("sí")) {
                return true;
            } else if (linea.equals("n") || linea.equals("no")) {
                return false;
            } else {
                System.out.println("Respuesta inválida. Escriba 's' o 'n'.");
            }
        }
    }
}