/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.bean;

import com.tienda.excepcions.ConsultarException;
import com.tienda.excepcions.GrabarException;
import com.tienda.excepcions.InsertarException;
import com.tienda.modelo.Log;
import com.tienda.modelo.Producto;
import com.tienda.modelo.Tipo;
import com.tienda.modelo.Tracking;
import com.tienda.servicios.LogFacade;
import com.tienda.servicios.ProductoFacade;
import com.tienda.servicios.TipoFacade;
import com.tienda.servicios.TrackingFacade;
import com.tienda.utilidades.Combos;
import com.tienda.utilidades.Formulario;
import com.tienda.utilidades.Mensajes;
import com.tienda.utilidades.TransaccionEnum;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author ricardo
 */
@ManagedBean(name = "producto")
@ViewScoped
public class ProductoBean {

    @ManagedProperty(value = "#{datosLogueo}")
    private DatosLogueoBean seguridad;
    @EJB
    private ProductoFacade ejbProducto;
    @EJB
    private TrackingFacade ejbTracking;
    @EJB
    private TipoFacade ejbTipo;
    @EJB
    private LogFacade ejbLog;

    private Formulario frmProducto = new Formulario();
    private Formulario frmProductoV = new Formulario();
    private Producto producto;
    private List<Producto> productos;

    private int valor;
    private boolean salida;
    private boolean ingreso;
    private boolean baja;
    private Tipo tipo;

    public ProductoBean() {
    }

    @PostConstruct
    private void init() {
        initProductos();
    }

