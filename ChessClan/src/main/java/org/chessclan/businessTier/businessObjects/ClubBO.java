/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xcays
 */
@Service("ClubBO")
public class ClubBO implements Serializable{    
    
    @Autowired
    private ClubRepository clubRepo;
    
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
