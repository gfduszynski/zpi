/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
    private List<Date> postDates;
    @ManagedProperty("#{PostBO}")
    PostBO postBO;
    private Post selectedPost;
    private List<Integer> mapPosts;
    private Integer page;
    private Map<Integer,ArrayList<Integer>> mapToPrevAndNext;


    public PostsViewBean() {
    }

    @PostConstruct
    public void initialize() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String postId = params.get("id");
        this.mapToPrevAndNext = new HashMap<Integer,ArrayList<Integer>>();
        page = 1;

        try {
            int pId = Integer.valueOf(postId);
            this.selectedPost = postBO.findOnePost(pId);
            if (this.selectedPost == null) {
                loadPosts();
            }

        } catch (NumberFormatException e) {
            loadPosts();
        }
        helpMapping();
        loadMapping();
            
    }

    private void loadPosts() {
        this.posts = new ArrayList<Post>();
        this.postDates = new ArrayList<Date>();
        Iterator<Post> posts = postBO.findAllPostsWithUsers().iterator();
        while (posts.hasNext()) {
            Post tmp = posts.next();
            this.posts.add(tmp);
        }
        //Sort posts by date
        Collections.sort(this.posts);
        for(Post p:this.posts){
            if(!postDates.contains(p.getDatePublished())){
                postDates.add(p.getDatePublished());
            }
        }
        
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Date> getPostDates() {
        return postDates;
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
    
    public Map<Integer, ArrayList<Integer>> getMapToPrevAndNext()
    {
        return this.mapToPrevAndNext;
    }
    
    public void setMapToPrevAndNext(Map<Integer, ArrayList<Integer>> tmpmap)
    {
        this.mapToPrevAndNext = tmpmap;
    }
    
    public Integer getPrevFromId(Integer id){
        if(mapToPrevAndNext.get(id)!=null)
        {
            return mapToPrevAndNext.get(id).get(0);
        }
        return -11;
    }
    
    public Integer getNextFromId(Integer id){
        if(mapToPrevAndNext.get(id)!=null)
        {
            return mapToPrevAndNext.get(id).get(1);
        }
        return -1;
    }
    
    
     private void helpMapping() {
        this.mapPosts = new ArrayList<Integer>();
        Iterator<Post> posts = postBO.findAllPostsWithUsers().iterator();
        Integer index = 0;
        while (posts.hasNext()) {
            Post tmp = posts.next();
            tmp.getUser();
            mapPosts.add(tmp.getId());
            ++index;
        }
    }
    
    private void loadMapping()
    {     
        for(int i = 0 ; i <= mapPosts.size()-1 ; i++){
            ArrayList<Integer> altmp = new ArrayList<Integer>();
            if(i == 0){
                altmp.add(-1);
            } else {altmp.add(mapPosts.get(i-1));}
            if (i == mapPosts.size()-1){
                altmp.add(-1);
            } else{{altmp.add(mapPosts.get(i+1));}}
            this.mapToPrevAndNext.put(mapPosts.get(i), altmp);
        }
    }
}