    public void initProductos() {
        try {
            productos = ejbProducto.getProductosEstado();
        } catch (ConsultarException ex) {
            Logger.getLogger(ProductoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SelectItem[] getTipoItem() throws ConsultarException {
        Map p = new HashMap();
        p.put(";where", "o.padre is null");
        p.put(";orden", "o.nombre");
        return Combos.getSelectItems(ejbTipo.encontarParametros(p), true);
    }

    public SelectItem[] getTipoHijoItem() throws ConsultarException {
        if (producto.getTipo().getPadre() == null) {
            return null;
        }
        Map p = new HashMap();
        p.put(";where", "o.padre= :padre");
        p.put("padre", producto.getTipo().getPadre());
        return Combos.getSelectItems(ejbTipo.encontarParametros(p), true);
    }

    public Tipo getTipo(int id) throws ConsultarException {
        return ejbTipo.find(id);
    }

    public void nuevoProducto() {
        producto = new Producto();
        producto.setTipo(new Tipo());
        cambiaFormulario(0);
        ingreso = false;
        salida = false;
    }

    public String ingresaProducto(Producto p) {
        this.producto = p;
        if (producto == null) {
            return null;
        }
        cambiaFormulario(2);
        ingreso = true;
        return null;
    }

    public String salidaProducto(Producto p) {
        this.producto = p;
        if (producto == null) {
            return null;
        }
        cambiaFormulario(2);
        salida = true;
        return null;
    }

    public String editarProducto(Producto p) {
        this.producto = p;
        if (producto == null) {
            return null;
        }
        cambiaFormulario(2);
        return null;
    }

    public String bajaProducto(Producto p) {
        this.producto = p;
        if (producto == null) {
            return null;
        }
        cambiaFormulario(2);
        baja = true;
        return null;
    }

    public boolean codigoRepetido(String codigo) {
        Producto p = ejbProducto.getProductoCodigo(codigo);
        return p == null;
    }

    public String guardar() {

        if (producto.getTipo() == null) {
            Mensajes.error("Seleccione Tipo");
            return null;
        }
        if (producto.getDescripcion().isEmpty()) {
            Mensajes.error("Ingrese descripcion");
            return null;
        }
        if (producto.getCodigo().isEmpty()) {
            Mensajes.error("Ingrese codigo");
            return null;
        }

        if (frmProductoV.isNuevo()) {
            if (valor == 0) {
                Mensajes.error("Ingrese un valor mayor a cero");
                return null;
            }
        }

        producto.setEstado(Boolean.TRUE);
        try {
            String usr = seguridad.getUsuario().getUsuario();
            if (producto.getId() == null) {
                if (codigoRepetido(producto.getCodigo())) {
                    Mensajes.informacion("Codigo de producto ya existe");
                    return null;
                }
                ejbProducto.create(producto, usr);
                ingresarTracking(usr, ultimoTracking(producto));
            } else {
                ejbProducto.edit(producto, usr);
            }
            Mensajes.informacion("Transaccion exitosa");
            cambiaFormulario(1);
            initProductos();
            producto = null;
        } catch (GrabarException | InsertarException e) {
            Logger.getLogger(ProductoBean.class.getName()).log(Level.SEVERE, null, e);
            Mensajes.error("Error en la transaccion");
        }
        return null;
    }

    public String guardarTransaccion() {
        if (valor == 0) {
            Mensajes.informacion("Ingrese valor");
            return null;
        }
        try {
            String usr = seguridad.getUsuario().getUsuario();
            int saldo = ultimoTracking(producto);
            if (ingreso) {
                ingresarTracking(usr, saldo);
                ingreso = false;
            } else if (salida) {
                saldo -= valor;
                if (saldo < 0) {
                    Mensajes.informacion("Saldo no disponible para transaccion");
                    return null;
                }
                salidaTracking(usr, saldo, TransaccionEnum.O);
                salida = false;
            } else if (baja) {
                saldo -= valor;
                if (saldo < 0) {
                    Mensajes.informacion("Saldo no disponible para transaccion");
                    return null;
                }
                salidaTracking(usr, saldo, TransaccionEnum.B);
                baja = false;
            }
            Mensajes.informacion("Transaccion exitosa");
            cambiaFormulario(1);
            producto = null;
            valor = 0;

        } catch (InsertarException e) {
            Logger.getLogger(ProductoBean.class.getName()).log(Level.SEVERE, null, e);
            Mensajes.fatal("Error en la transaccion");
        }
        return null;
    }

    public int ultimoTracking(Producto p) {
        int sec = ejbTracking.getSecuencialProductoStock(p);
        if (sec != 0) {
            return ejbTracking.getUltimoSaldoPrducto(sec);
        }
        return 0;
    }

    private void ingresarTracking(String usr, int saldo) throws InsertarException {
        saldo += valor;
        Tracking t = new Tracking();
        t.setFecha(Calendar.getInstance().getTime());
        t.setProducto(producto);
        t.setTipo(TransaccionEnum.I);
        t.setUsuario(usr);
        t.setValor(valor);
        t.setSaldo(saldo);
        ejbTracking.create(t, usr);
        generaLog(Tracking.class.getName(), t.toString(), usr);
    }

    private void salidaTracking(String usr, int saldo, TransaccionEnum tr) throws InsertarException {
        Tracking t = new Tracking();
        t.setFecha(Calendar.getInstance().getTime());
        t.setProducto(producto);
        t.setTipo(tr);
        t.setUsuario(usr);
        t.setValor(valor);
        t.setSaldo(saldo);
        ejbTracking.create(t, usr);
        generaLog(Tracking.class.getName(), t.toString(), usr);
    }

    /**
     * 0 insertar nuevo registro; 1 cancelar ventana frmProductoV; 2 editar
     * registro
     *
     * @param opcion
     */
    public void cambiaFormulario(int opcion) {
        switch (opcion) {
            case 0:
                frmProducto.insertar();
                frmProductoV.insertar();
                break;
            case 1:
                frmProducto.cancelar();
                frmProductoV.cancelar();
                ingreso = false;
                salida = false;
                producto = null;
                break;
            case 2:
                frmProducto.insertar();
                frmProductoV.editar();
                break;

        }
    }

    public void nuevoTipoPadre() {
        tipo = new Tipo();
    }

    public void guardarTipoPadre() {
        try {
            ejbTipo.create(tipo, seguridad.getUsuario().getUsuario());
            Mensajes.informacion("Transaccion Existosa");
            tipo = null;
        } catch (InsertarException ex) {
            Logger.getLogger(ProductoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String guardarTipoHijo() {
        try {
            ejbTipo.create(tipo, seguridad.getUsuario().getUsuario());
            Mensajes.informacion("Transaccion Existosa");
            getTipoHijoItem();
            tipo = null;
        } catch (InsertarException | ConsultarException ex) {
            Logger.getLogger(ProductoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void generaLog(String entidad, String cadena, String usr) throws InsertarException {
        Log l = new Log();
        l.setEntidad(entidad);
        l.setFecha(Calendar.getInstance().getTime());
        l.setUsuario(usr);
        l.setValor(cadena);
        ejbLog.create(l, usr);
    }

    public Formulario getFrmProducto() {
        return frmProducto;
    }

    public void setFrmProducto(Formulario frmProducto) {
        this.frmProducto = frmProducto;
    }

    public Formulario getFrmProductoV() {
        return frmProductoV;
    }

    public void setFrmProductoV(Formulario frmProductoV) {
        this.frmProductoV = frmProductoV;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isSalida() {
        return salida;
    }

    public void setSalida(boolean salida) {
        this.salida = salida;
    }

    public boolean isIngreso() {
        return ingreso;
    }

    public void setIngreso(boolean ingreso) {
        this.ingreso = ingreso;
    }

    public DatosLogueoBean getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(DatosLogueoBean seguridad) {
        this.seguridad = seguridad;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public boolean isBaja() {
        return baja;
    }

    public void setBaja(boolean baja) {
        this.baja = baja;
    }

}
