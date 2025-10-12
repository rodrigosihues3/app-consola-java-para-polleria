package com.mycompany.apppolleria.clases;

public class Mesas {

    private int numeroMesa;
    private boolean estadoMesa;

    public Mesas(int numeroMesa, boolean estadoMesa) {
        this.numeroMesa = numeroMesa;
        this.estadoMesa = estadoMesa;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public boolean isEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(boolean estadoMesa) {
        this.estadoMesa = estadoMesa;
    }

}
