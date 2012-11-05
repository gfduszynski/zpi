/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.CategoryBO;
import org.chessclan.businessTier.businessObjects.ClubBO;
import org.chessclan.businessTier.businessObjects.TournamentBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.Category;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.Round;
import org.chessclan.dataTier.models.Round.NoPlayers;
import org.chessclan.dataTier.models.Round.NotFinished;
import org.chessclan.dataTier.models.Tournament;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Xcays
 */
@ManagedBean(name = "tdBean")
@ViewScoped
public class TournamentsDashboardBean implements Serializable {

    private List<Tournament> tournaments;
    private Map<Integer, Boolean> checked;
    private Map<Integer, Boolean> editable;
    private Boolean createNewTournament;
    private Boolean deletable;
    private Boolean checkAll;
    private Tournament newtournament;
    //form vars
    private Integer id;
    private String name;
    private Date date;
    private String description;
    private Club club;
    private Category category;
    private Round currentRound;
    //end
    @Transient
    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmBO;
    
    @Transient
    @ManagedProperty("#{ClubBO}")
    private ClubBO clbBO;
    
    @Transient
    @ManagedProperty("#{CategoryBO}")
    private CategoryBO catBO;
    
    @Transient
    @ManagedProperty("#{UserManagementBO}")
    private UserManagementBO umBO;

    public TournamentsDashboardBean() {

    }
    
    @PostConstruct
    @Transactional(propagation= Propagation.MANDATORY)
    public void initialize() {
        this.tournaments = new ArrayList<Tournament>();
        this.checked = new HashMap<Integer, Boolean>();
        this.editable = new HashMap<Integer, Boolean>();
        Iterator<Tournament> tnm = tmBO.findAll().iterator();
        while(tnm.hasNext()){
            Tournament tmp = tnm.next();
            tournaments.add(tmp);
            checked.put(tmp.getId(), false);
            editable.put(tmp.getId(), false);
        }
        this.checkAll = false;
        this.deletable = false;
    }

    public void generateTournaments() throws Round.NotJoinableRound
    {
        UsernamePasswordAuthenticationToken lUAuth = umBO.getLoggedUserAuthentication();
        String lolek = lUAuth.getName();
        umBO.findUserByEmail(lUAuth.getName());
        try {
            Tournament t = tmBO.registerTournament(10,2,"tname", new Date(), "tDescc", clbBO.findClubById(1), catBO.findCategoryById(1));
            tmBO.joinTorunament(t);
            tmBO.joinTournament(t, umBO.findUserById(2));
            tmBO.goToNextRound(t);
        } catch (NotFinished ex) {
            Logger.getLogger(TournamentsDashboardBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoPlayers ex) {
            Logger.getLogger(TournamentsDashboardBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeTournament(Tournament tnm) {
        tmBO.deleteTournament(tnm);
        editable.remove(tnm.getId());
        checked.remove(tnm.getId());
        tournaments.remove(tnm);
    }

    public void updateTournament(Tournament tnm) {
        System.out.println("TNM update start");
        tmBO.saveTournament(tnm);
        System.out.println("TNM saved");
        editable.put(tnm.getId(), false);
        System.out.println("TNM editable false");
        checked.put(tnm.getId(), false);
        System.out.println("TNM checked false");
        tournaments.set(tournaments.indexOf(tnm), tnm);
        System.out.println("TNMs update success");
    }

    public void saveNewTournament() {
        newtournament.setClub(clbBO.findClubByName(newtournament.getClub().getName()));
        newtournament.setCategory(catBO.findCategoryById(newtournament.getCategory().getId()));
        tmBO.saveTournament(newtournament);
        editable.put(newtournament.getId(), false);
        checked.put(newtournament.getId(), false);
        createNewTournament = false;
        tournaments.add(newtournament);
    }
    
    public void addNewTournament() {
        newtournament = new Tournament(null, "name", new Date(), "description", null);
        createNewTournament = true;
    }
    
    public void cancelNewTournament() {
        createNewTournament = false;
        newtournament = null;
    }
    

    public void selectAll(){
        checkAll = !checkAll;
        for(int i=0;i<tournaments.size();i++){
                checked.put(tournaments.get(i).getId(), checkAll);
        }
        if(!checkAll){
            for(int i=0;i<tournaments.size();i++){
                editable.put(tournaments.get(i).getId(), false);
            }
        }
        deletable = checkAll;
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
    
    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public Integer getTournamentId() {
        return id;
    }

    public void setTournamentId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public TournamentBO getTmBO() {
        return tmBO;
    }

    public void setTmBO(TournamentBO tmBO) {
        this.tmBO = tmBO;
    }    
    
    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }
        public CategoryBO getCatBO() {
        return catBO;
    }

    public void setCatBO(CategoryBO catBO) {
        this.catBO = catBO;
    }

    public ClubBO getClbBO() {
        return clbBO;
    }

    public void setClbBO(ClubBO clbBO) {
        this.clbBO = clbBO;
    }
    
    public void setEditableForSelected()
    {        
        for(int i=0;i<tournaments.size();i++){
            if(checked.get(tournaments.get(i).getId())) {
                editable.put(tournaments.get(i).getId(), true);
                deletable = true;
            }
        }
    }
    
    public void removeSelected()
    {
        if(deletable){
            for(int i=0;i<tournaments.size();i++){
                if(checked.get(tournaments.get(i).getId())) {
                   removeTournament(tournaments.get(i));
                    --i;
                }
            }
            deletable = false;
        }
    }
        
    public void saveSelected()
    {
        for(int i=0;i<tournaments.size();i++){
            if(checked.get(tournaments.get(i).getId())) {
                editable.put(tournaments.get(i).getId(),false);
                checked.put(tournaments.get(i).getId(), false);
                tmBO.saveTournament(tournaments.get(i));
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
    
    public Boolean getCreateNewTournament()
    {
        return createNewTournament;
    }
    
    public void setCreateNewTournament(Boolean cnt)
    {
        this.createNewTournament = cnt;
    }
    
    public Tournament getNewtournament()
    {
        return newtournament;
    }
    
    public void setNewtournament(Tournament t)
    {
        this.newtournament = t;
    }
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public Round getCurrentRound()
    {
        return currentRound;
    }
    
    public void setCurrentRound(Round currentRound)
    {
        this.currentRound = currentRound;
    }
    
    public void changeCheckedOne(int id){
           if(!checked.get(id)){checked.put(id, false);
           }else{checked.put(id, true);}
    }
    
    
}
