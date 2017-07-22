/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.bean;

import com.tienda.servicios.ProductoFacade;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ricardo
 */
@ManagedBean(name = "producto")
@ViewScoped
public class ProductoBean {

    @EJB
    private ProductoFacade ejbProducto;
    
    public ProductoBean() {
    }
    
}
