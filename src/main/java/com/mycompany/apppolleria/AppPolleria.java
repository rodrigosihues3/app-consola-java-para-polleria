package com.mycompany.apppolleria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.mycompany.apppolleria.clases.Clientes;
import com.mycompany.apppolleria.clases.Menus;
import com.mycompany.apppolleria.clases.Mesas;
import com.mycompany.apppolleria.clases.Productos;
import com.mycompany.apppolleria.clases.Ventas;

public class AppPolleria {

  // public static final String ANSI_RESET = "\u001B[0m";
  // public static final String ANSI_RED = "\u001B[31m";
  // public static final String ANSI_GREEN = "\u001B[32m";

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    Ventas[] listaVentasAnteriores = new Ventas[0]; // Arreglo para almacenar las ventas anteriores
    Ventas[] listaVentasDelDia = new Ventas[0]; // Arreglo para almacenar las ventas
    Clientes[] listaClientes = new Clientes[0]; // Arreglo para almacenar los clientes
    Mesas[] mesas = new Mesas[0]; // Arreglo para almacenar las mesas
    Productos[] listaProductosPollos = new Productos[0]; // Arreglo para almacenar los productos de pollos
    Productos[] listaProductosEnsaladas = new Productos[0]; // Arreglo para almacenar los productos de ensaladas
    Productos[] listaProductosPostres = new Productos[0]; // Arreglo para almacenar los productos de postres
    Productos[] listaProductosBebidas = new Productos[0]; // Arreglo para almacenar los productos de bebidas

    mesas = inicializarMesas(mesas); // Inicializar las mesas

    // INICIALIZACIÓN DE DATOS DEL NEGOCIO
    listaProductosPollos = agregarProducto(listaProductosPollos, new Productos("1/8 Mostrito", 15.00,
        new String[] { "arroz chaufa", "papas fritas", "1/8 pollo", "ensalada" }));
    listaProductosPollos = agregarProducto(listaProductosPollos, new Productos("1/8 Pollo a la brasa", 12.00,
        new String[] { "1/8 pollo", "papas fritas", "ensalada" }));
    listaProductosPollos = agregarProducto(listaProductosPollos, new Productos("1/4 Pollo a la brasa", 18.00,
        new String[] { "1/4 pollo", "papas fritas", "ensalada" }));
    listaProductosPollos = agregarProducto(listaProductosPollos, new Productos("1/2 Pollo a la brasa", 28.00,
        new String[] { "1/2 pollo", "papas fritas", "ensalada" }));
    listaProductosPollos = agregarProducto(listaProductosPollos, new Productos("Pollo entero a la brasa", 52.00,
        new String[] { "1 pollo", "papas familiares", "ensalada grande" }));

    listaProductosEnsaladas = agregarProducto(listaProductosEnsaladas, new Productos("Ensalada clásica", 5.00,
        new String[] { "lechuga", "tomate", "zanahoria", "limón" }));
    listaProductosEnsaladas = agregarProducto(listaProductosEnsaladas, new Productos("Ensalada rusa", 6.00,
        new String[] { "papa", "zanahoria", "arveja", "mayonesa" }));
    listaProductosEnsaladas = agregarProducto(listaProductosEnsaladas, new Productos("Ensalada de col", 5.00,
        new String[] { "col", "zanahoria", "crema" }));
    listaProductosEnsaladas = agregarProducto(listaProductosEnsaladas, new Productos("Ensalada mixta", 6.50,
        new String[] { "lechuga", "tomate", "pepino", "zanahoria", "limón" }));
    listaProductosEnsaladas = agregarProducto(listaProductosEnsaladas, new Productos("Ensalada con palta", 7.00,
        new String[] { "palta", "lechuga", "tomate", "aceite de oliva" }));

    listaProductosPostres = agregarProducto(listaProductosPostres, new Productos("Helado personal", 4.00,
        new String[] { "vaso de helado", "sabor a elección" }));
    listaProductosPostres = agregarProducto(listaProductosPostres, new Productos("Gelatina", 3.00,
        new String[] { "gelatina sabor fresa", "vasito" }));
    listaProductosPostres = agregarProducto(listaProductosPostres, new Productos("Mazamorra morada", 4.00,
        new String[] { "maíz morado", "canela", "fruta picada" }));
    listaProductosPostres = agregarProducto(listaProductosPostres, new Productos("Arroz con leche", 4.00,
        new String[] { "arroz cocido", "leche", "canela", "pasas" }));
    listaProductosPostres = agregarProducto(listaProductosPostres,
        new Productos("Combo de postres", 7.00, new String[] { "mazamorra", "arroz con leche", "mitad y mitad" }));

    listaProductosBebidas = agregarProducto(listaProductosBebidas,
        new Productos("Inca Kola personal 500ml", 3.50, null));
    listaProductosBebidas = agregarProducto(listaProductosBebidas,
        new Productos("Inca Kola 1Lt", 6.00, null));
    listaProductosBebidas = agregarProducto(listaProductosBebidas,
        new Productos("Coca-Cola personal 500ml", 3.50, null));
    listaProductosBebidas = agregarProducto(listaProductosBebidas,
        new Productos("Jarra de chicha morada 1Lt", 8.00, null));
    listaProductosBebidas = agregarProducto(listaProductosBebidas,
        new Productos("Agua mineral 500ml", 2.50, null));

    listaClientes = agregarCliente(listaClientes, new Clientes("JUAN", "PERÉZ RAMIREZ", "12345678", "987654321"));
    listaClientes = agregarCliente(listaClientes, new Clientes("ANA", "GÓMEZ RUIZ", "87654321", "923456789"));
    listaClientes = agregarCliente(listaClientes, new Clientes("LUIS", "MARTÍNEZ TOMAYA", "11223344", "956789123"));
    listaClientes = agregarCliente(listaClientes, new Clientes("MARIA", "LÓPEZ OCHOA", "44332211", "921654987"));
    listaClientes = agregarCliente(listaClientes, new Clientes("RODRIGO", "SIHUES YANQUI", "74663928", "987654321"));

    listaVentasAnteriores = agregarVenta(listaVentasAnteriores,
        new Ventas(1, "JUAN", listaClientes[1], "Consumo en local", mesas[1],
            new Productos[] { listaProductosPollos[1], listaProductosEnsaladas[1], listaProductosBebidas[1] },
            3, 24, "Efectivo", LocalDateTime.of(2025, 7, 12, 11, 0)));

    listaVentasAnteriores = agregarVenta(listaVentasAnteriores,
        new Ventas(2, "LUIS", listaClientes[1], "Para llevar", mesas[2],
            new Productos[] { listaProductosPollos[2], listaProductosPostres[1] },
            2, 21, "Yape / Plin", LocalDateTime.of(2025, 7, 12, 13, 30)));

