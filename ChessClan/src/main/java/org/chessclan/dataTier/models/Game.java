/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.models;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "games")
public class Game implements Serializable {

    public enum GameResult {

        WHITE_WON, BLACK_WON, DRAW, UNFINISHED;
    }
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Expose
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "event")
    private String event;
    @Expose
    @Basic(optional = true)
    @Size(min = 1, max = 256)
    @Column(name = "site")
    private String site;
    @Expose
    @Basic(optional = true)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Expose
    @Basic(optional = true)
    @Column(name = "round")
    private int round;
    @Expose
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "white")
    private String white;
    @Expose
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "black")
    private String black;
    @Expose
    @Basic(optional = false)
    @NotNull
    @Column(name = "result")
    @Enumerated(EnumType.ORDINAL)
    private GameResult result;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partOf", fetch = FetchType.EAGER)
    private Set<Move> moves;
    @Expose
    @JoinColumn(name = "owner", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private User owner;

    public Game() {
    }

    public Game(String event, String site, Date date, int round, String white, String black, GameResult result, User owner){
        this.event = event;
        this.site = site;
        this.date = date;
        this.round = round;
        this.white = white;
        this.black = black;
        this.result = result;
        this.moves = new HashSet<Move>();
        this.owner = owner;
    }
    
    public void addMove(Move move){
        move.setPartOf(this);
        this.moves.add(move);
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getWhite() {
        return white;
    }

    public void setWhite(String white) {
        this.white = white;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Move> getMoves() {
        return moves;
    }

    public void setMoves(Set<Move> moves) {
        this.moves = moves;
    }


    
}
