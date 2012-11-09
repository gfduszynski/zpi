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

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "crTmtBean")
@ViewScoped
public class CreateTournamentBean implements Serializable{

    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmBO;
    @ManagedProperty("#{CategoryBO}")
    private CategoryBO ctBO;
    private String tmtName;
    private String tmtDescription;
    private Date tmtDate;
    private List<Category> categories;
    private LinkedList<Category> tmtCategories;
    private Category selectedCat;
    private boolean tmtValid;

    public CreateTournamentBean() {
        this.tmtCategories = new LinkedList<Category>();
        this.tmtValid = true;
    }

    @PostConstruct
    public void initialize() {
        this.categories = ctBO.findAll();
        this.selectedCat = categories.get(0);
    }
    
    public void addCategory(){
        System.out.println("Category trying to add: "+selectedCat.getName());
        if(!tmtCategories.contains(this.selectedCat)) {
            this.tmtCategories.add(selectedCat);
        }
    }
    
    public void saveTournament(){
        
    }
    
    public boolean validateTournamentName(){
        if(this.tmtName.length() > 1){
            this.tmtValid = true;
        }else{
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

    public Category getSelectedCat() {
        return selectedCat;
    }

    public void setSelectedCat(Category selectedCat) {
        this.selectedCat = selectedCat;
    }

    public boolean isTmtValid() {
        return tmtValid;
    }

    public void setTmtValid(boolean tmtValid) {
        this.tmtValid = tmtValid;
    }
    
    
}
