/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.chessclan.businessTier.businessObjects.TournamentBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Xcays & Grzesiek
 */
@Service("TournamentBO")
public class TournamentBOImpl implements TournamentBO, Serializable {

    @Autowired
    private TournamentRepository tRepo;
    @Autowired
    private RoundRepository rRepo;
    @Autowired
    private PairingCardRepository pcRepo;
    @Autowired
    private CategoryRepository catRepo;
    @Autowired
    private UserManagementBO umBO;

    @Override
    public Tournament registerTournament(int numberOfRounds, int pointsForBye, String name, Date startDate, String description, Club club, Category category) {
        Tournament t = new Tournament(name, startDate, description, club, category, Tournament.State.NOT_STARTED, pointsForBye, numberOfRounds);
        t = tRepo.save(t);
        Round prevRound = new Round(null, -1, Round.State.JOINING);
        t.setCurrentRound(prevRound);
        prevRound.setTournament(t);
        prevRound.setRoundEnd(startDate);
        prevRound.setRoundStart(Calendar.getInstance().getTime());
        prevRound = rRepo.save(prevRound);

        for (int i = 0; i < numberOfRounds; i++) {
            Round r = new Round(null, i, Round.State.NOT_STARTED);
            r.setTournament(t);
            r.setPrevRound(prevRound);
            r = rRepo.save(r);
            prevRound.setNextRound(r);
            rRepo.save(prevRound);
            prevRound = r;
        }
        return tRepo.saveAndFlush(t);
    }

    @Override
    public PairingCard joinTournament(Tournament t) throws Round.NotJoinableRound {
        return joinTournament(t, umBO.getLoggedUser());
    }

    @Override
    public PairingCard joinTournament(Tournament t, User u) throws Round.NotJoinableRound {
        if (t.getCurrentRound().getRoundState() != State.JOINING) {
            throw new Round.NotJoinableRound();
        }
        PairingCard pc = new PairingCard(null, 0);
        pc.setPlayer(u);
        pc.setTournament(t);
        pc.setRound(t.getCurrentRound());
        pc.setColor(PairingCard.Color.NO_COLOR);
        //u.getPairingCardSet().add(pc);
        return pcRepo.saveAndFlush(pc);
    }

    @Override
    public Tournament leaveTournament(Tournament t, PairingCard pc) throws Round.NotJoinableRound {
        if (t.getCurrentRound().getRoundState() != State.JOINING) {
            throw new Round.NotJoinableRound();
        }

        t.getPairingCardSet().remove(pc);
        pcRepo.delete(pc);
        return tRepo.saveAndFlush(t);
    }

    @Override
    public Tournament goToNextRound(Tournament t) throws Round.NotFinished, Round.NoPlayers {
        t = tRepo.findOne(t.getId());
        Round currentRound = t.getCurrentRound();
        // If too few players
        if (currentRound.getPairingCardSet().size() < 2) {
            throw new Round.NoPlayers("[TournamentBO->goToNextRound(tId=" + t.getId() + ")] playerCount<2");
        }
        // Check if all the pairing cards are filled
        if (currentRound.getRoundState() != Round.State.JOINING) {
            for (PairingCard pc : currentRound.getPairingCardSet()) {
                // Guzik to sprawdza!, trzeba stan karty sprawdzić
                if (pc.getScore() == 0) {
                    //throw new Round.NotFinished("[TournamentBO->goToNextRound(tId="+t.getId()+")] player_card(id="+pc.getId()+") has invalid score: "+pc.getScore());
                }
            }
        }
        // If tournament not yet started, start it
        if (currentRound.getRoundState() == Round.State.JOINING) {
            currentRound.setRoundState(Round.State.FINISHED);
        }
        t.setCurrentRound(currentRound.getNextRound());
        currentRound.getNextRound().setRoundState(State.STARTED);
        // Find pairs for players
        Set<PairingCard> newPairingCards = mockupPairPlayers(currentRound.getPairingCardSet(), currentRound.getNextRound());
        currentRound.getNextRound().setPairingCardSet(newPairingCards);

        return tRepo.saveAndFlush(t);
    }

    private Set<PairingCard> mockupPairPlayers(Set<PairingCard> oldPairingCards, Round currentRound) {
        Set<PairingCard> newPairingCards = new HashSet<PairingCard>();
        PairingCard[] players = new PairingCard[1];
        players = oldPairingCards.toArray(players);
        List<PairingCard> sortedPlayers = new ArrayList<PairingCard>(Arrays.asList(players));
        Collections.sort(sortedPlayers);

        while (sortedPlayers.size() > 1) {
            PairingCard a = new PairingCard(sortedPlayers.get(0), currentRound);
            PairingCard b = new PairingCard(sortedPlayers.get(1), currentRound);
            boolean aWhite = a.getColorDiff() > b.getColorDiff();
            pairPlayers(aWhite ? a : b, aWhite ? b : a);
            sortedPlayers.remove(0);
            sortedPlayers.remove(0);
            newPairingCards.add(a);
            newPairingCards.add(b);
        }
        if (sortedPlayers.size() > 0) {
            PairingCard p = new PairingCard(sortedPlayers.get(0), currentRound);
            p.setByes(p.getByes() + 1);
            p.setColor(PairingCard.Color.NO_COLOR);
            p.setScore(p.getScore() + 1);
            p.setOpponent(null);
            newPairingCards.add(p);
        }

        return newPairingCards;
    }

