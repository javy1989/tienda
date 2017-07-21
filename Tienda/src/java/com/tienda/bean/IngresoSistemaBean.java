/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.bean;

import com.tienda.modelo.Entidad;
import com.tienda.servicios.EntidadFacade;
import com.tienda.utilidades.Mensajes;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ricardo
 */
@ManagedBean(name = "ingreso")
@RequestScoped
public class IngresoSistemaBean {
    
    @ManagedProperty(value = "#{datosLogueo}")
    private DatosLogueoBean seguridad;
    @EJB
    private EntidadFacade ejbEntidad;
    private String usr;
    private String pwd;
    private Entidad usuario;

    public IngresoSistemaBean() {
    }
    
    public String login() {
        usuario = ejbEntidad.traeUsrPwd(usr, pwd);
        if (usuario == null) {
            pwd="";
            Mensajes.error("Usuario o contrasenia invalidos");
            return null;
        }
        seguridad.setLogueo(usuario);
        if (seguridad.getGrupo()==null) {
            Mensajes.informacion("Usuario no tiene asignado perfil");
            return null;
        }
        return "/sistema/blanco.xhtml?faces-redirect=true";
    }
    
    public DatosLogueoBean getSeguridad() {
        return seguridad;
    }
    
    public void setSeguridad(DatosLogueoBean seguridad) {
        this.seguridad = seguridad;
    }
    
    public String getUsr() {
        return usr;
    }
    
    public void setUsr(String usr) {
        this.usr = usr;
    }
    
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
}
