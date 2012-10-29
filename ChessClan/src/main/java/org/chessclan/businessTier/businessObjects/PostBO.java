/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.chessclan.dataTier.models.Post;
import org.chessclan.dataTier.models.User;
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
    
    public void deletePosts(Iterable<Post> posts)
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
    
    
    public List<Post> findLatestPosts(int numberOfPosts)
    {
         List<Post> postList;
         postList = postRepo.findByDateExpiresAfter(new Date());
         if(postList.size()<= numberOfPosts) {
            return postList;
        }else { return postList.subList(postList.size()-numberOfPosts, postList.size()-1);}
    }
    
    public Page<Post> findLatestPublishedPosts(Pageable pgbl)
    {
         return postRepo.findByDateExpiresAfterAndDatePublishedBefore(new Date(),new Date(), pgbl);
    }
    
    
    public List<Post> findLatestPublishedPosts(int numberOfPosts)
    {
         List<Post> postList;
         postList = postRepo.findByDateExpiresAfterAndDatePublishedBefore(Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
         if(postList.size()<= numberOfPosts) {
            return postList;
        }else { return postList.subList(postList.size()-numberOfPosts, postList.size()-1);}
    }
    public Iterable<Post> findAllPosts(Sort s)
    {
        return postRepo.findAll(s);
    }
    
    public List<Post> findUserPosts(User u)
    {
        return postRepo.findByUser(u);
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