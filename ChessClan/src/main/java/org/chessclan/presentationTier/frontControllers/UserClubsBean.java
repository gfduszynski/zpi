/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.util.List;
import javax.annotation.PostConstruct;
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

    @ManagedProperty("#{ClubBO}")
    ClubBO clubBO;
    
    @ManagedProperty("#{UserManagementBO}")
    UserManagementBO userBO;

    private List<Club> clubs;

    private Club userClub;

    @ManagedProperty(value = "#{loginBean.user}")
    private User user;
    
    public UserClubsBean() {
    }

    @PostConstruct
    public void initialize() {
        this.clubs = clubBO.findAllWithMembers();
        this.user = this.userBO.findUserByEmailWithClub(user.getEmail());
        this.userClub = user.getUserClub();
    }

    public void signOutFromClub() {
        clubBO.leaveClub();
        this.userClub = null;
    }

    public void signToClub(Club club) {
        clubBO.joinClub(club);
        this.userClub = club;
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

    public Club getUserClub() {
        return userClub;
    }

    public void setUserClub(Club userClub) {
        this.userClub = userClub;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserManagementBO getUserBO() {
        return userBO;
    }

    public void setUserBO(UserManagementBO userBO) {
        this.userBO = userBO;
    }
}
