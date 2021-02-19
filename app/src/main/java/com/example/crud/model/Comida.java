package com.example.crud.model;

public class Comida {
    private String nombre;
    private String precio;
    private String acompaniado;
    private Boolean disponible;
    private String udi;
    private String tipo;



    public Comida() {
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUdi() {
        return udi;
    }

    public void setUdi(String udi) {
        this.udi = udi;
    }

    public String getAcompaniado() {
        return acompaniado;
    }

    public void setAcompaniado(String acompaniado) {
        this.acompaniado = acompaniado;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible =  disponible ;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
