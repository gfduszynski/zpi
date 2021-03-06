/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.Role;
import org.chessclan.dataTier.models.User;
import org.chessclan.dataTier.repositories.RoleRepository;
import org.chessclan.dataTier.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Grzegorz Duszyski <gfduszynski@gmail.com>
 */
@Service("UserManagementBO")
public class UserManagementBOImpl implements UserManagementBO {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private ShaPasswordEncoder passwordEncoder;

    @Override
    public UsernamePasswordAuthenticationToken getLoggedUserAuthentication() {
        return (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getLoggedUser() {
        UsernamePasswordAuthenticationToken loggedUserAuthentication = getLoggedUserAuthentication();
        User u = userRepo.findByEmail(((org.springframework.security.core.userdetails.User) loggedUserAuthentication.getPrincipal()).getUsername());
        return u;
    }

    @Override
    public User registerUser(String login, String email, boolean enabled, String password, String firstName, String lastName, Date birthDate, int sex) {
        User u = new User(null, login, email, enabled, password, firstName, lastName, birthDate, Calendar.getInstance().getTime(), sex);
        return userRepo.save(encodePassword(u));
    }

    @Override
    public User encodePassword(User u) {
        u.setPassword(passwordEncoder.encodePassword(u.getPassword(), null));
        return u;
    }

    @Override
    @Transactional
    public User assignRole(int userId, Role.Type role) {
        User u = userRepo.findOne(userId);
        Role r = roleRepo.findByRoleName(role.name());
        u.getRoleSet().add(r);
        r.getUserSet().add(u);
        roleRepo.saveAndFlush(r);
        return u;
    }

    @Override
    public User resetPassword(User user, String password) {
        user.setPassword(password);
        encodePassword(user);
        saveUser(user);
        return user;
    }

    @Override
    public User saveUser(User u) {
        return userRepo.save(u);
    }

    @Override
    public Iterable<User> saveUsers(Iterable<User> u) {
        return userRepo.save(u);
    }

    @Transactional
    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    @Transactional
    public List<User> findClubUsers(Club c) {
        return userRepo.findByUserClub(c);
    }

    @Override
    public User findUserById(int id) {
        return userRepo.findOne(id);
    }

    @Override
    public Iterable<User> findUsersById(Iterable<Integer> ids) {
        return userRepo.findAll(ids);
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return findUserByEmail(email) != null;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Page<User> findAll(Pageable p) {
        return userRepo.findAll(p);
    }

    @Override
    public Iterable<User> findAll(Sort s) {
        return userRepo.findAll(s);
    }

    @Override
    public void deleteUser(int id) {
        userRepo.delete(id);
    }

    @Override
    public void deleteUser(User u) {
        userRepo.delete(u);
    }

    @Override
    public void deleteUsers(Iterable<User> users) {
        userRepo.delete(users);
    }

    public User findUserByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public List<User> findByFirstnameAndLastname(String fn, String ln) {
        return fetchClubs(userRepo.findByFirstNameAndLastName(fn, ln));
    }
    
    @Override
    @Transactional
    public List<User> findByFirstNameContainingOrLastNameContaining(String fn, String ln){
        return userRepo.findByFirstNameContainingOrLastNameContaining(fn, ln);
    }
    
    @Override
    public List<User> findByFirstname(String fn) {
        userRepo.findByFirstName(fn);
        return fetchClubs(userRepo.findByFirstName(fn));
    }

    @Override
    public List<User> findByLastname(String ln) {
        return fetchClubs(userRepo.findByLastName(ln));
    }

    @Transactional
    private List<User> fetchClubs(List<User> users) {
        for (User u : users) {
            if (u.getUserClub() != null) {
                u.getUserClub().getName().toString();
            }
        }
        return users;
    }
    
    @Override
    public User findUserWithClub(User user) {
        User u = userRepo.findOne(user.getId());
        if (u.getUserClub() != null) {
            u.getUserClub().getName().toString();
        }
        return u;
    }
}
