/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import org.chessclan.dataTier.models.PairingCard;
import org.chessclan.dataTier.repositories.PairingCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xcays
 */
public interface PairingCardBO{
    public PairingCard savePairingCard(PairingCard g);
    public Iterable<PairingCard> savePairingCards(Iterable<PairingCard> g);
    public PairingCard findPairingCardById(int id);
    public Iterable<PairingCard> findPairingCardById(Iterable<Integer> ids);
    public Iterable<PairingCard> findAll();
    public void deleteGame(int id);
    public void deleteGame(PairingCard g);
    public void deletePairingCards(Iterable<PairingCard> gs);
}