    private Set<PairingCard> pairPlayers(Set<PairingCard> oldPairingCards, Round currentRound) {
        // Generate score brackets
        HashMap<Float, LinkedList<PairingCard>> scoreBrackets =
                generateScoreBrackets(oldPairingCards, currentRound);
        // Pairing results
        Set<PairingCard> newPairingCards = new HashSet<PairingCard>();

        // Sort bracket key's according to score
        Float[] keys = new Float[1];
        keys = scoreBrackets.keySet().toArray(keys);
        List<Float> sortedBrackets = (List<Float>) Arrays.asList(keys);
        Collections.sort(sortedBrackets);

        // Pairing players -----------------------------------------------------

        // List of players with downfloat
        List<PairingCard> downFloaters = new LinkedList<PairingCard>();

        //Pairing params
        int maxNumOfPairs = 0;
        int minNumOfPairs = 0;
        int numOfDownfloaters = 0;
        float maxScore = currentRound.getNumber(); // Number of wins * points for win
        for (int b = sortedBrackets.size(); b > 0; b--) {
            LinkedList<PairingCard> scoreBracket = scoreBrackets.get(sortedBrackets.get(b - 1)); // Highest score bracket - descending

            // Add downfloaters & determine type of bracket
            boolean homogeneoeus = downFloaters.size() >= scoreBracket.size();
            boolean reminder = false;
            scoreBracket.addAll(downFloaters);
            // TODO determine remaining score bracket, top scorers

            // C.1 - Filter incompatible players -------------------------------
            Set<PairingCard> incompatible = filterIncompatiblePlayers(scoreBracket, downFloaters);
            downFloaters.clear();

            // C.2 - Calculate param's -----------------------------------------
            maxNumOfPairs = (int) Math.floor(scoreBracket.size() / 2.0);  // Number of players in S1 and S2
            numOfDownfloaters = downFloaters.size();                    // Number of players moved down from higher score grups
            minNumOfPairs = calcMinNumOfPairs(currentRound.getNumber() % 2 == 0, scoreBracket);

            // C.3 - Set requirements ------------------------------------------
            int requiredNumOfPairs = homogeneoeus ? maxNumOfPairs : minNumOfPairs;
            boolean respectB2 = maxScore / 2.0 < sortedBrackets.get(b - 1); // Respect rule B2
            boolean respectA7d = currentRound.getNumber() % 2 == 0;         // Respect rule A7.d
            int requiredMiNumOfPairs = minNumOfPairs;                   // C.3.d
            boolean respectB5ByDownfloater = true;                      // Respect rule B5
            boolean respectB6ByDownfloater = true;                      // Respect rule B6
            boolean respectB5ByUpfloater = true;                      // Respect rule B5
            boolean respectB6ByUpfloater = true;                      // Respect rule B6

            // C.4 - Establish sub-groups (player score S2>=S1) ----------------
            List<PairingCard> S1 = new LinkedList<PairingCard>();
            List<PairingCard> S2 = new LinkedList<PairingCard>();
            // Sort All players by score
            List<PairingCard> orderedByScore = Arrays.asList((PairingCard[]) scoreBracket.toArray());
            Collections.sort(orderedByScore);
            Collections.reverse(orderedByScore);
            // Check if homogeneous by downfloater 
            // TODO: Something is wrong here
            if (!homogeneoeus) {
                homogeneoeus = orderedByScore.get(0) == orderedByScore.get(orderedByScore.size() - 1);
            }
            // Assign players to S1
            for (int i = 0; i < requiredNumOfPairs; i++) {
                S1.add(orderedByScore.get(i));
            }
            // Assign players to S1
            for (int i = requiredNumOfPairs; i < orderedByScore.size(); i++) {
                S2.add(orderedByScore.get(i));
            }
            // C.5 - Sort S1 and S2 --------------------------------------------
            Collections.sort(S1);
            Collections.sort(S2);
            // C.6 - Try to find the pairing -----------------------------------
            List<PairingCard> notPaired = new LinkedList<PairingCard>();
            List<PairingCard> candidatePairings = findPairings(S1, S2, notPaired);
            if (candidatePairings.size() > requiredMiNumOfPairs * 2) { // Each pair consists of 2 pairing card's
                // Now pairing in score bracket is considered complete
                if (homogeneoeus || reminder) {
                    downFloaters.addAll(notPaired);
                    // Go to next score bracket
                } else {
                    /*
                     Mark the current transposition and the value of P (it may be useful later).
                     Redefine P = P1 – M1
                     Continue at C4 with the remainder group.
                     */
                }
            }
        }

        return newPairingCards;
    }

