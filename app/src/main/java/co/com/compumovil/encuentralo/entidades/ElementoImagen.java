package co.com.compumovil.encuentralo.entidades;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ElementoImagen implements Serializable {
  private Integer id;
  private String nombre;
  private String descripcion;
  private String categoria;

  public ElementoImagen(Integer id, String nombre, String descripcion, String categoria) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.categoria = categoria;
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }


}

