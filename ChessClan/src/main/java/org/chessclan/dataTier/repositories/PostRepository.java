/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import org.chessclan.dataTier.models.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Xcays
 */
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    Post findByAuthorId(Integer id);
}
