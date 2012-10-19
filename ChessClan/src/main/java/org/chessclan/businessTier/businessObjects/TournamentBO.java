/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import java.util.Date;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.Round;
import org.chessclan.dataTier.models.Tournament;
import org.chessclan.dataTier.repositories.PairingCardRepository;
import org.chessclan.dataTier.repositories.RoundRepository;
import org.chessclan.dataTier.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private PairingCardRepository gRepo;
    
    public Tournament registerTournament(String tName, Date tDate, String tDesc, Club tClub){
        Tournament t = new Tournament(null, tName, tDate, tDesc, tClub);
        return tRepo.saveAndFlush(t);
    }
    
    
    public Tournament goToNextRound(Tournament t) throws Round.NotFinished{
    
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
