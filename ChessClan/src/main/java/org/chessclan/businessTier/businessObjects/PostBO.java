/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.util.List;
import org.chessclan.dataTier.models.Post;
import org.chessclan.dataTier.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
/**
 *
 * @author Xcays
 */
public interface PostBO {
    public void deletePost(int id);
    public void deletePost(Post p);
    public void deletePosts(Iterable <Post> posts);
    public void deleteAllPosts();
    public Iterable<Post> findAllPosts();
    public Iterable<Post> findAllPostsWithUsers();
    public Iterable<Post> findAllPostsSelected(Iterable<Integer> iter);
    public Page<Post> findAllPosts(Pageable pgbl);
    public List<Post> findAllPublishedPosts();
    public Page<Post> findLatestPosts(Pageable pgbl);
    public List<Post> findLatestPosts(int numberOfPosts);
    public Page<Post> findLatestPublishedPosts(Pageable pgbl);
    public List<Post> findLatestPublishedPosts(int numberOfPosts);
    public Iterable<Post> findAllPosts(Sort s);
    public List<Post> findUserPosts(User u);
    public Post findOnePost(Integer id);
    public Post savePost(Post p);
    public Iterable<Post> savePosts(Iterable<Post> itrbl);
}