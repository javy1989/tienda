/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.conveter;

import com.tienda.bean.ProductoBean;
import com.tienda.excepcions.ConsultarException;
import com.tienda.modelo.Tipo;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author desarrollo
 */
@FacesConverter(forClass = Tipo.class)
public class TipoC implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Tipo tipo = null;
        try {
            ProductoBean bean = (ProductoBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "producto");
            int id = Integer.parseInt(value);
            tipo = bean.getTipo(id);
        } catch (ConsultarException | NumberFormatException e) {
        }
        return tipo;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Tipo tipo = (Tipo) value;
        if (tipo.getId() == null) {
            return "0";
        }
        return ((Tipo) value).getId().toString();
    }

}
