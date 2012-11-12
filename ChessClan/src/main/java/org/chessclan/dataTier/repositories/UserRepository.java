/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import java.util.List;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByLogin(String login);
    List<User> findUsersByUserClub(Club club);
    User findUserByEmailWithClub(String email);
}
