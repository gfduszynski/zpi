/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import java.util.Date;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.User;
import org.chessclan.dataTier.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xcays
 */
@Service("ClubBO")
public class ClubBO implements Serializable{    
    
    @Autowired
    private ClubRepository clubRepo;
    
    @Autowired
    private UserManagementBO umBO;
    
    public Club registerClub(String name, String description, Date creationDate, User owner){
        Club c =  new Club(null, name, creationDate, owner);
        c.setDescription(description);
        owner.setOwnedClub(c);
        return clubRepo.saveAndFlush(c);
    }
    
    public Club joinClub(Club c){return joinClub(c,umBO.getLoggedUser());}
    public Club joinClub(Club c, User u){
        c.getUserSet().add(u);
        u.setUserClub(c);
        return clubRepo.save(c);
    }
    
    public Club leaveClub(){return leaveClub(umBO.getLoggedUser().getUserClub(),umBO.getLoggedUser());}
    public Club leaveClub(Club c, User u){
        c.getUserSet().remove(u);
        u.setUserClub(null);
        return clubRepo.save(c);
    }
    
    public Club saveClub(Club c){
        return clubRepo.save(c);
    }
    
    public Iterable<Club> saveClubs(Iterable<Club> c){
        return clubRepo.save(c);
    }
    
    public Club findClubById(int id){
        return clubRepo.findOne(id);
    }
    
    public Iterable<Club> findClubsById(Iterable<Integer> ids){
        return clubRepo.findAll(ids);
    }
    
    public Iterable<Club> findAll(){
        return clubRepo.findAll();
    }
    
    public void deleteClub(int id){
        clubRepo.delete(id);
    }
    
    public void deleteClub(Club c){
        clubRepo.delete(c);
    }
    
    public void deleteClubs(Iterable<Club> clubs){
        clubRepo.delete(clubs);
    }
}
