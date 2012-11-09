package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.PostBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.Post;

/**
 *
 * @author Xcays
 */
@ManagedBean(name = "pdBean")
@ViewScoped
public class PostsDashboardBean implements Serializable {

    private List<Post> posts;
    private Map<Integer, Boolean> checked;
    private Map<Integer, Boolean> editable;
    private Boolean createNewPost;
    private Boolean deletable;
    private Boolean checkAll;
    private Boolean hasChecked;
    private Post newpost;
    private String authorEmail;
    private Integer newExpiresAfter;
    private Integer expiresAfter;
    private List<Boolean> npvalidation;
    private Map<Integer, List<Boolean>> validation;
    //form vars
    private Integer id;
    private String title;
    private String content;
    private Date dateCreated, datePublished, dateExpires;
    private Integer user;
    //end
    //@Transient
    @ManagedProperty("#{PostBO}")
    private PostBO poBO;
    @Transient
    @ManagedProperty("#{UserManagementBO}")
    private UserManagementBO umBO;

    public PostsDashboardBean() {
    }

    @PostConstruct
    public void initialize() {
        this.validation = new HashMap<Integer, List<Boolean>>();
        this.posts = new ArrayList<Post>();
        this.checked = new HashMap<Integer, Boolean>();
        this.editable = new HashMap<Integer, Boolean>();
        Iterator<Post> pst = poBO.findAllPostsWithUsers().iterator();
        while (pst.hasNext()) {
            Post tmp = pst.next();
            posts.add(tmp);
            checked.put(tmp.getId(), false);
            editable.put(tmp.getId(), false);
            validation.put(tmp.getId(), Arrays.asList(true, true, false));
        }
        this.deletable = false;
        this.checkAll = false;
        this.hasChecked = false;
        this.newExpiresAfter = 7;
        this.expiresAfter = 7;
    }

    public Boolean validateTitle(Post p, List<Boolean> l) {
        if (p.getTitle() != null) {
            if (p.getTitle().length() > 2) {
                l.set(0, true);
                return true;
            } else {
                l.set(0, false);
                return false;
            }
        }
        l.set(0, false);
        return false;
    }

    public Boolean validateContent(Post p, List<Boolean> l) {
        if (p.getTitle() != null) {
            if (p.getContent().length() > 29) {
                l.set(1, true);
                return true;
            } else {
                l.set(1, false);
                return false;
            }
        }
        l.set(1, false);
        return false;
    }

    public boolean validateHasNotErrors(Post p, List<Boolean> l) {
        if (validateTitle(p, l) && validateContent(p, l)) {
            l.set(2, true);
            return true;
        }
        l.set(2, false);
        return false;
    }

    public void updatePost(Post post) {
        if(validateHasNotErrors(post, validation.get(post.getId()))){
        poBO.savePost(post);
        editable.put(post.getId(), false);
        checked.put(post.getId(), false);
        posts.set(posts.indexOf(post), post);
        }
    }

    public void publishPost(Post post) {
        if(validateHasNotErrors(post, validation.get(post.getId()))){
        Calendar cal = Calendar.getInstance();
        post.setDatePublished(cal.getTime());
        cal.add(Calendar.DATE, expiresAfter);
        post.setDateExpires(cal.getTime());
        poBO.savePost(post);
        editable.put(post.getId(), false);
        checked.put(post.getId(), false);
        }
    }

    public void cancelNewPost() {
        createNewPost = false;
        newpost = null;
    }

    public void selectAll() {
        checkAll = !checkAll;
        for (int i = 0; i < posts.size(); i++) {
            checked.put(posts.get(i).getId(), checkAll);
        }
        if (!checkAll) {
            for (int i = 0; i < posts.size(); i++) {
                editable.put(posts.get(i).getId(), false);
            }
        }
        deletable = checkAll;
    }

    public void changeCheckedOne(int id) {
        if (!checked.get(id)) {
            checked.put(id, false);
        } else {
            checked.put(id, true);
        }
    }

