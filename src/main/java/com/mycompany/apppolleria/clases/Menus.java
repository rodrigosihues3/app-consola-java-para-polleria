package com.mycompany.apppolleria.clases;

public class Menus {

    private String titulo;
    private String[] opciones = new String[20];
    private String[] mensajeInfoAnt;
    private String[] mensajeInfoPos;

    public Menus() {
    }

    public Menus(String titulo, String[] opciones) {
        this.titulo = titulo;
        this.opciones = opciones;
    }

    public Menus(String titulo, String[] opciones, String[] mensajeInfoAnt, String[] mensajeInfoPos) {
        this.titulo = titulo;
        this.opciones = opciones;
        this.mensajeInfoAnt = mensajeInfoAnt;
        this.mensajeInfoPos = mensajeInfoPos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public void setOpciones(String[] opciones) {
        this.opciones = opciones;
    }

    public String[] getMensajeInfoAnt() {
        return mensajeInfoAnt;
    }

    public void setMensajeInfoAnt(String[] mensajeInfoAnt) {
        this.mensajeInfoAnt = mensajeInfoAnt;
    }

    public String[] getMensajeInfoPos() {
        return mensajeInfoPos;
    }

    public void setMensajeInfoPos(String[] mensajeInfoPos) {
        this.mensajeInfoPos = mensajeInfoPos;
    }

    public void agregarOpciones(String opcion) {
        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i] == null) {
                opciones[i] = opcion;
                break;
            }
        }
    }

    public String agregarOpciones(String... opciones) {
        this.opciones = new String[opciones.length];

        System.arraycopy(opciones, 0, this.opciones, 0, opciones.length);

        String mensaje = "Opciones agregadas correctamente: \n";

        for (String opcion : opciones) {
            mensaje += "- " + opcion + "\n";
        }

        return mensaje;
    }

    public static String centrarTexto(int ancho, String texto) {
        if (texto == null) {
            texto = "";
        }
        if (texto.length() > ancho) {
            return texto.substring(0, ancho) + "\n";
        }
        int padding = (ancho - texto.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + texto + " ".repeat(Math.max(0, ancho - texto.length() - padding));
    }

    public static String centrarTexto(int ancho, String texto, char simbolo) {
        int contenidoLargo = texto.length() + 2; // Espacios a cada lado
        int padding = (ancho - contenidoLargo) / 2;

        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(simbolo).repeat(padding));
        sb.append(" ").append(texto).append(" ");
        sb.append(String.valueOf(simbolo).repeat(ancho - sb.length())); // Relleno restante
        sb.append("\n");

        return sb.toString();
    }

    @Override
    public String toString() {
        String menu = "\n";

        menu += "=".repeat(50) + "\n";
        menu += "|" + centrarTexto(48, titulo) + "|\n";
        menu += "=".repeat(50) + "\n";

        if (mensajeInfoAnt != null) {
            for (String mensaje : mensajeInfoAnt) {
                menu += mensaje;
            }
        }

        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i] != null) {
                menu += String.format("| (%d) %-42s |%n", i + 1, opciones[i]);
            }
        }

        if (mensajeInfoPos != null) {
            for (String mensaje : mensajeInfoPos) {
                menu += mensaje;
            }
        }

        menu += "=".repeat(50) ;

        menu += "\nSelecciona una opción: ";

        return menu;
    }

}
