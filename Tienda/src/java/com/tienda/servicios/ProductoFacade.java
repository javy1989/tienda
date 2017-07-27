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
import javax.persistence.Query;

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

    public List<Producto> getProductosEstado() throws ConsultarException {
        Map parametros = new HashMap();
        parametros.put(";where", "o.estado=true");
        parametros.put(";orden", "o.descripcion");
        return encontarParametros(parametros);
    }

    public Producto getProductoCodigo(String codigo) {
        try {
            Query q = em.createQuery("SELECT p FROM Producto p WHERE p.codigo= :cod");
            q.setParameter("cod", codigo);
            q.getSingleResult();
            return (Producto) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
}
