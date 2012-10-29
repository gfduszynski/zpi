/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.models;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Giorgio
 */
@Entity
@Table(name = "pairing_cards")
public class PairingCard implements Serializable, Comparable<PairingCard>{
    public enum Color {NO_COLOR,WHITE,BLACK;}
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private float score;
    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "tournament")
    @ManyToOne
    private Tournament tournament;
    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "round")
    @ManyToOne
    private Round round;
    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "player")
    @ManyToOne
    private User player;
    @JoinColumn(name = "opponent", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER)
    private PairingCard opponent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "floats")
    private int floats = 0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "byes")
    private int byes = 0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "color")
    @Enumerated(EnumType.ORDINAL)
    private Color color;
    @Basic(optional = false)
    @NotNull
    @Column(name = "color_diff")
    private int colorDiff = 0;

    public PairingCard() {
    }

    public PairingCard(Integer id) {
        this.id = id;
    }

    public PairingCard(Integer id, float score) {
        this.id = id;
        this.score = score;
    }

    public PairingCard(PairingCard pc, Round currentRound) {
        this.score = pc.score;
        this.tournament = pc.tournament;
        this.round = currentRound;
        this.player = pc.player;
        this.opponent = null;
        this.color = pc.color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public PairingCard getOpponent() {
        return opponent;
    }

    public void setOpponent(PairingCard opponent) {
        this.opponent = opponent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 29 * hash + Float.floatToIntBits(this.score);
        hash = 29 * hash + (this.player != null ? this.player.hashCode() : 0);
        hash = 29 * hash + this.floats;
        hash = 29 * hash + this.byes;
        hash = 29 * hash + (this.color != null ? this.color.hashCode() : 0);
        hash = 29 * hash + this.colorDiff;
        return hash;
    }


    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PairingCard)) {
            return false;
        }
        PairingCard other = (PairingCard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.chessclan.dataTier.models.PairingCard[ id=" + id + " ]";
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public int getFloats() {
        return floats;
    }

    public void setFloats(int floats) {
        this.floats = floats;
    }

    public int getByes() {
        return byes;
    }

    public void setByes(int byes) {
        this.byes = byes;
    }

    public int getColorDiff() {
        return colorDiff;
    }

    public void setColorDiff(int colorDiff) {
        this.colorDiff = colorDiff;
    }    

    @Override
    public int compareTo(PairingCard o) {
        float diff = getScore()-o.getScore();
        int scoreCMP = diff==0?0:(diff>0?1:-1);
        if(scoreCMP!=0) {
            return scoreCMP;
        }
        
        diff = getPlayer().getRating()-o.getPlayer().getRating();
        int ratingCMP = diff==0?0:(diff>0?1:-1);
        if(ratingCMP!=0) {
            return ratingCMP;
        }
        
        int titleCMP = getPlayer().getFideTitle().compareTo(o.getPlayer().getFideTitle());
        if(titleCMP!=0){
            return titleCMP;
        }
        
        int lastnameCMP = getPlayer().getLastName().compareTo(o.getPlayer().getLastName());
        if(lastnameCMP!=0){
            return lastnameCMP;
        }
        
        int firstNameCMP = getPlayer().getFirstName().compareTo(o.getPlayer().getFirstName());
        if(firstNameCMP!=0){
            return firstNameCMP;
        }
        
        return 0;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
