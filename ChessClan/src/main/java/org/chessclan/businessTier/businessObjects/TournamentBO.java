/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.chessclan.dataTier.models.Category;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.PairingCard;
import org.chessclan.dataTier.models.Round;
import org.chessclan.dataTier.models.Tournament;
import org.chessclan.dataTier.models.User;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Xcays & Grzesiek
 */
public interface TournamentBO{
    @Transactional
    public Tournament registerTournament(int numberOfRounds, int pointsForBye, String name, Date startDate, String description, Club club, Category category);
    @Transactional    
    public PairingCard joinTournament(Tournament t) throws Round.NotJoinableRound;
    @Transactional
    public PairingCard joinTournament(Tournament t, User u) throws Round.NotJoinableRound;
    @Transactional
    public Tournament goToNextRound(Tournament t) throws Round.NotFinished, Round.NoPlayers;
    @Transactional
    public List<Tournament> findTournamentsByClub(Club club);
    // DAO Wrappers
    public Tournament saveTournament(Tournament t);
    public Iterable<Tournament> saveTournaments(Iterable<Tournament> t);
    public Tournament findTournamentById(int id);
    public Iterable<Tournament> findTournamentsById(Iterable<Integer> ids);
    @Transactional
    public Iterable<Tournament> findAll();
    public void deleteTournament(int id);
    @Transactional
    public void deleteTournament(Tournament t);
    public void deleteTournaments(Iterable<Tournament> ts);
    @Transactional
    public Tournament leaveTournament(Tournament t, PairingCard pc) throws Round.NotJoinableRound;
    @Transactional
    public Tournament fetchRelations(Tournament t);
    @Transactional 
    public List<Tournament> findTournamentsWithClubAndRoundsAndPC();
    @Transactional 
    public List<Tournament> findUserTournaments(User user);    
    Map<User, Float> getResults(Tournament t);
    @Transactional
    public Set<PairingCard> filterUniquePairingCards(Round currentRound);
    public PairingCard findOnePairingCard(int id);
    public PairingCard savePairingCard(PairingCard pc);
    public List<Round> getRoundList(Tournament t);
}
