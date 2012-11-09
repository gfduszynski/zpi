package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
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
    private Boolean hasChecked;
    private List<Boolean> ntvalidation;
    private Map<Integer, List<Boolean>> validation;
    private ArrayList<SelectItem> categoryList;
    private ArrayList<SelectItem> clubList;
    private ArrayList<SelectItem> roundsList;
    private ArrayList<SelectItem> pointsList;
    //form vars
    private Integer id;
    private String name;
    private Date date;
    private String description;
    private Category category;
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
        this.validation = new HashMap<Integer, List<Boolean>>();
        this.tournaments = new ArrayList<Tournament>();
        this.checked = new HashMap<Integer, Boolean>();
        this.editable = new HashMap<Integer, Boolean>();
        Iterator<Tournament> tnm = tmBO.findAll().iterator();
        while(tnm.hasNext()){
            Tournament tmp = tnm.next();
            tournaments.add(tmp);
            checked.put(tmp.getId(), false);
            editable.put(tmp.getId(), false);
            validation.put(tmp.getId(), Arrays.asList(true, true, true, false));
        
        }
        this.checkAll = false;
        this.deletable = false;
        this.hasChecked = false;
        getCategoryList();
        getClubList();
        getRoundsList();
        getPointsList();
    }
    
        public Boolean validateName(Tournament tnm, List<Boolean> l)
    {    
        if (tnm.getName() != null) {
            if (tnm.getName().length() > 4) {
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
    
    public Boolean validateDescription(Tournament tnm, List<Boolean> l)
    {    
        if (tnm.getDescription() != null) {
            if (tnm.getDescription().length() > 29) {
                l.set(1, true);
                return true;
            } else {
                l.set(1, false);
                return false;
            }
        } else {
            l.set(1, false);
            return false;
        }
    }
    
    public Boolean validateDate(Tournament tnm, List<Boolean> l)
    {    
        if (tnm.getDate() != null) {
            l.set(2, true);
            return true;
        } else {
            l.set(2, false);
            return false;
        }
    }
    
    public boolean validateHasNotErrors(Tournament tnm, List<Boolean> l)
    {
        if(validateName(tnm,l)&&validateDescription(tnm,l)&&validateDate(tnm,l))
        {
            l.set(3, true);
            return true;
        }
        l.set(3, false);
        return false;
    }
    
      public ArrayList<SelectItem> getCategoryList()
      {
         categoryList = new ArrayList<SelectItem>();
         Iterable<Category> cat =  catBO.findAll();
         Iterator<Category> iterator = cat.iterator();
           while(iterator.hasNext()) {
            Category c = iterator.next();
            categoryList.add(new SelectItem(c.getName(), c.getName()));
            }
         return categoryList;
     } 

      
      public ArrayList<SelectItem> getRoundsList()
      {
        //Ile k*rwa rund ? 
        roundsList = new ArrayList<SelectItem>();
        for(int i = 0 ; i < 10; i++ )
        {
            roundsList.add(new SelectItem(i,i+""));
        }  
        return roundsList;
      }
      
      public ArrayList<SelectItem> getPointsList()
      {
        pointsList  = new ArrayList<SelectItem>();  
        //Ile k*rwa punktów ? 
        for(int i = 0 ; i < 10; i++ )
        {
            pointsList.add(new SelectItem(i,i+""));
        }  
        return pointsList;
      }
      
      public ArrayList<SelectItem> getClubList()
      {
         clubList = new ArrayList<SelectItem>();
         Iterable<Club> clb =  clbBO.findAll();
         Iterator<Club> iterator = clb.iterator();
           while(iterator.hasNext()) {
            Club c = iterator.next();
            clubList.add(new SelectItem(c.getName(), c.getName()));
            }
         return clubList;
     }
      
    public void generateTournaments() throws Round.NotJoinableRound
    {
        UsernamePasswordAuthenticationToken lUAuth = umBO.getLoggedUserAuthentication();
        String lolek = lUAuth.getName();
        umBO.findUserByEmail(lUAuth.getName());
        try {
            Tournament t = tmBO.registerTournament(10,2,"tname", new Date(), "tDescc", clbBO.findClubById(1), catBO.findCategoryById(1));
            tmBO.joinTournament(t);
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
    
    public void updateTournament(Tournament tnm) {
        System.out.println("TNM update start");
        tmBO.saveTournament(tnm);
        System.out.println("TNM saved");
        editable.put(tnm.getId(), false);
        System.out.println("TNM editable false");
        checked.put(tnm.getId(), false);
        System.out.println("TNM checked false");
    }

    public void saveNewTournament() {
        if(validateHasNotErrors(newtournament,ntvalidation)){
        newtournament.setClub(clbBO.findClubByName(newtournament.getClub().getName()));
        newtournament.setCategory(catBO.findCategoryById(newtournament.getCategory().getId()));
        tmBO.saveTournament(newtournament);
        editable.put(newtournament.getId(), false);
        checked.put(newtournament.getId(), false);
        createNewTournament = false;
        tournaments.add(newtournament);
        }
    }
    
    public void addNewTournament() {    
        newtournament = new Tournament("", new Date(), "", clbBO.findClubById(1), catBO.findCategoryById(1), Tournament.State.NOT_STARTED);
        newtournament.setNumberOfRounds(1);
        newtournament.setPointsForBye(1);
        ntvalidation = Arrays.asList(true, true, true, false);
        createNewTournament = true;
    }
    
    public void cancelNewTournament() {
        createNewTournament = false;
        newtournament = null;
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

  /*  public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }*/

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
        
    public void saveSelected()
    {
        for(int i=0;i<tournaments.size();i++){
            if(validateHasNotErrors(tournaments.get(i), validation.get(tournaments.get(i).getId()))){
            if(checked.get(tournaments.get(i).getId())) {
                editable.put(tournaments.get(i).getId(),false);
                checked.put(tournaments.get(i).getId(), false);
                tmBO.saveTournament(tournaments.get(i));
            }
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
    
   /* public Round getCurrentRound()
    {
        return currentRound;
    }
    
    public void setCurrentRound(Round currentRound)
    {
        this.currentRound = currentRound;
    }*/
    
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
    
        public List<Boolean> getNtvalidation()
    {
        return this.ntvalidation;
    }
   
    public Boolean getHasChecked()
    {
        for(int i=0;i<tournaments.size();i++){
            if(checked.get(tournaments.get(i).getId())) { return this.hasChecked = true;
                }
        }
        return this.hasChecked=false;
    }
    
    public void setHasChecked(Boolean hasChecked)
    {
        this.hasChecked = hasChecked;
    }

        
    public void setNtvalidation(List<Boolean> ntvalidation)
    {
        this.ntvalidation = ntvalidation;
    }
    
    public Map<Integer, List<Boolean>> getValidation()
    {
        return this.validation;
    }
    
    public void setValidation(Map<Integer, List<Boolean>> validation)
    {
        this.validation = validation;
    }

    public List<Boolean> getTournamentValidation(Integer id)
    {
        return this.validation.get(id);
    }
    
    public void setTournamentValidation(Integer id, List<Boolean> l)
    {
        this.validation.put(id,l);
    }
    
    
    
    
    
    
    /* Do optymalizacji */
    
    public void refreshList()
    {
        tournaments.clear();
        Iterator<Tournament> tnm = tmBO.findAll().iterator();
        while(tnm.hasNext()){
            tournaments.add(tnm.next());}
    }
    
    
}