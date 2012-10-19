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
import org.chessclan.businessTier.businessObjects.ClubBO;
import org.chessclan.dataTier.models.Club;

/**
 *
 * @author Xcays
 */
@ManagedBean(name = "cdBean")
@SessionScoped
public class ClubsDashboardBean implements Serializable {

    private List<Club> clubs;
    private Map<Integer, Boolean> checked;
    private Map<Integer, Boolean> editable;
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
        initialize();
    }

    public void editClub(Club club) {
        clBO.saveClub(club);
        initialize();
        }

    public void updateClub(Club club) {
        clBO.saveClub(club);
        initialize();
    }

    public void addNewClub() {
        clBO.saveClub(new Club(clubs.size()+1,"clubName",new Date()));
        initialize();
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
}
