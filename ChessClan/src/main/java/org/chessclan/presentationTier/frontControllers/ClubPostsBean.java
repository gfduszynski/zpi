/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.chessclan.businessTier.businessObjects.PostBO;
import org.chessclan.dataTier.models.Post;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "cPostsBean")
@ViewScoped
public class ClubPostsBean implements Serializable {

    private List<Post> allPosts;
    private List<Post> userPosts;
    private String title;
    private String content;
    private boolean postPublished;
    private boolean postSaved;
    private boolean postWrong;
    private int postlt;
    @ManagedProperty("#{PostBO}")
    PostBO postBO;
    @ManagedProperty("#{loginBean.user}")
    User user;

    public ClubPostsBean() {
        this.postPublished = false;
        this.postSaved = false;
        this.postWrong = false;
        this.postlt = 1;
    }

    @PostConstruct
    public void initialize() {
        this.allPosts = postBO.findAllPosts();
        this.userPosts = postBO.findUserPosts(this.user);
    }
    
        public void saveAndPublish() {
        if (validateContent() && validateTitle()) {
            Post.PostLifeTime plt;
            switch (postlt) {
                case 1:
                    plt = Post.PostLifeTime.WEEK;
                    break;
                case 2:
                    plt = Post.PostLifeTime.TWOWEEKS;
                    break;
                case 3:
                    plt = Post.PostLifeTime.THREEWEEKS;
                    break;
                default:
                    plt = Post.PostLifeTime.WEEK;
                    break;
            }
            Post publishedPost = postBO.savePost(new Post(title, content, user, new Date(), plt));
            if (publishedPost != null) {
                this.postPublished = true;
                this.userPosts.add(publishedPost);
                this.allPosts.add(publishedPost);
            }
            this.title = "";
            this.content = "";
        }
    }

    public void save() {
        if (validateContent() && validateTitle()) {
            Post.PostLifeTime plt = null;
            switch (postlt) {
                case 1:
                    plt = Post.PostLifeTime.WEEK;
                    break;
                case 2:
                    plt = Post.PostLifeTime.TWOWEEKS;
                    break;
                case 3:
                    plt = Post.PostLifeTime.THREEWEEKS;
                    break;
                default:
                    plt = Post.PostLifeTime.WEEK;
                    break;
            }
            Post savedPost = postBO.savePost(new Post(title, content, user, null, plt));
            if (savedPost != null) {
                this.postSaved = true;
                this.userPosts.add(savedPost);
                this.allPosts.add(savedPost);
            }
            this.title = "";
            this.content = "";
        }
    }

    public void publishPost(Post post) {
        post.setDatePublished(Calendar.getInstance().getTime());
        postBO.savePost(post);
    }

    public void unPublishPost(Post post) {
        post.setDatePublished(null);
        this.allPosts.remove(post);
        postBO.savePost(post);
    }

    public void removePost(Post post) {
        this.userPosts.remove(post);
        this.allPosts.remove(post);
        postBO.deletePost(post);
    }

    public boolean validateTitle() {
        if (title != null) {
            if (title.length() > 0) {
                this.postWrong = false;
                return true;
            } else {
                this.postWrong = true;
                return false;
            }
        } else {
            this.postWrong = true;
            return false;
        }
    }

    public boolean validateContent() {
        if (content != null) {
            if (content.length() > 0) {
                this.postWrong = false;
                return true;
            } else {
                this.postWrong = true;
                return false;
            }
        } else {
            this.postWrong = true;
            return false;
        }
    }

    public List<Post> getAllPosts() {
        return allPosts;
    }

    public void setAllPosts(List<Post> allPosts) {
        this.allPosts = allPosts;
    }

    public List<Post> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(List<Post> userPosts) {
        this.userPosts = userPosts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPostPublished() {
        return postPublished;
    }

    public void setPostPublished(boolean postPublished) {
        this.postPublished = postPublished;
    }

    public boolean isPostSaved() {
        return postSaved;
    }

    public void setPostSaved(boolean postSaved) {
        this.postSaved = postSaved;
    }

    public boolean isPostWrong() {
        return postWrong;
    }

    public void setPostWrong(boolean postWrong) {
        this.postWrong = postWrong;
    }

    public int getPostlt() {
        return postlt;
    }

    public void setPostlt(int postlt) {
        this.postlt = postlt;
    }

    public PostBO getPostBO() {
        return postBO;
    }

    public void setPostBO(PostBO postBO) {
        this.postBO = postBO;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
