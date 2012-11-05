/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.util.ArrayList;
import java.util.Date;
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
public class CreateTournamentBean {

    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmBO;
    @ManagedProperty("#{CategoryBO}")
    private CategoryBO ctBO;
    private String tmtName;
    private String tmtDescription;
    private Date tmtDate;
    private List<Category> categories;
    private List<Category> tmtCategories;
    private Category selectedCat;

    public CreateTournamentBean() {
        this.tmtCategories = new ArrayList<Category>();
    }

    @PostConstruct
    public void initialize() {
        this.categories = ctBO.findAll();
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

    public List<Category> getTmtCategories() {
        return tmtCategories;
    }

    public void setTmtCategories(List<Category> tmtCategories) {
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
        System.out.println("sel cat: "+selectedCat);
    }
    
    
}
