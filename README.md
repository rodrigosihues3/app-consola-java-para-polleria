# 📋 INFORME: APP POLERÍA - SISTEMA DE GESTIÓN PARA POLLERÍAS

## 1. INFORMACIÓN GENERAL DEL PROYECTO

### 1.1 Identificación del Proyecto
- **Nombre**: App Polería - Sistema de Gestión para Pollerías
- **Tipo**: Aplicación de Consola (CLI - Command Line Interface)
- **Contexto**: Proyecto académico

### 1.2 Propósito General
La aplicación tiene como objetivo principal proporcionar un **sistema integral de gestión para establecimientos de comida rápida tipo pollerías**, permitiendo administrar de manera eficiente:
- La venta de productos
- La gestión de clientes
- El seguimiento de mesas disponibles
- La generación de reportes y estadísticas
- El control de inventario de productos
- La persistencia de datos

### 1.3 Problema que Resuelve
En un contexto pre-digital o para pequeñas pollerías, los procesos manuales presentan los siguientes desafíos:

1. **Gestión manual de ventas**: Imposibilidad de registrar y rastrear las ventas de forma ordenada
2. **Pérdida de datos**: Sin persistencia, los registros se pierden cuando se cierra la caja
3. **Falta de información de clientes**: No se pueden identificar clientes frecuentes
4. **Control deficiente de mesas**: Imposibilidad de saber qué mesas están disponibles
5. **Reportes inexactos**: Dificultad para calcular ingresos diarios y estadísticas
6. **Falta de trazabilidad**: No hay constancia de quién realizó cada venta ni cuándo
7. **Generación manual de comprobantes**: Proceso lento y propenso a errores

---

## 2. STACK TECNOLÓGICO

### 2.1 Lenguaje de Programación
- **Lenguaje**: Java
- **Versión**: 21
- **Características de Java 21 utilizadas**:
  - Expresiones Switch mejoradas (Switch expressions con `->`):
    ```java
    case "1" -> // Realizar venta
    case "2" -> // Gestionar ventas
    ```
  - Streams
  - LocalDateTime para manejo de fechas modernas
  - Clases genericas
  - Arreglos avanzados (List, Set, HashMap)

### 2.2 Herramientas de Construcción y Gestión de Dependencias
- **Build Tool**: Apache Maven 4.0.0
- **GroupId**: `com.mycompany`
- **ArtifactId**: `AppPolleria`
- **Versión del Artefacto**: 1.0-SNAPSHOT
- **Empaquetado**: JAR (Java Archive)
- **Punto de Entrada Principal**: `com.mycompany.apppolleria.AppPolleria`

### 2.3 Dependencias
El proyecto **no tiene dependencias externas** en el `pom.xml`. Utiliza únicamente:
- **Librerías estándar de Java** (java.time, java.io, java.util, etc.)
- **API nativa de Java** para:
  - Entrada/Salida de consola
  - Serialización de objetos
  - Manipulación de fechas y horas
  - Colecciones genéricas

### 2.4 Configuración de Proyecto
```xml
<modelVersion>4.0.0</modelVersion>
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
<maven.compiler.release>21</maven.compiler.release>
```

---

## 3. ARQUITECTURA Y ESTRUCTURA DEL PROYECTO

### 3.1 Estructura de Directorios
```
app-consola-java-para-polleria/
├── pom.xml                                    # Configuración de Maven
├── nb-configuration.xml                       # Configuración de NetBeans
├── polleria.dat                               # Datos persistentes
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── mycompany/
│                   └── apppolleria/
│                       ├── AppPolleria.java   # Clase principal
│                       └── clases/
│                           ├── Clientes.java
│                           ├── Datos.java
│                           ├── Menus.java
│                           ├── Mesas.java
│                           ├── Productos.java
│                           ├── Repositorio.java
│                           └── Ventas.java
└── target/                                    # Directorio compilado (generado por Maven)
```

### 3.2 Paradigma Arquitectónico
La aplicación sigue un **patrón arquitectónico en capas**:

```
┌─────────────────────────────────────┐
│     Capa de Presentación/UI         │
│  (AppPolleria.java - Consola)       │
├─────────────────────────────────────┤
│     Capa de Lógica de Negocio       │
│  (Ventas, Clientes, Productos)      │
├─────────────────────────────────────┤
│     Capa de Acceso a Datos          │
│  (Repositorio, Datos, Serialización)│
├─────────────────────────────────────┤
│     Persistencia de Datos           │
│  (archivo: polleria.dat)            │
└─────────────────────────────────────┘
```

### 3.3 Patrón de Diseño: Patrón Repositorio Genérico
La clase `Repositorio<T>` implementa un patrón genérico que actúa como intermediario entre la lógica de negocio y los datos, proporcionando operaciones CRUD (Create, Read, Update, Delete).

---

## 4. ANÁLISIS DETALLADO DE CADA COMPONENTE

### 4.1 CLASE: AppPolleria.java (Punto de Entrada Principal)

#### Propósito
Es la clase principal que coordina toda la interfaz de usuario, los flujos de proceso y la interacción con el usuario a través de la consola.

#### Funcionalidades Principales
```
MENÚ PRINCIPAL
├─ 1. Realizar venta
├─ 2. Gestionar ventas
├─ 3. Gestionar clientes
├─ 4. Gestionar productos
├─ 5. Reportes y estadísticas
└─ 6. Salir
```

#### 4.1.1 Sub-Menú: REALIZAR VENTA
Este es el flujo más complejo de la aplicación. Comprende:

