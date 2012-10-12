/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

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
@ManagedBean(name="lpBean")
@ViewScoped
public class LatestPostsBean {

    private List<Post> latestPosts;
    @ManagedProperty("#{PostBO}")
    PostBO postBO;

    public LatestPostsBean() {
    }

    @PostConstruct
    public void initialize() {
        this.latestPosts = new ArrayList<Post>();
        Iterator<Post> posts = postBO.findAllPosts().iterator();
        while(posts.hasNext()){
            latestPosts.add(posts.next());
        }
    }

    public List<Post> getLatestPosts() {
        return latestPosts;
    }

    public void setLatestPosts(List<Post> latestPosts) {
        this.latestPosts = latestPosts;
    }

    public PostBO getPostBO() {
        return postBO;
    }

    public void setPostBO(PostBO postBO) {
        this.postBO = postBO;
    }
}
