/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import java.util.List;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Xcays
 */
@Transactional(readOnly=false,propagation= Propagation.REQUIRED) 
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
    
    public List<Tournament> findByClub(Club club);
}
