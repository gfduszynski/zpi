/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.chessclan.businessTier.businessObjects.PostBO;
import org.chessclan.dataTier.models.Post;
import org.chessclan.dataTier.models.User;
import org.chessclan.dataTier.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author Xcays
 */
@Service("PostBO")
public class PostBOImpl implements PostBO{    
    
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
    @Transactional
    public Iterable<Post> findAllPostsWithUsers()
    {
        Iterable<Post> result =  postRepo.findAll();
        Iterator<Post> it = result.iterator();
        while(it.hasNext())
            it.next().getUser().getEmail();
        return result;
    }
    
    public Iterable<Post> findAllPostsSelected(Iterable<Integer> iter)
    {
        return postRepo.findAll(iter);
    }
    
    public Page<Post> findAllPosts(Pageable pgbl)
    {
        return postRepo.findAll(pgbl);
    }
    
    public List<Post> findAllPublishedPosts()
    {
        Calendar cal = Calendar.getInstance();
        return postRepo.findByDateExpiresAfterAndDatePublishedBefore(cal.getTime(), cal.getTime());
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
        }else { return postList.subList(0, numberOfPosts-1);}
    }
    
    public Page<Post> findLatestPublishedPosts(Pageable pgbl)
    {
         Calendar cal = Calendar.getInstance();
         return postRepo.findByDateExpiresAfterAndDatePublishedBefore(cal.getTime(), cal.getTime(), pgbl);
    }
    
    
    @Transactional
    public List<Post> findLatestPublishedPosts(int numberOfPosts)
    {
         Calendar cal = Calendar.getInstance();
         List<Post> postList;
         postList = postRepo.findByDateExpiresAfterAndDatePublishedBefore(cal.getTime(), cal.getTime());
         if(postList.size()> numberOfPosts) {
            postList = postList.subList(0, numberOfPosts);
        }
        for(int i = 0; i<postList.size(); i++)
            postList.get(i).getUser().toString();
        return postList;
    }
    public Iterable<Post> findAllPosts(Sort s)
    {
        return postRepo.findAll(s);
    }
    @Transactional
    public List<Post> findUserPosts(User u)
    {
        List<Post> result = postRepo.findByUser(u);
        Iterator<Post> it = result.iterator();
        while(it.hasNext())
            it.next().getUser().getEmail();
        return result;
    }
    
    public Post findOnePost(Integer id)
    {
        return postRepo.findOne(id);
    }
    
    public Post savePost(Post p)
    {
        return postRepo.save(p);
    }
    
    public Iterable<Post> savePosts(Iterable<Post> itrbl)
    {
        return postRepo.save(itrbl);
    }
}