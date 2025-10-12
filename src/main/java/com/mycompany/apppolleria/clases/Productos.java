package com.mycompany.apppolleria.clases;

public class Productos {

  private String nombre;
  private double precio;
  private String[] contenido;

  public Productos() {
  }

  public Productos(String nombre, double precio, String[] contenido) {
    this.nombre = nombre;
    this.precio = precio;
    this.contenido = contenido;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  public String[] getContenido() {
    return contenido;
  }

  public void setContenido(String[] contenido) {
    this.contenido = contenido;
  }

  public static Productos buscarProductoPorNombre(Productos[] listaProductos, String nombre) {
    if (nombre == null || nombre.trim().isEmpty()) {
      return null;
    }

    nombre = nombre.trim().toLowerCase();
    for (Productos producto : listaProductos) {
      if (producto.getNombre().toLowerCase().contains(nombre)) {
        return producto;
      }
    }

    return null;
  }

  public static void listarProductos(String titulo, Productos[] listaProductos) {
    System.out.println("\n" + "=".repeat(60));
    System.out.println("|" + Menus.centrarTexto(58, titulo) + "|");
    System.out.println("=".repeat(60));

    for (Productos producto : listaProductos) {
      if (producto != null) {
        System.out.printf("| %-10s: %-44s |%n", "NOMBRE", producto.getNombre());
        System.out.printf("| %-10s: S/. %-40.2f |%n", "PRECIO", producto.getPrecio());

        if (producto.getContenido() != null) {
          System.out.printf("| %-10s: %-44s |%n", "CONTENIDO", "");
          for (String item : producto.getContenido()) {
            System.out.printf("|  - %-53s |%n", item);
          }
        }

        System.out.println("-".repeat(60));
      }
    }
  }

  public static void mostrarProducto(String titulo, Productos producto) {
    System.out.println("\n" + "=".repeat(60));
    System.out.println("|" + Menus.centrarTexto(58, titulo) + "|");
    System.out.println("=".repeat(60));

    System.out.printf("| %-10s: %-44s |%n", "NOMBRE", producto.getNombre());
    System.out.printf("| %-10s: S/. %-40.2f |%n", "PRECIO", producto.getPrecio());

    if (producto.getContenido() != null) {
      System.out.printf("| %-10s: %-44s |%n", "CONTENIDO", "");
      for (String item : producto.getContenido()) {
        System.out.printf("|  - %-53s |%n", item);
      }
    }

    System.out.println("-".repeat(60));
  }

}
