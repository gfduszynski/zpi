/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
    
    private String tmtName;
    private String tmtDescription;
    private Date tmtDate;
    private List<Category> categories;

    public CreateTournamentBean() {
    }
    
    @PostConstruct
    public void initialize(){
        
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
}