**Paso 1: Búsqueda o Registro de Cliente**
- Solicita DNI al usuario
- Busca el cliente en la base de datos usando expresiones Lambda:
  ```java
  cliente = datos.clientes.buscar(c -> c.getDni().equals(dniBusqueda));
  ```
- Si el cliente existe, muestra sus datos
- Si NO existe, ofrece tres opciones:
  - Registrar nuevo cliente (requiere nombres, apellidos, celular)
  - Continuar sin registrar cliente (utiliza datos por defecto)
  - Reintentar con otro DNI

**Paso 2: Selección de Tipo de Venta**
- Consumo en local (requiere seleccionar mesa)
- Para llevar
- Delivery

**Paso 3: Visualización y Selección de Mesas (si aplica)**
- Muestra una matriz visual de 3x4 mesas (12 mesas total)
- Indica estado: `[ ]` libre o `[X]` ocupada
- Permite seleccionar mesa disponible

**Paso 4: Selección de Productos**
- Menú dinámico generado desde la base de datos
- Categorías de productos:
  - Pollos (1/8, 1/4, 1/2, Pollo entero)
  - Ensaladas (clásica, rusa, de col, mixta, con palta)
  - Postres (helado, gelatina, mazamorra, arroz con leche)
  - Bebidas (Inca Kola, Coca-Cola, chicha morada, agua mineral)
- Cada selección agrega el producto a la lista de compra
- Muestra detalles del producto agregado

**Paso 5: Método de Pago**
- Efectivo (calcula vuelto automáticamente)
- Tarjeta de crédito
- Tarjeta de débito
- Yape/Plin
- Transferencia bancaria

**Paso 6: Generación y Exportación de Venta**
- Asigna número de venta
- Registra encargado
- Calcula total
- Guarda en base de datos
- Genera voucher (comprobante) con formato profesional
- Exporta voucher a archivo `.txt`
- Muestra simulación de proceso con delays (pauses)

#### 4.1.2 Sub-Menú: GESTIONAR VENTAS
**Opciones disponibles:**
1. **Listar ventas de hoy**: Muestra todas las ventas del día actual
2. **Listar ventas según fecha**: Permite buscar ventas de fechas anteriores (formato dd-MM-yyyy)
3. **Buscar venta**: Busca una venta específica por número y fecha
4. **Volver al menú principal**

#### 4.1.3 Sub-Menú: GESTIONAR CLIENTES
**Opciones disponibles:**
1. **Listar clientes**: Muestra todos los clientes registrados
2. **Buscar cliente**: Busca por DNI
3. **Registrar cliente**: Crea nuevo cliente con validaciones
4. **Eliminar cliente**: Elimina cliente existente (requiere confirmación)
5. **Volver al menú principal**

**Validaciones en registro de cliente:**
- DNI debe tener exactamente 8 caracteres numéricos
- No permite DNI duplicados
- Nombres y apellidos solo aceptan caracteres alfabéticos
- Celular acepta entre 1 y 9 dígitos

#### 4.1.4 Sub-Menú: GESTIONAR PRODUCTOS
**Opciones disponibles:**
1. **Listar productos**: Por categoría (Pollos, Ensaladas, Postres, Bebidas)
2. **Buscar producto**: Búsqueda por nombre (búsqueda parcial)
3. **Agregar producto**: Registra nuevo producto con:
   - Nombre
   - Precio
   - Contenido (ingredientes/componentes)
4. **Volver al menú principal**

#### 4.1.5 Sub-Menú: REPORTES Y ESTADÍSTICAS
**Opciones disponibles:**
1. **Reporte del día**: Muestra:
   - Total de ventas realizadas hoy
   - Ingresos totales en soles
2. **Reporte por fecha**: Permite especificar una fecha para ver estadísticas
3. **Productos más vendidos**: Muestra:
   - Producto más vendido histórico
   - Producto más vendido hoy
4. **Cliente más frecuente**: Muestra:
   - Cliente con más compras históricamente
   - Cliente con más compras hoy
5. **Volver al menú principal**

#### 4.1.6 Métodos Auxiliares de AppPolleria

| Método | Parámetros | Retorno | Descripción |
|--------|-----------|---------|-------------|
| `mostrarMesas()` | filas, columnas, Mesas[] | void | Renderiza matriz visual de mesas en consola |
| `mostrarMenu()` | título, mensaje, carácter, opciones | void | Genera menú formateado |
| `enviarOpciones()` | List<Productos> | String[] | Convierte lista de productos a opciones de menú |
| `mostrarDetallesProducto()` | Productos | void | Muestra info de producto agregado |
| `validarEntrada()` | entrada, min, max, tipo | boolean | Valida entrada con 6 tipos diferentes |
| `mostrarMejorProducto()` | etiqueta, List<Ventas> | void | Calcula y muestra producto más vendido |
| `mostrarMejorCliente()` | etiqueta, List<Ventas>, Repositorio | void | Calcula y muestra cliente más frecuente |

#### 4.1.7 Sistema de Validación de Entrada
Existe un método flexible `validarEntrada()` que acepta 6 tipos de validación:

| Tipo | Regla de Validación |
|------|-------------------|
| `TEXTO` | Solo letras, espacios, acentos y ñ |
| `ENTERO-OPCIONES` | Números dentro de rango min-max |
| `ENTERO-DATO` | Números exactamente de longitud min-max |
| `DECIMAL` | Números con hasta 2 decimales |
| `FECHA` | Formato dd-MM-yyyy |
| Desconocido | Retorna error |

