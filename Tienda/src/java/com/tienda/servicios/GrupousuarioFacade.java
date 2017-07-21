/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.servicios;

import com.tienda.modelo.Grupousuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ricardo
 */
@Stateless
public class GrupousuarioFacade extends AbstractFacade<Grupousuario> {

    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupousuarioFacade() {
        super(Grupousuario.class);
    }
    
    public Grupousuario traeGrupodeUsuario(String usuario){
        Query q =em.createQuery("select g from Grupousuario as g where g.usuario= :usr");
        q.setParameter("usr", usuario);
        return  (Grupousuario) q.getSingleResult();
    }
}
