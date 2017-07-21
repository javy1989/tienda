/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.servicios;

import com.tienda.excepcions.ConsultarException;
import com.tienda.modelo.Entidad;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ricardo
 */
@Stateless
public class EntidadFacade extends AbstractFacade<Entidad> {

    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntidadFacade() {
        super(Entidad.class);
    }

    public Entidad traeUsrPwd(String usr, String pwd) {
        Query q = em.createQuery("select e from Entidad as e where e.usuario= :usr and e.pwd= :pwd");
        q.setParameter("usr", usr);
        q.setParameter("pwd", pwd);
        return (Entidad) q.getSingleResult();
    }
}
