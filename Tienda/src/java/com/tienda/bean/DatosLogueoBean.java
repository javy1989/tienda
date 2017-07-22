/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.bean;

import com.tienda.modelo.Usuario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ricardo
 */
@ManagedBean(name = "datosLogueo")
@SessionScoped
public class DatosLogueoBean implements Serializable {

    private Usuario usuario;

    public DatosLogueoBean() {
    }

    public String setLogueo(Usuario usuario) {
        this.usuario = usuario;
        return null;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
