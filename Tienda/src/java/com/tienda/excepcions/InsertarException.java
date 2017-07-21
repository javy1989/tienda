/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.excepcions;

import javax.ejb.ApplicationException;

/**
 *
 * @author luisfernando
 */
@ApplicationException(rollback = true)
public class InsertarException extends Exception{
    public InsertarException(String message, Throwable cause) {
        super("Error al ejecutar la inserción del Registro  :\n" + message, cause);
    }

    public InsertarException(String message) {
        super("Error al ejecutar la inserción del Registro  :\n" + message);
    }
    
    
}
