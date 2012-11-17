/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.lang.reflect.Type;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "moves")
public class Move implements Serializable {

//    public class MoveSerializer implements JsonSerializer<Move> {
//        @Override
//        public JsonElement serialize(Move src, Type typeOfSrc, JsonSerializationContext context) {
//            JsonObject obj = new JsonObject();
//            obj.addProperty("id", src.id);
//            obj.addProperty("fromX", src.fromX);
//            obj.addProperty("fromY", src.fromY);
//            obj.addProperty("toX", src.toX);
//            obj.addProperty("toY", src.toY);
//            obj.addProperty("isSingular", src.isSingular);
//            return obj;
//        }
//    }
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Expose
    @Basic(optional = false)
    @NotNull
    @Column(name = "fromX")
    private int fromX;
    @Expose
    @Basic(optional = false)
    @NotNull
    @Column(name = "fromY")
    private int fromY;
    @Expose
    @Basic(optional = false)
    @NotNull
    @Column(name = "toX")
    private int toX;
    @Expose
    @Basic(optional = false)
    @NotNull
    @Column(name = "toY")
    private int toY;
    @Expose
    @Basic(optional = false)
    @NotNull
    @Column(name = "isSingular")
    private boolean isSingular;
    @JoinColumn(name = "partOf", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Game partOf;

    public Move() {
    }

    public Move(int fromX, int fromY, int toX, int toY, boolean isSingular) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.isSingular = isSingular;
    }

    public int getFromX() {
        return fromX;
    }

    public void setFromX(int fromX) {
        this.fromX = fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public void setFromY(int fromY) {
        this.fromY = fromY;
    }

    public int getToX() {
        return toX;
    }

    public void setToX(int toX) {
        this.toX = toX;
    }

    public int getToY() {
        return toY;
    }

    public void setToY(int toY) {
        this.toY = toY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsSingular() {
        return isSingular;
    }

    public void setIsSingular(boolean isSingular) {
        this.isSingular = isSingular;
    }

    public Game getPartOf() {
        return partOf;
    }

    public void setPartOf(Game partOf) {
        this.partOf = partOf;
    }
}
