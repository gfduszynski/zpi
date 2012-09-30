/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "udBean")
@ViewScoped
public class UsersDashboardBean implements Serializable {

    private Logger logger;
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

    public UsersDashboardBean() {
        this.logger = Logger.getLogger(UsersDashboardBean.class);
        users = new ArrayList<User>();
        checked = new HashMap<Integer, Boolean>();
        users.add(new User(1, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), 1, "passwd", null));
        users.add(new User(2, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), 1, "passwd", null));
        users.add(new User(3, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), 1, "passwd", null));
        checked.put(1,false);
        checked.put(2,false);
        checked.put(3,false);
    }

    public void removeUser(User user) {
        logger.info("Removing " + user.toString());

        users.remove(user);
    }

    public void editUser(User user) {
        logger.info("Editing...");

        user.setEditable(true);
    }

    public void updateUser(User user) {
        logger.info("Updating...");

        user.setEditable(false);
    }

    public void addNewUser(User user) {
        logger.info("Adding...");
        users.add(new User(100, "Daniello", "Engello", "daniel.engel@poczta.pl", new Date(), new Date(), 1, "passwd", null));

    }
    
    public void selectAll(){
        for(int i=0;i<users.size();i++){
            if(!checked.get(users.get(i).getUserId())) {
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
}
