/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import org.chessclan.dataTier.models.Round;

/**
 *
 * @author Xcays
 */
public interface RoundBO{
    public Round saveRound(Round r);
    public Iterable<Round> saveRounds(Iterable<Round> r);
    public Round findRoundById(int id);
    public Iterable<Round> findRoundsById(Iterable<Integer> ids);
    public Iterable<Round> findAll();
    public void deleteRound(int id);
    public void deleteRound(Round r);
    public void deleteRounds(Iterable<Round> rs);
}
