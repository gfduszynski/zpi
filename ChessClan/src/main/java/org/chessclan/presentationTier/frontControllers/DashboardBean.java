/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.chessclan.businessTier.services.AuthenticationService;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean
@ViewScoped
public class DashboardBean implements Serializable {

    @ManagedProperty(value = "#{authenticationService}")
    private AuthenticationService authenticationService; // injected Spring defined service for bikes
    private List<User> users;
    private List<Club> clubs;
    private User updatedUser;
    private Club updatedClub;

    public DashboardBean() {
        users = new ArrayList<User>();
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), "passwd"));
    }

    public String logout() {

        authenticationService.logout();

        return "/default.xhtml";
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Club getUpdatedClub() {
        return updatedClub;
    }

    public void setUpdatedClub(Club updatedClub) {
        this.updatedClub = updatedClub;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
