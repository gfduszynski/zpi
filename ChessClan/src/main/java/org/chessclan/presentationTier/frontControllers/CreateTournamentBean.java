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
    private String tmtName = "[Nazwa turnieju]";
    private String tmtDescription = "[Opis]";
    private Date tmtDate;
    private List<Category> categories;
    private LinkedList<Category> tmtCategories;
    private int selectedCatId;
    private boolean nameValid = true;
    private boolean descValid = true;
    private boolean dateValid = true;
    private boolean pointsValid = true;
    private boolean roundsValid = true;
    private boolean categoriesValid = true;
    private String pointsForBye = "1";
    private String numberOfRounds = "7";
    private boolean createTmtSuccess;

    public CreateTournamentBean() {
        this.tmtCategories = new LinkedList<Category>();
        this.tmtDate = new Date();
        createTmtSuccess = false;
    }

    @PostConstruct
    public void initialize() {
        this.categories = ctBO.findAll();
        this.selectedCatId = categories.get(0).getId();
    }

    public void addCategory() {
        if (tmtCategories.size() > 0) {
            return;
        }
        boolean contains = false;
        for (Category c : tmtCategories) {
            if (c.getId() == selectedCatId) {
                contains = true;
                return;
            }
        }
        if (!contains) {
            for (Category c : categories) {
                if (c.getId() == selectedCatId) {
                    this.tmtCategories.add(c);
                    this.categories.remove(c);
                    return;
                }
            }
        }
    }
    
    public boolean validateCats(){
        this.categoriesValid = (this.tmtCategories.size() != 0);
        return categoriesValid;
    }

    public void saveTournament() throws NotFinished, NoPlayers {
        if (validateCats() && validateTournamentName() && validateTournamentDesc() && validateDate() && validatePoints() && validateRounds() && tmtCategories.size() > 0) {
            Tournament t = tmBO.registerTournament(Integer.parseInt(numberOfRounds), Integer.parseInt(pointsForBye), tmtName, tmtDate, tmtDescription, user.getOwnedClub(), tmtCategories.get(0));
            this.createTmtSuccess = true;
            ctBean.getClubTournaments().add(t);
        }
    }

    public void addNextTmt() {
        this.categories = ctBO.findAll();
        this.createTmtSuccess = false;
        numberOfRounds = "7";
        pointsForBye = "1";
        selectedCatId = categories.get(0).getId();
        tmtCategories.clear();
        tmtDate = new Date();
        tmtDescription = "";
        tmtName = "";
    }

    public boolean validateTournamentName() {
        if (this.tmtName.length() > 1) {
            nameValid = true;
        } else {
            nameValid = false;
        }
        return nameValid;
    }

    public boolean validateTournamentDesc() {
        if (this.tmtDescription.length() > 1) {
            descValid = true;
        } else {
            descValid = false;
        }
        return descValid;
    }

    public boolean validateDate() {
        if (this.tmtDate == null) {
            dateValid = false;
        } else {
            dateValid = true;
        }
        return dateValid;
    }

    public boolean validatePoints() {
        try {
            float tmp = Float.parseFloat(pointsForBye);
            if (tmp > 0) {
                pointsValid = true;
            } else {
                pointsValid = false;
            }
        }
        catch (Exception e) {
            pointsValid = false;
        }
        return pointsValid;
    }

    public boolean validateRounds() {
        try {
            int tmp = Integer.parseInt(numberOfRounds);
            if (tmp > 0) {
                roundsValid = true;
            } else {
                roundsValid = false;
            }
        }
        catch (Exception c) {
            roundsValid = false;
        }

        return roundsValid;
    }

    public void removeCat(Category cat) {
        this.tmtCategories.remove(cat);
        this.categories.add(cat);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPointsForBye() {
        return pointsForBye;
    }

    public void setPointsForBye(String pointsForBye) {
        this.pointsForBye = pointsForBye;
    }

    public String getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(String numberOfRounds) {
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

    public boolean isNameValid() {
        return nameValid;
    }

    public void setNameValid(boolean nameValid) {
        this.nameValid = nameValid;
    }

    public boolean isDescValid() {
        return descValid;
    }

    public void setDescValid(boolean descValid) {
        this.descValid = descValid;
    }

    public boolean isDateValid() {
        return dateValid;
    }

    public void setDateValid(boolean dateValid) {
        this.dateValid = dateValid;
    }

    public boolean isPointsValid() {
        return pointsValid;
    }

    public void setPointsValid(boolean pointsValid) {
        this.pointsValid = pointsValid;
    }

    public boolean isRoundsValid() {
        return roundsValid;
    }

    public void setRoundsValid(boolean roundsValid) {
        this.roundsValid = roundsValid;
    }

    public boolean isCategoriesValid() {
        return categoriesValid;
    }

    public void setCategoriesValid(boolean categoriesValid) {
        this.categoriesValid = categoriesValid;
    }
    
    
}
