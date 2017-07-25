/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.bean;

import com.tienda.pojo.Venta;
import com.tienda.servicios.TrackingFacade;
import com.tienda.utilidades.Mensajes;
import com.tienda.utilidades.TransaccionEnum;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Ricardo.Bravo
 */
@ManagedBean(name = "venta")
@ViewScoped
public class VentaBean {

    @EJB
    private TrackingFacade ejbTracking;

    private List<Venta> ventas;
    private Date fechaInicio;
    private Date fechaFin;

    public VentaBean() {
    }

    public String reporteVentas() {
        Calendar cal = Calendar.getInstance();
        if (fechaInicio.compareTo(cal.getTime()) > 0) {
            Mensajes.informacion("Fecha inicio deve ser menor a la actual");
            return null;
        }
        if (fechaFin.compareTo(cal.getTime()) > 0) {
            Mensajes.informacion("Fecha inicio deve ser menor a la actual");
            return null;
        }

        ventas = ejbTracking.getVentasFecha(fechaInicio, fechaFin, TransaccionEnum.O);
        if (ventas.isEmpty()) {
            Mensajes.informacion("No existe reporte para mostrar con la fechas");
            return null;
        }
        return null;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

}