    private HashMap<Float, LinkedList<PairingCard>> generateScoreBrackets(Set<PairingCard> oldPairingCards, Round currentRound) {
        HashMap<Float, LinkedList<PairingCard>> scoreBrackets = new HashMap<Float, LinkedList<PairingCard>>();
        // Assign players to their score brackets with new pairing card
        for (PairingCard pc : oldPairingCards) {
            // Add missing score bracket
            if (!scoreBrackets.containsKey(pc.getScore())) {
                scoreBrackets.put(pc.getScore(), new LinkedList<PairingCard>());
            }
            // Add players new paring card to score bracket
            scoreBrackets.get(pc.getScore()).add(new PairingCard(pc, currentRound));
        }
        return scoreBrackets;
    }

    private Set<PairingCard> filterIncompatiblePlayers(LinkedList<PairingCard> scoreBracket, List<PairingCard> downFloaters) {
        Set<PairingCard> result = new HashSet<PairingCard>();
        throw new NotImplementedException();
        //return result;
    }

    private int calcMinNumOfPairs(boolean evenRound, LinkedList<PairingCard> scoreBracket) {
        throw new NotImplementedException();
    }

    private List<PairingCard> findPairings(List<PairingCard> S1, List<PairingCard> S2, List<PairingCard> notPaired) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<PairingCard> performTransposition(List<PairingCard> s2, int transpositionNumber) {
        throw new NotImplementedException();
    }

    private int performExchange(List<PairingCard> s1, List<PairingCard> s2) {
        throw new NotImplementedException();
    }

    private void pairPlayers(PairingCard white, PairingCard black) {
        white.setOpponent(black);
        white.setColorDiff(white.getColorDiff() + 1);
        white.setColor(PairingCard.Color.WHITE);
        black.setOpponent(white);
        black.setColorDiff(black.getColorDiff() - 1);
        black.setColor(PairingCard.Color.BLACK);
    }

    @Override
    public List<Tournament> findTournamentsByClub(Club club) {
        return tRepo.findByClub(club);
    }

    // DAO Wrappers
    @Override
    public Tournament saveTournament(Tournament t) {
        return tRepo.save(t);
    }

    @Override
    public Iterable<Tournament> saveTournaments(Iterable<Tournament> t) {
        return tRepo.save(t);
    }

    @Override
    public Tournament findTournamentById(int id) {
        return tRepo.findOne(id);
    }

    @Override
    public Iterable<Tournament> findTournamentsById(Iterable<Integer> ids) {
        return tRepo.findAll(ids);
    }

    @Override
    public Iterable<Tournament> findAll() {
        return tRepo.findAll();
    }

    @Override
    public void deleteTournament(int id) {
        tRepo.delete(id);
    }

    @Override
    public void deleteTournament(Tournament t) {
        tRepo.delete(t);
    }

    @Override
    public void deleteTournaments(Iterable<Tournament> ts) {
        tRepo.delete(ts);
    }

    @Override
    public Tournament fetchRelations(Tournament t) {
        Tournament tRes = tRepo.findOne(t.getId());
        for (PairingCard pc : tRes.getPairingCardSet()) {
            pc.getPlayer().getFirstName().toString();
        }
        t.getCurrentRound().getRoundState().toString();
        return tRes;
    }

    @Override
    public List<Tournament> findTournamentsWithClubAndRoundsAndPC() {
        List<Tournament> result = tRepo.findAll();
        for (Tournament t : result) {
            t.getClub().getName().toString();
            t.getCurrentRound().getRoundState().toString();
            if(!t.getPairingCardSet().isEmpty()){
                Iterator<PairingCard> iter = t.getPairingCardSet().iterator();
                while(iter.hasNext()){
                    iter.next().getPlayer().getFirstName();
                }
            }
        }
        return result;
    }

    @Override
    public List<Tournament> findUserTournaments(User user) {
        List<Tournament> result = tRepo.findAll();
        List<Tournament> userTmt = new LinkedList<Tournament>();
        for (Tournament t : result) {
            t.getClub().getName().toString();
            boolean userInTmt = false;
            for (PairingCard pc : t.getCurrentRound().getPairingCardSet()) {
                if (pc.getPlayer().getId() == user.getId()) {
                    userInTmt = true;
                }
            }
            if(userInTmt){
                userTmt.add(t);
            }
        }
        return userTmt;
    }
}
