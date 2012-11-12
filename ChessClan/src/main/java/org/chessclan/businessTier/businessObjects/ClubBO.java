/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.util.Date;
import java.util.List;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.User;

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
    public Club findClubByName(String name);
    public Iterable<Club> findClubsById(Iterable<Integer> ids);
    public List<Club> findAll();
    public Iterable<Club> findAllWithOwners();
    public void deleteClub(int id);
    public void deleteClub(Club c);
    public void deleteClubs(Iterable<Club> clubs);
    public List<Club> findAllWithMembers();
    public List<User> findClubMembers(Club c);
}