---

### 4.2 CLASE: Clientes.java

#### Propósito
Representa la entidad de cliente y encapsula toda la información relacionada a personas que realizan compras.

#### Atributos
```java
private String nombres;      // Nombre(s) del cliente
private String apellidos;    // Apellido(s) del cliente
private String dni;          // Documento Nacional de Identidad (8 dígitos)
private String celular;      // Número de contacto celular
```

#### Constructores
- `Clientes()` - Vacío (constructor por defecto)
- `Clientes(nombres, apellidos, dni, celular)` - Constructor parametrizado

#### Métodos Importantes

| Método | Propósito |
|--------|-----------|
| `getNombres() / setNombres()` | Acceso a nombres |
| `getApellidos() / setApellidos()` | Acceso a apellidos |
| `getDni() / setDni()` | Acceso a DNI |
| `getCelular() / setCelular()` | Acceso a celular |
| `buscarClienteDNI(lista, dni)` | Búsqueda lineal de cliente por DNI |
| `listarClientes(título, lista)` | Imprime lista formateada de clientes |
| `mostrarCliente(título, cliente)` | Muestra detalles de un cliente |

#### Serialización
Implementa `Serializable` con `serialVersionUID = 1L` para permitir persistencia de datos.

---

### 4.3 CLASE: Productos.java

#### Propósito
Representa los artículos ofrecidos por la pollería (pollos, ensaladas, postres, bebidas).

#### Atributos
```java
private String nombre;        // Nombre del producto (ej: "1/4 Pollo a la brasa")
private double precio;        // Precio en soles
private String[] contenido;   // Array de ingredientes/componentes
```

#### Métodos Importantes

| Método | Propósito |
|--------|-----------|
| `getNombre() / setNombre()` | Acceso a nombre |
| `getPrecio() / setPrecio()` | Acceso a precio |
| `getContenido() / setContenido()` | Acceso a ingredientes |
| `buscarProductoPorNombre()` | Búsqueda parcial case-insensitive |
| `listarProductos()` | Imprime lista formateada |
| `mostrarProducto()` | Muestra detalles de un producto |

#### Datos Precargados (Datos por Defecto)
La aplicación incluye un catálogo inicial:

**POLLOS:**
- 1/8 Mostrito (S/. 15.00) - arroz chaufa, papas fritas, 1/8 pollo, ensalada
- 1/8 Pollo a la brasa (S/. 12.00) - 1/8 pollo, papas fritas, ensalada
- 1/4 Pollo a la brasa (S/. 18.00) - 1/4 pollo, papas fritas, ensalada
- 1/2 Pollo a la brasa (S/. 28.00) - 1/2 pollo, papas fritas, ensalada
- Pollo entero a la brasa (S/. 52.00) - 1 pollo, papas familiares, ensalada grande

**ENSALADAS:**
- Ensalada clásica (S/. 5.00)
- Ensalada rusa (S/. 6.00)
- Ensalada de col (S/. 5.00)
- Ensalada mixta (S/. 6.50)
- Ensalada con palta (S/. 7.00)

**POSTRES:**
- Helado personal (S/. 4.00)
- Gelatina (S/. 3.00)
- Mazamorra morada (S/. 4.00)
- Arroz con leche (S/. 4.00)
- Combo de postres (S/. 7.00)

**BEBIDAS:**
- Inca Kola personal 500ml (S/. 3.50)
- Inca Kola 1Lt (S/. 6.00)
- Coca-Cola personal 500ml (S/. 3.50)
- Jarra de chicha morada 1Lt (S/. 8.00)
- Agua mineral 500ml (S/. 2.50)

---

### 4.4 CLASE: Ventas.java

#### Propósito
Representa una transacción comercial que registra toda la información de una compra realizada en la pollería.

#### Atributos
```java
private int numeroVenta;           // Identificador único de la venta (ej: V-0001)
private String encargado;          // Persona que realiza/registra la venta
private Clientes cliente;          // Referencia al cliente (puede ser null)
private String tipoVenta;          // "Consumo en local", "Para llevar", "Delivery"
private Mesas mesa;                // Referencia a mesa (null si no es consumo local)
private List<Productos> productos; // Productos incluidos en la venta
private int cantidadProductos;     // Cantidad de productos
private double total;              // Monto total a pagar en soles
private String metodoPago;         // Método de pago utilizado
private LocalDateTime fechaVenta;  // Fecha y hora exacta de la transacción
```

#### Constructores
- `Ventas()` - Vacío
- `Ventas(numeroVenta, encargado, cliente, tipoVenta, mesa, productos, cantidadProductos, total, metodoPago, fechaVenta)` - Completo

#### Métodos Importantes

| Método | Propósito |
|--------|-----------|
| `agregarProducto(Productos)` | Agrega producto a la lista de compra |
| `buscarVentaFechaNumero()` | Búsqueda de venta por fecha y número |
| `listarVentas()` | Imprime lista de ventas formateada |
| `mostrarVenta()` | Muestra detalles de una venta |
| `generarVoucherVenta()` | Crea ticket/comprobante profesional |
| `exportarVoucherVenta()` | Guarda voucher en archivo `.txt` |

#### Proceso de Generación de Voucher
El voucher incluye:
- Encabezado: Nombre de la pollería, RUC, dirección
- Número de venta, fecha, cajero
- Método de pago y tipo de venta
- Número de mesa (si aplica)
- Datos del cliente (si está registrado)
- Listado de productos con precios
- Total a pagar
- Monto entregado y vuelto (solo si es efectivo)
- Mensaje de agradecimiento

