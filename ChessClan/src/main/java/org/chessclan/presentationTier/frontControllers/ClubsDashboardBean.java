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
import javax.faces.bean.ViewScoped;
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.ClubBO;
import org.chessclan.dataTier.models.Club;

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
    
    //form vars
    private Integer id;
    private String name;
    private Date creationDate;
    private String description;
    //end
    @Transient
    @ManagedProperty("#{ClubBO}")
    private ClubBO clBO;

    public ClubsDashboardBean() {
}
    
    @PostConstruct
    public void initialize() {
        this.clubs = new ArrayList<Club>();
        this.checked = new HashMap<Integer, Boolean>();
        this.editable = new HashMap<Integer, Boolean>();
        Iterator<Club> clb = clBO.findAll().iterator();
        int i=1;
        while(clb.hasNext()){
            clubs.add(clb.next());
            checked.put(i, false);
            editable.put(i, false);
            ++i;
        }
    }


    public void removeClub(Club club) {
        clBO.deleteClub(club);
    }

    public void editClub(Club club) {
        clBO.saveClub(club);
        }

    public void updateClub(Club club) {
        clBO.saveClub(club);
    }
    
    public void selectAll(){
        for(int i=0;i<clubs.size();i++){
            if(!checked.get(clubs.get(i).getId()) || !checked.containsKey(clubs.get(i).getId())) {
                checked.put(clubs.get(i).getId(), true);
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


    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubss) {
        this.clubs = clubss;
    }

    public Integer getClubId() {
        return id;
    }

    public void setClubId(Integer id) {
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

     public void addNewClub() {
        createNewClub = true;
        newclub = new Club();
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
    
    public void removeSelected()
    {
        if(deletable){
            for(int i=0;i<clubs.size();i++){
                if(checked.get(clubs.get(i).getId())) {
                    editable.remove(clubs.get(i).getId());
                    checked.remove(clubs.get(i).getId());
                    clBO.deleteClub(clubs.get(i));
                    clubs.remove(i);
                    --i;
                }
            }
            deletable = false;
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
       
    public void saveSelected()
    {
        for(int i=0;i<clubs.size();i++){
            if(checked.get(clubs.get(i).getId())) {
                editable.put(clubs.get(i).getId(),false);
                checked.put(clubs.get(i).getId(), false);
                clBO.saveClub(clubs.get(i));
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
        clBO.saveClub(newclub);
        clubs.add(newclub);
        editable.put(newclub.getId(), false);
        checked.put(newclub.getId(), false);
        createNewClub = false;
        clubs.add(newclub);
    }
    
    public void cancelNewClub() {
        createNewClub = false;
        newclub = null;
    }
}
