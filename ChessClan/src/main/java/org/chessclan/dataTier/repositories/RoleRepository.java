/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import org.chessclan.dataTier.models.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Daniel
 */
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
