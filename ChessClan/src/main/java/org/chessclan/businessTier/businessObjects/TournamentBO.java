/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import org.chessclan.dataTier.models.Category;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.PairingCard;
import org.chessclan.dataTier.models.Round;
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
    public Tournament registerTournament(String tName, Date tDate, String tDesc, Club tClub, Category cat){        
        Round initialRound = new Round(null, 0, Round.State.JOINING);
        Tournament t = new Tournament(null, tName, tDate, tDesc, tClub);
        t.setCategory(cat);
        t = tRepo.save(t);
        // daty rozpoczęcia i zakończenia
        t.setCurrentRound(initialRound);
        t.setRoundSet(new HashSet<Round>());
        t.getRoundSet().add(initialRound);
        initialRound.setTournament(t);
        return tRepo.saveAndFlush(t);
    }
    
    public PairingCard joinTorunament(Tournament t){return joinTournament(t, umBO.getLoggedUser());}
    public PairingCard joinTournament(Tournament t, User u){
        PairingCard pc = new PairingCard(null,0);
        pc.setPlayer(u);
        pc.setTournament(t);
        pc.setRound(t.getCurrentRound());
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
        for(PairingCard pc : currentRound.getPairingCardSet()){
            if(pc.getScore()==-1){
                throw new Round.NotFinished("[TournamentBO->goToNextRound(tId="+t.getId()+")] player_card(id="+pc.getId()+") has invalid score: "+pc.getScore());
            }
        }
        // If tournament not yet started
        if(currentRound.getRoundState()==Round.State.JOINING){
            currentRound.setRoundState(Round.State.FINISHED);
        }
        Round nextRound = new Round(null, currentRound.getNumber()+1, Round.State.STARTED);
        t.setCurrentRound(nextRound);
        t.getRoundSet().add(nextRound);
        nextRound.setTournament(t);
        nextRound.setPrevRound(currentRound);
        // Provide new pairing cards for next round
        
        
        return tRepo.saveAndFlush(t);
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
}
