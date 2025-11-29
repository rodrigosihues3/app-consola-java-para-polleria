package com.mycompany.apppolleria.clases;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ventas implements Serializable {

    private static final long serialVersionUID = 1L;
    private int numeroVenta;
    private String encargado;
    private Clientes cliente;
    private String tipoVenta;
    private Mesas mesa;
    private List<Productos> productos = new ArrayList<>();
    private int cantidadProductos;
    private double total;
    private String metodoPago;
    private LocalDateTime fechaVenta;

    public Ventas() {
    }

    public Ventas(int numeroVenta, String encargado, Clientes cliente, String tipoVenta, Mesas mesa,
            List<Productos> productos,
            int cantidadProductos, double total, String metodoPago, LocalDateTime fechaVenta) {
        this.numeroVenta = numeroVenta;
        this.encargado = encargado;
        this.cliente = cliente;
        this.tipoVenta = tipoVenta;
        this.mesa = mesa;
        this.productos = productos;
        this.cantidadProductos = cantidadProductos;
        this.total = total;
        this.metodoPago = metodoPago;
        this.fechaVenta = fechaVenta;
    }

    public int getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(int numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public Mesas getMesa() {
        return mesa;
    }

    public void setMesa(Mesas mesa) {
        this.mesa = mesa;
    }

    public List<Productos> getProductos() {
        return productos;
    }

    public void setProductos(List<Productos> productos) {
        this.productos = productos;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    // MÉTODOS LÓGICOS
    public void agregarProducto(Productos productoNuevo) {
        this.productos.add(productoNuevo);
    }

    public static Ventas buscarVentaFechaNumero(List<Ventas> listaVentas, LocalDate fecha, int numeroVenta) {
        for (Ventas venta : listaVentas) {
            if (venta.getFechaVenta().toLocalDate().isEqual(fecha)
                    && venta.getNumeroVenta() == numeroVenta) {
                return venta;
            }
        }

        return null;
    }

    public static void listarVentas(String titulo, List<Ventas> listaVentas, LocalDate fecha) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("|" + Menus.centrarTexto(58, titulo) + "|");
        System.out.println("=".repeat(60));

        for (Ventas venta : listaVentas) {
            if (fecha == null || fecha.isEqual(venta.getFechaVenta().toLocalDate())) {
                String fechaFormateada = venta.getFechaVenta()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                String numVentaFormateado = String.format("V-%04d", venta.getNumeroVenta());
                String dni = "N/A";
                String cliente = "CLIENTE NO REGISTRADO";
                if (venta.getCliente() != null) {
                    cliente = venta.getCliente().getNombres() + " " + venta.getCliente().getApellidos();
                    dni = venta.getCliente().getDni();
                }

                System.out.printf("| %-15s: %-39s |%n", "N° DE VENTA", numVentaFormateado);
                System.out.printf("| %-15s: %-39s |%n", "ENCARGADO", venta.getEncargado());
                System.out.printf("| %-15s: %-39s |%n", "CLIENTE", cliente);
                System.out.printf("| %-15s: %-39s |%n", "DNI", dni);
                if (venta.getTipoVenta().equals("Consumo en local")) {
                    System.out.printf("| %-15s: %-39s |%n", "MESA", venta.getMesa().getNumeroMesa());
                }
                System.out.printf("| %-15s: %-39s |%n", "TIPO DE VENTA", venta.getTipoVenta());
                System.out.printf("| %-15s: %-39s |%n", "FECHA DE VENTA", fechaFormateada);
                System.out.printf("| %-15s: %-39s |%n", "PRODUCTOS", "");

                for (Productos producto : venta.getProductos()) {
                    System.out.printf("|  - %-38s S/. %-10.2f |%n", producto.getNombre(), producto.getPrecio());
                }

                System.out.printf("| %-15s: %-39s |%n", "TOTAL", venta.getTotal());
                System.out.println("-".repeat(60));
            }
        }
    }

    public static void mostrarVenta(String titulo, Ventas venta) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("|" + Menus.centrarTexto(58, titulo) + "|");
        System.out.println("=".repeat(60));

        String fechaFormateada = venta.getFechaVenta()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String numVentaFormateado = String.format("V-%04d", venta.getNumeroVenta());
        String dni = "N/A";
        String cliente = "CLIENTE NO REGISTRADO";
        if (venta.getCliente() != null) {
            cliente = venta.getCliente().getNombres() + venta.getCliente().getApellidos();
            dni = venta.getCliente().getDni();
        }

        System.out.printf("| %-15s: %-39s |%n", "N° DE VENTA", numVentaFormateado);
        System.out.printf("| %-15s: %-39s |%n", "ENCARGADO", venta.getEncargado());
        System.out.printf("| %-15s: %-39s |%n", "CLIENTE", cliente);
        System.out.printf("| %-15s: %-39s |%n", "DNI", dni);
        if (venta.getTipoVenta().equals("Consumo en local")) {
            System.out.printf("| %-15s: %-39s |%n", "MESA", venta.getMesa().getNumeroMesa());
        }
        System.out.printf("| %-15s: %-39s |%n", "TIPO DE VENTA", venta.getTipoVenta());
        System.out.printf("| %-15s: %-39s |%n", "FECHA DE VENTA", fechaFormateada);
        System.out.printf("| %-15s: %-39s |%n", "PRODUCTOS", "");

        for (Productos producto : venta.getProductos()) {
            System.out.printf("|  - %-38s S/. %-10.2f |%n", producto.getNombre(), producto.getPrecio());
        }

        System.out.printf("| %-15s: %-39s |%n", "TOTAL", venta.getTotal());
        System.out.println("-".repeat(60));
    }

    public String generarVoucherVenta(double efectivoEntregado) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = this.fechaVenta.format(formatoFecha);
        String numeroVentaFormateado = String.format("V-%04d", this.numeroVenta);
        String strMonto;
        int longitud = 50;

        sb.append("=".repeat(longitud)).append("\n");
        sb.append(Menus.centrarTexto(longitud, "POLLERÍA 'EL SABOR CRIOLLO'")).append("\n");
        sb.append(Menus.centrarTexto(longitud, "RUC: 12345678901")).append("\n");
        sb.append(Menus.centrarTexto(longitud, "Av. Principal 123 - ICA")).append("\n");
        sb.append("=".repeat(longitud)).append("\n");

        sb.append(String.format("VENTA N°  : %s%n", numeroVentaFormateado));
        sb.append(String.format("FECHA     : %s%n", fechaFormateada));
        sb.append(String.format("CAJERO    : %s%n", this.encargado));
        sb.append(String.format("PAGO      : %s%n", this.metodoPago));
        sb.append(String.format("TIPO      : %s%n", this.tipoVenta));

        if (this.tipoVenta.equalsIgnoreCase("Consumo en local") && mesa != null) {
            sb.append(String.format("MESA      : %d%n", mesa.getNumeroMesa()));
        }

        if (cliente != null) {
            sb.append("CLIENTE   : ").append(cliente.getNombres()).append(" ").append(cliente.getApellidos())
                    .append("\n");
            sb.append("DNI       : ").append(cliente.getDni()).append("\n");
        } else {
            sb.append("CLIENTE   : CLIENTE\n");
        }

        sb.append("\n");
        sb.append("-".repeat(longitud)).append("\n");
        sb.append(String.format("%-30s    %-15s%n",
                Menus.centrarTexto(30, "Productos"),
                Menus.centrarTexto(15, "Precio")))
                .append("-".repeat(longitud)).append("\n");

        for (Productos producto : productos) {
            strMonto = "S/. " + String.format("%.2f", producto.getPrecio());
            sb.append(String.format("%-35s %-15s%n", producto.getNombre(), strMonto));
        }

        sb.append("-".repeat(longitud)).append("\n");
        strMonto = "S/. " + String.format("%.2f", total);
        sb.append(String.format("%-35s %-15s%n", "TOTAL A PAGAR", strMonto));
        sb.append("-".repeat(longitud)).append("\n").append("\n");

        // Se muestra solo si es efectivo
        if ("Efectivo".equalsIgnoreCase(metodoPago)) {
            sb.append("-".repeat(longitud)).append("\n");
            strMonto = "S/. " + String.format("%.2f", efectivoEntregado);
            sb.append(String.format("%-35s %-15s%n", "Paga con", strMonto));

            strMonto = "S/. " + String.format("%.2f", (efectivoEntregado - total));
            sb.append(String.format("%-35s %-15s%n", "Vuelto", strMonto));
            sb.append("-".repeat(longitud)).append("\n").append("\n");
        }

        sb.append("=".repeat(longitud)).append("\n");
        sb.append(Menus.centrarTexto(longitud, "¡GRACIAS POR SU COMPRA!")).append("\n");
        sb.append("=".repeat(longitud)).append("\n");

        return sb.toString();
    }

    public void exportarVoucherVenta(String voucher) {
        String nombreArchivo = String.format("voucher_%04d.txt", this.numeroVenta);

        try (java.io.FileWriter writer = new java.io.FileWriter(nombreArchivo)) {
            writer.write(voucher);
            System.out.println("> Voucher generado correctamente: " + nombreArchivo);
        } catch (java.io.IOException e) {
            System.out.println("Error al generar el voucher: " + e.getMessage());
        }
    }

}
