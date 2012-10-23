/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.ProjectStage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Daniel
 */
public class DefaultExceptionHandler extends ExceptionHandlerWrapper {

    private static final Log LOG = LogFactory.getLog(DefaultExceptionHandler.class);
    /**
     * key for session scoped message detail
     */
    public static final String MESSAGE_DETAIL_KEY = "ip.client.jsftoolkit.messageDetail";
    private ExceptionHandler wrapped;

    public DefaultExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {

        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.isProjectStage(ProjectStage.Development)) {
            // Code for development mode. E.g. let the parent handle exceptions
            getWrapped().handle();
        } else {
            for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
                ExceptionQueuedEvent event = i.next();
                ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

                String redirectPage = "/";

                Throwable t = context.getException();

                try {
                    if (t instanceof AbortProcessingException) {
                        // about AbortProcessingException see JSF 2 spec.
                        LOG.error("An unexpected exception has occurred by event listener(s)", t);

//                        fc.getExternalContext().getSessionMap()
//                                .put(DefaultExceptionHandler.MESSAGE_DETAIL_KEY, t.getLocalizedMessage());
                    } else if (t instanceof ViewExpiredException) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("View '" + ((ViewExpiredException) t).getViewId() + "' is expired", t);
                        }

                        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
                        if (session != null) {
                            // should not happen
                            session.invalidate();
                        }
                    }
                } finally {
                    i.remove();
                }
                try {
                    fc.getExternalContext().redirect(redirectPage);
                } catch (IOException ex) {
                    Logger.getLogger(DefaultExceptionHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }
    }

    protected String handleUnexpected(FacesContext facesContext, final Throwable t) {
        LOG.error("An unexpected internal error has occurred", t);

        return "jsftoolkit.exception.UncheckedException";
    }
}
