/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.utils;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 *
 * @author Daniel
 */
public class DefaultExceptionHandlerFactory extends ExceptionHandlerFactory {
    private ExceptionHandlerFactory parent;

    public DefaultExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler eh = parent.getExceptionHandler();
        eh = new DefaultExceptionHandler(eh);

        return eh;
    }
}
