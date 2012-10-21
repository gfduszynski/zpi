/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.chessclan.businessTier.businessObjects.ClubBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "ucBean")
@ViewScoped
public class UserClubsBean {

    @ManagedProperty("#{UserManagementBO}")
    UserManagementBO umBO;
    @ManagedProperty("#{ClubBO}")
    ClubBO clubBO;
    @ManagedProperty(value = "#{loginBean.user}")
    private User user;
    private List<Club> clubs;

    public UserClubsBean() {
    }

    public void initialize() {
        this.clubs = new ArrayList<Club>();
        Iterator<Club> posts = clubBO.findAll().iterator();
        while (posts.hasNext()) {
            clubs.add(posts.next());
        }
    }

    public void signOutFromClub() {
        clubBO.leaveClub();
    }

    public void signToClub(Club club) {
        clubBO.joinClub(club);
    }

    public ClubBO getClubBO() {
        return clubBO;
    }

    public void setClubBO(ClubBO clubBO) {
        this.clubBO = clubBO;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }
}
