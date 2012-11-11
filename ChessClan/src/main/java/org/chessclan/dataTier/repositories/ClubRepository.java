/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import java.util.List;
import org.chessclan.dataTier.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Xcays
 */
@Transactional
public interface ClubRepository extends JpaRepository<Club, Integer> {
    Club findByName(String name);
    List<Club> findAllWithMembers();
}
