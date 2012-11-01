/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.chessclan.businessTier.businessObjects.PostBO;
import org.chessclan.dataTier.models.Post;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "pvBean")
@RequestScoped
public class PostsViewBean implements Serializable {

    private List<Post> posts;
    @ManagedProperty("#{PostBO}")
    PostBO postBO;
    private Post selectedPost;

    public PostsViewBean() {
    }

    @PostConstruct
    public void initialize() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String postId = params.get("id");

        try {
            int pId = Integer.valueOf(postId);
            this.selectedPost = postBO.findOnePost(pId);
            if (this.selectedPost == null) {
                loadPosts();
            }

        } catch (NumberFormatException e) {
            loadPosts();
        }

    }

    private void loadPosts() {
        this.posts = new ArrayList<Post>();
        Iterator<Post> postss = postBO.findAllPostsWithUsers().iterator();
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

    public Post getSelectedPost() {
        return selectedPost;
    }

    public void setSelectedPost(Post selectedPost) {
        this.selectedPost = selectedPost;
    }
}
