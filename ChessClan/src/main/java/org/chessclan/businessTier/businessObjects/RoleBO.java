/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import org.chessclan.dataTier.models.Role;
import org.chessclan.dataTier.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Grzegorz Duszyski <gfduszynski@gmail.com>
 */
@Service("RoleBO")
public class RoleBO implements Serializable{    
    
    @Autowired
    private RoleRepository roleRepo;
    
    public Role saveRole(Role r){
        return roleRepo.save(r);
    }
    
    public Iterable<Role> saveRoles(Iterable<Role> u){
        return roleRepo.save(u);
    }
    
    public Role findRoleByName(String roleName){
        return roleRepo.findByRoleName(roleName);
    }

    public Role findRoleById(int id){
        return roleRepo.findOne(id);
    }
    
    public Iterable<Role> findUsersById(Iterable<Integer> ids){
        return roleRepo.findAll(ids);
    }
    
    public Iterable<Role> findAll(){
        return roleRepo.findAll();
    }
    
    public Page<Role> findAll(Pageable p){
        return roleRepo.findAll(p);
    }
    
    public Iterable<Role> findAll(Sort s){
        return roleRepo.findAll(s);
    }
    
    public void deleteUser(int id){
        roleRepo.delete(id);
    }
    
    public void deleteUser(Role u){
        roleRepo.delete(u);
    }
    
    public void deleteUsers(Iterable<Role> roles){
        roleRepo.delete(roles);
    }
}
