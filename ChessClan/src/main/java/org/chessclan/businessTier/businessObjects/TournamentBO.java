/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
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

/**
 *
 * @author Xcays & Grzesiek
 */
@Service("TournamentBO")
public class TournamentBO implements Serializable{    
    
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
    
    @Transactional(readOnly=false,propagation= Propagation.REQUIRED)
    public Tournament registerTournament(int numberOfRounds, float pointsForBye, String name, Date startDate, String description, Club club, Category category){        
        Tournament t = new Tournament(null, name, startDate, description, club);
        t.setPointsForBye(pointsForBye);
        t.setCategory(category);
        t.setNumberOfRounds(numberOfRounds);
        t = tRepo.save(t);
        Round prevRound = new Round(null, -1, Round.State.JOINING);      
        t.setCurrentRound(prevRound);
        prevRound.setTournament(t);
        prevRound.setRoundEnd(startDate);
        prevRound.setRoundStart(Calendar.getInstance().getTime());
        prevRound = rRepo.save(prevRound);

        for(int i =0; i<numberOfRounds; i++){
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
    
    public PairingCard joinTorunament(Tournament t) throws NotJoinableRound{return joinTournament(t, umBO.getLoggedUser());}
    public PairingCard joinTournament(Tournament t, User u) throws NotJoinableRound{
        if(t.getCurrentRound().getRoundState() != State.JOINING){
            throw new NotJoinableRound();
        }
        PairingCard pc = new PairingCard(null,0);
        pc.setPlayer(u);
        pc.setTournament(t);
        pc.setRound(t.getCurrentRound());
        pc.setColor(PairingCard.Color.NO_COLOR);
        u.getPairingCardSet().add(pc);
        return pcRepo.saveAndFlush(pc);
    }
    
    public Tournament goToNextRound(Tournament t) throws Round.NotFinished, Round.NoPlayers{
        t = tRepo.findOne(t.getId());
        Round currentRound = t.getCurrentRound();
        // If too few players
        if(currentRound.getPairingCardSet().size()<2) {
            throw new Round.NoPlayers("[TournamentBO->goToNextRound(tId="+t.getId()+")] playerCount<2");
        }
        // Check if all the pairing cards are filled
        if(currentRound.getRoundState()!=Round.State.JOINING) {
            for(PairingCard pc : currentRound.getPairingCardSet()){
                // Guzik to sprawdza!, trzeba stan karty sprawdzić
                if(pc.getScore()==0){
                    throw new Round.NotFinished("[TournamentBO->goToNextRound(tId="+t.getId()+")] player_card(id="+pc.getId()+") has invalid score: "+pc.getScore());
                }
            }
        }
        // If tournament not yet started, start it
        if(currentRound.getRoundState()==Round.State.JOINING){
            currentRound.setRoundState(Round.State.FINISHED);
        }
        Round nextRound = new Round(null, currentRound.getNumber()+1, Round.State.STARTED);
        t.setCurrentRound(nextRound);
        t.getRoundSet().add(nextRound);
        nextRound.setTournament(t);
        nextRound.setPrevRound(currentRound);
        // Find pairs for players
        Set<PairingCard> newPairingCards = pairPlayers(currentRound.getPairingCardSet(),currentRound);
        nextRound.setPairingCardSet(newPairingCards);
        
        return tRepo.saveAndFlush(t);
    }
    
    private Set<PairingCard> pairPlayers(Set<PairingCard> oldPairingCards, Round currentRound){
        HashMap<Float,LinkedList<PairingCard>> scoreBrackets = new HashMap<Float, LinkedList<PairingCard>>();
        Set<PairingCard> newPairingCards = new HashSet<PairingCard>();
        
        // Assign players to their score brackets with new pairing card
        for(PairingCard pc : oldPairingCards){
            // Add missing score bracket
            if(!scoreBrackets.containsKey(pc.getScore())){
                scoreBrackets.put(pc.getScore(), new LinkedList<PairingCard>());
            }
            // Add players new paring card to score bracket
            LinkedList<PairingCard> bracket = scoreBrackets.get(pc.getScore());
            PairingCard newPC = new PairingCard();
            newPC.setByes(pc.getByes());
            newPC.setColorDiff(pc.getColorDiff());
            newPC.setFloats(pc.getFloats());
            newPC.setId(null);
            newPC.setOpponent(null);
            newPC.setPlayer(pc.getPlayer());
            newPC.setRound(currentRound);
            newPC.setScore(pc.getScore());
            newPC.setTournament(pc.getTournament());
            bracket.add(newPC);
        }
        // Sort bracket key's according to score
        Float[] keys = new Float[1];
        keys = scoreBrackets.keySet().toArray(keys);
        
        List<Float> sortedBrackets = (List<Float>)Arrays.asList((Float[])keys);
        Collections.sort(sortedBrackets);
        // Pairing players
        List<PairingCard> downFloaters = new LinkedList<PairingCard>();
        //Pairing params
        int Z=0;
        int X=0;
        int P=0;
        for(int b = sortedBrackets.size(); b>0; b--){
            LinkedList<PairingCard> scoreBracket = scoreBrackets.get(sortedBrackets.get(b-1)); // Highest score bracket - descending
            // Calculate param's
            int P0 = (int) Math.floor(scoreBracket.size()/2.0);     // Number of players in S1 and S2
            int M0 = downFloaters.size();                           // Number of players moved down from higher score grups
            int P1=P0;
            int M1=M0;
            int Z1=0;
            int X1=0;
            if(currentRound.getNumber()%2==0){
                // If round is even calculate Z1
            }
            
            HashSet<PairingCard> S1 = new HashSet<PairingCard>();
            HashSet<PairingCard> S2 = new HashSet<PairingCard>();   // player score S2>=S1
            
            // Add downfloaters
            boolean homogeneoeus = downFloaters.size()>=scoreBracket.size();
            scoreBracket.addAll(downFloaters); 
            downFloaters.clear();
            
            // Sort players by score
            List<PairingCard> orderedByScore = Arrays.asList((PairingCard[])scoreBracket.toArray());
            Collections.sort(orderedByScore);
            Collections.reverse(orderedByScore);
            
            // if not homogeneous by downfloater
            if(!homogeneoeus) {
                homogeneoeus = orderedByScore.get(0) == orderedByScore.get(orderedByScore.size()-1);
            }
            
            // Assign players to S2
            for(int i = 0; i < P0; i++){
                S2.add(orderedByScore.get(i));
            }
            // Assign players to S1
            for(int i = P0; i < (P0+P0); i++){
                S1.add(orderedByScore.get(i));
            }
            // Move downfloaters
            for(int i = P0+P0; i < scoreBracket.size(); i++){
                PairingCard downFloater = orderedByScore.get(i);
                
                downFloaters.add(downFloater);
            }
        }
        
        return newPairingCards;
    }
    
    
    private void pairPlayers(PairingCard white, PairingCard black) {
	    white.setOpponent(black);
	    white.setColorDiff(white.getColorDiff()+1);
            white.setColor(PairingCard.Color.WHITE);
            black.setOpponent(white);
            black.setColorDiff(black.getColorDiff()-1);
	    black.setColor(PairingCard.Color.BLACK);
    }
    
    // DAO Wrappers
    public Tournament saveTournament(Tournament t){
        return tRepo.save(t);
    }
    
    public Iterable<Tournament> saveTournaments(Iterable<Tournament> t){
        return tRepo.save(t);
    }
    
    public Tournament findTournamentById(int id){
        return tRepo.findOne(id);
    }
    
    public Iterable<Tournament> findTournamentsById(Iterable<Integer> ids){
        return tRepo.findAll(ids);
    }
    
    public Iterable<Tournament> findAll(){
        return tRepo.findAll();
    }
    
    public void deleteTournament(int id){
        tRepo.delete(id);
    }
    
    public void deleteTournament(Tournament t){
        tRepo.delete(t);
    }
    
    public void deleteTournaments(Iterable<Tournament> ts){
        tRepo.delete(ts);
    }
    
    public static class NotJoinableRound extends Exception{
        
    }
}
