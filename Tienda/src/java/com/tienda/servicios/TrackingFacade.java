/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.servicios;

import com.tienda.modelo.Producto;
import com.tienda.modelo.Tracking;
import com.tienda.pojo.Venta;
import com.tienda.utilidades.TransaccionEnum;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

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
    
    public int getSecuencialProductoStock(Producto p) {
        try {
            Query qs = em.createQuery("select MAX(t.id) FROM Tracking as t where t.producto= :p");
            qs.setParameter("p", p);
            return (Integer) qs.getSingleResult();
        } catch (NullPointerException e) {
            return 0;
        }
    }
    
    public int getUltimoSaldoPrducto(int secuencial) {
        Query q = em.createQuery("select t from Tracking as t where t.id= :id");
        q.setParameter("id", secuencial);
        return ((Tracking) q.getSingleResult()).getSaldo();
    }
    
    public List<Venta> getVentasFecha(Date fechaI, Date fechaF, TransaccionEnum tipo) {
        List<Venta> ventas = new LinkedList<>();
        try {
            Query q = em.createQuery("select sum(t.valor),t.producto.codigo,t.producto.descripcion,t.usuario from Tracking as t where t.tipo= :t and t.fecha BETWEEN :fechaI and :fechaF GROUP BY t.producto.id,t.usuario")
                    .setParameter("t", tipo)
                    .setParameter("fechaI", fechaI, TemporalType.DATE)
                    .setParameter("fechaF", fechaF, TemporalType.DATE);
            List<Object[]> respuesta = q.getResultList();
            for (int i = 0; i < respuesta.size(); i++) {
                Object[] arr = respuesta.get(i);
                Venta v = new Venta();
                for (int j = 0; j < arr.length; j++) {
                    switch (j) {
                        case 0:
                            v.setVentas(Integer.parseInt(arr[j].toString()));
                            break;
                        case 1:
                            v.setCodigo(arr[j].toString());
                            break;
                        case 2:
                            v.setDescripcion(arr[j].toString());
                            break;
                        case 3:
                            v.setUsuario(arr[j].toString());
                            break;
                        
                    }
                }
                ventas.add(v);
            }
            return ventas;
        } catch (Exception e) {
            
            return null;
        }
        
    }
}
