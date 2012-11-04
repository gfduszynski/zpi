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
import org.chessclan.businessTier.businessObjects.UserManagementBO;
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
    private Boolean createNewPost;
    private Boolean deletable;
    private Post newpost;
    private String authorEmail;
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
    @Transient
    @ManagedProperty("#{UserManagementBO}")
    private UserManagementBO umBO;

    public PostsDashboardBean() {
}
    
    @PostConstruct
    public void initialize() {
        this.posts = new ArrayList<Post>();
        this.checked = new HashMap<Integer, Boolean>();
        this.editable = new HashMap<Integer, Boolean>();
        Iterator<Post> pst = poBO.findAllPostsWithUsers().iterator();
        while(pst.hasNext()){            
            Post tmp = pst.next();
            posts.add(tmp);
            checked.put(tmp.getId(), false);
            editable.put(tmp.getId(), false);
        }
        this.createNewPost = false;
        this.deletable = false;
    }


    public void removePost(Post post) {
        poBO.deletePost(post);
        editable.remove(post.getId());
        checked.remove(post.getId());
        posts.remove(post);
    }

    public void updatePost(Post post) 
    {
        poBO.savePost(post);
        editable.put(post.getId(), false);
        checked.put(post.getId(), false);
        posts.set(posts.indexOf(post), post);
    }
    
    public void saveNewPost() {
        newpost.setUser(umBO.findUserByEmail(authorEmail));
        poBO.savePost(newpost);
        editable.put(newpost.getId(), false);
        checked.put(newpost.getId(), false);
        createNewPost = false;
        posts.add(newpost);
    }

    public void addNewPost() {
        newpost = new Post(posts.size()+1, "Post name", "Post content", false, new Date());
        newpost.setUser(umBO.getLoggedUser());
        createNewPost = true;
    }
    
    public void cancelNewPost() {
        createNewPost = false;
        newpost = null;
    }
    
    public void selectAll(){
        for(int i=0;i<posts.size();i++){
            if(!checked.get(posts.get(i).getId()) || !checked.containsKey(posts.get(i).getId())) {
                checked.put(posts.get(i).getId(), true);
            }
        }
        deletable = true;
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
    
    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
    
    
    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
    
    public PostBO getPoBO() {
        return poBO;
    }

    public void setPoBO(PostBO poBO) {
        this.poBO = poBO;
    }
    
    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }
    
    public Boolean getCreateNewPost()
    {
        return createNewPost;
    }
    
    public void setCreateNewPost(Boolean cnp)
    {
        this.createNewPost = cnp;
    }
    
    public Post getNewpost()
    {
        return newpost;
    }
    
    public void setNewpost(Post newpost)
    {
        this.newpost = newpost;
    }
    
    public String getAuthorEmail()
    {
        return authorEmail;
    }
    
    public void setAuthorEmail(String authorEmail)
    {
        this.authorEmail = authorEmail;
    }

    
    public void setEditableForSelected()
    {        
        for(int i=0;i<posts.size();i++){
            if(checked.get(posts.get(i).getId())) {
                editable.put(posts.get(i).getId(), true);
                deletable = true;
            }
        }
    }
    
    public void removeSelected()
    {
        if(deletable){
            for(int i=0;i<posts.size();i++){
                if(checked.get(posts.get(i).getId())) {
                   removePost(posts.get(i));
                    --i;
                }
            }
            deletable = false;
        }
    }
        
    public void saveSelected()
    {
        for(int i=0;i<posts.size();i++){
            if(checked.get(posts.get(i).getId())) {
                editable.put(posts.get(i).getId(),false);
                checked.put(posts.get(i).getId(), false);
                poBO.savePost(posts.get(i));
            }
        }
        deletable = false;
    }

    public Boolean getDeletable()
    {
        return deletable;
    }
    
    public void setDeletable(Boolean deletable)
    {
        this.deletable = deletable;
    }

}
