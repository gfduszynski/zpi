/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.Role;
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
    private Boolean enabled;
    private Boolean checkAll;
    private Boolean hasChecked;
    private Matcher matcher;
    private Map<Integer,List<Boolean>> validation;
    private List<Boolean> nuvalidation;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
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
        pattern = Pattern.compile(EMAIL_PATTERN);
        this.users = new ArrayList<User>();
        this.checked = new HashMap<Integer, Boolean>();
        this.editable = new HashMap<Integer, Boolean>();
        this.validation = new HashMap<Integer, List<Boolean>>();
        Iterator<User> usr = umBO.findAll().iterator();
        while(usr.hasNext()){
            User tmp = usr.next();
            users.add(tmp);
            checked.put(tmp.getId(), false);
            editable.put(tmp.getId(), false);
            validation.put(tmp.getId(), Arrays.asList(true, true, true, true, true, true, true, true, false));
        }
        this.checkAll = false;
        this.deletable = false;
        this.hasChecked = false;
    }

    public void updateUser(User user,List<Boolean> l) {
        if(validateHasNotErrors(user, l, true)){
        umBO.saveUser(user);
        umBO.encodePassword(user);
        editable.put(user.getId(), false);
        checked.put(user.getId(), false);
        users.set(users.indexOf(user), user);}
    }
    
    public void saveNewUser(List<Boolean> l) {
        if(validateHasNotErrors(newuser,l, false)){
        newuser.setPassword(umBO.encodePassword(newuser).getEmail());
        umBO.saveUser(newuser);
        umBO.assignRole(newuser.getId(), Role.Type.ADMIN);
        editable.put(newuser.getId(), false);
        checked.put(newuser.getId(), false);
        createNewUser = false;
        users.add(newuser);
        }
    }

    public void addNewUser() {
        newuser = new User("", "", "", "", "", new Date(), new Date());
        createNewUser = true;
        nuvalidation = Arrays.asList(true, true, true, true, true, true, true, true, true);
       }
    
    public boolean validateFirstName(User u, List<Boolean> l)
    {
        if (u.getFirstName() != null) {
            if (u.getFirstName().length() > 2) {
                l.set(0, true);
                return true;
            } else {
                l.set(0, false);
                return false;
            }
        } else {
            l.set(0, false);
            return false;
        }
    }
    
    public boolean validateLastName(User u, List<Boolean> l)
    {
        if (u.getLastName() != null) {
            if (u.getLastName().length() > 2) {
                l.set(1, true);
                return true;
            } else {
                l.set(1, false);
                return false;
            }
        } else {
            l.set(1, false);
            return false;
        }
    }
    
    
    public boolean validateEmail    (User u, List<Boolean> l, Boolean registered)
    {   
        matcher = pattern.matcher(u.getEmail());
        if(registered)
        {
            if(u.getEmail().equals(umBO.findUserById(u.getId()).getEmail()))
            {
                l.set(2, true);
                l.set(3, true);
                l.set(4, true);
                return true;
            }
        }
        if (umBO.isEmailRegistered(u.getEmail())) {
            
            l.set(2, false);
            l.set(3, true);
            l.set(4, false);
            return false;
        } else {
            if (matcher.matches()) {
                l.set(2, true);
                l.set(3, true);
                l.set(4, true);
                return true;
            } else {
                l.set(2, true);
                l.set(3, false);
                l.set(4, true);
                return false;
            }
        }
    }
    
    public boolean validateLogin(User u, List<Boolean> l, Boolean registered) {
        if(registered)
        {
            if(u.getLogin().equals(umBO.findUserById(u.getId()).getLogin()))
            {
                l.set(5, true);
                return true;
            }
        }
        if (u.getLogin() != null) {
            if (u.getLogin().length() > 4 && umBO.findUserByLogin(u.getLogin())==null) {
                l.set(5,true);
                return true;
            } else {
                l.set(5, false);
                return false;
            }
        } else {
                l.set(5, false);
            return false;
        }
    }
    
    public boolean validatePassword(User u, List<Boolean> l) {
        if (u.getPassword() != null) {
            if (u.getPassword().length() > 4) {
                l.set(6,true);
                return true;
            } else {
                l.set(6, false);
                return false;
            }
        } else {
                l.set(6, false);
            return false;
        }
    }
    
    
    public boolean validateBirthDate(User u , List<Boolean> l) {
        if (u.getBirthDate() == null) {
            l.set(7, false);
            return false;
        } else {
            l.set(7, true);
            return true;
        }
    }
    
    public boolean validateHasNotErrors(User u, List<Boolean> l, Boolean r)
    {   
        if(validateFirstName(u,l)&&validateLastName(u,l)&&validateEmail(u,l,r)&&validateLogin(u,l,r)&&validatePassword(u,l)&&validateBirthDate(u,l))
        {
            l.set(8, true);
            return true;
        }
        l.set(8, false);
        return false;
    }
    
    public void cancelNewUser() {
        createNewUser = false;
        newuser = null;
    }
    
    public void selectAll(){
        checkAll = !checkAll;
        for(int i=0;i<users.size();i++){
                checked.put(users.get(i).getId(), checkAll);
        }
        if(!checkAll){
            for(int i=0;i<users.size();i++){
                editable.put(users.get(i).getId(), false);
            }
        }
        deletable = checkAll;
    }
    
    public void disableSelected(){
        for(int i=0;i<users.size();i++){
                if(checked.get(users.get(i).getId())&& users.get(i).getEnabled()){
                    users.get(i).setEnabled(false);
                    umBO.saveUser(users.get(i));
                    users.set(users.indexOf(users.get(i)), users.get(i));
                }
        }
    }

        
    public void enableSelected(){
        for(int i=0;i<users.size();i++){
                if(checked.get(users.get(i).getId())&& !users.get(i).getEnabled()){
                    users.get(i).setEnabled(true);
                    umBO.saveUser(users.get(i));
                    users.set(users.indexOf(users.get(i)), users.get(i));
                }
        }
    }
    
    public void disableOne(User user){
        for(int i=0;i<users.size();i++){
                if(users.get(i).getId()==user.getId()){
                    users.get(i).setEnabled(false);
                    user.setEnabled(false);
                    umBO.saveUser(user);
                    break;
                }
        }
    }

        
    public void enableOne(User user){
        for(int i=0;i<users.size();i++){
                if(users.get(i).getId()==user.getId()){
                    users.get(i).setEnabled(true);
                    user.setEnabled(true);
                    umBO.saveUser(user);
                    break;
                }
        }
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
           users.set(id-1, umBO.findUserById(id));
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
    
    public void saveSelected()
    {
        for(int i=0;i<users.size();i++){
            if(validateHasNotErrors(users.get(i),validation.get(users.get(i).getId()),true)){
            if(checked.get(users.get(i).getId())) {
                editable.put(users.get(i).getId(),false);
                checked.put(users.get(i).getId(), false);
                umBO.saveUser(users.get(i));
                umBO.encodePassword(users.get(i));
                }
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
    
    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public Boolean getEnabled()
    {
        return this.enabled;
    }
    
    public void changeCheckedOne(int id){
           if(!checked.get(id)){checked.put(id, false);
           }else{checked.put(id, true);}
    }
    
    public void setCheckAll(Boolean checkAll)
    {
        this.checkAll = checkAll;
    }
    
    public Boolean getCheckAll()
    {
        return this.checkAll;
    }
    
    public void setHasChecked(Boolean hasChecked)
    {
        this.hasChecked = hasChecked;
    }
    
    public Boolean getHasChecked()
    {
        for(int i=0;i<users.size();i++){
            if(checked.get(users.get(i).getId())) { return this.hasChecked = true;
                }
        }
        return this.hasChecked=false;
    }
    
    public List<Boolean> getNuvalidation()
    {
        return this.nuvalidation;
    }
    
    public void setNuvalidation(List<Boolean> nuvalidation)
    {
        this.nuvalidation = nuvalidation;
    }
    public Map<Integer,List<Boolean>> getValidation()
    {
        return this.validation;
    }
    
    public void setValidation(Map<Integer, List<Boolean>> validation)
    {
        this.validation = validation;
    }
    
    
    public List<Boolean> getUserValidation(Integer id)
    {
        return this.validation.get(id);
    }
    
    public void setUserValidation(Integer id, List<Boolean> l)
    {
        this.validation.put(id,l);
    }
}