/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import java.util.List;
import org.chessclan.dataTier.models.Round;
import org.chessclan.dataTier.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Xcays
 */
public interface RoundRepository extends JpaRepository<Round, Integer> {
    @Transactional
    List<Round> findRoundByTournamentOrderByNumberAsc(Tournament t);
}
