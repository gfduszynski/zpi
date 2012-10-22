/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.PostBO;
import org.chessclan.dataTier.models.Post;

/**
 *
 * @author Xcays
 */
@ManagedBean(name = "pdBean")
@SessionScoped
public class PostsDashboardBean implements Serializable {

    private List<Post> posts;
    private Map<Integer, Boolean> checked;
    private Map<Integer, Boolean> editable;
    //form vars
    private Integer id;
    private String title;
    private String content;
    private Boolean published;
    private Date dateCreated, datePublished, dateExpires;
    private Integer user;
    //end
    @Transient
    @ManagedProperty("#{PostBO}")
    private PostBO poBO;

    public PostsDashboardBean() {
}
    
    @PostConstruct
    public void initialize() {
        this.posts = new ArrayList<Post>();
        this.checked = new HashMap<Integer, Boolean>();
        this.editable = new HashMap<Integer, Boolean>();
        Iterator<Post> pst = poBO.findAllPosts().iterator();
        int i=1;
        while(pst.hasNext()){
            posts.add(pst.next());
            checked.put(i, false);
            editable.put(i, false);
            ++i;
        }
    }


    public void removePost(Post post) {
        poBO.deletePost(post);
        initialize();
    }

    public void editPost(Post post) {
        poBO.savePost(post);
        initialize();
        }

    public void updatePost(Post post) {
        poBO.savePost(post);
        initialize();
    }

    public void addNewPost() {
        poBO.savePost(new Post(posts.size()+1, "Post name", "Post content", false, new Date()));
        initialize();
        }
    
    public void selectAll(){
        for(int i=0;i<posts.size();i++){
            if(!checked.get(posts.get(i).getId()) || !checked.containsKey(posts.get(i).getId())) {
                checked.put(posts.get(i).getId(), true);
            }
        }
    }

    public void changeCheckedOne(int id){
           if(!checked.get(id)){checked.put(id, false);
           }else{checked.put(id, true);}
    }

    public Map<Integer, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Integer, Boolean> checked) {
        this.checked = checked;
    }
    
    public void selectOneEditable(int id){
           editable.put(id, true);
    }
    
    public void deselectOneEditable(int id){
           editable.put(id, false);
    }
    
    public Map<Integer, Boolean> getEditable() {
        return editable;
    }

    public void setEditable(Map<Integer, Boolean> editable) {
        this.editable = editable;
    }


    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Integer getPostId() {
        return id;
    }

    public void setPostId(Integer id) {
        this.id = id;
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
    
    public Date getCreationDate() {
        return dateCreated;
    }

    public void setCreationDate(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public Date getPublishedDate() {
        return datePublished;
    }

    public void setPublishedDate(Date datePublished) {
        this.datePublished = datePublished;
    }
    
    public Date getExpiresDate() {
        return dateExpires;
    }

    public void setExpiresDate(Date dateExpires) {
        this.dateExpires = dateExpires;
    }
    
    public PostBO getPoBO() {
        return poBO;
    }

    public void setPoBO(PostBO poBO) {
        this.poBO = poBO;
    }    
}
