package com.mycompany.apppolleria.clases;

public class Clientes {

    private String nombres;
    private String apellidos;
    private String dni;
    private String celular;

    public Clientes() {
    }

    public Clientes(String nombres, String apellidos, String dni, String celular) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.celular = celular;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public static Clientes buscarClienteDNI(Clientes[] listaClientes, String dni) {
        for (Clientes cliente : listaClientes) {
            if (cliente.getDni().equals(dni)) {
                return cliente; // Retorna el cliente encontrado
            }
        }

        return null;
    }

    public static void listarClientes(String titulo, Clientes[] listaClientes) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("|" + Menus.centrarTexto(58, titulo) + "|");
        System.out.println("=".repeat(60));

        for (Clientes cliente : listaClientes) {
            if (cliente != null) {
                System.out.printf("| %-15s: %-39s |%n", "NOMBRES", cliente.getNombres());
                System.out.printf("| %-15s: %-39s |%n", "APELLIDOS", cliente.getApellidos());
                System.out.printf("| %-15s: %-39s |%n", "DNI", cliente.getDni());
                System.out.printf("| %-15s: %-39s |%n", "CELULAR", cliente.getCelular());
                System.out.println("-".repeat(60));
            }
        }
    }

    public static void mostrarCliente(String titulo, Clientes cliente) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("|" + Menus.centrarTexto(58, titulo) + "|");
        System.out.println("=".repeat(60));

        System.out.printf("| %-15s: %-39s |%n", "NOMBRES", cliente.getNombres());
        System.out.printf("| %-15s: %-39s |%n", "APELLIDOS", cliente.getApellidos());
        System.out.printf("| %-15s: %-39s |%n", "DNI", cliente.getDni());
        System.out.printf("| %-15s: %-39s |%n", "CELULAR", cliente.getCelular());

        System.out.println("-".repeat(60));
    }

}
