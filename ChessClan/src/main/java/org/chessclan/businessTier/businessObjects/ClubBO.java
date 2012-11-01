/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
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
public interface ClubBO{
    public Club registerClub(String name, String description, Date creationDate, User owner);
    public Club joinClub(Club c);
    public Club joinClub(Club c, User u);
    public Club leaveClub();
    public Club leaveClub(Club c, User u);
    public Club saveClub(Club c);
    public Iterable<Club> saveClubs(Iterable<Club> c);
    public Club findClubById(int id);
    public Iterable<Club> findClubsById(Iterable<Integer> ids);
    public Iterable<Club> findAll();
    public Iterable<Club> findAllWithOwners();
    public void deleteClub(int id);
    public void deleteClub(Club c);
    public void deleteClubs(Iterable<Club> clubs);
}
