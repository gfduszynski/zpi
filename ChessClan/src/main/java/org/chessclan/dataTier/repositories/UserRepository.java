/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import org.chessclan.dataTier.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Daniel
 */
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findByEmail(String email);
}
