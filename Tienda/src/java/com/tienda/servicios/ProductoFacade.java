/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.servicios;

import com.tienda.excepcions.ConsultarException;
import com.tienda.modelo.Producto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ricardo
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }

    public List<Producto> traeProductos() throws ConsultarException {
        Map parametros=new HashMap();
        parametros.put(";where", "o.estado=true");
        parametros.put(";orden", "o.descripcion");
        return  encontarParametros(parametros);
    }
}