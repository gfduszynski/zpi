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
import javax.faces.bean.SessionScoped;
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.CategoryBO;
import org.chessclan.businessTier.businessObjects.ClubBO;
import org.chessclan.businessTier.businessObjects.TournamentBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.Round.NoPlayers;
import org.chessclan.dataTier.models.Round.NotFinished;
import org.chessclan.dataTier.models.Tournament;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * @author Xcays
 */
@ManagedBean(name = "tdBean")
@SessionScoped
public class TournamentsDashboardBean implements Serializable {

    private List<Tournament> tournaments;
    private Map<Integer, Boolean> checked;
    private Map<Integer, Boolean> editable;
    //form vars
    private Integer id;
    private String name;
    private Date date;
    private String description;
    private Integer club;
    private Integer category;
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
    public void initialize() {
        this.tournaments = new ArrayList<Tournament>();
        this.checked = new HashMap<Integer, Boolean>();
        this.editable = new HashMap<Integer, Boolean>();
        Iterator<Tournament> tnm = tmBO.findAll().iterator();
        int i=1;
        while(tnm.hasNext()){
            tournaments.add(tnm.next());
            checked.put(i, false);
            editable.put(i, false);
            ++i;
        }
    }

    public void generateTournaments()
    {
        UsernamePasswordAuthenticationToken lUAuth = umBO.getLoggedUserAuthentication();
        String lolek = lUAuth.getName();
        umBO.findUserByEmail(lUAuth.getName());
        //try {
            Tournament t = tmBO.registerTournament(10,2,"tname", new Date(), "tDescc", clbBO.findClubById(1), catBO.findCategoryById(1));
            //tmBO.joinTorunament(t);
            //tmBO.joinTournament(t, umBO.findUserById(2));
            //tmBO.goToNextRound(t);
        /*} catch (NotFinished ex) {
            Logger.getLogger(TournamentsDashboardBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoPlayers ex) {
            Logger.getLogger(TournamentsDashboardBean.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    public void removeTournament(Tournament t) {
        
        tmBO.deleteTournament(t);
        initialize();
    }

    public void editTournament(Tournament t) {
        tmBO.saveTournament(t);
        initialize();
    }

    public void updateUser(Tournament t) {
        tmBO.saveTournament(t);
        initialize();
    }

    public void addNewTournament() {
        tmBO.saveTournament(new Tournament(null, "name", new Date(), "description", null));
        initialize();
    }
    
    public void selectAll(){
        for(int i=0;i<tournaments.size();i++){
            if(!checked.get(tournaments.get(i).getId()) || !checked.containsKey(tournaments.get(i).getId())) {
                checked.put(tournaments.get(i).getId(), true);
            }
        }
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

    public Integer getClub() {
        return club;
    }

    public void setClub(Integer club) {
        this.club = club;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
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
}