**Ejemplo de formato:**
```
==================================================
           POLLERÍA 'EL SABOR CRIOLLO'
              RUC: 12345678901
           Av. Principal 123 - ICA
==================================================
VENTA N°  : V-0001
FECHA     : 12/07/2025 15:30:45
CAJERO    : MARCOS
PAGO      : Efectivo
TIPO      : Consumo en local
MESA      : 5
CLIENTE   : JUAN PERÉZ RAMIREZ
DNI       : 12345678

--------------------------------------------------
Productos                           Precio
--------------------------------------------------
1/4 Pollo a la brasa               S/. 18.00
Ensalada clásica                   S/. 5.00
Coca-Cola personal 500ml           S/. 3.50
--------------------------------------------------
TOTAL A PAGAR                       S/. 26.50
--------------------------------------------------

--------------------------------------------------
Paga con                            S/. 30.00
Vuelto                              S/. 3.50
--------------------------------------------------

==================================================
         ¡GRACIAS POR SU COMPRA!
==================================================
```

---

### 4.5 CLASE: Mesas.java

#### Propósito
Representa una mesa física del local donde pueden consumir los clientes.

#### Atributos
```java
private int numeroMesa;      // Identificador de la mesa (1-12)
private boolean estadoMesa;  // true = libre, false = ocupada
```

#### Métodos Simples
- `getNumeroMesa() / setNumeroMesa()`
- `isEstadoMesa() / setEstadoMesa()`

#### Características
- Inicialización: Se crean 12 mesas por defecto
- Estado aleatorio inicial (pseudo-aleatorio con `Random`)
- Representación visual: `[ ]` (libre) o `[X]` (ocupada)

---

### 4.6 CLASE: Datos.java (Orquestador de Persistencia)

#### Propósito
Actúa como contenedor central de todos los datos y gestiona la persistencia serializada.

#### Atributos Públicos
```java
public Repositorio<Clientes> clientes;
public Repositorio<Productos> productosPollos;
public Repositorio<Productos> productosEnsaladas;
public Repositorio<Productos> productosPostres;
public Repositorio<Productos> productosBebidas;
public Repositorio<Ventas> ventas;
public Mesas[] mesas;
```

#### Métodos Principales

| Método | Propósito |
|--------|-----------|
| `cargar()` | Método estático que carga datos del archivo `polleria.dat` |
| `guardar(mostrarMensaje)` | Serializa todos los datos a archivo |
| `cargarDatosPorDefecto()` | Inicializa datos de prueba si es primera ejecución |
| `inicializarMesas()` | Crea array de 12 mesas |

#### Flujo de Persistencia
```
Inicio de Aplicación
        ↓
Datos.cargar()
        ↓
    ¿Existe polleria.dat?
   /                     \
 SÍ                       NO
 ↓                         ↓
Deserializar      Crear nuevos datos
(ObjectInputStream)  (cargarDatosPorDefecto)
 ↓                         ↓
Restaurar estado      Guardar en archivo
anterior              (primer inicio)
 ↓                         ↓
Retornar Datos      Retornar Datos nuevos
```

#### Datos Precargados en Primera Ejecución
- **5 clientes iniciales** con datos de prueba
- **Productos**: 20 artículos distribuidos en 4 categorías
- **Mesas**: 12 mesas con estados aleatorios
- **Ventas**: 4 ventas históricas de prueba

---

### 4.7 CLASE: Repositorio<T> (Patrón Genérico)

#### Propósito
Implementa el patrón Repository proporcionando operaciones CRUD (Create, Read, Update, Delete) genéricas para cualquier tipo de dato.

#### Características
- **Genérico**: Trabaja con cualquier tipo `<T>`
- **Serializable**: Permite persistencia de su contenido
- **Funcional**: Acepta expresiones Lambda para búsquedas

#### Métodos Disponibles

| Método | Descripción |
|--------|------------|
| `agregar(T elemento)` | Añade elemento a la lista |
| `eliminar(T elemento)` | Remueve elemento de la lista |
| `obtenerTodos()` | Retorna toda la lista |
| `buscar(Predicate<T> criterio)` | Busca primer elemento que cumple criterio (Lambda) |
| `filtrar(Predicate<T> criterio)` | Retorna lista de todos elementos que cumplen criterio |
| `cantidad()` | Retorna tamaño de la lista |

#### Ejemplos de Uso
```java
// Búsqueda de cliente por DNI
Clientes cliente = datos.clientes.buscar(c -> c.getDni().equals("12345678"));

// Filtrar ventas de hoy
List<Ventas> ventasHoy = datos.ventas.filtrar(
    v -> v.getFechaVenta().toLocalDate().equals(LocalDate.now())
);

// Filtrar ventas de una fecha específica
List<Ventas> ventasFecha = datos.ventas.filtrar(
    v -> v.getFechaVenta().toLocalDate().isEqual(fechaBuscada)
);
```

---

### 4.8 CLASE: Menus.java (Generador de Interfaz)

#### Propósito
Proporciona utilidades para crear y renderizar menús formateados en la consola.

#### Atributos
```java
private String titulo;              // Título del menú
private String[] opciones;          // Array de opciones disponibles
private String[] mensajeInfoAnt;    // Mensajes antes de las opciones
private String[] mensajeInfoPos;    // Mensajes después de las opciones
```

