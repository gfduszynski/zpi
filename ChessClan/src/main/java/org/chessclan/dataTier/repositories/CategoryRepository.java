/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import org.chessclan.dataTier.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Xcays
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