    public Map<Integer, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Integer, Boolean> checked) {
        this.checked = checked;
    }

    public void selectOneEditable(int id) {
        editable.put(id, true);
    }

    public void deselectOneEditable(int id) {
        posts.set(posts.indexOf(poBO.findOnePost(id)), poBO.findOnePost(id));
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public void deletePost(Post post) {
        poBO.deletePost(post);
        editable.remove(post.getId());
        checked.remove(post.getId());
        posts.remove(post);

        System.out.println("CURRENT POST REMOVED");
    }

    public void setEditableForSelected() {
        for (int i = 0; i < posts.size(); i++) {
            if (checked.get(posts.get(i).getId())) {
                editable.put(posts.get(i).getId(), true);
                deletable = true;
            }
        }
    }

    public void deleteSelected() {
        System.out.println("STARTING REMOVE SELECTED METHOD");
        if (deletable) {
            for (int i = 0; i < posts.size(); i++) {
                if (checked.get(posts.get(i).getId())) {
                    deletePost(posts.get(i));
                    --i;
                }
            }
            deletable = false;
        }
    }

    public void saveSelected() {
        for (int i = 0; i < posts.size(); i++) {
            if(validateHasNotErrors(posts.get(i), validation.get(posts.get(i).getId()))){
            if (checked.get(posts.get(i).getId())) {
                editable.put(posts.get(i).getId(), false);
                checked.put(posts.get(i).getId(), false);
                poBO.savePost(posts.get(i));
            }
            }
        }
        deletable = false;
    }

    public Boolean getDeletable() {
        return deletable;
    }

    public void setDeletable(Boolean deletable) {
        this.deletable = deletable;
    }

    public void saveNewPost() {
        if (validateHasNotErrors(newpost, npvalidation)) {
            poBO.savePost(newpost);
            editable.put(newpost.getId(), false);
            checked.put(newpost.getId(), false);
            createNewPost = false;
            posts.add(newpost);
        }
    }

    public void publishNewPost() {
        if (validateHasNotErrors(newpost, npvalidation)) {
            Calendar cal = Calendar.getInstance();
            newpost.setDatePublished(cal.getTime());
            cal.add(Calendar.DATE, newExpiresAfter);
            newpost.setDateExpires(cal.getTime());
            poBO.savePost(newpost);
            editable.put(newpost.getId(), false);
            checked.put(newpost.getId(), false);
            createNewPost = false;
            posts.add(newpost);
        }
    }

    public void addNewPost() {
        newpost = new Post();
        newpost.setTitle("");
        newpost.setContent("");
        newpost.setDateCreated(Calendar.getInstance().getTime());
        newpost.setDatePublished(null);
        newpost.setDateExpires(null);
        newpost.setUser(umBO.getLoggedUser());
        npvalidation = new ArrayList<Boolean>();
        npvalidation = Arrays.asList(true, true, true, false);
        createNewPost = true;
    }

    public Boolean getCreateNewPost() {
        return createNewPost;
    }

    public void setCreateNewPost(Boolean cnp) {
        this.createNewPost = cnp;
    }

    public Post getNewpost() {
        return newpost;
    }

    public void setNewpost(Post newpost) {
        this.newpost = newpost;
    }

    public Integer getNewExpiresAfter() {
        return newExpiresAfter;
    }

    public void setNewExpiresAfter(Integer newExpiresAfter) {
        this.newExpiresAfter = newExpiresAfter;
    }

    public Integer getExpiresAfter() {
        return expiresAfter;
    }

    public void setExpiresAfter(Integer expiresAfter) {
        this.expiresAfter = expiresAfter;
    }

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }

    public PostBO getPoBO() {
        return poBO;
    }

    public void setPoBO(PostBO poBO) {
        this.poBO = poBO;
    }

    public List<Boolean> getNpvalidation() {
        return this.npvalidation;
    }

    public void setNpvalidation(List<Boolean> npvalidation) {
        this.npvalidation = npvalidation;
    }

    public Map<Integer, List<Boolean>> getValidation() {
        return this.validation;
    }

    public void setValidation(Map<Integer, List<Boolean>> validation) {
        this.validation = validation;
    }

    public List<Boolean> getPostValidation(Integer id) {
        return this.validation.get(id);
    }

    public void setPostValidation(List<Boolean> npvalidation) {
        this.validation.put(id, npvalidation);
    }
       
    public Boolean getHasChecked()
    {
        for(int i=0;i<posts.size();i++){
            if(checked.get(posts.get(i).getId())) { return this.hasChecked = true;
                }
        }
        return this.hasChecked=false;
    }
    
    public void setHasChecked(Boolean hasChecked)
    {
        this.hasChecked = hasChecked;
    }
}
