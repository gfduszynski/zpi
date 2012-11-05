/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects.impl;

import org.chessclan.businessTier.businessObjects.ResultsBO;
import org.chessclan.dataTier.models.Result;
import org.chessclan.dataTier.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xcays
 */
@Service("ResultBO")
public class ResultsBOImpl implements ResultsBO{    
    
    @Autowired
    private ResultRepository resultRepo;
    
    public Result saveResult(Result r){
        return resultRepo.save(r);
    }
    
    public Iterable<Result> saveResults(Iterable<Result> r){
        return resultRepo.save(r);
    }
    
    public Result findResultById(int id){
        return resultRepo.findOne(id);
    }
    
    public Iterable<Result> findResultsById(Iterable<Integer> ids){
        return resultRepo.findAll(ids);
    }
    
    public Iterable<Result> findAll(){
        return resultRepo.findAll();
    }
    
    public void deleteResult(int id){
        resultRepo.delete(id);
    }
    
    public void deleteResult(Result r){
        resultRepo.delete(r);
    }
    
    public void deleteResults(Iterable<Result> rs){
        resultRepo.delete(rs);
    }
}
