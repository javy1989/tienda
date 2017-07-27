/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.servicios;

import com.tienda.modelo.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ricardo
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario traeUsrPwd(String usr, String pwd) {
        try {
            Query q = em.createQuery("select u from Usuario as u where u.usuario= :usr and u.pwd= :pwd");
            q.setParameter("usr", usr);
            q.setParameter("pwd", pwd);
            return (Usuario) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
}
