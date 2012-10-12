/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.chessclan.dataTier.models.Role;
import org.chessclan.dataTier.models.User;
import org.chessclan.dataTier.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Grzegorz Duszyski <gfduszynski@gmail.com>
 */
@Service("UserBO")
public class UserBO implements Serializable{    
    
    @Autowired
    private UserRepository userRepo;
    
    public Authentication getLoggedUserAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    public User registerUser(String login, String email, boolean enabled, String password, String firstName, String lastName, Date birthDate, int sex){
        User u = new User(null, login, email, enabled, password, firstName, lastName, birthDate, Calendar.getInstance().getTime(), sex);
        return userRepo.save(u);
    }
    
    public User assignRole(User u, Role r){
        if(u.getRolesCollection() == null){
            u.setRolesCollection(new ArrayList<Role>());
        }
        u.getRolesCollection().add(r);
        return userRepo.save(u);
    }
    
    public User saveUser(User u){
        return userRepo.save(u);
    }
    
    public Iterable<User> saveUsers(Iterable<User> u){
        return userRepo.save(u);
    }
    
    public User findUserByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public User findUserById(int id){
        return userRepo.findOne(id);
    }
    
    public Iterable<User> findUsersById(Iterable<Integer> ids){
        return userRepo.findAll(ids);
    }
    
    public boolean isEmailRegistered(String email){
        return findUserByEmail(email)!=null;
    }
    
    public Iterable<User> findAll(){
        return userRepo.findAll();
    }
    
    public Page<User> findAll(Pageable p){
        return userRepo.findAll(p);
    }
    
    public Iterable<User> findAll(Sort s){
        return userRepo.findAll(s);
    }
    
    public void deleteUser(int id){
        userRepo.delete(id);
    }
    
    public void deleteUser(User u){
        userRepo.delete(u);
    }
    
    public void deleteUsers(Iterable<User> users){
        userRepo.delete(users);
    }
}