#### Métodos Principales

| Método | Propósito |
|--------|-----------|
| `centrarTexto(ancho, texto)` | Centra texto en un ancho específico con espacios |
| `centrarTexto(ancho, texto, símbolo)` | Centra texto y rellena con símbolo específico |
| `toString()` | Genera representación formateada del menú completo |

#### Formato de Salida del Menú
```
==================================================
                  TITULO DEL MENU
==================================================
| (1) Primera opción                             |
| (2) Segunda opción                             |
| (3) Tercera opción                             |
==================================================
Selecciona una opción: 
```

---

## 5. FLUJOS DE PROCESOS PRINCIPALES

### 5.1 Flujo de Realización de Venta (El más complejo)

```
INICIO
  ↓
┌─────────────────────────────────────────────────┐
│ 1. GESTIÓN DE CLIENTE                           │
├─────────────────────────────────────────────────┤
│ ┌─────────────────────────────────────────────┐ │
│ │ Solicitar DNI                               │ │
│ └──────────┬──────────────────────────────────┘ │
│            ↓                                    │
│ ¿Cliente existe?                               │
│   ├─ SÍ: Usar datos existentes                │
│   └─ NO: ¿Registrar nuevo cliente?            │
│        ├─ SÍ: Solicitar datos y registrar     │
│        ├─ NO: Usar datos por defecto          │
│        └─ REINTENTAR: Volver a solicitar DNI │
└─────────────────────────────────────────────────┘
  ↓
┌─────────────────────────────────────────────────┐
│ 2. TIPO DE VENTA                                │
├─────────────────────────────────────────────────┤
│ ├─ Consumo en local ──→ (Requiere mesa)        │
│ ├─ Para llevar        ──→ (Sin mesa)           │
│ └─ Delivery           ──→ (Sin mesa)           │
└─────────────────────────────────────────────────┘
  ↓
┌─────────────────────────────────────────────────┐
│ 3. SELECCIÓN DE MESA (si aplica)               │
├─────────────────────────────────────────────────┤
│ Mostrar matriz 3x4                              │
│ ¿Mesa disponible (libre)?                       │
│   ├─ SÍ: Asignar mesa                          │
│   └─ NO: Solicitar otra mesa                   │
└─────────────────────────────────────────────────┘
  ↓
┌─────────────────────────────────────────────────┐
│ 4. SELECCIÓN DE PRODUCTOS (Bucle)              │
├─────────────────────────────────────────────────┤
│ ┌────────────────────────────────────────────┐ │
│ │ Mostrar categorías                         │ │
│ │ ├─ Pollos                                  │ │
│ │ ├─ Ensaladas                               │ │
│ │ ├─ Postres                                 │ │
│ │ ├─ Bebidas                                 │ │
│ │ ├─ Generar venta                           │ │
│ │ └─ Cancelar venta                          │ │
│ └────────────────────────────────────────────┘ │
│ Si selecciona categoría: Mostrar productos     │
│ Seleccionar producto → Agregar a compra        │
│ Repetir hasta seleccionar "Generar venta"      │
└─────────────────────────────────────────────────┘
  ↓
┌─────────────────────────────────────────────────┐
│ 5. MÉTODO DE PAGO                               │
├─────────────────────────────────────────────────┤
│ ├─ Efectivo ──────────→ (Calcular vuelto)     │
│ ├─ Tarjeta de crédito                         │
│ ├─ Tarjeta de débito                          │
│ ├─ Yape / Plin                                 │
│ └─ Transferencia bancaria                      │
│                                                 │
│ Si Efectivo: ¿Monto ≥ Total?                  │
│   └─ NO: Solicitar nuevo monto                │
└─────────────────────────────────────────────────┘
  ↓
┌─────────────────────────────────────────────────┐
│ 6. PROCESAMIENTO Y GENERACIÓN                   │
├─────────────────────────────────────────────────┤
│ Asignar número de venta                         │
│ Guardar en base de datos                        │
│ Generar voucher                                 │
│ Exportar voucher a archivo .txt                │
│ Mostrar voucher en consola                      │
└─────────────────────────────────────────────────┘
  ↓
RETORNAR AL MENÚ PRINCIPAL
```

---

## 6. CARACTERÍSTICAS TÉCNICAS AVANZADAS

### 6.1 Expresiones Lambda (Java 8+)
El proyecto utiliza expressions Lambda para búsquedas funcionales:
```java
// Búsqueda de cliente
cliente = datos.clientes.buscar(c -> c.getDni().equals(dniBusqueda));

// Filtrado de ventas de hoy
ventasDeHoy = datos.ventas.filtrar(
    v -> v.getFechaVenta() != null 
    && v.getFechaVenta().toLocalDate().equals(LocalDate.now())
);
```

### 6.2 Streams API (Java 8+)
Se utilizan streams para operaciones de agregación:
```java
// Calcular ingresos totales
double ingresoTotal = ventasDeHoy.stream()
    .mapToDouble(Ventas::getTotal)
    .sum();

// Filtrar ventas de una fecha específica
List<Ventas> ventasFiltradas = ventasAnteriores.stream()
    .filter(v -> v.getFechaVenta().toLocalDate().isEqual(fechaFormateada))
    .toList();

// Buscar si existen ventas en una fecha
boolean hayVentas = ventasAnteriores.stream()
    .anyMatch(v -> fecha.isEqual(v.getFechaVenta().toLocalDate()));
```

