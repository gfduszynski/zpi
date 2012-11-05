/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.chessclan.dataTier.models.Category;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.PairingCard;
import org.chessclan.dataTier.models.Round;
import org.chessclan.dataTier.models.Round.State;
import org.chessclan.dataTier.models.Tournament;
import org.chessclan.dataTier.models.User;
import org.chessclan.dataTier.repositories.CategoryRepository;
import org.chessclan.dataTier.repositories.PairingCardRepository;
import org.chessclan.dataTier.repositories.RoundRepository;
import org.chessclan.dataTier.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Xcays & Grzesiek
 */
public interface TournamentBO{
    public Tournament registerTournament(int numberOfRounds, float pointsForBye, String name, Date startDate, String description, Club club, Category category);
    public PairingCard joinTorunament(Tournament t) throws Round.NotJoinableRound;
    public PairingCard joinTournament(Tournament t, User u) throws Round.NotJoinableRound;
    public Tournament goToNextRound(Tournament t) throws Round.NotFinished, Round.NoPlayers;
    public List<Tournament> findTournamentsByClub(Club club);
    // DAO Wrappers
    public Tournament saveTournament(Tournament t);
    public Iterable<Tournament> saveTournaments(Iterable<Tournament> t);
    public Tournament findTournamentById(int id);
    public Iterable<Tournament> findTournamentsById(Iterable<Integer> ids);
    public Iterable<Tournament> findAll();
    public void deleteTournament(int id);
    public void deleteTournament(Tournament t);
    public void deleteTournaments(Iterable<Tournament> ts);
}