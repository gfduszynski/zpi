/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects.impl;

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
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author Xcays
 */
@Service("PostBO")
public class PostBOImpl implements PostBO{    
    
    @Autowired
    private PostRepository postRepo;
    
    
    @Override
    public void deletePost(int id)
    {
        postRepo.delete(id);
    }
    
    @Override
    public void deletePost(Post p)
    {
        postRepo.delete(p);
    }
    
    @Override
    public void deletePosts(Iterable <Post> posts)
    {
        postRepo.delete(posts);
    }
    
    @Override
    public void deleteAllPosts()
    {
        postRepo.deleteAll();
    }
    
    @Override
    public List<Post> findAllPosts()
    {
        return postRepo.findAll();
    }
    @Transactional
    @Override
    public List<Post> findAllPostsWithUsers()
    {
        List<Post> result =  postRepo.findAll();
        Iterator<Post> it = result.iterator();
        while(it.hasNext()) {
            it.next().getUser().getEmail();
        }
        return result;
    }
    
    @Override
    public Iterable<Post> findAllPostsSelected(Iterable<Integer> iter)
    {
        return postRepo.findAll(iter);
    }
    
    @Override
    public Page<Post> findAllPosts(Pageable pgbl)
    {
        return postRepo.findAll(pgbl);
    }
    
    @Override
    public List<Post> findAllPublishedPosts()
    {
        Calendar cal = Calendar.getInstance();
        return postRepo.findByDateExpiresAfterAndDatePublishedIsNotNull(cal.getTime());
    }
    
    
    @Override
    public Page<Post> findLatestPosts(Pageable pgbl)
    {
         return postRepo.findByDateExpiresAfter(new Date(), pgbl);
    }
    
    
    @Override
    public List<Post> findLatestPosts(int numberOfPosts)
    {
         List<Post> postList;
         postList = postRepo.findByDateExpiresAfter(new Date());
         if(postList.size()<= numberOfPosts) {
            return postList;
        }else { return postList.subList(0, numberOfPosts-1);}
    }
    
    @Override
    public Page<Post> findLatestPublishedPosts(Pageable pgbl)
    {
         Calendar cal = Calendar.getInstance();
         return postRepo.findByDateExpiresAfterAndDatePublishedIsNotNull(cal.getTime(), pgbl);
    }
    
    
    @Transactional
    @Override
    public List<Post> findLatestPublishedPosts(int numberOfPosts)
    {
         Calendar cal = Calendar.getInstance();
         List<Post> postList;
         postList = postRepo.findByDateExpiresAfterAndDatePublishedIsNotNull(cal.getTime());
         if(postList.size()> numberOfPosts) {
            postList = postList.subList(0, numberOfPosts);
        }
        for(int i = 0; i<postList.size(); i++) {
            postList.get(i).getUser().toString();
        }
        return postList;
    }
    @Override
    public Iterable<Post> findAllPosts(Sort s)
    {
        return postRepo.findAll(s);
    }
    @Transactional
    @Override
    public List<Post> findUserPosts(User u)
    {
        List<Post> result = postRepo.findByUser(u);
        Iterator<Post> it = result.iterator();
        while(it.hasNext()) {
            it.next().getUser().getEmail();
        }
        return result;
    }
    
    @Override
    public Post findOnePost(Integer id)
    {
        Post post = postRepo.findOne(id);
        User user = post.getUser();
        return post;
    }
    
    @Override
    public Post savePost(Post p)
    {
        return postRepo.save(p);
    }
    
    @Override
    public Iterable<Post> savePosts(Iterable<Post> itrbl)
    {
        return postRepo.save(itrbl);
    }
}