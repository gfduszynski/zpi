/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import org.chessclan.dataTier.models.Game;
import org.chessclan.dataTier.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xcays
 */
@Service("GameBO")
public class GameBO implements Serializable{    
    
    @Autowired
    private GameRepository gameRepo;
    
    public Game saveGame(Game g){
        return gameRepo.save(g);
    }
    
    public Iterable<Game> saveGames(Iterable<Game> g){
        return gameRepo.save(g);
    }
    
    public Game findGameById(int id){
        return gameRepo.findOne(id);
    }
    
    public Iterable<Game> findGamesById(Iterable<Integer> ids){
        return gameRepo.findAll(ids);
    }
    
    public Iterable<Game> findAll(){
        return gameRepo.findAll();
    }
    
    public void deleteGame(int id){
        gameRepo.delete(id);
    }
    
    public void deleteGame(Game g){
        gameRepo.delete(g);
    }
    
    public void deleteGames(Iterable<Game> gs){
        gameRepo.delete(gs);
    }
}