### 6.3 Switch Expressions (Java 14+)
El código utiliza switch expressions modernas en lugar de switch statements:
```java
// Formato antiguo (no usado)
switch (opción) {
    case "1":
        // hacer algo
        break;
}

// Formato moderno (usado en el proyecto)
switch (opcionMenuPrincipal) {
    case "1" -> { /* REALIZAR VENTA */ }
    case "2" -> { /* GESTIONAR VENTAS */ }
    case "3" -> { /* GESTIONAR CLIENTES */ }
    // ...
}
```

### 6.4 Serialización de Objetos
Toda la información se persiste mediante serialización:
```java
// Guardar
try (ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream("polleria.dat"))) {
    out.writeObject(this);
}

// Cargar
try (ObjectInputStream in = new ObjectInputStream(
        new FileInputStream("polleria.dat"))) {
    return (Datos) in.readObject();
}
```

### 6.5 API de Fecha/Hora Moderna (java.time)
```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Obtener fecha actual
LocalDate hoy = LocalDate.now();

// Obtener fecha y hora actual
LocalDateTime ahora = LocalDateTime.now();

// Comparaciones
if (fecha.toLocalDate().equals(LocalDate.now())) { }
if (fecha.isBefore(LocalDate.now())) { }

// Formateo
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
String fechaFormato = ahora.format(formatter);
```

### 6.6 Genéricos en Java
La clase `Repositorio<T>` es un ejemplo de clase genérica:
```java
public class Repositorio<T> implements Serializable {
    private List<T> lista;
    
    public void agregar(T elemento) { }
    public T buscar(Predicate<T> criterio) { }
    public List<T> filtrar(Predicate<T> criterio) { }
}

// Uso
Repositorio<Clientes> clientes = new Repositorio<>();
Repositorio<Productos> productos = new Repositorio<>();
Repositorio<Ventas> ventas = new Repositorio<>();
```

---

## 7. PERSISTENCIA DE DATOS

### 7.1 Mecanismo de Almacenamiento
- **Formato**: Serialización binaria de objetos Java
- **Archivo**: `polleria.dat` (en el directorio de ejecución)
- **Ubicación**: Raíz del proyecto (donde se ejecuta el JAR)

### 7.2 Estructura de Persistencia
```
polleria.dat
├── Objeto Datos
│   ├── Repositorio<Clientes>
│   │   └── List<Clientes>
│   │       ├── Clientes {nombres, apellidos, dni, celular}
│   │       ├── Clientes {nombres, apellidos, dni, celular}
│   │       └── ... (N clientes)
│   │
│   ├── Repositorio<Productos> (Pollos)
│   │   └── List<Productos> { ... }
│   ├── Repositorio<Productos> (Ensaladas)
│   │   └── List<Productos> { ... }
│   ├── Repositorio<Productos> (Postres)
│   │   └── List<Productos> { ... }
│   ├── Repositorio<Productos> (Bebidas)
│   │   └── List<Productos> { ... }
│   │
│   ├── Repositorio<Ventas>
│   │   └── List<Ventas>
│   │       ├── Ventas {numeroVenta, cliente, productos, fecha, ...}
│   │       ├── Ventas {numeroVenta, cliente, productos, fecha, ...}
│   │       └── ... (N ventas)
│   │
│   └── Mesas[] (Array de 12 mesas)
│       ├── Mesa {numeroMesa, estado}
│       ├── Mesa {numeroMesa, estado}
│       └── ... (12 mesas)
```

### 7.3 Ciclo de Vida de Datos
1. **Inicio**: Se carga `polleria.dat` (o se crea con datos por defecto)
2. **Operación**: Cambios se mantienen en memoria (no persisten automáticamente)
3. **Guardado**: Llamadas a `datos.guardar(false)` después de cambios importantes
4. **Cierre**: Los datos quedan guardados para próxima ejecución

### 7.4 Eventos de Persistencia
Se guarda después de:
- Registrar nuevo cliente
- Registrar nueva venta
- Registrar nuevo producto
- Eliminar cliente

---

## 8. VALIDACIÓN DE DATOS

### 8.1 Sistema de Validación Integral

| Tipo | Patrón Regex | Requisitos |
|------|------------|-----------|
| `TEXTO` | `[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\s]+` | Solo letras, espacios y acentos |
| `ENTERO-OPCIONES` | `\d+` | Número entero dentro de rango |
| `ENTERO-DATO` | `\d+` | Número de longitud exacta |
| `DECIMAL` | `\d+(\.\d{1,2})?` | Hasta 2 decimales |
| `FECHA` | `[0-9\-]+` | Solo números y guiones (dd-MM-yyyy) |

### 8.2 Validaciones Específicas por Campo

**DNI del Cliente:**
- Exactamente 8 dígitos numéricos
- No permite DNI duplicados
- Validación: `ENTERO-DATO` (8, 8)

**Nombres/Apellidos:**
- Mínimo 1 carácter
- Máximo 100 caracteres
- Solo alfabetos y espacios
- Validación: `TEXTO` (1, 100)

**Celular:**
- Entre 9 y 9 dígitos (para Perú)
- Validación: `ENTERO-DATO` (9, 9)

**Monto de Dinero:**
- Formato decimal con 2 decimales máximo
- Validación: `DECIMAL`
- Requisito: `monto ≥ total`

**Número de Opción:**
- Entero en rango válido del menú
- Validación: `ENTERO-OPCIONES`

---

## 9. INTERFAZ DE USUARIO (UI)

### 9.1 Características de la Consola

