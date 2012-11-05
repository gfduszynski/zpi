/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.chessclan.businessTier.businessObjects.ClubBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.User;
import org.chessclan.dataTier.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Xcays
 */
@Service("ClubBO")
public class ClubBOImpl implements ClubBO {

    @Autowired
    private ClubRepository clubRepo;
    @Autowired
    private UserManagementBO umBO;

    public Club registerClub(String name, String description, Date creationDate, User owner) {
        Club c = new Club(null, name, creationDate, owner);
        c.setDescription(description);
        owner.setOwnedClub(c);
        return clubRepo.saveAndFlush(c);
    }

    @Transactional
    public Club joinClub(Club c) {
        return joinClub(c, umBO.getLoggedUser());
    }

    @Transactional
    public Club joinClub(Club c, User u) {
        u = umBO.findUserById(u.getId());
        c.getUserSet().add(u);
        u.setUserClub(c);
        umBO.saveUser(u);
        return clubRepo.save(c);
    }

    public Club leaveClub() {
        return leaveClub(umBO.getLoggedUser().getUserClub(), umBO.getLoggedUser());
    }

    public Club leaveClub(Club c, User u) {
        c.getUserSet().remove(u);
        u.setUserClub(null);
        umBO.saveUser(u);
        return clubRepo.save(c);
    }

    public Club saveClub(Club c) {
        return clubRepo.save(c);
    }

    public Iterable<Club> saveClubs(Iterable<Club> c) {
        return clubRepo.save(c);
    }

    public Club findClubById(int id) {
        return clubRepo.findOne(id);
    }

    public Iterable<Club> findClubsById(Iterable<Integer> ids) {
        return clubRepo.findAll(ids);
    }

    @Transactional
    public List<Club> findAll() {
        return clubRepo.findAll();
    }

    @Transactional
    public List<Club> findAllWithMembers() {
        List<Club> clubs = clubRepo.findAll();
        for(Club c : clubs){
            c.getUserSet();
        }
        return clubs;
    }

    @Transactional
    public Iterable<Club> findAllWithOwners() {
        Iterable<Club> result = findAll();
        Iterator<Club> it = result.iterator();
        while (it.hasNext()) {
            it.next().getOwner().getEmail();
        }
        return result;
    }

    public void deleteClub(int id) {
        clubRepo.delete(id);
    }

    public void deleteClub(Club c) {
        clubRepo.delete(c);
    }

    public void deleteClubs(Iterable<Club> clubs) {
        clubRepo.delete(clubs);
    }

    public Club findClubByName(String name) {
        return clubRepo.findByName(name);
    }
}
