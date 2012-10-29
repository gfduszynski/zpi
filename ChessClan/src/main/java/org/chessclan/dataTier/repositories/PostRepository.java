/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositories;

import java.util.Date;
import java.util.List;
import org.chessclan.dataTier.models.Post;
import org.chessclan.dataTier.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Xcays
 */
public interface PostRepository extends PagingAndSortingRepository<Post, Integer>,  JpaRepository<Post, Integer> {

    Page<Post> findByDateExpiresAfter(Date date_expires, Pageable pgbl);
    List<Post> findByDateExpiresAfter(Date date_expires);

    Page<Post> findByDateExpiresAfterAndDatePublishedBefore(Date date_expires, Date date_published, Pageable pgbl);
    List<Post> findByDateExpiresAfterAndDatePublishedBefore(Date date_expires, Date date_published);
    
    List<Post> findByUser(User u);
}