**Elementos Visuales:**
- Bordes: `=` para separadores principales, `-` para secundarios
- Formato: Ancho de 50 caracteres para menús, 60 para listados
- Centrado automático de títulos
- Alineación columnar para tablas

**Indicadores Visuales:**
```
Mesas Libres:    [ ]
Mesas Ocupadas:  [X]

Estado Positivo: "CLIENTE ENCONTRADO"
Estado Negativo: "CLIENTE NO ENCONTRADO"
```

### 9.2 Ejemplos de Interfaz

**Menú Principal:**
```
==================================================
               POLLERIA - MENU PRINCIPAL
==================================================
| (1) Realizar venta                             |
| (2) Gestionar ventas                           |
| (3) Gestionar clientes                         |
| (4) Gestionar productos                        |
| (5) Reportes y estadisticas                    |
| (6) Salir                                      |
==================================================
Selecciona una opción: 
```

**Matriz de Mesas:**
```
==================================================
                   SELECCIONAR MESA
==================================================

  ********    ********    ********    ********
  | M-01 |    | M-02 |    | M-03 |    | M-04 |
  | [ ]  |    | [X]  |    | [ ]  |    | [ ]  |
  ********    ********    ********    ********

  ********    ********    ********    ********
  | M-05 |    | M-06 |    | M-07 |    | M-08 |
  | [X]  |    | [X]  |    | [ ]  |    | [ ]  |
  ********    ********    ********    ********

==================================================
         Ocupada: [X]    |    Libre: []
```

---

## 10. FLUJO DE EJECUCIÓN GENERAL

### 10.1 Inicialización
```
┌─────────────────────────────────────────┐
│ 1. JVM inicia AppPolleria.main()        │
├─────────────────────────────────────────┤
│ 2. Se crea Scanner para entrada         │
├─────────────────────────────────────────┤
│ 3. Se llama Datos.cargar()              │
│    ├─ Intenta leer polleria.dat         │
│    └─ Si no existe, crea datos nuevos   │
├─────────────────────────────────────────┤
│ 4. Se crean todos los menús             │
├─────────────────────────────────────────┤
│ 5. Se establece nombre encargado        │
│    (hardcoded: "MARCOS")                │
├─────────────────────────────────────────┤
│ 6. Inicia bucle principal (do-while)    │
└─────────────────────────────────────────┘
```

### 10.2 Bucle Principal
```
do {
    // Actualizar datos dinámicos
    - Recargar ventas de hoy
    - Recargar ventas anteriores
    - Recargar lista de clientes
    - Recargar menús de productos
    
    // Mostrar menú y procesar opción
    - Mostrar menú principal
    - Leer opción del usuario
    - Validar entrada
    - Ejecutar acción según opción
    
} while (iniciarPrograma == true);
```

### 10.3 Terminación
```
case "6" -> {
    scanner.close();
    System.out.println("Saliendo del programa...");
    System.out.println("PROGRAMA FINALIZADO. ¡GRACIAS POR USAR LA APLICACION!");
    iniciarPrograma = false;  // Sale del bucle
}
// Los datos persisten automáticamente en polleria.dat
```

---

## 11. TECNOLOGÍAS Y CONCEPTOS DE POO UTILIZADOS

### 11.1 Principios de POO

| Principio | Aplicación |
|-----------|-----------|
| **Encapsulamiento** | Atributos privados con getters/setters en todas las clases |
| **Herencia** | No se usa directamente; podría mejorarse con una clase base `Entidad` |
| **Polimorfismo** | Métodos sobrecargados en `Menus` (centrarTexto con 2 o 3 parámetros) |
| **Abstracción** | Interfaz `Serializable` para persistencia transparente |

### 11.2 Patrones de Diseño

| Patrón | Implementación |
|--------|----------------|
| **Repositorio** | `Repositorio<T>` proporciona acceso genérico a datos |
| **Singleton** | `Datos.cargar()` proporciona instancia única de datos |
| **Factory** | Métodos `buscar()` y `filtrar()` generan colecciones |
| **Builder** | Constructores parametrizados para entidades |

### 11.3 Características Java Modernas
- **Java 21**: Version target
- **Lambda Expressions**: Búsqueda funcional
- **Streams API**: Agregación y transformación
- **Switch Expressions**: Lógica condicional moderna
- **Generics**: Tipos parametrizados (`Repositorio<T>`)
- **Try-with-resources**: Gestión automática de recursos
- **java.time API**: Manejo de fechas y horas

---

## 12. VOLUMEN DE CÓDIGO

| Componente | Líneas Aproximadas | Métodos | Clases |
|-----------|------------------|---------|--------|
| AppPolleria.java | ~1,100 | 7 | 1 |
| Clientes.java | ~80 | 8 | 1 |
| Productos.java | ~100 | 8 | 1 |
| Ventas.java | ~300 | 12 | 1 |
| Mesas.java | ~30 | 4 | 1 |
| Datos.java | ~150 | 4 | 1 |
| Repositorio.java | ~50 | 6 | 1 |
| Menus.java | ~100 | 5 | 1 |
| **TOTAL** | **~1,810** | **54** | **8** |

---

## 13. CASOS DE USO

