/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Giorgio
 */
@Entity
@Table(name = "rounds")
@NamedQueries({
    @NamedQuery(name = "Round.findAll", query = "SELECT r FROM Round r")})
public class Round implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "number")
    private int number;
    @Basic(optional = false)
    @NotNull
    @Column(name = "state")
    private int state;
    @Column(name = "start")
    @Temporal(TemporalType.DATE)
    private Date start;
    @Column(name = "end")
    @Temporal(TemporalType.DATE)
    private Date end;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "round", fetch = FetchType.EAGER)
    private Set<Category> categorySet;
    @OneToMany(mappedBy = "prevRound", fetch = FetchType.EAGER)
    private Set<Round> roundSet;
    @JoinColumn(name = "prev_round", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Round prevRound;
    @JoinColumn(name = "tournament", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Tournament tournament;

    public Round() {
    }

    public Round(Integer id) {
        this.id = id;
    }

    public Round(Integer id, int number, int state) {
        this.id = id;
        this.number = number;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }

    public Set<Round> getRoundSet() {
        return roundSet;
    }

    public void setRoundSet(Set<Round> roundSet) {
        this.roundSet = roundSet;
    }

    public Round getPrevRound() {
        return prevRound;
    }

    public void setPrevRound(Round prevRound) {
        this.prevRound = prevRound;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
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
        if (!(object instanceof Round)) {
            return false;
        }
        Round other = (Round) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.chessclan.dataTier.models.Round[ id=" + id + " ]";
    }
    
}
