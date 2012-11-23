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
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "cmmBean")
@ViewScoped
public class ClubMembersBean {

    @ManagedProperty("#{ClubBO}")
    private ClubBO clubBO;
    @ManagedProperty("#{UserManagementBO}")
    private UserManagementBO umBO;
    @ManagedProperty(value = "#{loginBean.user}")
    private User user;
    private List<User> members;
    private String searchFN;
    private String searchLN;
    private List<User> foundUsers;
    private boolean notValidCriteria;

    public ClubMembersBean() {
        notValidCriteria = false;
    }

    @PostConstruct
    public void initialize() {
        this.members = clubBO.findClubMembers(user.getOwnedClub());
    }

    public void removeMember(User u) {
        u.setUserClub(null);
        members.remove(u);
        umBO.saveUser(u);
    }

    public void addMember(User u) {
        u.setUserClub(this.user.getOwnedClub());
        members.add(u);
        umBO.saveUser(u);
    }

    public void findUsers() {
        if (searchFN == null || searchFN.isEmpty()) {
            if (searchLN == null || searchLN.isEmpty()) {
                this.notValidCriteria = true;
            } else {
                this.foundUsers = umBO.findByLastname(searchLN);
            }
        } else {
            if (searchLN == null || searchLN.isEmpty()) {
                this.foundUsers = umBO.findByFirstname(searchFN);
            } else {
                this.foundUsers = umBO.findByFirstnameAndLastname(searchFN, searchLN);
            }
        }
    }

    public ClubBO getClubBO() {
        return clubBO;
    }

    public void setClubBO(ClubBO clubBO) {
        this.clubBO = clubBO;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
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

    public String getSearchFN() {
        return searchFN;
    }

    public void setSearchFN(String searchFN) {
        this.searchFN = searchFN;
    }

    public String getSearchLN() {
        return searchLN;
    }

    public void setSearchLN(String searchLN) {
        this.searchLN = searchLN;
    }

    public List<User> getFoundUsers() {
        return foundUsers;
    }

    public void setFoundUsers(List<User> foundUsers) {
        this.foundUsers = foundUsers;
    }

    public boolean isNotValidCriteria() {
        return notValidCriteria;
    }

    public void setNotValidCriteria(boolean notValidCriteria) {
        this.notValidCriteria = notValidCriteria;
    }
}