    listaVentasAnteriores = agregarVenta(listaVentasAnteriores,
        new Ventas(3, "MARÍA", listaClientes[2], "Consumo en local", mesas[3],
            new Productos[] { listaProductosPollos[2], listaProductosBebidas[0], listaProductosPostres[0] },
            3, 25.5, "Tarjeta de crédito", LocalDateTime.of(2025, 7, 12, 16, 15)));

    listaVentasDelDia = agregarVenta(listaVentasDelDia,
        new Ventas(1, "JUAN", listaClientes[3], "Consumo en local", mesas[0],
            new Productos[] { listaProductosPollos[0], listaProductosEnsaladas[0], listaProductosBebidas[0] },
            3, 23, "Efectivo", LocalDateTime.now()));
    listaVentasDelDia = agregarVenta(listaVentasDelDia,
        new Ventas(2, "JUAN", listaClientes[3], "Consumo en local", mesas[0],
            new Productos[] { listaProductosPollos[0], listaProductosEnsaladas[0], listaProductosBebidas[0] },
            3, 23.5, "Efectivo", LocalDateTime.now()));
    listaVentasDelDia = agregarVenta(listaVentasDelDia,
        new Ventas(3, "JUAN", listaClientes[3], "Consumo en local", mesas[0],
            new Productos[] { listaProductosPollos[0], listaProductosEnsaladas[1], listaProductosBebidas[1] },
            3, 27, "Efectivo", LocalDateTime.now()));
    listaVentasDelDia = agregarVenta(listaVentasDelDia,
        new Ventas(4, "JUAN", listaClientes[1], "Consumo en local", mesas[0],
            new Productos[] { listaProductosPollos[0], listaProductosEnsaladas[1], listaProductosBebidas[1] },
            3, 27, "Efectivo", LocalDateTime.now()));

    // MENU PRINCIPAL
    Menus menuPrincipal = new Menus("POLLERIA - MENU PRINCIPAL",
        new String[] {
            "Realizar venta",
            "Gestionar ventas",
            "Gestionar clientes",
            "Gestionar productos",
            "Reportes y estadisticas",
            "Salir"
        });

    // 1. SUB MENU REALIZAR PEDIDO, antes se toman los datos del cliente
    Menus menuRealizarVenta = new Menus("SELECCIONE LOS PRODUCTOS DEL PEDIDO",
        new String[] {
            "Pollos",
            "Ensaladas",
            "Postres",
            "Bebidas",
            "Generar venta",
            "Cancelar venta"
        });

    // SUB MENU DE REALIZAR PEDIDO - POLLOS
    Menus subMenuRealizarPedidoPollos = new Menus("POLLOS", enviarOpciones(listaProductosPollos));
    // SUB MENU DE REALIZAR PEDIDO - ENSALADAS
    Menus subMenuRealizarPedidoEnsaladas = new Menus("ENSALADAS", enviarOpciones(listaProductosEnsaladas));
    // SUB MENU DE REALIZAR PEDIDO - POSTRES
    Menus subMenuRealizarPedidoPostres = new Menus("POSTRES", enviarOpciones(listaProductosPostres));
    // SUB MENU DE REALIZAR PEDIDO - BEBIDAS
    Menus subMenuRealizarPedidoBebidas = new Menus("BEBIDAS", enviarOpciones(listaProductosBebidas));

    // SUB MENU DE MÉTODO DE PAGO
    Menus subMenuMetodoPago = new Menus("MÉTODO DE PAGO",
        new String[] {
            "Efectivo",
            "Tarjeta de crédito",
            "Tarjeta de débito",
            "Yape / Plin",
            "Transferencia bancaria",
            "Volver al menú anterior"
        });

    // 2. SUB MENU GESTIONAR VENTAS
    Menus menuGestionarVentas = new Menus("MENU - GESTIONAR VENTAS",
        new String[] {
            "Listar ventas de hoy",
            "Listar ventas segun fecha",
            "Buscar venta",
            "Volver al menu principal"
        });

    // 3. SUB MENU GESTIONAR VENTAS
    Menus menuGestionarClientes = new Menus("MENU - GESTIONAR CLIENTES",
        new String[] {
            "Listar clientes",
            "Buscar cliente",
            "Registrar cliente",
            "Volver al menu principal"
        });

    // 4. SUB MENU GESTIONAR PRODUCTOS
    Menus menuGestionarProductos = new Menus("MENU - GESTIONAR PRODUCTOS",
        new String[] {
            "Listar productos",
            "Buscar producto",
            "Agregar producto",
            "Volver al menu principal"
        });

    // 5. SUB MENU GESTIONAR PRODUCTOS
    Menus menuReportes = new Menus("MENU - REPORTES Y ESTADISTICAS",
        new String[] {
            "Reporte del dia",
            "Reporte por fecha",
            "Productos mas vendidos",
            "Cliente mas frecuente",
            "Volver al menu principal"
        });

    String nombreEncargado = "JUAN"; // Nombre del encargado

