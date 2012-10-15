/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import java.util.Date;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.Tournament;
import org.chessclan.dataTier.repositories.PairingCardRepository;
import org.chessclan.dataTier.repositories.RoundRepository;
import org.chessclan.dataTier.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author Xcays
 */
@Service("TurnamentBO")
public class TurnamentBO implements Serializable{    
    
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
    

}