/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.jpa;

import org.chessclan.dataTier.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
}
