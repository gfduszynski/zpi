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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Giorgio
 */
@Entity
@Table(name = "pairing_cards")
@NamedQueries({
    @NamedQuery(name = "PairingCard.findAll", query = "SELECT p FROM PairingCard p")})
public class PairingCard implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "tournament")
    private int tournament;
    @Basic(optional = false)
    @NotNull
    @Column(name = "round")
    private int round;
    @Basic(optional = false)
    @NotNull
    @Column(name = "player")
    private int player;
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
    @OneToMany(mappedBy = "opponent", fetch = FetchType.EAGER)
    private Set<PairingCard> pairingCardSet;
    @JoinColumn(name = "opponent", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PairingCard opponent;

    public PairingCard() {
    }

    public PairingCard(Integer id) {
        this.id = id;
    }

    public PairingCard(Integer id, float score) {
        this.id = id;
        this.score = score;
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

    public Set<PairingCard> getPairingCardSet() {
        return pairingCardSet;
    }

    public void setPairingCardSet(Set<PairingCard> pairingCardSet) {
        this.pairingCardSet = pairingCardSet;
    }

    public PairingCard getOpponent() {
        return opponent;
    }

    public void setOpponent(PairingCard opponent) {
        this.opponent = opponent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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

    public int getTournament() {
        return tournament;
    }

    public void setTournament(int tournament) {
        this.tournament = tournament;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
    
}
