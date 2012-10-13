/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.User;
import org.springframework.security.core.Authentication;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "udBean")
@SessionScoped
public class UsersDashboardBean implements Serializable {

    private List<User> users;
    private Map<Integer, Boolean> checked;
    //form vars
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private Date creationDate;
    private Integer sex;
    private String password;
    private Integer userClubId;
    //end
    @Transient
    @ManagedProperty("#{UserManagementBO}")
    private UserManagementBO umBO;

    public UsersDashboardBean() {
        users = new ArrayList<User>();
        checked = new HashMap<Integer, Boolean>();
        users.add(new User(1, "login", "d@d.pl", true, "pass","n","ln", new Date(), new Date(),1));
        //users.add(new User(2, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), 1, "passwd", null));
        //users.add(new User(3, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), 1, "passwd", null));
        checked.put(1,false);
        //checked.put(2,false);
        //checked.put(3,false);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void editUser(User user) {
        //user.setEditable(true);
    }

    public void updateUser(User user) {
        //user.setEditable(false);
    }

    public void addNewUser(User user) {
        //users.add(new User(100, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), 1, "passwd", null));
        //user.setEditable(true);
    }
    
    public void selectAll(){
        Authentication b  = umBO.getLoggedUserAuthentication();
        for(int i=0;i<users.size();i++){
            if(!checked.get(users.get(i).getUserId()) || !checked.containsKey(users.get(i).getUserId())) {
                checked.put(users.get(i).getUserId(), true);
            }
        }
    }

    public Map<Integer, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Integer, Boolean> checked) {
        this.checked = checked;
    }
    

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserClubId() {
        return userClubId;
    }

    public void setUserClubId(Integer userClubId) {
        this.userClubId = userClubId;
    }

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }    
}
