package org.chessclan.presentationTier.frontControllers;

import java.io.IOException;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Grzegorz Duszyski <gfduszynski@gmail.com>
 */
@SessionScoped
@ManagedBean(name = "loginBean")
public class LoginBean {

    private String username = "";
    private String password = "";
    private boolean loggedIn = false;
    @ManagedProperty("#{UserManagementBO}")
    UserManagementBO umBO;
    private boolean loginError;
    @ManagedProperty("#{sessionBean}")
    SessionBean sessionBean;

    /**
     * @return @throws IOException
     * @throws ServletException
     */
    public String doLogin() throws IOException, ServletException {
        this.loginError = false;

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher =
                ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");

        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();

        Map sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        if (((BadCredentialsException) sessionMap.get("SPRING_SECURITY_LAST_EXCEPTION")) != null) {
            this.loginError = true;
        } else {
            Authentication auth = umBO.getLoggedUserAuthentication();
            if (auth != null) {
                sessionBean.setUser(umBO.findUserByEmail(((User)auth.getPrincipal()).getUsername()));
                sessionBean.setLoggedUser(true);
            } else {
                this.loginError = true;
            }
        }

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

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }

    public boolean isLoginError() {
        return loginError;
    }

    public void setLoginError(boolean loginError) {
        this.loginError = loginError;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
    
}