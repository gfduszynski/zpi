/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.util.Date;
import java.util.List;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.Role;
import org.chessclan.dataTier.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Grzegorz Duszyski <gfduszynski@gmail.com>
 */
public interface UserManagementBO{
    public UsernamePasswordAuthenticationToken getLoggedUserAuthentication();
    public User getLoggedUser();
    public User registerUser(String login, String email, boolean enabled, String password, String firstName, String lastName, Date birthDate, int sex);
    public User encodePassword(User u);
    @Transactional
    public User assignRole(int userId, Role.Type role);
    public User resetPassword(User user, String password);
    public User saveUser(User u);
    public Iterable<User> saveUsers(Iterable<User> u);
    public User findUserByEmail(String email);
    public User findUserById(int id);
    public Iterable<User> findUsersById(Iterable<Integer> ids);
    public boolean isEmailRegistered(String email);
    public Iterable<User> findAll();
    public Page<User> findAll(Pageable p);
    public Iterable<User> findAll(Sort s);
    public void deleteUser(int id);
    public void deleteUser(User u);
    public void deleteUsers(Iterable<User> users);
    public User findUserByEmailWithClub(String email);
}
