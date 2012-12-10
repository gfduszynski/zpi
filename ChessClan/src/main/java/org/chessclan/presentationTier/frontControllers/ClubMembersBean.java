/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.chessclan.businessTier.businessObjects.ClubBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.Role;
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
    private String search;
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

    public boolean isMember(User user) {
        if (user.getUserClub() == null) {
            return false;
        }
        return this.user.getOwnedClub().getId() == user.getUserClub().getId();
    }

    public void findUsers() {
        if (search == null || search.isEmpty()) {
            this.notValidCriteria = true;
            return;
        } else {
            this.foundUsers = umBO.findByFirstNameContainingOrLastNameContaining(search, search);
            Iterator<User> it = foundUsers.iterator();
            while (it.hasNext()) {
                User u = it.next();
                boolean isUser = false;
                for (Role r : u.getRoleSet()) {
                    if ("USER".equals(r.getRoleName())) {
                        isUser = true;
                    }
                }
                if (!isUser) {
                    foundUsers.remove(u);
                }
            }
        }
        if (foundUsers.size() > 0) {
            this.notValidCriteria = false;
        } else {
            this.notValidCriteria = true;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
