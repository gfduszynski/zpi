package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.ClubBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Xcays
 */
@ManagedBean(name = "cdBean")
@ViewScoped
public class ClubsDashboardBean implements Serializable {

    private List<Club> clubs;
    private Map<Integer, Boolean> checked;
    private Map<Integer, Boolean> editable;
    private Club newclub;
    private Boolean createNewClub;
    private Boolean deletable;
    private Boolean checkAll;
    private Boolean hasChecked;
    private String ownerEmail;
    private List<Boolean> ncvalidation;
    private Map<Integer, List<Boolean>> validation;
    private ArrayList<SelectItem> ownerList;
    @ManagedProperty("#{UserManagementBO}")
    private UserManagementBO usBO;
    //form vars
    private Integer id;
    private String name;
    private Date creationDate;
    private String description;
    private User owner;
    //end
    @Transient
    @ManagedProperty("#{ClubBO}")
    private ClubBO clBO;

    public ClubsDashboardBean() {
}
    
    @PostConstruct
    public void initialize() {
        this.validation = new HashMap<Integer, List<Boolean>>();
        this.clubs = new ArrayList<Club>();
        this.checked = new HashMap<Integer, Boolean>();
        this.editable = new HashMap<Integer, Boolean>();
        Iterator<Club> clb = clBO.findAllWithOwners().iterator();
        while(clb.hasNext()){
            Club tmp = clb.next();
            clubs.add(tmp);
            checked.put(tmp.getId(), false);
            editable.put(tmp.getId(), false);
            validation.put(tmp.getId(), Arrays.asList(true, true, true, false));
        }
        this.checkAll = false;
        this.deletable = false; 
        this.hasChecked = false;
        getOwnerList();
    }
    
    public Boolean validateName(Club cl, List<Boolean> l)
    {    
        if (cl.getName() != null) {
            if (cl.getName().length() > 0) {
                l.set(0, true);
                return true;
            } else {
                l.set(0, false);
                return false;
            }
        } else {
            l.set(0, false);
            return false;
        }
    }
    
    
    public Boolean validateCreationDate(Club cl, List<Boolean> l)
    {    
        if (cl.getCreationDate() != null) {
            l.set(1, true);
            return true;
        } else {
            l.set(1, false);
            return false;
        }
    }
    
    public boolean validateHasNotErrors(Club c, List<Boolean> l)
    {
        if(validateName(c,l)&&validateCreationDate(c,l))
        {
            l.set(2, true);
            return true;
        }
        l.set(2, false);
        return false;
    }
    
     public ArrayList<SelectItem> getOwnerList()
      {
         ownerList = new ArrayList<SelectItem>();
         Iterable<User> user =  usBO.findAll();
         Iterator<User> iterator = user.iterator();
           while(iterator.hasNext()) {
            User u = iterator.next();
            ownerList.add(new SelectItem(u.getEmail(), u.getEmail()));
            }
         return ownerList;
     }
    
    public void removeClub(Club club) {
        clBO.deleteClub(club);
        editable.remove(club.getId());
        checked.remove(club.getId());
        clubs.remove(club);
    }
    
    public void removeSelected()
    {
        if(deletable){
            for(int i=0;i<clubs.size();i++){
                if(checked.get(clubs.get(i).getId())) {
                   removeClub(clubs.get(i));
                    --i;
                }
            }
            deletable = false;
        }
    }

