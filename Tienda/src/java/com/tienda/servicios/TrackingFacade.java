/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.servicios;

import com.tienda.modelo.Producto;
import com.tienda.modelo.Tracking;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ricardo
 */
@Stateless
public class TrackingFacade extends AbstractFacade<Tracking> {

    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrackingFacade() {
        super(Tracking.class);
    }

    public int getUltimoSaldoPrducto(Producto p) {
        Query q = em.createQuery("select t from Tracking as t where t.producto= :producto ORDER BY t.id DESC");
        q.setParameter("producto", p);
        return (q.getFirstResult());
    }
}
