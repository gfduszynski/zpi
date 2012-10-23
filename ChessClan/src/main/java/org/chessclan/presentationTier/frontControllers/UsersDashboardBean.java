/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "udBean")
@ViewScoped
public class UsersDashboardBean implements Serializable {

    private List<User> users;
    private Map<Integer, Boolean> checked;
    private Map<Integer, Boolean> editable;
    private User newuser;
    private Boolean deletable; 
    private Boolean createNewUser;
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

    }
    
    @PostConstruct
    public void initialize() {
        this.users = new ArrayList<User>();
        this.checked = new HashMap<Integer, Boolean>();
        this.editable = new HashMap<Integer, Boolean>();
        Iterator<User> usr = umBO.findAll().iterator();
        while(usr.hasNext()){
            User tmp = usr.next();
            users.add(tmp);
            checked.put(tmp.getId(), false);
            editable.put(tmp.getId(), false);
        }
        this.deletable = false;
    }


    public void removeUser(User user) {
        umBO.deleteUser(user);
        editable.remove(user.getId());
        checked.remove(user.getId());
        users.remove(user);
    }

    public void updateUser(User user) {
        umBO.saveUser(user);
        editable.put(user.getId(), false);
        checked.put(user.getId(), false);
        users.set(users.indexOf(user), user);
    }
    
    public void saveNewUser() {
        umBO.saveUser(newuser);
        users.add(newuser);
        editable.put(newuser.getId(), false);
        checked.put(newuser.getId(), false);
        createNewUser = false;
        users.add(newuser);
    }

    public void addNewUser() {
        createNewUser = true;
        newuser = new User("New User", users.size()+"@chessclan.pl", "pass", "Name", "Last Name", new Date(), new Date());
    }
    
    public void cancelNewUser() {
        createNewUser = false;
        newuser = null;
    }
    
    public void selectAll(){
        for(int i=0;i<users.size();i++){
            if(!checked.get(users.get(i).getId()) || !checked.containsKey(users.get(i).getId())) {
                checked.put(users.get(i).getId(), true);
            }
        }
        deletable = true;
    }

    public Map<Integer, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Integer, Boolean> checked) {
        this.checked = checked;
    }
    
    public void selectOneEditable(int id){
           editable.put(id, true);
    }
    
    public void deselectOneEditable(int id){
           editable.put(id, false);
    }
    
    public Map<Integer, Boolean> getEditable() {
        return editable;
    }

    public void setEditable(Map<Integer, Boolean> editable) {
        this.editable = editable;
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
        
    public void setEditableForSelected()
    {        
        for(int i=0;i<users.size();i++){
            if(checked.get(users.get(i).getId())) {
                editable.put(users.get(i).getId(), true);
                deletable = true;
            }
        }
    }
    
    public void removeSelected()
    {
        if(deletable){
            for(int i=0;i<users.size();i++){
                if(checked.get(users.get(i).getId())) {
                    editable.remove(users.get(i).getId());
                    checked.remove(users.get(i).getId());
                    umBO.deleteUser(users.get(i));
                    users.remove(i);
                    --i;
                }
            }
            deletable = false;
        }
    }
        
    public void saveSelected()
    {
        for(int i=0;i<users.size();i++){
            if(checked.get(users.get(i).getId())) {
                editable.put(users.get(i).getId(),false);
                checked.put(users.get(i).getId(), false);
                umBO.saveUser(users.get(i));
            }
        }
        deletable = false;
    }
    
    public Boolean getDeletable()
    {
        return deletable;
    }
    
    public void setDeletable(Boolean deletable)
    {
        this.deletable = deletable;
    }
    
    public Boolean getCreateNewUser()
    {
        return createNewUser;
    }
    
    public void setCreateNewUser(Boolean cnu)
    {
        this.createNewUser = cnu;
    }
        public User getNewuser()
    {
        return newuser;
    }
    
    public void setNewuser(User u)
    {
        this.newuser = u;
    }

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }    
}
