/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects.impl;

import org.chessclan.businessTier.businessObjects.PairingCardBO;
import org.chessclan.dataTier.models.PairingCard;
import org.chessclan.dataTier.repositories.PairingCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xcays
 */
@Service("PairingCardBO")
public class PairingCardBOImpl implements PairingCardBO{    
    
    @Autowired
    private PairingCardRepository pcRepo;
    
    public PairingCard savePairingCard(PairingCard g){
        return pcRepo.save(g);
    }
    
    public Iterable<PairingCard> savePairingCards(Iterable<PairingCard> g){
        return pcRepo.save(g);
    }
    
    public PairingCard findPairingCardById(int id){
        return pcRepo.findOne(id);
    }
    
    public Iterable<PairingCard> findPairingCardById(Iterable<Integer> ids){
        return pcRepo.findAll(ids);
    }
    
    public Iterable<PairingCard> findAll(){
        return pcRepo.findAll();
    }
    
    public void deleteGame(int id){
        pcRepo.delete(id);
    }
    
    public void deleteGame(PairingCard g){
        pcRepo.delete(g);
    }
    
    public void deletePairingCards(Iterable<PairingCard> gs){
        pcRepo.delete(gs);
    }
}
