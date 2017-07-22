/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.bean;

import com.tienda.modelo.Usuario;
import com.tienda.servicios.UsuarioFacade;
import com.tienda.utilidades.Mensajes;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

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
    private UsuarioFacade ejbUsuario;
    private String usr;
    private String pwd;


    public IngresoSistemaBean() {
    }
    
    public String login() {
        try {
            Usuario  usuario = ejbUsuario.traeUsrPwd(usr, pwd);
            if (usuario == null) {
                pwd="";
                Mensajes.error("Usuario o contrasenia invalidos");
                return null;
            }
            seguridad.setLogueo(usuario);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./sistema/blanco.jsf?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(IngresoSistemaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
