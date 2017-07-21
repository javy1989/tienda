/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.bean;

import com.tienda.modelo.Entidad;
import com.tienda.modelo.Grupousuario;
import com.tienda.servicios.GrupousuarioFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ricardo
 */
@ManagedBean(name = "datosLogueo")
@SessionScoped
public class DatosLogueoBean implements Serializable {

    @EJB
    private GrupousuarioFacade ejbGrupo;

    private Entidad usuario;
    private Grupousuario grupo;

    public DatosLogueoBean() {
    }

    public String setLogueo(Entidad entidad) {
        this.usuario = entidad;
        grupo = ejbGrupo.traeGrupodeUsuario(usuario.getPin());
        if (grupo == null) {
            this.usuario = null;
            return null;
        }

        return null;
    }

    public Entidad getUsuario() {
        return usuario;
    }

    public void setUsuario(Entidad usuario) {
        this.usuario = usuario;
    }

    public Grupousuario getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupousuario grupo) {
        this.grupo = grupo;
    }

}
