/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects.impl;

import java.io.Serializable;
import org.chessclan.businessTier.businessObjects.RoundBO;
import org.chessclan.dataTier.models.Round;
import org.chessclan.dataTier.repositories.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xcays
 */
@Service("RoundBO")
public class RoundBOImpl implements RoundBO{    
    
    @Autowired
    private RoundRepository roundRepo;
    
    public Round saveRound(Round r){
        return roundRepo.save(r);
    }
    
    public Iterable<Round> saveRounds(Iterable<Round> r){
        return roundRepo.save(r);
    }
    
    public Round findRoundById(int id){
        return roundRepo.findOne(id);
    }
    
    public Iterable<Round> findRoundsById(Iterable<Integer> ids){
        return roundRepo.findAll(ids);
    }
    
    public Iterable<Round> findAll(){
        return roundRepo.findAll();
    }
    
    public void deleteRound(int id){
        roundRepo.delete(id);
    }
    
    public void deleteRound(Round r){
        roundRepo.delete(r);
    }
    
    public void deleteRounds(Iterable<Round> rs){
        roundRepo.delete(rs);
    }
}
