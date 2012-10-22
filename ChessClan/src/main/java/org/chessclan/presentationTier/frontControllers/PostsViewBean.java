/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.ArrayList;
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
@ManagedBean(name="pvBean")
@ViewScoped
public class PostsViewBean implements Serializable {

    private List<Post> posts;
    @ManagedProperty("#{PostBO}")
    PostBO postBO;

    public PostsViewBean() {
    }

    @PostConstruct
    public void initialize() {
        this.posts = new ArrayList<Post>();
        Iterator<Post> postss = postBO.findAllPosts().iterator();
        while (postss.hasNext()) {
            this.posts.add(postss.next());
        }
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public PostBO getPostBO() {
        return postBO;
    }

    public void setPostBO(PostBO postBO) {
        this.postBO = postBO;
    }
}
