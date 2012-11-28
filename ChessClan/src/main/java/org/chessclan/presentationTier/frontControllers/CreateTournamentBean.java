/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.chessclan.businessTier.businessObjects.CategoryBO;
import org.chessclan.businessTier.businessObjects.TournamentBO;
import org.chessclan.dataTier.models.Category;
import org.chessclan.dataTier.models.Round.NoPlayers;
import org.chessclan.dataTier.models.Round.NotFinished;
import org.chessclan.dataTier.models.Tournament;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "crTmtBean")
@ViewScoped
public class CreateTournamentBean implements Serializable {

    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmBO;
    @ManagedProperty("#{CategoryBO}")
    private CategoryBO ctBO;
    @ManagedProperty(value = "#{loginBean.user}")
    private User user;
    @ManagedProperty("#{ctBean}")
    ClubTournamentsBean ctBean;

    private String tmtName;
    private String tmtDescription;
    private Date tmtDate;
    private List<Category> categories;
    private LinkedList<Category> tmtCategories;
    private int selectedCatId;
    private boolean tmtValid;
    private int pointsForBye;
    private int numberOfRounds;
    private boolean createTmtSuccess;

    public CreateTournamentBean() {
        this.tmtCategories = new LinkedList<Category>();
        this.tmtValid = true;
        this.pointsForBye=1;
        this.tmtDate = new Date();
        createTmtSuccess = false;
    }

    @PostConstruct
    public void initialize() {
        this.categories = ctBO.findAll();
        this.selectedCatId = categories.get(0).getId();
    }

    public void addCategory() {
        boolean contains = false;
        for(Category c : tmtCategories){
            if(c.getId() == selectedCatId){
                contains = true;
                return;
            }
        }
        if (!contains) {
            for(Category c : categories){
                if(c.getId() == selectedCatId){
                    this.tmtCategories.add(c);
                    this.categories.remove(c);
                    return;
                }
            }
        }
    }

    public void saveTournament() throws NotFinished, NoPlayers {
        Tournament t = tmBO.registerTournament(numberOfRounds, pointsForBye, tmtName, tmtDate, tmtDescription, user.getOwnedClub(), tmtCategories.get(0));
        this.createTmtSuccess = true;
        ctBean.getClubTournaments().add(t);
    }
    
    public void addNextTmt(){
        this.categories = ctBO.findAll();
        this.createTmtSuccess = false;
        numberOfRounds = 7;
        pointsForBye = 1;
        selectedCatId = categories.get(0).getId();
        tmtCategories.clear();
        tmtDate = new Date();
        tmtDescription="";
        tmtName="";
        tmtValid=true;
    }

    public boolean validateTournamentName() {
        if (this.tmtName.length() > 1) {
            this.tmtValid = true;
        } else {
            this.tmtValid = false;
        }
        return tmtValid;
    }
    public boolean validateTournamentContent() {
        if (this.tmtName.length() > 1) {
            this.tmtValid = true;
        } else {
            this.tmtValid = false;
        }
        return tmtValid;
    }

    public TournamentBO getTmBO() {
        return tmBO;
    }

    public void setTmBO(TournamentBO tmBO) {
        this.tmBO = tmBO;
    }

    public String getTmtName() {
        return tmtName;
    }

    public void setTmtName(String tmtName) {
        this.tmtName = tmtName;
    }

    public String getTmtDescription() {
        return tmtDescription;
    }

    public void setTmtDescription(String tmtDescription) {
        this.tmtDescription = tmtDescription;
    }

    public Date getTmtDate() {
        return tmtDate;
    }

    public void setTmtDate(Date tmtDate) {
        this.tmtDate = tmtDate;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public LinkedList<Category> getTmtCategories() {
        return tmtCategories;
    }

    public void setTmtCategories(LinkedList<Category> tmtCategories) {
        this.tmtCategories = tmtCategories;
    }

    public CategoryBO getCtBO() {
        return ctBO;
    }

    public void setCtBO(CategoryBO ctBO) {
        this.ctBO = ctBO;
    }

    public int getSelectedCatId() {
        return selectedCatId;
    }

    public void setSelectedCatId(int selectedCatId) {
        this.selectedCatId = selectedCatId;
    }

    public boolean isTmtValid() {
        return tmtValid;
    }

    public void setTmtValid(boolean tmtValid) {
        this.tmtValid = tmtValid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPointsForBye() {
        return pointsForBye;
    }

    public void setPointsForBye(int pointsForBye) {
        this.pointsForBye = pointsForBye;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public boolean isCreateTmtSuccess() {
        return createTmtSuccess;
    }

    public void setCreateTmtSuccess(boolean createTmtSuccess) {
        this.createTmtSuccess = createTmtSuccess;
    }

    public ClubTournamentsBean getCtBean() {
        return ctBean;
    }

    public void setCtBean(ClubTournamentsBean ctBean) {
        this.ctBean = ctBean;
    }
    
    
}
