/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.util.ArrayList;
import java.util.Iterator;
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

    private List<Club> clubs;
    
    @ManagedProperty(value = "#{loginBean.userClub}")
    private Club userClub;

    public UserClubsBean() {
    }

    @PostConstruct
    public void initialize() {
        this.clubs = clubBO.findAllWithMembers();
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


}
