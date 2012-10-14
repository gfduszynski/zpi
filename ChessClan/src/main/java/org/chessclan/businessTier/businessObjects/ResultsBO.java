/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import org.chessclan.dataTier.models.Results;
import org.chessclan.dataTier.repositories.ResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xcays
 */
@Service("ResultsBO")
public class ResultsBO implements Serializable{    
    
    @Autowired
    private ResultsRepository resultsRepo;
    
    public Results saveResult(Results r){
        return resultsRepo.save(r);
    }
    
    public Iterable<Results> saveResults(Iterable<Results> r){
        return resultsRepo.save(r);
    }
    
    public Results findResultById(int id){
        return resultsRepo.findOne(id);
    }
    
    public Iterable<Results> findResultsById(Iterable<Integer> ids){
        return resultsRepo.findAll(ids);
    }
    
    public Iterable<Results> findAll(){
        return resultsRepo.findAll();
    }
    
    public void deleteResult(int id){
        resultsRepo.delete(id);
    }
    
    public void deleteResult(Results r){
        resultsRepo.delete(r);
    }
    
    public void deleteResults(Iterable<Results> rs){
        resultsRepo.delete(rs);
    }
}
