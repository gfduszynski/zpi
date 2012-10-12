package org.chessclan.presentationTier.frontControllers;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletException;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {

    private String login;
    private String password;
    private User user;

    public String doLogin() throws IOException, ServletException {
       /* ExternalContext context =
                FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher =
                ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");

        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        // It's OK to return null here because Faces is just going to exit.*/
        return null;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
