/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.ArrayList;
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
@ManagedBean(name = "lpBean")
@ViewScoped
public class LatestPostsBean implements Serializable {

    private List<List<Post>> carousel;
    private List<Post> latestPosts;
    @ManagedProperty("#{PostBO}")
    PostBO postBO;

    public LatestPostsBean() {
    }

    @PostConstruct
    public void initialize() {
        carousel = new ArrayList<List<Post>>();
        this.latestPosts = postBO.findLatestPublishedPosts(12);
        if (this.latestPosts.size() >= 12) {
            for (int i = 0; i < 4; i++) {
                ArrayList<Post> tmp = new ArrayList<Post>();
                tmp.add(latestPosts.get(i * 3));
                tmp.add(latestPosts.get(i * 3 + 1));
                tmp.add(latestPosts.get(i * 3 + 2));
                carousel.add(tmp);
            }
        } else if (this.latestPosts.size() >= 9) {
            for (int i = 0; i < 3; i++) {
                ArrayList<Post> tmp = new ArrayList<Post>();
                tmp.add(latestPosts.get(i * 3));
                tmp.add(latestPosts.get(i * 3 + 1));
                tmp.add(latestPosts.get(i * 3 + 2));
                carousel.add(tmp);
            }
        } else if (this.latestPosts.size() >= 6) {
            for (int i = 0; i < 2; i++) {
                ArrayList<Post> tmp = new ArrayList<Post>();
                tmp.add(latestPosts.get(i * 3));
                tmp.add(latestPosts.get(i * 3 + 1));
                tmp.add(latestPosts.get(i * 3 + 2));
                carousel.add(tmp);
            }
        } else {
            ArrayList<Post> tmp = new ArrayList<Post>();
            for (int i = 0; i < (this.latestPosts.size()>3?3:this.latestPosts.size()); i++) {
                tmp.add(latestPosts.get(i));
            }
            carousel.add(tmp);
        }
    }

    public PostBO getPostBO() {
        return postBO;
    }

    public void setPostBO(PostBO postBO) {
        this.postBO = postBO;
    }

    public List<List<Post>> getCarousel() {
        return carousel;
    }

    public void setCarousel(List<List<Post>> carousel) {
        this.carousel = carousel;
    }
}
