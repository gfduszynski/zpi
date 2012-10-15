/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import org.chessclan.dataTier.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Xcays
 */
public interface ResultRepository extends JpaRepository<Result, Integer> {
}