    public void selectAll(){
        checkAll = !checkAll;
        for(int i=0;i<clubs.size();i++){
                checked.put(clubs.get(i).getId(), checkAll);
        }
        if(!checkAll){
            for(int i=0;i<clubs.size();i++){
                editable.put(clubs.get(i).getId(), false);
            }
        }
        deletable = checkAll;
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
    
    public void refreshList(){
        clubs.clear();
        Iterator<Club> clb = clBO.findAllWithOwners().iterator();
        while(clb.hasNext()){
            clubs.add(clb.next());}
    }
    
    public void deselectOneEditable(int id){
        refreshList();
        editable.put(id, false);
    }
    
    public Map<Integer, Boolean> getEditable() {
        return editable;
    }
    public void setOwnerEmail(String ownerEmail){
        this.ownerEmail=ownerEmail;
    }
    
    public String getOwnerEmail() {
        return this.ownerEmail;
    }

    public void setEditable(Map<Integer, Boolean> editable) {
        this.editable = editable;
    }


    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubss) {
        this.clubs = clubss;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public ClubBO getClBO() {
        return clBO;
    }

    public void setClBO(ClubBO clBO) {
        this.clBO = clBO;
    } 
    
    public UserManagementBO getUsBO() {
        return usBO;
    }

    public void setUsBO(UserManagementBO usBO) {
        this.usBO = usBO;
    }    
     
    public void setEditableForSelected()
    {        
        for(int i=0;i<clubs.size();i++){
            if(checked.get(clubs.get(i).getId())) {
                editable.put(clubs.get(i).getId(), true);
                deletable = true;
            }
        }
    }
    
    public Boolean getDeletable()
    {
        return deletable;
    }
    
    public void setDeletable(Boolean deletable)
    {
        this.deletable = deletable;
    }
   
    public User getOwner()
    {
        return owner;
    }
    
    public void setOwner(User owner)
    {
        this.owner = owner;
    }
    
    public void saveSelected()
    {
        for(int i=0;i<clubs.size();i++){
            if(validateHasNotErrors(clubs.get(i), validation.get(clubs.get(i).getId()))){
            if(checked.get(clubs.get(i).getId())) {
                editable.put(clubs.get(i).getId(),false);
                checked.put(clubs.get(i).getId(), false);
                clBO.saveClub(clubs.get(i));
            }
            }
        }
        deletable = false;
    }
    
    
    public Boolean getCreateNewClub()
    {
        return createNewClub;
    }
    
    public void setCreateNewClub(Boolean cnu)
    {
        this.createNewClub = cnu;
    }
    
    public Club getNewclub()
    {
        return newclub;
    }
    
    public void setNewclub(Club c)
    {
        this.newclub = c;
    }
    
    public void saveNewClub() {
           if(validateHasNotErrors(newclub,ncvalidation)){
        newclub.setOwner(usBO.findUserByEmail(ownerEmail));
        clBO.saveClub(newclub);
        clubs.add(newclub);
        editable.put(newclub.getId(), false);
        checked.put(newclub.getId(), false);
        createNewClub = false;
        
                }
    }
    
    public void updateClub(Club club) {
        clBO.saveClub(club);
        //clubs.add(newclub);
        editable.put(club.getId(), false);
        checked.put(club.getId(), false);
    }
    
    public void cancelNewClub() {
        createNewClub = false;
        newclub = null;
    }
    
    public void addNewClub() {
        newclub = new Club("Club name", new Date(), usBO.getLoggedUser(), " Club description");
        ncvalidation = new ArrayList<Boolean>();
        ncvalidation = Arrays.asList(true, true, true, false);
        createNewClub = true;
    }
    
    public Boolean getHasChecked()
    {
        for(int i=0;i<clubs.size();i++){
            if(checked.get(clubs.get(i).getId())) { return this.hasChecked = true;
                }
        }
        return this.hasChecked=false;
    }
    
    public void setHasChecked(Boolean hasChecked)
    {
        this.hasChecked = hasChecked;
    }
    
    public List<Boolean> getNcvalidation()
    {
        return this.ncvalidation;
    }
    
    public void setNcvalidation(List<Boolean> ncvalidation)
    {
        this.ncvalidation = ncvalidation;
    }
    
    public Map<Integer, List<Boolean>> getValidation()
    {
        return this.validation;
    }
    
    public void setValidation(Map<Integer, List<Boolean>> validation)
    {
        this.validation = validation;
    }

    public List<Boolean> getClubValidation(Integer id)
    {
        return this.validation.get(id);
    }
    
    public void setClubValidation(Integer id, List<Boolean> l)
    {
        this.validation.put(id,l);
    }
}
