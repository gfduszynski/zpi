/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.joda.time.DateTime;

/**
 *
 * @author Giorgio
 */
@Entity
@Table(name = "posts")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "content")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_created")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    @Column(name = "date_published")
    @Temporal(TemporalType.DATE)
    private Date datePublished;
    @Column(name = "date_expires")
    @Temporal(TemporalType.DATE)
    private Date dateExpires;
    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    public Post() {
    }

    public Post(Integer id) {
        this.id = id;
    }

    public Post(String title, String content, User user, Date published, PostLifeTime plt) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.dateCreated = Calendar.getInstance().getTime();
        this.datePublished = published;
        if (plt == null) {
            this.dateExpires = null;
        } else {
            DateTime currentDate = new DateTime();
            switch (plt.value) {
                case 1:
                    currentDate.plusDays(7);
                    break;
                case 2:
                    currentDate.plusDays(14);
                    break;
                case 3:
                    currentDate.plusDays(21);
                    break;
                default:
                    currentDate.plusDays(1);
                    break;

            }
            this.dateExpires = currentDate.toDate();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public Date getDateExpires() {
        return dateExpires;
    }

    public void setDateExpires(Date dateExpires) {
        this.dateExpires = dateExpires;
    }

    public User getUser() {
        //user.getEmail();
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.chessclan.dataTier.models.Post[ id=" + id + " ]";
    }

    public enum PostLifeTime {

        WEEK(1), TWOWEEKS(2), THREEWEEKS(3);
        private int value;

        private PostLifeTime(int value) {
            this.value = value;
        }
    };
}
