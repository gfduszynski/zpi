/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import org.chessclan.dataTier.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Xcays
 */
public interface ClubRepository extends JpaRepository<Club, Integer> {
    @Transactional
    Club findByName(String name);
}
