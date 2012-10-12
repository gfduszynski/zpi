/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import java.util.Date;
import org.chessclan.dataTier.models.Post;
import org.chessclan.dataTier.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
/**
 *
 * @author Xcays
 */
@Service("PostBO")
public class PostBO implements Serializable{    
    
    @Autowired
    private PostRepository postRepo;
    
    
    public void deletePost(int id)
    {
        postRepo.delete(id);
    }
    
    public void deletePost(Post p)
    {
        postRepo.delete(p);
    }
    
    public void deletePosts(Iterable <Post> posts)
    {
        postRepo.delete(posts);
    }
    
    public void deleteAllPosts()
    {
        postRepo.deleteAll();
    }
    
    public Iterable<Post> findAllPosts()
    {
        return postRepo.findAll();
    }
    
   /* public Iterable<Post> findLatestPosts(int num)
    {
        return postRepo.
    }*/
    
    public Iterable<Post> findAllPostsSelected(Iterable<Integer> iter)
    {
        return postRepo.findAll(iter);
    }
    
    public Page<Post> findAllPosts(Pageable pgbl)
    {
        return postRepo.findAll(pgbl);
    }
    
    public Page<Post> findLatestPosts(Pageable pgbl)
    {
         return postRepo.findByDateExpiresAfter(new Date(), pgbl);
    }
    
    public Iterable<Post> findAllPosts(Sort s)
    {
        return postRepo.findAll(s);
    }
    
    public Post findOnePost(Integer id)
    {
        return postRepo.findOne(id);
    }
    
    public Post savePost(Post p)
    {
        return postRepo.save(p);
    }
    
    Iterable<Post> savePosts(Iterable<Post> itrbl)
    {
        return postRepo.save(itrbl);
    }
}