### 13.1 Caso de Uso 1: Registrar una Venta Completa
```
Actor: Vendedor
Precondición: Aplicación iniciada

Flujo Principal:
1. Selecciona "Realizar venta"
2. Ingresa DNI del cliente (12345678)
3. Sistema encuentra cliente existente
4. Selecciona tipo de venta: "Consumo en local"
5. Selecciona mesa disponible (M-05)
6. Agrega "1/4 Pollo a la brasa" (S/. 18.00)
7. Agrega "Coca-Cola personal" (S/. 3.50)
8. Selecciona "Generar venta"
9. Elige método de pago: "Efectivo"
10. Ingresa monto: 30.00
11. Sistema calcula vuelto: 8.50
12. Se genera y exporta voucher
13. Venta registrada en base de datos

Postcondición: Nueva venta en registro, voucher guardado, mesa ocupada
```

### 13.2 Caso de Uso 2: Generar Reporte Diario
```
Actor: Gerente/Encargado
Precondición: Existencia de ventas del día

Flujo Principal:
1. Selecciona "Reportes y estadísticas"
2. Selecciona "Reporte del día"
3. Sistema calcula:
   - Número de ventas (4)
   - Ingresos totales (S/. 95.00)
4. Muestra reporte formateado

Postcondición: Información de desempeño diario disponible
```

### 13.3 Caso de Uso 3: Registrar Nuevo Cliente
```
Actor: Vendedor/Recepcionista
Precondición: Cliente no registrado en sistema

Flujo Principal:
1. Durante realización de venta
2. Ingresa DNI: 98765432
3. Cliente no encontrado
4. Opta por registrar nuevo cliente
5. Ingresa: Nombres, Apellidos, Celular
6. Confirma registro
7. Cliente guardado y utilizado en la venta

Postcondición: Nuevo cliente en base de datos, venta asociada
```

---

## 14. LIMITACIONES Y CONSIDERACIONES

### 14.1 Limitaciones Actuales
1. **Interfaz de Consola**: Sin interfaz gráfica
2. **Encargado Hardcoded**: "MARCOS" es fijo (no permite múltiples usuarios)
3. **Datos por Defecto**: RUC y dirección de la pollería son ficticios
4. **Sin Autenticación**: No hay login de usuario
5. **Persistencia Simple**: Solo serialización binaria (vulnerable a cambios de estructura)
6. **Sin Búsqueda Avanzada**: No permite filtros complejos
7. **Menú Dinámico Recargado**: Se recalcula en cada iteración (ineficiente)

### 14.2 Consideraciones de Seguridad
- **Sin Validación SSL/TLS**: No hay cifrado de datos
- **Archivo Binario Legible**: El archivo `polleria.dat` puede corruparse fácilmente
- **Sin Control de Acceso**: Cualquiera con acceso al archivo puede leerlo/modificarlo
- **Sin Auditoría**: No hay registro de quién realizó qué cambios

### 14.3 Escalabilidad
- **Rendimiento**: Para ~1,000 clientes y ~10,000 ventas debería funcionar bien
- **Base de Datos**: Recomendaría migrar a base de datos relacional (MySQL, PostgreSQL)
- **Datos Históricos**: Sin límite definido; podría crecer indefinidamente

---

## 15. REQUISITOS PARA EJECUTAR

### 15.1 Requisitos de Sistema
- **JDK/JRE**: Java 21 o superior
- **Sistema Operativo**: Windows, Linux, macOS
- **Espacio en Disco**: ~5MB mínimo
- **RAM**: 256MB mínimo

### 15.2 Compilación y Ejecución
```bash
# Compilar con Maven
mvn clean compile

# Empaquetar
mvn package

# Ejecutar
java -jar target/AppPolleria-1.0-SNAPSHOT.jar

# O ejecutar directamente
mvn exec:java -Dexec.mainClass="com.mycompany.apppolleria.AppPolleria"
```

---

## 16. RESUMEN RÁPIDO

### 16.1 ¿Qué es?
Una aplicación de consola Java desarrollada como proyecto académico que simula un **sistema completo de gestión para pollerías**, permitiendo realizar ventas, gestionar productos, clientes, mesas y generar reportes.

### 16.2 ¿Qué hace?
- Registra y persiste ventas con detalles completos
- Gestiona base de datos de clientes
- Administra catálogo de productos (pollos, ensaladas, postres, bebidas)
- Controla disponibilidad de mesas
- Genera comprobantes (vouchers) profesionales
- Proporciona reportes y estadísticas de ventas
- Calcula productos más vendidos y clientes más frecuentes

### 16.3 ¿Cómo funciona?
1. Carga datos persistentes (o crea nuevos en primera ejecución)
2. Presenta menú interactivo con 6 opciones principales
3. Procesa entrada del usuario con validaciones
4. Realiza operaciones sobre repositorios genéricos
5. Guarda cambios en archivo binario (`polleria.dat`)
6. Exporta reportes en texto

### 16.4 Conceptos Técnicos Clave
- **POO**: Encapsulamiento, abstracción, polimorfismo
- **Genéricos**: `Repositorio<T>` reutilizable para cualquier tipo
- **Lambda**: Búsquedas funcionales y filtrado de datos
- **Streams**: Agregación y transformación de colecciones
- **Serialización**: Persistencia de objetos Java
- **java.time**: Manejo moderno de fechas y horas
- **Switch Expressions**: Lógica condicional moderna

### 16.5 Estructura
- **8 clases Java** (~1,810 líneas de código)
- **1 archivo de configuración Maven**
- **1 archivo de persistencia binaria** (generado en tiempo de ejecución)

---

**FIN DEL INFORME TÉCNICO**

Este informe proporciona una visión completa y detallada del proyecto, incluyendo su arquitectura, funcionalidades, implementación técnica, y flujos de proceso.
