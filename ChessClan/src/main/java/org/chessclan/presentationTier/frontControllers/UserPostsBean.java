/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.chessclan.businessTier.businessObjects.PostBO;
import org.chessclan.dataTier.models.Post;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "upBean")
@ViewScoped
public class UserPostsBean implements Serializable {

    private List<Post> allPosts;
    private List<Post> userPosts;
    private String title;
    private String content;
    private boolean postPublished;
    private boolean postSaved;
    private boolean postWrong;
    @ManagedProperty("#{PostBO}")
    PostBO postBO;
    
    @ManagedProperty("#{loginBean}")
    LoginBean loginBean;

    public UserPostsBean() {
        this.postPublished = false;
        this.postSaved = false;
        this.postWrong = false;
    }

    @PostConstruct
    public void initialize() {
        this.allPosts = new ArrayList<Post>();
        Iterator<Post> posts = postBO.findAllPosts().iterator();
        while (posts.hasNext()) {
            allPosts.add(posts.next());
        }
    }

    public void saveAndPublish() {
        Post publishedPost = postBO.savePost(new Post(title, content, true, loginBean.getUser()));
        if (publishedPost != null) {
            this.postPublished = true;
        }
        this.title = "";
        this.content = "";
    }

    public void save() {
        Post publishedPost = postBO.savePost(new Post(title, content, false, loginBean.getUser()));
        if (publishedPost != null) {
            this.postSaved = true;
        }
        this.title = "";
        this.content = "";
    }

    public void validateTitle() {
        if (title != null) {
            if (title.length() > 0) {
                this.postWrong = false;
            } else {
                this.postWrong = true;
            }
        } else {
            this.postWrong = true;
        }
    }

    public void validateContent() {
        if (content != null) {
            if (content.length() > 0) {
                this.postWrong = false;
            } else {
                this.postWrong = true;
            }
        } else {
            this.postWrong = true;
        }
    }

    public List<Post> getAllPosts() {
        return allPosts;
    }

    public void setAllPosts(List<Post> allPosts) {
        this.allPosts = allPosts;
    }

    public PostBO getPostBO() {
        return postBO;
    }

    public void setPostBO(PostBO postBO) {
        this.postBO = postBO;
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

    public boolean getPostPublished() {
        return postPublished;
    }

    public void setPostPublished(boolean postPublished) {
        this.postPublished = postPublished;
    }

    public boolean getPostSaved() {
        return postSaved;
    }

    public void setPostSaved(boolean postSaved) {
        this.postSaved = postSaved;
    }

    public boolean getPostWrong() {
        return postWrong;
    }

    public void setPostWrong(boolean postWrong) {
        this.postWrong = postWrong;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

}
