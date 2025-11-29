package com.mycompany.apppolleria.clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Repositorio<T> implements Serializable {

  private List<T> lista;

  public Repositorio() {
    this.lista = new ArrayList<>();
  }

  public void agregar(T elemento) {
    this.lista.add(elemento);
  }

  public void eliminar(T elemento) {
    this.lista.remove(elemento);
  }

  public List<T> obtenerTodos() {
    return this.lista;
  }

  // Busca según una condición (Lambda)
  public T buscar(Predicate<T> criterio) {
    for (T elemento : lista) {
      if (criterio.test(elemento)) {
        return elemento;
      }
    }
    return null;
  }

  // Método para filtrar varios elementos (ej. ventas de una fecha)
  public List<T> filtrar(Predicate<T> criterio) {
    List<T> resultado = new ArrayList<>();
    for (T elemento : lista) {
      if (criterio.test(elemento)) {
        resultado.add(elemento);
      }
    }
    return resultado;
  }

  public int cantidad() {
    return this.lista.size();
  }
}
