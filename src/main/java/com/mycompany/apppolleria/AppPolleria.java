package com.mycompany.apppolleria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.mycompany.apppolleria.clases.Clientes;
import com.mycompany.apppolleria.clases.Datos;
import com.mycompany.apppolleria.clases.Menus;
import com.mycompany.apppolleria.clases.Mesas;
import com.mycompany.apppolleria.clases.Productos;
import com.mycompany.apppolleria.clases.Repositorio;
import com.mycompany.apppolleria.clases.Ventas;

public class AppPolleria {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Datos datos = Datos.cargar();

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

    // 1. SUB MENU REALIZAR PEDIDO
    Menus menuRealizarVenta = new Menus("SELECCIONE LOS PRODUCTOS DEL PEDIDO",
        new String[] {
            "Pollos",
            "Ensaladas",
            "Postres",
            "Bebidas",
            "Generar venta",
            "Cancelar venta"
        });

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

    // 3. SUB MENU GESTIONAR CLIENTES
    Menus menuGestionarClientes = new Menus("MENU - GESTIONAR CLIENTES",
        new String[] {
            "Listar clientes",
            "Buscar cliente",
            "Registrar cliente",
            "Eliminar cliente",
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

    // 5. SUB MENU GESTIONAR REPORTES
    Menus menuReportes = new Menus("MENU - REPORTES Y ESTADISTICAS",
        new String[] {
            "Reporte del dia",
            "Reporte por fecha",
            "Productos mas vendidos",
            "Cliente mas frecuente",
            "Volver al menu principal"
        });

    String nombreEncargado = "MARCOS"; // Nombre del encargado

    // BUCLE PRINCIPAL - INICIO DEL PROGRAMA
    String opcionMenuPrincipal;
    boolean iniciarPrograma = true;
    do {
      // SUBMENUS DINÁMICOS DE PRODUCTOS
      Menus subMenuRealizarPedidoPollos = new Menus("POLLOS", enviarOpciones(datos.productosPollos.obtenerTodos()));
      Menus subMenuRealizarPedidoEnsaladas = new Menus("ENSALADAS",
          enviarOpciones(datos.productosEnsaladas.obtenerTodos()));
      Menus subMenuRealizarPedidoPostres = new Menus("POSTRES", enviarOpciones(datos.productosPostres.obtenerTodos()));
      Menus subMenuRealizarPedidoBebidas = new Menus("BEBIDAS", enviarOpciones(datos.productosBebidas.obtenerTodos()));

      List<Ventas> ventasDeHoy = datos.ventas
          .filtrar(v -> v.getFechaVenta() != null && v.getFechaVenta().toLocalDate().equals(LocalDate.now()));
      List<Ventas> ventasAnteriores = datos.ventas
          .filtrar(v -> v.getFechaVenta() != null && v.getFechaVenta().toLocalDate().isBefore(LocalDate.now()));
      List<Clientes> listaClientes = datos.clientes.obtenerTodos();

      System.out.print(menuPrincipal);
      opcionMenuPrincipal = scanner.nextLine();

      if (validarEntrada(opcionMenuPrincipal, 1, 6, "ENTERO-OPCIONES")) {
        System.out.println("Opción no válida, por favor intente de nuevo.");
        continue;
      }

      switch (opcionMenuPrincipal) {
        case "1" -> // REALIZAR VENTA
        {
          Ventas venta = new Ventas();
          menuRealizarVenta.setTitulo("SELECCIONE LOS PRODUCTOS DEL PEDIDO");

          // PROCESO PARA BUSCAR CLIENTE
          Clientes cliente = null;
          String dniCliente;
          String nombresCliente = "CLIENTE";
          String apellidosCliente = "";
          String celularCliente = "000000000";

          // MENU PARA INGRESAR O BUSCAR CLIENTE
          boolean iniciarMenuCliente = true;
          do {
            System.out.print("\nIngrese el DNI del cliente: ");
            dniCliente = scanner.nextLine();

            if (validarEntrada(dniCliente, 8, 8, "ENTERO-DATO")) {
              System.out.println("DNI inválido. Debe contener exactamente 8 caracteres numéricos.");
              continue;
            }

            // BÚSQUEDA USANDO EL REPOSITORIO USANDO LAMBDA
            String dniBusqueda = dniCliente;
            cliente = datos.clientes.buscar(c -> c.getDni().equals(dniBusqueda));

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
              String opcionRegistrarCliente;

              while (true) {
                mostrarMenu(
                    "CLIENTE NO ENCONTRADO",
                    "¿Desea registrar al cliente?",
                    "*",
                    "Sí", "No, continuar la venta", "No, volver a ingresar el DNI");
                opcionRegistrarCliente = scanner.nextLine();

                if (validarEntrada(opcionRegistrarCliente, 1, 3, "ENTERO-OPCIONES")) {
                  System.out.println("Opción no válida, por favor intente de nuevo.");
                  continue;
                }

                break;
              }

              switch (opcionRegistrarCliente) {
                case "1" -> // Registrar nuevo cliente
                {
                  System.out.println("\n" + "#".repeat(50));
                  System.out.println(
                      "|" + Menus.centrarTexto(48, "INGRESE LOS DATOS DEL CLIENTE") + "|");
                  System.out.println("#".repeat(50));

                  while (true) {
                    System.out.printf("  %-10s: ", "DNI");
                    dniCliente = scanner.nextLine();
                    if (validarEntrada(dniCliente, 8, 8, "ENTERO-DATO")) {
                      System.out.println("DNI inválido. Debe contener exactamente 8 caracteres numéricos.\n");
                      continue;
                    }
                    Clientes clienteExistente = Clientes.buscarClienteDNI(listaClientes, dniCliente);
                    if (clienteExistente != null) {
                      System.out.println("El DNI ya está registrado. Por favor ingrese un DNI diferente.\n");
                      continue;
                    }
                    break;
                  }

                  while (true) {
                    System.out.printf("  %-10s: ", "Nombres");
                    nombresCliente = scanner.nextLine().toUpperCase();
                    if (validarEntrada(nombresCliente, 1, 100, "TEXTO")) {
                      System.out.println("Nombres inválidos. Deben contener caracteres alfabéticos.\n");
                      continue;
                    }
                    break;
                  }

                  while (true) {
                    System.out.printf("  %-10s: ", "Apellidos");
                    apellidosCliente = scanner.nextLine().toUpperCase();
                    if (validarEntrada(apellidosCliente, 1, 100, "TEXTO")) {
                      System.out.println("Apellidos inválidos. Deben contener caracteres alfabéticos.\n");
                      continue;
                    }
                    break;
                  }

                  while (true) {
                    System.out.printf("  %-10s: ", "Celular");
                    celularCliente = scanner.nextLine().toUpperCase();
                    if (validarEntrada(celularCliente, 1, 9, "ENTERO-DATO")) {
                      System.out.println("Celular inválido. Debe contener entre 1 y 9 dígitos numéricos.\n");
                      continue;
                    }
                    break;
                  }

                  cliente = new Clientes(
                      nombresCliente,
                      apellidosCliente,
                      dniCliente,
                      celularCliente);

                  // PROCESO PARA GUARDAR CLIENTE
                  datos.clientes.agregar(cliente);
                  datos.guardar(false);
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
                case "2" -> // Continuar venta sin registrar cliente
                {
                  dniCliente = "00000000";
                  cliente = null;

                  venta.setCliente(cliente);

                  System.out.println("\n" + "=".repeat(50));
                  System.out.println(
                      "|" + Menus.centrarTexto(48, "DATOS POR DEFECTO") + "|");
                  System.out.println("=".repeat(50));
                  System.out.printf("| %-10s: %-35s|%n", "DNI", dniCliente);
                  System.out.printf("| %-10s: %-35s|%n", "Cliente", nombresCliente);
                  System.out.printf("| %-10s: %-35s|%n", "Celular", celularCliente);
                  System.out.println("=".repeat(50));

                  iniciarMenuCliente = false;
                }
                case "3" -> System.out.println("Volviendo a solicitar el DNI...");
                default -> System.out.println("Opción no válida, por favor intente de nuevo.");
              }
            }
          } while (iniciarMenuCliente);
          venta.setCliente(cliente);

          // PROCESO PARA SELECCIONAR TIPO DE VENTA Y MESA (si aplica)
          String opcionTipoVenta;
          String numeroMesa;
          String tipoVenta = "";

          // MOSTRAR MENÚ PARA SELECCIONAR TIPO DE VENTA
          boolean iniciarMenuTipoVenta = true;
          do {
            mostrarMenu(
                "TIPO DE VENTA",
                "",
                "*",
                "Consumo en local", "Para llevar", "Delivery");
            opcionTipoVenta = scanner.nextLine();

            if (validarEntrada(opcionTipoVenta, 1, 3, "ENTERO-OPCIONES")) {
              System.out.println("Opción no válida, por favor intente de nuevo.");
              continue;
            }

            switch (opcionTipoVenta) {
              case "1" -> {
                tipoVenta = "Consumo en local";

                boolean iniciarMenuNumeroMesa = true;
                do {
                  System.out.println("\n" + "=".repeat(50));
                  System.out.println("|" + Menus.centrarTexto(48, "SELECCIONAR MESA") + "|");
                  System.out.println("=".repeat(50) + "\n");
                  mostrarMesas(3, 4, datos.mesas);
                  System.out.println("=".repeat(50));
                  System.out.print("Ingrese solo el número de mesa (Ejm: 1, 5, 10, etc...): ");
                  numeroMesa = scanner.nextLine();

                  if (validarEntrada(numeroMesa, 1, datos.mesas.length, "ENTERO-OPCIONES")) {
                    System.out.println("Número de mesa inválido, por favor intente de nuevo.");
                    continue;
                  }

                  if (datos.mesas[Integer.parseInt(numeroMesa) - 1].isEstadoMesa()) {
                    venta.setMesa(datos.mesas[Integer.parseInt(numeroMesa) - 1]);

                    System.out.println("Mesa seleccionada: M-"
                        + String.format("%02d", venta.getMesa().getNumeroMesa()));

                    menuRealizarVenta.setTitulo("M-"
                        + String.format("%02d", venta.getMesa().getNumeroMesa())
                        + " | SELECCIONE LOS PRODUCTOS DEL PEDIDO");

                    iniciarMenuNumeroMesa = false;
                  } else {
                    System.out.println("La mesa "
                        + String.format("M-%02d", Integer.parseInt(numeroMesa)) + " ESTÁ OCUPADA"
                        + ", por favor seleccione otra mesa.");
                  }
                } while (iniciarMenuNumeroMesa);
              }
              case "2" -> {
                tipoVenta = "Para llevar";
                venta.setMesa(null);
                System.out.println("Venta para llevar seleccionado.");
              }
              case "3" -> {
                tipoVenta = "Delivery";
                venta.setMesa(null);
                System.out.println("Venta para delivery seleccionado.");
              }
              default ->
                System.out.println("Opción no válida, por favor intente de nuevo.");
            }

            venta.setTipoVenta(tipoVenta);
            iniciarMenuTipoVenta = false;
          } while (iniciarMenuTipoVenta);

          // MENU PARA SELECCIONAR PRODUCTOS
          boolean iniciarMenuRealizarVenta = true;
          do {
            Productos productoElegido = null;
            String nombreProducto = "";
            String opcionTipoProducto;
            String opcionProducto;

            System.out.print(menuRealizarVenta);
            opcionTipoProducto = scanner.nextLine();
            if (validarEntrada(opcionTipoProducto, 1, menuRealizarVenta.getOpciones().length, "ENTERO-OPCIONES")) {
              System.out.println("Opción no válida, por favor intente de nuevo.");
              continue;
            }

            switch (opcionTipoProducto) {
              case "1" -> // Pollos
              {
                while (true) {
                  System.out.print(subMenuRealizarPedidoPollos);
                  opcionProducto = scanner.nextLine();

                  if (validarEntrada(opcionProducto, 1, subMenuRealizarPedidoPollos.getOpciones().length,
                      "ENTERO-OPCIONES")) {
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                    continue;
                  }

                  if (Integer.parseInt(opcionProducto) == subMenuRealizarPedidoPollos.getOpciones().length) {
                    System.out.println("Volviendo al menú anterior...");
                  } else {
                    nombreProducto = subMenuRealizarPedidoPollos.getOpciones()[Integer.parseInt(opcionProducto) - 1];
                    productoElegido = Productos.buscarProductoPorNombre(
                        datos.productosPollos.obtenerTodos(),
                        nombreProducto);
                  }

                  break;
                }
              }
              case "2" -> // Ensaladas
              {
                while (true) {
                  System.out.print(subMenuRealizarPedidoEnsaladas);
                  opcionProducto = scanner.nextLine();

                  if (validarEntrada(opcionProducto, 1, subMenuRealizarPedidoEnsaladas.getOpciones().length,
                      "ENTERO-OPCIONES")) {
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                    continue;
                  }

                  if (Integer.parseInt(opcionProducto) == subMenuRealizarPedidoEnsaladas.getOpciones().length) {
                    System.out.println("Volviendo al menú anterior...");
                  } else {
                    nombreProducto = subMenuRealizarPedidoEnsaladas.getOpciones()[Integer.parseInt(opcionProducto) - 1];
                    productoElegido = Productos.buscarProductoPorNombre(
                        datos.productosEnsaladas.obtenerTodos(),
                        nombreProducto);
                  }

                  break;
                }
              }
              case "3" -> // Postres
              {
                while (true) {
                  System.out.print(subMenuRealizarPedidoPostres);
                  opcionProducto = scanner.nextLine();

                  if (validarEntrada(opcionProducto, 1, subMenuRealizarPedidoPostres.getOpciones().length,
                      "ENTERO-OPCIONES")) {
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                    continue;
                  }

                  if (Integer.parseInt(opcionProducto) == subMenuRealizarPedidoPostres.getOpciones().length) {
                    System.out.println("Volviendo al menú anterior...");
                  } else {
                    nombreProducto = subMenuRealizarPedidoPostres.getOpciones()[Integer.parseInt(opcionProducto) - 1];
                    productoElegido = Productos.buscarProductoPorNombre(
                        datos.productosPostres.obtenerTodos(),
                        nombreProducto);
                  }

                  break;
                }
              }
              case "4" -> // Bebidas
              {
                while (true) {
                  System.out.print(subMenuRealizarPedidoBebidas);
                  opcionProducto = scanner.nextLine();

                  if (validarEntrada(opcionProducto, 1, subMenuRealizarPedidoBebidas.getOpciones().length,
                      "ENTERO-OPCIONES")) {
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                    continue;
                  }

                  if (Integer.parseInt(opcionProducto) == subMenuRealizarPedidoBebidas.getOpciones().length) {
                    System.out.println("Volviendo al menú anterior...");
                  } else {
                    nombreProducto = subMenuRealizarPedidoBebidas.getOpciones()[Integer.parseInt(opcionProducto) - 1];
                    productoElegido = Productos.buscarProductoPorNombre(
                        datos.productosBebidas.obtenerTodos(),
                        nombreProducto);
                  }

                  break;
                }
              }
              case "5" -> // Generar venta
              {
                String metodoPago;
                double montoEfectivo = 0;
                double vuelto = 0;
                double totalVenta = 0.0;
                String voucher;

                for (Productos producto : venta.getProductos()) {
                  totalVenta += producto.getPrecio();
                }

                String opcionMetodoPago;
                boolean iniciarSubMenuMetodoPago = true;
                do {
                  String totalVentaFormateado = String.format("%.2f", totalVenta);
                  subMenuMetodoPago.setTitulo("METODO DE PAGO - TOTAL S/ " + totalVentaFormateado);
                  System.out.print(subMenuMetodoPago);
                  opcionMetodoPago = scanner.nextLine();

                  if (validarEntrada(opcionMetodoPago, 1, subMenuMetodoPago.getOpciones().length, "ENTERO-OPCIONES")) {
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                    continue;
                  }

                  if (opcionMetodoPago.equals("6")) {
                    System.out.println("Volviendo al menú anterior...");
                    iniciarSubMenuMetodoPago = false;
                  } else {
                    metodoPago = subMenuMetodoPago.getOpciones()[Integer.parseInt(opcionMetodoPago) - 1];

                    if (opcionMetodoPago.equals("1")) {
                      do {
                        System.out.print("Ingrese el monto en efectivo: ");
                        String montoInput = scanner.nextLine();

                        if (validarEntrada(montoInput, 1, 10, "DECIMAL")) {
                          System.out.println("Monto inválido, por favor intente de nuevo.");
                          continue;
                        }

                        montoEfectivo = Double.parseDouble(montoInput);
                        vuelto = montoEfectivo - totalVenta;

                        if (vuelto < 0)
                          System.out.println("El monto ingresado es insuficiente.\n");
                      } while (vuelto < 0);
                    }

                    // ESTABLECER DATOS DE LA VENTA
                    venta.setNumeroVenta(ventasDeHoy.size() > 0 ? ventasDeHoy.size() + 1 : 1);
                    venta.setEncargado(nombreEncargado);
                    venta.setCliente(cliente);
                    venta.setCantidadProductos(venta.getProductos().size());
                    venta.setTotal(totalVenta);
                    venta.setMetodoPago(metodoPago);
                    venta.setFechaVenta(LocalDateTime.now());

                    // PROCESO PARA MOSTRAR VOUCHER
                    try {
                      datos.ventas.agregar(venta);
                      datos.guardar(false);

                      System.out.println("\n> Venta registrada con éxito");
                      Thread.sleep(1000);

                      System.out.println("> Generando voucher de venta...");
                      Thread.sleep(1000);

                      System.out.println("> Voucher generado con éxito...");
                      Thread.sleep(1000);

                      System.out.println("> Mostrando voucher...\n");
                      Thread.sleep(1000);

                      montoEfectivo = opcionMetodoPago.equals("1") ? montoEfectivo : 0;

                      voucher = venta.generarVoucherVenta(montoEfectivo);

                      System.out.println(voucher);
                      Thread.sleep(1000);

                      System.out.println("\n\n> Exportando voucher...");
                      Thread.sleep(1000);

                      System.out.println("> Voucher exportado con éxito...");
                      Thread.sleep(1000);

                      venta.exportarVoucherVenta(voucher);
                    } catch (InterruptedException e) {
                    }

                    iniciarMenuRealizarVenta = false;
                    iniciarSubMenuMetodoPago = false;
                  }
                } while (iniciarSubMenuMetodoPago);
              }
              case "6" -> // Cancelar pedido y volver al menu principal
              {
                System.out.println("Pedido cancelado, volviendo al menu principal...");
                iniciarMenuRealizarVenta = false;
              }
              default -> System.out.println("Opción no válida, vuelva a elegir.");
            }

            if (!(opcionTipoProducto.equals("5") || opcionTipoProducto.equals("6"))) {
              if (productoElegido != null) {
                venta.agregarProducto(productoElegido);
                mostrarDetallesProducto(productoElegido);
              } else {
                System.out.println("Producto no encontrado, por favor intente de nuevo.");
              }
            }
          } while (iniciarMenuRealizarVenta);
        }
        case "2" -> // GESTIONAR VENTAS
        {
          String opcionGestionarVentas;

          do {
            System.out.print(menuGestionarVentas);
            opcionGestionarVentas = scanner.nextLine();

            if (validarEntrada(opcionGestionarVentas, 1, menuGestionarVentas.getOpciones().length, "ENTERO-OPCIONES")) {
              System.out.println("Opción no válida, por favor intente de nuevo.");
              continue;
            }

            switch (opcionGestionarVentas) {
              case "1" -> // Listar ventas del dia actual
              {
                if (ventasDeHoy.size() > 0) {
                  Ventas.listarVentas("LISTA DE VENTAS DE HOY", ventasDeHoy, null);
                } else {
                  System.out.println("No hay ventas registradas para hoy.");
                }
              }
              case "2" -> // Listar ventas segun fecha
              {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "LISTA DE VENTAS POR FECHA") + "|");
                System.out.println("=".repeat(50));
                System.out.print("Ingresa una fecha (dd-MM-yyyy): ");
                String fechaIngresada = scanner.nextLine();

                if (validarEntrada(fechaIngresada, 10, 10, "FECHA")) {
                  System.out.println("Formato de fecha inválido. Use este formato: dd-MM-yyyy.");
                  break;
                }

                try {
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                  LocalDate fecha = LocalDate.parse(fechaIngresada, formatter);

                  if (!ventasAnteriores.isEmpty()) {
                    boolean hayVentas = ventasAnteriores.stream()
                        .anyMatch(v -> fecha.isEqual(v.getFechaVenta().toLocalDate()));

                    if (hayVentas) {
                      Ventas.listarVentas("LISTA DE VENTAS DEL " + fechaIngresada, ventasAnteriores, fecha);
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
              case "3" -> // Buscar venta
              {
                String numeroVenta;
                String fechaIngresada;

                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "BUSCAR VENTA") + "|");
                System.out.println("=".repeat(50));
                System.out.print("Ingresa una fecha (dd-MM-yyyy): ");
                fechaIngresada = scanner.nextLine();

                if (validarEntrada(fechaIngresada, 10, 10, "FECHA")) {
                  System.out.println("Formato de fecha inválido. Use este formato: dd-MM-yyyy.");
                  break;
                }

                do {
                  System.out.print("\nIngrese el numero de venta: ");
                  numeroVenta = scanner.nextLine();
                  if (validarEntrada(numeroVenta, 1, 10, "ENTERO-OPCIONES")) {
                    System.out.println("Número de venta inválido, por favor intente de nuevo.");
                    continue;
                  }
                  break;
                } while (true);

                try {
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                  LocalDate fecha = LocalDate.parse(fechaIngresada, formatter);
                  Ventas ventaHoy = Ventas.buscarVentaFechaNumero(ventasDeHoy, fecha, Integer.parseInt(numeroVenta));
                  Ventas ventaAnterior = Ventas.buscarVentaFechaNumero(ventasAnteriores, fecha,
                      Integer.parseInt(numeroVenta));

                  if (ventaHoy != null) {
                    Ventas.mostrarVenta("VENTA DE HOY ENCONTRADA", ventaHoy);
                  } else if (ventaAnterior != null) {
                    Ventas.mostrarVenta("VENTA ANTERIOR ENCONTRADA", ventaAnterior);
                  } else {
                    System.out.println("No hay venta registrada con numero en esa fecha.");
                  }
                } catch (DateTimeParseException e) {
                  System.out.println("Formato de fecha inválido. Intente con dd-MM-yyyy.");
                }
              }
              case "4" -> // Volver al menu principal
              {
                System.out.println("Volviendo al menu principal...");
              }
              default -> System.out.println("Opción no válida, por favor intente de nuevo.");
            }

            if (opcionGestionarVentas.equals("4"))
              break;
          } while (true);
        }
        case "3" -> // GESTIONAR CLIENTES
        {
          String opcionGestionarClientes;

          boolean iniciarMenuGestionarClientes = true;
          do {
            System.out.print(menuGestionarClientes);
            opcionGestionarClientes = scanner.nextLine();

            if (validarEntrada(opcionGestionarClientes, 1, menuGestionarClientes.getOpciones().length,
                "ENTERO-OPCIONES")) {
              System.out.println("Opción no válida, por favor intente de nuevo.");
              continue;
            }

            switch (opcionGestionarClientes) {
              case "1" -> // Listar clientes
              {
                if (!listaClientes.isEmpty()) {
                  Clientes.listarClientes("LISTA DE CLIENTES REGISTRADOS", listaClientes);
                } else {
                  System.out.println("No hay clientes registradas.");
                }
              }
              case "2" -> // Buscar clientes
              {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "BUSCAR CLIENTE") + "|");
                System.out.println("=".repeat(50));
                System.out.print("Ingresa el DNI del cliente: ");
                String dni = scanner.nextLine();

                if (validarEntrada(dni, 8, 8, "ENTERO-DATO")) {
                  System.out.println("DNI inválido. Debe contener exactamente 8 caracteres numéricos.");
                  break;
                }

                Clientes cliente = Clientes.buscarClienteDNI(listaClientes, dni);

                if (cliente != null) {
                  Clientes.mostrarCliente("CLIENTE ENCONTRADO", cliente);
                } else {
                  System.out.println("Cliente no encontrado.");
                }
              }
              case "3" -> // Registrar clientes
              {
                String dni = "";
                String nombres = "";
                String apellidos = "";
                String celular = "";

                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "REGISTRAR CLIENTE") + "|");
                System.out.println("=".repeat(50));

                while (true) {
                  System.out.printf("  %-10s: ", "DNI");
                  dni = scanner.nextLine();
                  if (validarEntrada(dni, 8, 8, "ENTERO-DATO")) {
                    System.out.println("DNI inválido. Debe contener exactamente 8 caracteres numéricos.\n");
                    continue;
                  }
                  Clientes clienteExistente = Clientes.buscarClienteDNI(listaClientes, dni);
                  if (clienteExistente != null) {
                    System.out.println("El DNI ya está registrado. Por favor ingrese un DNI diferente.\n");
                    continue;
                  }
                  break;
                }

                while (true) {
                  System.out.printf("  %-10s: ", "Nombres");
                  nombres = scanner.nextLine().toUpperCase();
                  if (validarEntrada(nombres, 1, 100, "TEXTO")) {
                    System.out.println("Nombres inválidos. Deben contener caracteres alfabéticos.\n");
                    continue;
                  }
                  break;
                }

                while (true) {
                  System.out.printf("  %-10s: ", "Apellidos");
                  apellidos = scanner.nextLine().toUpperCase();
                  if (validarEntrada(apellidos, 1, 100, "TEXTO")) {
                    System.out.println("Apellidos inválidos. Deben contener caracteres alfabéticos.\n");
                    continue;
                  }
                  break;
                }

                while (true) {
                  System.out.printf("  %-10s: ", "Celular");
                  celular = scanner.nextLine().toUpperCase();
                  if (validarEntrada(celular, 9, 9, "ENTERO-DATO")) {
                    System.out.println("Celular inválido. Debe contener entre 1 y 9 dígitos numéricos.\n");
                    continue;
                  }
                  break;
                }

                Clientes cliente = new Clientes(nombres, apellidos, dni, celular);

                // SUBMENU PARA CONFIRMAR GUARDADO DE CLIENTE
                while (true) {
                  mostrarMenu(
                      "CONFIRMAR REGISTRO DE CLIENTE",
                      "¿Desea guardar el cliente registrado?",
                      "*",
                      "Sí, guardar cliente", "No, cancelar registro de cliente");
                  String opcionConfirmarGuardarCliente = scanner.nextLine();

                  if (validarEntrada(opcionConfirmarGuardarCliente, 1, 2, "ENTERO-OPCIONES")) {
                    System.out.println("Opción no válida, por favor intente de nuevo.");
                    continue;
                  }

                  if (opcionConfirmarGuardarCliente.equals("1")) {
                    datos.clientes.agregar(cliente);
                    datos.guardar(false);

                    Clientes.mostrarCliente("CLIENTE REGISTRADO", cliente);
                  } else {
                    System.out.println("Registro de cliente cancelado.");
                  }

                  break;
                }
              }
              case "4" -> // Eliminar cliente
              {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "ELIMINAR CLIENTE") + "|");
                System.out.println("=".repeat(50));
                System.out.print("Ingresa el DNI del cliente a eliminar: ");
                String dni = scanner.nextLine();

                Clientes cliente = Clientes.buscarClienteDNI(listaClientes, dni);

                // MOSTRAR CLIENTE A ELIMINAR Y SUBMENU PARA CONFIRMAR ELIMINACIÓN
                if (cliente != null) {
                  Clientes.mostrarCliente("CLIENTE A ELIMINAR", cliente);

                  while (true) {
                    mostrarMenu(
                        "CONFIRMAR ELIMINACIÓN DE CLIENTE",
                        "¿Desea eliminar el cliente seleccionado?",
                        "*",
                        "Sí, eliminar cliente", "No, cancelar eliminación de cliente");
                    String opcionConfirmarEliminarCliente = scanner.nextLine();

                    if (validarEntrada(opcionConfirmarEliminarCliente, 1, 2, "ENTERO-OPCIONES")) {
                      System.out.println("Opción no válida, por favor intente de nuevo.");
                      continue;
                    }

                    if (opcionConfirmarEliminarCliente.equals("1")) {
                      datos.clientes.eliminar(cliente);
                      datos.guardar(false);

                      System.out.println("\n> Cliente eliminado con éxito.");
                    } else {
                      System.out.println("Eliminación de cliente cancelada.");
                    }

                    break;
                  }
                } else {
                  System.out.println("Cliente no encontrado.");
                }
              }
              case "5" -> // Volver al menu principal
              {
                System.out.println("Volviendo al menu principal...");
                iniciarMenuGestionarClientes = false;
              }
              default -> System.out.println("Opción no válida, por favor intente de nuevo.");
            }
          } while (iniciarMenuGestionarClientes);
        }
        case "4" -> // GESTIONAR PRODUCTOS
        {
          int opcionGestionarProductos;

          boolean iniciarMenuGestionarProductos = true;
          do {
            HashMap<String, List<Productos>> listaProductos = new HashMap<>();
            listaProductos.put("Pollos", new java.util.ArrayList<>(datos.productosPollos.obtenerTodos()));
            listaProductos.put("Ensaladas", new java.util.ArrayList<>(datos.productosEnsaladas.obtenerTodos()));
            listaProductos.put("Postres", new java.util.ArrayList<>(datos.productosPostres.obtenerTodos()));
            listaProductos.put("Bebidas", new java.util.ArrayList<>(datos.productosBebidas.obtenerTodos()));

            System.out.print(menuGestionarProductos);
            opcionGestionarProductos = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcionGestionarProductos) {
              case 1 -> { // Listar productos
                Menus menuCategorias = new Menus(
                    "PRODUCTOS - CATEGORIAS",
                    new String[] {
                        "Pollos",
                        "Ensaladas",
                        "Postres",
                        "Bebidas",
                        "Volver al menu anterior"
                    });

                boolean iniciarMenuCategorias = true;
                do {
                  System.out.print(menuCategorias);
                  int opcionCategoria = scanner.nextInt();
                  scanner.nextLine(); // Limpiar el buffer

                  switch (opcionCategoria) {
                    case 1 -> Productos.listarProductos("CATEGORIA POLLOS", datos.productosPollos.obtenerTodos());
                    case 2 ->
                      Productos.listarProductos("CATEGORIA ENSALADAS", datos.productosEnsaladas.obtenerTodos());
                    case 3 -> Productos.listarProductos("CATEGORIA POSTRES", datos.productosPostres.obtenerTodos());
                    case 4 -> Productos.listarProductos("CATEGORIA BEBIDAS", datos.productosBebidas.obtenerTodos());
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
                for (List<Productos> lista : listaProductos.values()) {
                  producto = Productos.buscarProductoPorNombre(lista, nombre);
                  if (producto != null) {
                    Productos.mostrarProducto("PRODUCTO ENCONTRADO", producto);
                    break;
                  }
                }

                if (producto == null)
                  System.out.println("Producto no encontrado.");
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
                switch (opcionCategoria) {
                  case 1 -> datos.productosPollos.agregar(producto);
                  case 2 -> datos.productosEnsaladas.agregar(producto);
                  case 3 -> datos.productosPostres.agregar(producto);
                  case 4 -> datos.productosBebidas.agregar(producto);
                }
                datos.guardar(false);
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
        case "5" -> // REPORTES Y CARACTERISTICAS
        {
          boolean iniciarMenuReportes = true;
          do {
            System.out.print(menuReportes);
            String opcionMenuReportes = scanner.nextLine();

            if (validarEntrada(opcionMenuReportes, 1, menuReportes.getOpciones().length, "ENTERO-OPCIONES")) {
              System.out.println("Opción no válida, por favor intente de nuevo.");
              continue;
            }

            switch (opcionMenuReportes) {
              case "1" -> // Reporte de hoy
              {
                if (ventasDeHoy.isEmpty()) {
                  System.out.println("No hay ventas registradas hoy.");
                  break;
                }

                int cantidad = ventasDeHoy.size();
                double ingresoTotal = ventasDeHoy.stream()
                    .mapToDouble(Ventas::getTotal)
                    .sum();

                String ingresTotalFormateado = String.format("S/. %.2f", ingresoTotal);
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "REPORTE DEL DÍA") + "|");
                System.out.println("=".repeat(50));
                System.out.printf("| %-20s: %24d |%n", "Total de ventas", cantidad);
                System.out.printf("| %-20s: %24s |%n", "Ingresos totales", ingresTotalFormateado);
                System.out.println("=".repeat(50));
              }
              case "2" -> // Reporte segun fecha
              {
                try {
                  System.out.println("\n" + "=".repeat(50));
                  System.out.println("|" + Menus.centrarTexto(48, "REPORTE POR FECHA") + "|");
                  System.out.println("=".repeat(50));
                  System.out.print("Ingresa una fecha (dd-MM-yyyy): ");
                  String fecha = scanner.nextLine();

                  LocalDate fechaFormateada = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                  List<Ventas> ventasFiltradas = ventasAnteriores.stream()
                      .filter(v -> v.getFechaVenta().toLocalDate().isEqual(fechaFormateada))
                      .toList();

                  if (ventasFiltradas.isEmpty()) {
                    System.out.println("No hay ventas registradas para esta fecha.");
                    break;
                  }

                  int cantidad = 0;
                  double ingresoTotal = 0;
                  for (Ventas venta : ventasFiltradas) {
                    cantidad++;
                    ingresoTotal += venta.getTotal();
                  }

                  String ingresoTotalFormateado = String.format("S/. %.2f", ingresoTotal);
                  System.out.println("\n" + "=".repeat(50));
                  System.out.println("|" + Menus.centrarTexto(48, "REPORTE DEL " + fecha) + "|");
                  System.out.println("=".repeat(50));
                  System.out.printf("| %-20s: %24d |%n", "Total de ventas", cantidad);
                  System.out.printf("| %-20s: %24s |%n", "Ingresos totales", ingresoTotalFormateado);
                  System.out.println("=".repeat(50));
                } catch (Exception e) {
                  System.out.println("Formato de fecha inválido. Intente con dd-MM-yyyy.");
                }
              }
              case "3" -> // Mostrar producto más vendido
              {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "PRODUCTOS MÁS VENDIDOS") + "|");
                System.out.println("=".repeat(50));
                mostrarMejorProducto("Histórico", ventasAnteriores);
                mostrarMejorProducto("Hoy", ventasDeHoy);
                System.out.println("=".repeat(50));
              }
              case "4" -> { // Mostrar cliente mas frecuente
                System.out.println("\n" + "=".repeat(50));
                System.out.println("|" + Menus.centrarTexto(48, "CLIENTE MÁS FRECUENTE") + "|");
                System.out.println("=".repeat(50));
                mostrarMejorCliente("Histórico", ventasAnteriores, datos.clientes);
                mostrarMejorCliente("Hoy", ventasDeHoy, datos.clientes);
                System.out.println("=".repeat(50));
              }
              case "5" -> // Volver al menu principal
              {
                System.out.println("Volviendo al menu principal...");
                iniciarMenuReportes = false;
              }
              default -> System.out.println("Opción no válida, por favor intente de nuevo.");
            }
          } while (iniciarMenuReportes);
        }
        case "6" -> // SALIR
        {
          scanner.close();
          System.out.println("\nSaliendo del programa...");
          System.out.println("PROGRAMA FINALIZADO. ¡GRACIAS POR USAR LA APLICACION!");
          iniciarPrograma = false;
        }
        default -> System.out.println("Opción no válida, por favor intente de nuevo.");
      }
    } while (iniciarPrograma);
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
          String estado = mesa.isEstadoMesa() ? "[ ]" : "[X]"; // true = libre
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

  public static void mostrarMenu(String titulo, String mensajeOpcional, String charPrincipal, String... opciones) {
    System.out.println();
    System.out.println(charPrincipal.repeat(50));
    System.out.println("|" + Menus.centrarTexto(48, titulo) + "|");
    System.out.println(charPrincipal.repeat(50));

    if (!mensajeOpcional.equals("")) {
      System.out.println("|" + Menus.centrarTexto(48, mensajeOpcional) + "|");
      System.out.printf("| %-47s|%n", "");
    }

    for (int i = 0; i < opciones.length; i++) {
      System.out.printf("| (%d) %-43s|%n", i + 1, opciones[i]);
    }

    System.out.println(charPrincipal.repeat(50));
    System.out.print("Seleccione una opción: ");
  }

  public static String[] enviarOpciones(List<Productos> listaProductos) {
    String[] arrayOpciones = new String[listaProductos.size() + 1];
    for (int i = 0; i < listaProductos.size(); i++) {
      arrayOpciones[i] = listaProductos.get(i).getNombre();
    }

    arrayOpciones[arrayOpciones.length - 1] = "Volver al menú anterior";

    return arrayOpciones;
  }

  public static void mostrarDetallesProducto(Productos producto) {
    System.out.println("\nPRODUCTO AGREGADO");
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

  public static boolean validarEntrada(String entrada, int min, int max, String tipo) {
    if (entrada == null || entrada.isBlank() || entrada.isEmpty()) {
      return true; // Hay error
    }

    // Validaciones por tipo de dato
    switch (tipo) {
      case "TEXTO":
        // Solo letras y espacios
        return !entrada.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")
            || entrada.length() < min
            || entrada.length() > max;

      case "ENTERO-OPCIONES":
        return !entrada.matches("\\d+") || Integer.parseInt(entrada) < min || Integer.parseInt(entrada) > max;

      case "ENTERO-DATO":
        return !entrada.matches("\\d+") || entrada.length() < min || entrada.length() > max;

      case "DECIMAL":
        // ^\\d+ -> Empieza con uno o más dígitos
        // (\\.\\d{1,2})? -> Opcionalmente un punto seguido de 1 o 2 dígitos
        return !entrada.matches("\\d+(\\.\\d{1,2})?") || entrada.length() < min || entrada.length() > max;

      case "FECHA":
        // Acepta números y guiones (ej: 12-12-2024)
        return !entrada.matches("[0-9\\-]+");

      default:
        return true; // Tipo desconocido es error
    }
  }

  private static void mostrarMejorProducto(String etiqueta, List<Ventas> listaVentas) {
    if (listaVentas.isEmpty()) {
      System.out.printf("| %-10s: %-34s |%n", etiqueta, "No hay ventas registradas");
      return;
    }

    HashMap<String, Integer> conteoProductos = new HashMap<>();

    for (Ventas venta : listaVentas) {
      for (Productos producto : venta.getProductos()) {
        String nombre = producto.getNombre();
        conteoProductos.put(nombre, conteoProductos.getOrDefault(nombre, 0) + 1);
      }
    }

    String mejorProducto = "";
    int maxVentas = 0;

    for (var entry : conteoProductos.entrySet()) {
      if (entry.getValue() > maxVentas) {
        maxVentas = entry.getValue();
        mejorProducto = entry.getKey();
      }
    }

    if (maxVentas > 0) {
      System.out.printf("| %-10s: %-34s |%n", etiqueta, mejorProducto + " (" + maxVentas + ")");
    } else {
      System.out.printf("| %-10s: %-34s |%n", etiqueta, "N/A");
    }
  }

  private static void mostrarMejorCliente(String etiqueta, List<Ventas> listaVentas,
      Repositorio<Clientes> repoClientes) {
    if (listaVentas.isEmpty()) {
      System.out.printf("| %-10s: %-34s |%n", etiqueta, "No hay ventas registradas");
      return;
    }

    HashMap<String, Integer> conteoClientes = new HashMap<>();

    for (Ventas venta : listaVentas) {
      if (venta.getCliente() != null) {
        String dni = venta.getCliente().getDni();
        conteoClientes.put(dni, conteoClientes.getOrDefault(dni, 0) + 1);
      }
    }

    String mejorDni = "";
    int maxCompras = 0;

    for (var entry : conteoClientes.entrySet()) {
      if (entry.getValue() > maxCompras) {
        maxCompras = entry.getValue();
        mejorDni = entry.getKey();
      }
    }

    if (maxCompras > 0) {
      final String dniBuscado = mejorDni;
      Clientes cliente = repoClientes.buscar(c -> c.getDni().equals(dniBuscado));

      String nombreMostrado = (cliente != null) ? cliente.getNombres() : "DNI: " + mejorDni;

      System.out.printf("| %-10s: %-34s |%n", etiqueta, nombreMostrado + " (" + maxCompras + ")");
    } else {
      System.out.printf("| %-10s: %-34s |%n", etiqueta, "N/A");
    }
  }
}