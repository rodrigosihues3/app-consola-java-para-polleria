package com.mycompany.apppolleria.clases;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Datos implements Serializable {

  public Repositorio<Clientes> clientes;
  public Repositorio<Productos> productosPollos;
  public Repositorio<Productos> productosEnsaladas;
  public Repositorio<Productos> productosPostres;
  public Repositorio<Productos> productosBebidas;
  public Repositorio<Ventas> ventas;
  public Mesas[] mesas;

  public Datos() {
    clientes = new Repositorio<>();
    productosPollos = new Repositorio<>();
    productosEnsaladas = new Repositorio<>();
    productosPostres = new Repositorio<>();
    productosBebidas = new Repositorio<>();
    ventas = new Repositorio<>();
    mesas = inicializarMesas();
  }

  private Mesas[] inicializarMesas() {
    Random random = new Random();
    Mesas[] arrayMesas = new Mesas[12];
    for (int i = 0; i < 12; i++) {
      arrayMesas[i] = new Mesas(i + 1, ((random.nextInt(10)) % 2 == 0));
    }
    return arrayMesas;
  }

  public static Datos cargar() {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("polleria.dat"))) {
      return (Datos) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("---- INICIANDO SISTEMA POR PRIMERA VEZ: CARGANDO DATOS POR DEFECTO ----");
      Datos datosNuevos = new Datos();
      datosNuevos.cargarDatosPorDefecto();
      datosNuevos.guardar(false);
      return datosNuevos;
    }
  }

  public void guardar(boolean mostrarMensaje) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("polleria.dat"))) {
      out.writeObject(this);
      if (mostrarMensaje)
        System.out.println("Datos guardados correctamente.");
    } catch (IOException e) {
      System.out.println("Error al guardar datos: " + e.getMessage());
    }
  }

  private void cargarDatosPorDefecto() {
    // PRODUCTOS
    // Pollos
    productosPollos.agregar(
        new Productos("1/8 Mostrito", 15.00, new String[] { "arroz chaufa", "papas fritas", "1/8 pollo", "ensalada" }));
    productosPollos.agregar(
        new Productos("1/8 Pollo a la brasa", 12.00, new String[] { "1/8 pollo", "papas fritas", "ensalada" }));
    productosPollos.agregar(
        new Productos("1/4 Pollo a la brasa", 18.00, new String[] { "1/4 pollo", "papas fritas", "ensalada" }));
    productosPollos.agregar(
        new Productos("1/2 Pollo a la brasa", 28.00, new String[] { "1/2 pollo", "papas fritas", "ensalada" }));
    productosPollos.agregar(new Productos("Pollo entero a la brasa", 52.00,
        new String[] { "1 pollo", "papas familiares", "ensalada grande" }));

    // Ensaladas
    productosEnsaladas
        .agregar(new Productos("Ensalada clásica", 5.00, new String[] { "lechuga", "tomate", "zanahoria", "limón" }));
    productosEnsaladas
        .agregar(new Productos("Ensalada rusa", 6.00, new String[] { "papa", "zanahoria", "arveja", "mayonesa" }));
    productosEnsaladas.agregar(new Productos("Ensalada de col", 5.00, new String[] { "col", "zanahoria", "crema" }));
    productosEnsaladas.agregar(
        new Productos("Ensalada mixta", 6.50, new String[] { "lechuga", "tomate", "pepino", "zanahoria", "limón" }));
    productosEnsaladas.agregar(
        new Productos("Ensalada con palta", 7.00, new String[] { "palta", "lechuga", "tomate", "aceite de oliva" }));

    // Postres
    productosPostres
        .agregar(new Productos("Helado personal", 4.00, new String[] { "vaso de helado", "sabor a elección" }));
    productosPostres.agregar(new Productos("Gelatina", 3.00, new String[] { "gelatina sabor fresa", "vasito" }));
    productosPostres
        .agregar(new Productos("Mazamorra morada", 4.00, new String[] { "maíz morado", "canela", "fruta picada" }));
    productosPostres
        .agregar(new Productos("Arroz con leche", 4.00, new String[] { "arroz cocido", "leche", "canela", "pasas" }));
    productosPostres.agregar(
        new Productos("Combo de postres", 7.00, new String[] { "mazamorra", "arroz con leche", "mitad y mitad" }));

    // Bebidas
    productosBebidas.agregar(new Productos("Inca Kola personal 500ml", 3.50, null));
    productosBebidas.agregar(new Productos("Inca Kola 1Lt", 6.00, null));
    productosBebidas.agregar(new Productos("Coca-Cola personal 500ml", 3.50, null));
    productosBebidas.agregar(new Productos("Jarra de chicha morada 1Lt", 8.00, null));
    productosBebidas.agregar(new Productos("Agua mineral 500ml", 2.50, null));

    // CLIENTES
    clientes.agregar(new Clientes("JUAN", "PERÉZ RAMIREZ", "12345678", "987654321"));
    clientes.agregar(new Clientes("ANA", "GÓMEZ RUIZ", "87654321", "923456789"));
    clientes.agregar(new Clientes("LUIS", "MARTÍNEZ TOMAYA", "11223344", "956789123"));
    clientes.agregar(new Clientes("MARIA", "LÓPEZ OCHOA", "44332211", "921654987"));
    clientes.agregar(new Clientes("RODRIGO", "SIHUES YANQUI", "74663928", "987654321"));

    // VENTAS
    // Venta 1 (Anterior)
    Ventas v1 = new Ventas(1, "JUAN", clientes.obtenerTodos().get(1), "Consumo en local", mesas[1],
        new ArrayList<>(Arrays.asList(productosPollos.obtenerTodos().get(1), productosEnsaladas.obtenerTodos().get(1),
            productosBebidas.obtenerTodos().get(1))),
        3, 24, "Efectivo", LocalDateTime.of(2025, 7, 12, 11, 0));
    ventas.agregar(v1);

    // Venta 2 (Anterior)
    Ventas v2 = new Ventas(2, "LUIS", clientes.obtenerTodos().get(1), "Para llevar", mesas[2],
        new ArrayList<>(Arrays.asList(productosPollos.obtenerTodos().get(2), productosPostres.obtenerTodos().get(1))),
        2, 21, "Yape / Plin", LocalDateTime.of(2025, 7, 12, 13, 30));
    ventas.agregar(v2);

    // Venta 3 (Anterior)
    Ventas v3 = new Ventas(3, "MARÍA", clientes.obtenerTodos().get(2), "Consumo en local", mesas[3],
        new ArrayList<>(Arrays.asList(productosPollos.obtenerTodos().get(2), productosBebidas.obtenerTodos().get(0),
            productosPostres.obtenerTodos().get(0))),
        3, 25.5, "Tarjeta de crédito", LocalDateTime.of(2025, 7, 12, 16, 15));
    ventas.agregar(v3);

    // Ventas del Día (Las agregamos al mismo repositorio general)
    // Venta 4 (Del día 1)
    Ventas v4 = new Ventas(4, "JUAN", clientes.obtenerTodos().get(3), "Consumo en local", mesas[0],
        new ArrayList<>(Arrays.asList(productosPollos.obtenerTodos().get(0), productosEnsaladas.obtenerTodos().get(0),
            productosBebidas.obtenerTodos().get(0))),
        3, 23, "Efectivo", LocalDateTime.now());
    ventas.agregar(v4);
  }
}
