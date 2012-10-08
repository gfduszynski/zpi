/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Daniel
 */
@ManagedBean
@RequestScoped
public class NewsBean {
    
    private List<Object> latestTournaments;
    private List<Object> latestMessage;

    public NewsBean() {
    }

    public List<Object> getLatestTournaments() {
        return latestTournaments;
    }

    public void setLatestTournaments(List<Object> latestTournaments) {
        this.latestTournaments = latestTournaments;
    }

    public List<Object> getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(List<Object> latestMessage) {
        this.latestMessage = latestMessage;
    }
}
