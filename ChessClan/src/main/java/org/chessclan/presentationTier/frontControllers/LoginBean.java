package org.chessclan.presentationTier.frontControllers;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author Grzegorz Duszyski <gfduszynski@gmail.com>
 */
@RequestScoped
@ManagedBean(name = "loginBean")
public class LoginBean {

    private String username = "";
    private String password = "";
    private boolean loggedIn = false;

    /**
     * @return @throws IOException
     * @throws ServletException
     */
    public String doLogin() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher =
                ( (ServletRequest) context.getRequest() ).getRequestDispatcher("/j_spring_security_check");

        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();

        // Faces are going to exit, sot it'sok to return null
        return null;
    }

    /**
     * @return
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @param username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    /**
     * @param loggedIn
     */
    public void setLoggedIn(final boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}