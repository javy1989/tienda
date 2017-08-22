/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.utilidades;

/**
 *
 * @author desarrollo
 */
public enum TransaccionEnum {

    I("Ingreso"),
    O("Salida"),
    B("Baja");
    private String descripcion;

    private TransaccionEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
