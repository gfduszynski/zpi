package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.chessclan.businessTier.businessObjects.UserBO;
import org.chessclan.businessTier.services.AuthenticationService;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {

    private String login;
    private String password;
    @ManagedProperty("#{authenticationService}")
    private AuthenticationService authenticationService; // injected Spring defined service for bikes

    public String login() {
        boolean success = authenticationService.login(login, password);

        if (success) {
            boolean isAdmin = true;
            if (isAdmin) {
                return "/administration/dashboard.xhtml";
            } else {
                return "/default.xhtml"; // return to application but being logged now  
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login or password incorrect."));
            return "/authorization/login.xhtml";
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthenticationService getAuthenticationService() {
        return this.authenticationService;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

}