    // BUCLE PRINCIPAL - INICIO DEL PROGRAMA
    int opcionMenuPrincipal;
    boolean iniciarPrograma = true;
    do {
      System.out.print(menuPrincipal);
      opcionMenuPrincipal = scanner.nextInt();
      scanner.nextLine(); // Limpiar el buffer

      switch (opcionMenuPrincipal) {
        case 1 -> { // REALIZAR VENTA
          Ventas venta = new Ventas();
          menuRealizarVenta.setTitulo("SELECCIONE LOS PRODUCTOS DEL PEDIDO");

          // PROCESO PARA BUSCAR CLIENTE
          Clientes cliente;
          String dniCliente;
          String nombresCliente = "CLIENTE";
          String apellidosCliente;
          String celularCliente = "000000000";

          // MENU PARA INGRESAR O BUSCAR CLIENTE
          boolean iniciarMenuCliente = true;
          do {
            System.out.print("\nIngrese el DNI del cliente: ");
            dniCliente = scanner.next();
            scanner.nextLine(); // Limpiar el buffer

            cliente = Clientes.buscarClienteDNI(listaClientes, dniCliente);

            if (cliente != null) {
              nombresCliente = cliente.getNombres();
              apellidosCliente = cliente.getApellidos();
              celularCliente = cliente.getCelular();

              System.out.println("\n" + "=".repeat(50));
              System.out.println("|" + Menus.centrarTexto(48, "CLIENTE ENCONTRADO") + "|");
              System.out.println("=".repeat(50));
              System.out.printf("| %-10s: %-35s|%n", "DNI", dniCliente);
              System.out.printf("| %-10s: %-35s|%n", "Cliente", nombresCliente);
              System.out.printf("| %-10s: %-35s|%n", "Apellidos", apellidosCliente);
              System.out.printf("| %-10s: %-35s|%n", "Celular", celularCliente);
              System.out.println("=".repeat(50));

              iniciarMenuCliente = false;
            } else {
              mostrarMenu(
                  "CLIENTE NO ENCONTRADO",
                  "¿Desea registrar al cliente?",
                  "*",
                  "Sí", "No, continuar la venta", "No, volver a ingresar el DNI");
              int opcionRegistrarCliente = scanner.nextInt();
              scanner.nextLine(); // Limpiar el buffer

              switch (opcionRegistrarCliente) {
                case 1 -> {
                  System.out.println("\n" + "#".repeat(50));
                  System.out.println(
                      "|" + Menus.centrarTexto(48, "INGRESE LOS DATOS DEL CLIENTE") + "|");
                  System.out.println("#".repeat(50));
                  System.out.printf("  %-10s: ", "DNI");
                  dniCliente = scanner.next();
                  scanner.nextLine(); // Limpiar el buffer
                  System.out.printf("  %-10s: ", "Nombre");
                  nombresCliente = scanner.nextLine();
                  System.out.printf("  %-10s: ", "Apellidos");
                  apellidosCliente = scanner.nextLine();
                  System.out.printf("  %-10s: ", "Celular");
                  celularCliente = scanner.nextLine();

                  cliente = new Clientes(
                      nombresCliente.toUpperCase(),
                      apellidosCliente.toUpperCase(),
                      dniCliente,
                      celularCliente);

                  // PROCESO PARA GUARDAR CLIENTE
                  agregarCliente(listaClientes, cliente);
                  venta.setCliente(cliente);

                  System.out.println("\n" + "=".repeat(50));
                  System.out.println(
                      "|" + Menus.centrarTexto(48, "CLIENTE REGISTRADO CON ÉXITO") + "|");
                  System.out.println("=".repeat(50));
                  System.out.printf("| %-10s: %-35s|%n", "DNI", dniCliente);
                  System.out.printf("| %-10s: %-35s|%n", "Cliente", nombresCliente);
                  System.out.printf("| %-10s: %-35s|%n", "Apellidos", apellidosCliente);
                  System.out.printf("| %-10s: %-35s|%n", "Celular", celularCliente);
                  System.out.println("=".repeat(50));

                  iniciarMenuCliente = false;
                }
                case 2 -> {
                  dniCliente = "00000000";
                  cliente = null;

                  venta.setCliente(cliente);

                  System.out.println("\n" + "=".repeat(50));
                  System.out.println(
                      "|" + Menus.centrarTexto(48, "DATOS POR DEFECTO A USAR") + "|");
                  System.out.println("=".repeat(50));
                  System.out.printf("| %-10s: %-35s|%n", "DNI", dniCliente);
                  System.out.printf("| %-10s: %-35s|%n", "Cliente", nombresCliente);
                  System.out.printf("| %-10s: %-35s|%n", "Celular", celularCliente);
                  System.out.println("=".repeat(50));

                  iniciarMenuCliente = false;
                }
                case 3 ->
                  System.out.println("Volviendo a solicitar el DNI...");
                default ->
                  System.out.println("Opción no válida, por favor intente de nuevo.");
              }
            }
          } while (iniciarMenuCliente);

          // PROCESO PARA SELECCIONAR TIPO DE VENTA Y MESA (si aplica)
          int opcionTipoVenta;
          int mesaCliente;
          String tipoVenta;

          // MOSRA MENÚ PARA SELECCIONAR TIPO DE VENTA
          boolean iniciarMenuTipoVenta;
          do {
            mostrarMenu("TIPO DE VENTA", "", "*", "Consumo en local", "Para llevar", "Delivery");
            opcionTipoVenta = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffers

            switch (opcionTipoVenta) {
              case 1 -> {
                tipoVenta = "Consumo en local";
                venta.setTipoVenta(tipoVenta);

                boolean iniciarMenuNumeroMesa = true;
                do {
                  System.out.println("\n" + "=".repeat(50));
                  System.out.println("|" + Menus.centrarTexto(48, "SELECCIONAR MESA") + "|");
                  System.out.println("=".repeat(50) + "\n");
                  mostrarMesas(3, 4, mesas);
                  System.out.println("=".repeat(50));
                  System.out.print("Ingrese solo el número de mesa: ");
                  mesaCliente = scanner.nextInt();
                  scanner.nextLine(); // Limpiar el buffer

                  if (mesaCliente >= 1 && mesaCliente <= 12) {
                    if (mesas[mesaCliente - 1].isEstadoMesa()) {
                      venta.setMesa(mesas[mesaCliente - 1]);

                      System.out.println("Mesa seleccionada: M-"
                          + String.format("%02d", venta.getMesa().getNumeroMesa()));

                      menuRealizarVenta.setTitulo("M-"
                          + String.format("%02d", venta.getMesa().getNumeroMesa())
                          + " | SELECCIONE LOS PRODUCTOS DEL PEDIDO");

                      iniciarMenuNumeroMesa = false;
                    } else {
                      System.out.println("La mesa "
                          + String.format("M-%02d", mesaCliente) + " ESTÁ OCUPADA"
                          + ", por favor seleccione otra mesa.");
                    }
                  } else {
                    System.out.println("Número de mesa inválido, por favor intente de nuevo.");
                  }
                } while (iniciarMenuNumeroMesa);
              }
              case 2 -> {
                tipoVenta = "Para llevar";
                venta.setTipoVenta(tipoVenta);
                venta.setMesa(null);
                System.out.println("Venta para llevar seleccionado.");
              }
              case 3 -> {
                tipoVenta = "Delivery";
                venta.setTipoVenta(tipoVenta);
                venta.setMesa(null);
                System.out.println("Venta para delivery seleccionado.");
              }
              default ->
                System.out.println("Opción no válida, por favor intente de nuevo.");
            }

            iniciarMenuTipoVenta = !(opcionTipoVenta >= 1 && opcionTipoVenta <= 3);
          } while (iniciarMenuTipoVenta);

          // MENU PARA SELECCIONAR PRODUCTOS
          boolean iniciarMenuRealizarVenta = true;
          do {
            int opcionTipoProducto;
            int opcionProducto;

            System.out.print(menuRealizarVenta);
            opcionTipoProducto = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            boolean iniciarSubMenuElegirProducto;
            switch (opcionTipoProducto) {
              case 1 -> { // Pollos
                do {
                  System.out.print(subMenuRealizarPedidoPollos);
                  opcionProducto = scanner.nextInt();
                  scanner.nextLine(); // Limpiar el buffer

                  if (opcionProducto >= 1 && opcionProducto <= subMenuRealizarPedidoPollos
                      .getOpciones().length - 1) {
                    String producto = subMenuRealizarPedidoPollos.getOpciones()[opcionProducto - 1];
                    Productos productoElegido = Productos.buscarProductoPorNombre(listaProductosPollos, producto);

                    // PROCESO PARA AGREGAR PRODUCTO
                    venta.agregarProducto(productoElegido);
                    // MOSTRAR DETALLES DEL PRODUCTO ELEGIDO
                    mostrarDetallesProducto(productoElegido);
                  } else if (opcionProducto == subMenuRealizarPedidoPollos.getOpciones().length) {
                    System.out.println("Volviendo al menú anterior...");
                  } else {
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                  }

                  iniciarSubMenuElegirProducto = !(opcionProducto >= 1 && opcionProducto <= subMenuRealizarPedidoPollos
                      .getOpciones().length);
                } while (iniciarSubMenuElegirProducto);
              }
              case 2 -> { // Ensaladas
                do {
                  System.out.print(subMenuRealizarPedidoEnsaladas);
                  opcionProducto = scanner.nextInt();
                  scanner.nextLine(); // Limpiar el buffer

                  if (opcionProducto >= 1 && opcionProducto <= subMenuRealizarPedidoEnsaladas
                      .getOpciones().length - 1) {
                    String producto = subMenuRealizarPedidoEnsaladas.getOpciones()[opcionProducto - 1];
                    Productos productoElegido = Productos.buscarProductoPorNombre(listaProductosEnsaladas, producto);

                    // PROCESO PARA AGREGAR PRODUCTO
                    venta.agregarProducto(productoElegido);
                    // MOSTRAR DETALLES DEL PRODUCTO ELEGIDO
                    mostrarDetallesProducto(productoElegido);
                  } else if (opcionProducto == subMenuRealizarPedidoEnsaladas.getOpciones().length) {
                    System.out.println("Volviendo al menú anterior...");
                  } else {
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                  }

                  iniciarSubMenuElegirProducto = !(opcionProducto >= 1
                      && opcionProducto <= subMenuRealizarPedidoEnsaladas
                          .getOpciones().length);
                } while (iniciarSubMenuElegirProducto);
              }
              case 3 -> { // Postres
                do {
                  System.out.print(subMenuRealizarPedidoPostres);
                  opcionProducto = scanner.nextInt();
                  scanner.nextLine(); // Limpiar el buffer

                  if (opcionProducto >= 1 && opcionProducto <= subMenuRealizarPedidoPostres
                      .getOpciones().length - 1) {
                    String producto = subMenuRealizarPedidoPostres.getOpciones()[opcionProducto - 1];
                    Productos productoElegido = Productos.buscarProductoPorNombre(listaProductosPostres, producto);

                    // PROCESO PARA AGREGAR PRODUCTO
                    venta.agregarProducto(productoElegido);
                    // MOSTRAR DETALLES DEL PRODUCTO ELEGIDO
                    mostrarDetallesProducto(productoElegido);
                  } else if (opcionProducto == subMenuRealizarPedidoPostres.getOpciones().length) {
                    System.out.println("Volviendo al menú anterior...");
                  } else {
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                  }

                  iniciarSubMenuElegirProducto = !(opcionProducto >= 1 && opcionProducto <= subMenuRealizarPedidoPostres
                      .getOpciones().length);
                } while (iniciarSubMenuElegirProducto);
              }
              case 4 -> { // Bebidas
                do {
                  System.out.print(subMenuRealizarPedidoBebidas);
                  opcionProducto = scanner.nextInt();
                  scanner.nextLine();

                  if (opcionProducto >= 1 && opcionProducto <= subMenuRealizarPedidoBebidas
                      .getOpciones().length - 1) {
                    String producto = subMenuRealizarPedidoBebidas.getOpciones()[opcionProducto - 1];
                    Productos productoElegido = Productos.buscarProductoPorNombre(listaProductosBebidas, producto);

                    // PROCESO PARA AGREGAR PRODUCTO
                    venta.agregarProducto(productoElegido);
                    // MOSTRAR DETALLES DEL PRODUCTO ELEGIDO
                    mostrarDetallesProducto(productoElegido);
                  } else if (opcionProducto == subMenuRealizarPedidoBebidas.getOpciones().length) {
                    System.out.println("Volviendo al menú anterior...");
                  } else {
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                  }

                  iniciarSubMenuElegirProducto = !(opcionProducto >= 1 && opcionProducto <= subMenuRealizarPedidoBebidas
                      .getOpciones().length);
                } while (iniciarSubMenuElegirProducto);
              }
              case 5 -> { // Generar venta
                String metodoPago;
                double montoEfectivo = 0;
                double vuelto;
                double totalVenta = 0.0;
                String voucher;

                for (Productos producto : venta.getProductos()) {
                  totalVenta += producto.getPrecio(); // Suponiendo un precio fijo por producto
                }

                int opcionMetodoPago;
                boolean iniciarSubMenuMetodoPago = true;
                do {
                  String totalVentaFormateado = String.format("%.2f", totalVenta);
                  subMenuMetodoPago.setTitulo("METODO DE PAGO - TOTAL S/ " + totalVentaFormateado);
                  System.out.print(subMenuMetodoPago);
                  opcionMetodoPago = scanner.nextInt();
                  scanner.nextLine(); // Limpiar el buffer

                  if (opcionMetodoPago >= 1 || opcionMetodoPago < 6) {
                    metodoPago = subMenuMetodoPago.getOpciones()[opcionMetodoPago
                        - 1];

                    if (opcionMetodoPago == 1) {
                      do {
                        System.out.print("Ingrese el monto en efectivo: ");
                        montoEfectivo = scanner.nextDouble();
                        scanner.nextLine(); // Limpiar el buffer

                        vuelto = montoEfectivo - totalVenta;

                        if (vuelto < 0)
                          System.out.println("El monto ingresado es insuficiente.\n");
                      } while (vuelto < 0);
                    }

                    // Proceso para guardar la venta
                    venta.setNumeroVenta(listaVentasDelDia.length + 1);
                    venta.setEncargado(nombreEncargado);
                    venta.setCliente(cliente);
                    venta.setCantidadProductos(venta.getProductos().length);
                    venta.setTotal(totalVenta);
                    venta.setMetodoPago(metodoPago);
                    venta.setFechaVenta(LocalDateTime.now());

                    listaVentasDelDia = agregarVenta(listaVentasDelDia, venta);

                    // PROCESO O FUNCION PARA MOSTRAR VOUCHER
                    try {
                      System.out.println("\nVenta registrada con éxito");
                      Thread.sleep(1000);

                      System.out.println("\nGenerando voucher de venta...");
                      Thread.sleep(1000);

                      System.out.println("\nVoucher generado con éxito...");
                      Thread.sleep(1000);

                      System.out.println("\nMostrando voucher...\n");
                      Thread.sleep(1000);

                      montoEfectivo = opcionMetodoPago == 1 ? montoEfectivo : 0;

                      voucher = venta.generarVoucherVenta(montoEfectivo);

                      System.out.println(voucher);
                      Thread.sleep(1000);

                      System.out.println("\n\nExportando voucher...");
                      Thread.sleep(1000);

                      System.out.println("\nVoucher exportado con éxito...");
                      Thread.sleep(1000);

                      System.out.println();
                      venta.exportarVoucherVenta(voucher);
                    } catch (InterruptedException e) {
                    }

                    iniciarMenuRealizarVenta = false;
                    iniciarSubMenuMetodoPago = false;
                  } else if (opcionMetodoPago == 6) {
                    System.out.println("Volviendo al menú anterior...");

                    iniciarSubMenuMetodoPago = false;
                  } else {
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                  }
                } while (iniciarSubMenuMetodoPago);
              }
              case 6 -> { // Cancelar pedido y volver al menu principal
                System.out.println("Pedido cancelado, volviendo al menu principal...");

                iniciarMenuRealizarVenta = false;
              }
              default ->
                System.out.println("Opción no válida, vuelva a elegir.");
            }
          } while (iniciarMenuRealizarVenta);
        }
        case 2 -> // GESTIONAR VENTAS
        {
          int opcionGestionarVentas;

          boolean iniciarMenuGestionarVentas = true;
          do {
            System.out.print(menuGestionarVentas);
            opcionGestionarVentas = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcionGestionarVentas) {
              case 1 -> { // Listar ventas del dia actual
                if (listaVentasDelDia.length > 0) {
                  Ventas.listarVentas("LISTA DE VENTAS DE HOY", listaVentasDelDia, null);
                } else {
                  System.out.println("No hay ventas registradas para hoy.");
                }
              }
              case 2 -> { // Listar ventas segun fecha
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "LISTA DE VENTAS POR FECHA") + "|");
                System.out.println("=".repeat(50));
                System.out.print("Ingresa una fecha (dd-MM-yyyy): ");
                String fechaIngresada = scanner.nextLine();

                try {
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                  LocalDate fecha = LocalDate.parse(fechaIngresada, formatter);

                  if (listaVentasAnteriores.length > 0) {
                    boolean hayVentas = false;

                    for (Ventas venta : listaVentasAnteriores) {
                      if (fecha.isEqual(venta.getFechaVenta().toLocalDate())) {
                        hayVentas = true;
                        break;
                      }
                    }
                    if (hayVentas) {
                      Ventas.listarVentas("LISTA DE VENTAS DEL " + fechaIngresada, listaVentasAnteriores, fecha);
                    } else {
                      System.out.println("No hay ventas registradas para esta fecha.");
                    }
                  } else {
                    System.out.println("No hay ventas registradas para esta fecha.");
                  }
                } catch (DateTimeParseException e) {
                  System.out.println("Formato de fecha inválido. Intente con dd-MM-yyyy.");
                }
              }
              case 3 -> { // Buscar venta
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "BUSCAR VENTA") + "|");
                System.out.println("=".repeat(50));
                System.out.print("Ingresa una fecha (dd-MM-yyyy): ");
                String fechaIngresada = scanner.nextLine();

                System.out.print("\nIngrese el numero de venta: ");
                int numeroVenta = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer

                try {
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                  LocalDate fecha = LocalDate.parse(fechaIngresada, formatter);
                  Ventas ventaHoy = Ventas.buscarVentaFechaNumero(listaVentasDelDia, fecha, numeroVenta);
                  Ventas ventaAnterior = Ventas.buscarVentaFechaNumero(listaVentasAnteriores, fecha, numeroVenta);

                  if (ventaHoy != null) {
                    Ventas.mostrarVenta("VENTA DE HOY ENCONTRADA", ventaHoy);
                  } else if (ventaAnterior != null) {
                    Ventas.mostrarVenta("VENTA ANTERIOR ENCONTRADA", ventaAnterior);
                  } else {
                    System.out.println("No hay ventas registradas para esta fecha y numero.");
                  }
                } catch (DateTimeParseException e) {
                  System.out.println("Formato de fecha inválido. Intente con dd-MM-yyyy.");
                }
              }
              case 4 -> { // Volver al menu principal
                System.out.println("Volviendo al menu principal...");

                iniciarMenuGestionarVentas = false;
              }
              default -> System.out.println("Opción no válida, por favor intente de nuevo.");
            }
          } while (iniciarMenuGestionarVentas);
        }
        case 3 -> // GESTIONAR CLIENTES
        {
          int opcionGestionarClientes;

          boolean iniciarMenuGestionarClientes = true;
          do {
            System.out.print(menuGestionarClientes);
            opcionGestionarClientes = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcionGestionarClientes) {
              case 1 -> { // Listar clientes
                if (listaClientes.length > 0) {
                  Clientes.listarClientes("LISTA DE CLIENTES REGISTRADOS", listaClientes);
                } else {
                  System.out.println("No hay clientes registradas.");
                }
              }
              case 2 -> { // Buscar clientes
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "BUSCAR CLIENTE") + "|");
                System.out.println("=".repeat(50));
                System.out.print("Ingresa el DNI del cliente: ");
                String dni = scanner.nextLine();

                Clientes cliente = Clientes.buscarClienteDNI(listaClientes, dni);

                if (cliente != null) {
                  Clientes.mostrarCliente("CLIENTE ENCONTRADO", cliente);
                } else {
                  System.out.println("Cliente no encontrado.");
                }
              }
              case 3 -> { // Registrar clientes
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "REGISTRAR CLIENTE") + "|");
                System.out.println("=".repeat(50));

                System.out.print("Ingresa el DNI del cliente: ");
                String dni = scanner.nextLine();
                System.out.println("-".repeat(50));
                System.out.print("Ingresa el nombre del cliente: ");
                String nombres = scanner.nextLine();
                System.out.println("-".repeat(50));
                System.out.print("Ingresa los apellidos del cliente: ");
                String apellidos = scanner.nextLine();
                System.out.println("-".repeat(50));
                System.out.print("Ingresa el celular del cliente: ");
                String celular = scanner.nextLine();
                System.out.println("-".repeat(50));

                Clientes cliente = new Clientes(nombres, apellidos, dni, celular);
                // PROCESO PARA GUARDAR CLIENTE
                listaClientes = agregarCliente(listaClientes, cliente);

                Clientes.mostrarCliente("CLIENTE REGISTRADO", cliente);
              }
              case 4 -> { // Volver al menu principal
                System.out.println("Volviendo al menu principal...");

                iniciarMenuGestionarClientes = false;
              }
              default -> System.out.println("Opción no válida, por favor intente de nuevo.");
            }
          } while (iniciarMenuGestionarClientes);
        }
        case 4 -> // GESTIONAR PRODUCTOS
        {
          int opcionGestionarProductos;

          boolean iniciarMenuGestionarProductos = true;
          do {
            Productos[][] listaProductos = { listaProductosPollos, listaProductosEnsaladas, listaProductosPostres,
                listaProductosBebidas };

            System.out.print(menuGestionarProductos);
            opcionGestionarProductos = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcionGestionarProductos) {
              case 1 -> { // Listar productos
                Menus menuCategorias = new Menus("PRODUCTOS - CATEGORIAS",
                    new String[] { "Pollos", "Ensaladas", "Postres", "Bebidas", "Volver al menu anterior" });

                boolean iniciarMenuCategorias = true;
                do {
                  System.out.print(menuCategorias);
                  int opcionCategoria = scanner.nextInt();
                  scanner.nextLine(); // Limpiar el buffer

                  switch (opcionCategoria) {
                    case 1 -> Productos.listarProductos("CATEGORIA POLLOS", listaProductosPollos);
                    case 2 -> Productos.listarProductos("CATEGORIA ENSALADAS", listaProductosEnsaladas);
                    case 3 -> Productos.listarProductos("CATEGORIA POSTRES", listaProductosPostres);
                    case 4 -> Productos.listarProductos("CATEGORIA BEBIDAS", listaProductosBebidas);
                    case 5 -> {
                      System.out.println("Volviendo al menu anterior...");
                      iniciarMenuCategorias = false;
                    }
                    default -> System.out.println("Opción no válida, por favor intente de nuevo.");
                  }

                } while (iniciarMenuCategorias);
              }

              case 2 -> { // Buscar producto por nombre
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "BUSCAR PRODUCTO") + "|");
                System.out.println("=".repeat(50));
                System.out.print("Ingresa el nombre del producto: ");
                String nombre = scanner.nextLine();

                Productos producto = null;
                for (Productos[] lista : listaProductos) {
                  producto = Productos.buscarProductoPorNombre(lista, nombre);
                  if (producto != null) {
                    break;
                  }
                }

                if (producto != null) {
                  Productos.mostrarProducto("PRODUCTO ENCONTRADO", producto);
                } else {
                  System.out.println("Producto no encontrado.");
                }
              }

              case 3 -> { // Registrar producto
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "REGISTRAR PRODUCTO") + "|");
                System.out.println("=".repeat(50));

                int opcionCategoria = 0;
                boolean iniciarEleccionCategoria = true;
                do {
                  System.out.println("|" + Menus.centrarTexto(48, "SELECCIÓN DE CATEGORÍA") + "|");
                  System.out.println("1. Pollos");
                  System.out.println("2. Ensaladas");
                  System.out.println("3. Postres");
                  System.out.println("4. Bebidas");
                  System.out.print("Selecciona una categoria: ");
                  opcionCategoria = scanner.nextInt();
                  scanner.nextLine(); // Limpiar

                  if (opcionCategoria >= 1 && opcionCategoria <= 4) {
                    iniciarEleccionCategoria = false;
                  } else {
                    System.out.println("Opción no válida, por favor intente de nuevo.");
                  }
                } while (iniciarEleccionCategoria);

                System.out.println("-".repeat(50));
                System.out.print("Nombre del producto: ");
                String nombre = scanner.nextLine();
                System.out.println("-".repeat(50));
                System.out.print("Precio del producto: ");
                double precio = scanner.nextDouble();
                scanner.nextLine(); // Limpiar buffer
                System.out.println("-".repeat(50));
                System.out.print("¿Cuántos elementos incluye el producto?: ");
                int cantidad = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                System.out.println("-".repeat(50));

                String[] contenido = new String[cantidad];
                for (int i = 0; i < cantidad; i++) {
                  System.out.printf(" - Elemento %d: ", i + 1);
                  contenido[i] = scanner.nextLine();
                }

                Productos producto = new Productos(nombre, precio, contenido);
                // PROCESO PARA GUARDAR PRODUCTO
                if (opcionCategoria == 1)
                  listaProductosPollos = agregarProducto(listaProductos[opcionCategoria - 1], producto);
                if (opcionCategoria == 2)
                  listaProductosEnsaladas = agregarProducto(listaProductos[opcionCategoria - 1], producto);
                if (opcionCategoria == 3)
                  listaProductosPostres = agregarProducto(listaProductos[opcionCategoria - 1], producto);
                if (opcionCategoria == 4)
                  listaProductosBebidas = agregarProducto(listaProductos[opcionCategoria - 1], producto);

                Productos.mostrarProducto("PRODUCTO REGISTRADO", producto);
              }
              case 4 -> { // Volver al menú principal
                System.out.println("Volviendo al menú principal...");

                iniciarMenuGestionarProductos = false;
              }
              default -> System.out.println("Opción no válida, por favor intente de nuevo.");
            }
          } while (iniciarMenuGestionarProductos);
        }
        case 5 -> // REPORTES Y CARACTERISTICAS
        {
          boolean iniciarMenuReportes = true;
          do {
            System.out.print(menuReportes);
            int opcionMenuReportes = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcionMenuReportes) {
              case 1 -> { // Reporte de hoy
                int cantidad = listaVentasDelDia.length;
                double ingresoTotal = 0;

                for (Ventas venta : listaVentasDelDia) {
                  ingresoTotal += venta.getTotal();
                }

                String ingresTotalFormateado = String.format("S/. %.2f", ingresoTotal);
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "REPORTE DEL DÍA") + "|");
                System.out.println("=".repeat(50));
                System.out.printf("| %-20s: %24d |%n", "Total de ventas", cantidad);
                System.out.printf("| %-20s: %24s |%n", "Ingresos totales", ingresTotalFormateado);
                System.out.println("=".repeat(50));
              }
              case 2 -> { // Reporte segun fecha
                try {
                  System.out.println("\n" + "=".repeat(50));
                  System.out.println("|" + Menus.centrarTexto(48, "REPORTE POR FECHA") + "|");
                  System.out.println("=".repeat(50));
                  System.out.print("Ingresa una fecha (dd-MM-yyyy): ");
                  String fecha = scanner.nextLine();

                  LocalDate fechaFormateada = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                  int cantidad = 0;
                  double ingresoTotal = 0;

                  for (Ventas venta : listaVentasAnteriores) {
                    if (venta.getFechaVenta().toLocalDate().isEqual(fechaFormateada)) {
                      cantidad++;
                      ingresoTotal += venta.getTotal();
                    }
                  }

                  String ingresTotalFormateado = String.format("S/. %.2f", ingresoTotal);
                  System.out.println("\n" + "=".repeat(50));
                  System.out.println("|" + Menus.centrarTexto(48, "REPORTE DEL " + fecha) + "|");
                  System.out.println("=".repeat(50));
                  System.out.printf("| %-20s: %24d |%n", "Total de ventas", cantidad);
                  System.out.printf("| %-20s: %24s |%n", "Ingresos totales", ingresTotalFormateado);
                  System.out.println("=".repeat(50));
                } catch (Exception e) {
                  System.out.println("Formato de fecha inválido. Intente con dd-MM-yyyy.");
                }
              }
              case 3 -> { // Mostrar producto más vendido
                // PRODUCTO MAS VENDIDO DESDE EN PASADAS
                String[] productosNoRepetidos = new String[100];
                int indiceProductosNoRepetidos = 0;
                for (Ventas venta : listaVentasAnteriores) {
                  for (Productos producto : venta.getProductos()) {
                    boolean existeEnProductosNoRepetidos = false;

                    for (String productosNoRepetido : productosNoRepetidos) {
                      if (productosNoRepetido == null) {
                        break;
                      } else if (productosNoRepetido.equals(producto.getNombre())) {
                        existeEnProductosNoRepetidos = true;
                        break;
                      }
                    }

                    if (!existeEnProductosNoRepetidos) {
                      productosNoRepetidos[indiceProductosNoRepetidos] = producto.getNombre();
                      indiceProductosNoRepetidos++;
                    }
                  }
                }

                int[] conteo = new int[100];
                for (int i = 0; i < productosNoRepetidos.length; i++) {
                  if (productosNoRepetidos[i] == null) {
                    break;
                  }
                  for (Ventas venta : listaVentasAnteriores) {
                    for (Productos producto : venta.getProductos()) {
                      if (productosNoRepetidos[i].equals(producto.getNombre())) {
                        conteo[i]++;
                      }
                    }
                  }
                }

                int indiceMejorProducto = 0;
                for (int i = 0; i < productosNoRepetidos.length; i++) {
                  if (productosNoRepetidos[i] == null) {
                    break;
                  }
                  if (conteo[i] > conteo[indiceMejorProducto]) {
                    indiceMejorProducto = i;
                  }
                }

                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "PRODUCTO MÁS VENDIDO") + "|");
                System.out.println("=".repeat(50));
                System.out.printf("| %-10s: %-34s |%n", "Histórico",
                    productosNoRepetidos[indiceMejorProducto] + " (" + conteo[indiceMejorProducto] + ")");

                // PRODUCTO MAS VENDIDO HOY
                productosNoRepetidos = new String[100];
                indiceProductosNoRepetidos = 0;
                for (Ventas venta : listaVentasDelDia) {
                  for (Productos producto : venta.getProductos()) {
                    boolean existeEnProductosNoRepetidos = false;

                    for (String productosNoRepetido : productosNoRepetidos) {
                      if (productosNoRepetido == null) {
                        break;
                      } else if (productosNoRepetido.equals(producto.getNombre())) {
                        existeEnProductosNoRepetidos = true;
                        break;
                      }
                    }

                    if (!existeEnProductosNoRepetidos) {
                      productosNoRepetidos[indiceProductosNoRepetidos] = producto.getNombre();
                      indiceProductosNoRepetidos++;
                    }
                  }
                }

                conteo = new int[100];
                for (int i = 0; i < productosNoRepetidos.length; i++) {
                  if (productosNoRepetidos[i] == null) {
                    break;
                  }
                  for (Ventas venta : listaVentasDelDia) {
                    for (Productos producto : venta.getProductos()) {
                      if (productosNoRepetidos[i].equals(producto.getNombre())) {
                        conteo[i]++;
                      }
                    }
                  }
                }

                indiceMejorProducto = 0;
                for (int i = 0; i < productosNoRepetidos.length; i++) {
                  if (productosNoRepetidos[i] == null) {
                    break;
                  }
                  if (conteo[i] > conteo[indiceMejorProducto]) {
                    indiceMejorProducto = i;
                  }
                }
                System.out.printf("| %-10s: %-34s |%n", "Hoy",
                    productosNoRepetidos[indiceMejorProducto] + " (" + conteo[indiceMejorProducto] + ")");
                System.out.println("=".repeat(50));
              }

              case 4 -> { // Mostrar cliente mas frecuente
                Clientes cliente;
                // PRODUCTO MAS VENDIDO DESDE EN PASADAS
                String[] clientesNoRepetidos = new String[100];
                int indiceClientesNoRepetidos = 0;
                for (Ventas venta : listaVentasAnteriores) {
                  boolean existeEnClientesNoRepetidos = false;

                  for (String dni : clientesNoRepetidos) {
                    if (dni == null) {
                      break;
                    } else if (dni.equals(venta.getCliente().getDni())) {
                      existeEnClientesNoRepetidos = true;
                      break;
                    }
                  }

                  if (!existeEnClientesNoRepetidos) {
                    clientesNoRepetidos[indiceClientesNoRepetidos] = venta.getCliente().getDni();
                    indiceClientesNoRepetidos++;
                  }
                }

                int[] conteo = new int[100];
                for (int i = 0; i < clientesNoRepetidos.length; i++) {
                  if (clientesNoRepetidos[i] == null) {
                    break;
                  }
                  for (Ventas venta : listaVentasAnteriores) {
                    if (clientesNoRepetidos[i].equals(venta.getCliente().getDni())) {
                      conteo[i]++;
                    }

                  }
                }

                int indiceClienteFrecuente = 0;
                for (int i = 0; i < clientesNoRepetidos.length; i++) {
                  if (clientesNoRepetidos[i] == null) {
                    break;
                  }
                  if (conteo[i] > conteo[indiceClienteFrecuente]) {
                    indiceClienteFrecuente = i;
                  }
                }

                cliente = Clientes.buscarClienteDNI(listaClientes, clientesNoRepetidos[indiceClienteFrecuente]);
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "CLIENTE MAS FRECUENTE") + "|");
                System.out.println("=".repeat(50));
                System.out.printf("| %-10s: %-34s |%n", "Histórico",
                    cliente.getNombres() + " " + cliente.getApellidos() + " (" + conteo[indiceClienteFrecuente] + ")");

                // PRODUCTO MAS VENDIDO HOY
                clientesNoRepetidos = new String[100];
                indiceClientesNoRepetidos = 0;
                for (Ventas venta : listaVentasDelDia) {

                  boolean existeEnProductosNoRepetidos = false;

                  for (String productosNoRepetido : clientesNoRepetidos) {
                    if (productosNoRepetido == null) {
                      break;
                    } else if (productosNoRepetido.equals(venta.getCliente().getDni())) {
                      existeEnProductosNoRepetidos = true;
                      break;
                    }
                  }

                  if (!existeEnProductosNoRepetidos) {
                    clientesNoRepetidos[indiceClientesNoRepetidos] = venta.getCliente().getDni();
                    indiceClientesNoRepetidos++;
                  }
                }

                conteo = new int[100];
                for (int i = 0; i < clientesNoRepetidos.length; i++) {
                  if (clientesNoRepetidos[i] == null) {
                    break;
                  }
                  for (Ventas venta : listaVentasDelDia) {
                    if (clientesNoRepetidos[i].equals(venta.getCliente().getDni())) {
                      conteo[i]++;
                    }
                  }
                }

                indiceClienteFrecuente = 0;
                for (int i = 0; i < clientesNoRepetidos.length; i++) {
                  if (clientesNoRepetidos[i] == null) {
                    break;
                  }
                  if (conteo[i] > conteo[indiceClienteFrecuente]) {
                    indiceClienteFrecuente = i;
                  }
                }

                cliente = Clientes.buscarClienteDNI(listaClientes, clientesNoRepetidos[indiceClienteFrecuente]);
                System.out.printf("| %-10s: %-34s |%n", "Hoy",
                    cliente.getNombres() + " " + cliente.getApellidos() + " (" + conteo[indiceClienteFrecuente] + ")");
                System.out.println("=".repeat(50));
              }
              case 5 -> { // Volver al menu principal
                System.out.println("Volviendo al menu principal...");

                iniciarMenuReportes = false;
              }
              default -> System.out.println("Opción no válida, por favor intente de nuevo.");
            }

          } while (iniciarMenuReportes);
        }
        case 6 -> {// SALIR
          scanner.close();
          System.out.println("\nSaliendo del programa...");
          System.out
              .println("PROGRAMA FINALIZADO. ¡GRACIAS POR USAR LA APLICACION!");
          iniciarPrograma = false;
        }
        default ->
          System.out.println("Opción no válida, por favor intente de nuevo.");
      }
    } while (iniciarPrograma);

  }

  public static Mesas[] inicializarMesas(Mesas[] arrayMesas) {
    arrayMesas = new Mesas[12];
    int indexMesa = 0;

    for (int fila = 0; fila < 12; fila++) {
      arrayMesas[indexMesa] = new Mesas(indexMesa + 1, ((fila + 1) % 2 == 0));
      indexMesa++;
    }

    return arrayMesas;
  }

  public static void mostrarMesas(int filas, int columnas, Mesas[] mesas) {
    int indexMesa = 0;
    int indexMesaEstado = 0;

    for (int fila = 0; fila < filas; fila++) {
      // Parte superior de las mesas
      for (int col = 0; col < columnas; col++) {
        System.out.print("  ********  ");
      }
      System.out.println();

      // Cuerpo con número de mesa y color según estado
      for (int col = 0; col < columnas; col++) {
        if (indexMesa < mesas.length) {
          Mesas mesa = mesas[indexMesa++];

          String numeroFormateado = String.format("M-%02d", mesa.getNumeroMesa());

          // Mostrar con color
          System.out.print("  | " + numeroFormateado + " |  ");
        } else {
          // Si ya no hay más mesas, dejar espacio vacío
          System.out.print("  |        |  ");
        }
      }
      System.out.println();

      // Fila con estado [X] o [ ]
      for (int col = 0; col < columnas; col++) {
        if (indexMesaEstado < mesas.length) {
          Mesas mesa = mesas[indexMesaEstado++];
          String estado = mesa.isEstadoMesa() ? "[ ]" : "[X]"; // true = ocupado
          System.out.print("  | " + estado + "  |  ");
        } else {
          System.out.print("  |        |  ");
        }
      }
      System.out.println();

      // Parte inferior de las mesas
      for (int col = 0; col < columnas; col++) {
        System.out.print("  ********  ");
      }
      System.out.println();
    }
    System.out.println(Menus.centrarTexto(50, "Ocupada: [X]    |    Libre: []"));
  }

  public static Ventas[] agregarVenta(Ventas[] listaVentas, Ventas venta) {
    Ventas[] nuevoArreglo = new Ventas[listaVentas.length + 1];
    System.arraycopy(listaVentas, 0, nuevoArreglo, 0, listaVentas.length);
    nuevoArreglo[listaVentas.length] = venta;
    return nuevoArreglo;
  }

  public static Clientes[] agregarCliente(Clientes[] listaClientes, Clientes cliente) {
    Clientes[] nuevoArreglo = new Clientes[listaClientes.length + 1];
    System.arraycopy(listaClientes, 0, nuevoArreglo, 0, listaClientes.length);
    nuevoArreglo[listaClientes.length] = cliente;
    return nuevoArreglo;
  }

  public static void mostrarMenu(String titulo, String mensajeOpcional, String charPrincipal, String... opciones) {
    System.out.println();
    System.out.println(charPrincipal.repeat(50));
    System.out.println("|" + Menus.centrarTexto(48, titulo) + "|");
    System.out.println(charPrincipal.repeat(50));

    if (!mensajeOpcional.equals("")) {
      System.out.println("|" + Menus.centrarTexto(48, "¿Desea registrar al cliente?") + "|");
      System.out.printf("| %-47s|%n", "");
    }

    for (int i = 0; i < opciones.length; i++) {
      System.out.printf("| (%d) %-43s|%n", i + 1, opciones[i]);
    }

    System.out.println(charPrincipal.repeat(50));
    System.out.print("Seleccione una opción: ");
  }

  public static Productos[] agregarProducto(Productos[] listaProductos, Productos producto) {
    Productos[] nuevoArreglo = new Productos[listaProductos.length + 1];
    System.arraycopy(listaProductos, 0, nuevoArreglo, 0, listaProductos.length);
    nuevoArreglo[listaProductos.length] = producto;
    return nuevoArreglo;
  }

  public static String[] enviarOpciones(Productos[] listaProductos) {
    String[] arrayOpciones = new String[listaProductos.length + 1];
    for (int i = 0; i < listaProductos.length; i++) {
      arrayOpciones[i] = listaProductos[i].getNombre();
    }

    arrayOpciones[arrayOpciones.length - 1] = "Volver al menú anterior";

    return arrayOpciones;
  }

  public static void mostrarDetallesProducto(Productos producto) {
    System.out.println("\nPRODUCTO AGREGADO:");
    System.out.println("-".repeat(50));
    System.out.printf("| %-10s: %-35s|%n", "Producto", producto.getNombre());
    System.out.printf("| %-10s: S/. %-31.2f|%n", "Precio", producto.getPrecio());
    if (producto.getContenido() != null) {
      System.out.printf("| %-10s: %-35s|%n", "Contenido", "");
      for (String ingrediente : producto.getContenido()) {
        System.out.printf("|  - %-44s|%n", ingrediente);
      }
    }
    System.out.println("-".repeat(50));
  }

}
