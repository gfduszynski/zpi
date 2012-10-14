/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import org.chessclan.dataTier.models.Round;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Xcays
 */
public interface RoundRepository extends JpaRepository<Round, Integer> {
}
