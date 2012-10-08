/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "rgsBean")
@ViewScoped
public class RegistrationBean {

    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private Integer sex;
    private String password;
    private String clubName;
    private String clubDescription;
    private int regOption;

    public RegistrationBean() {
        regOption = 0;
        System.out.println("Registration bean constructor.");
    }

    public void changeRegType() {
        if (regOption == 0) {
            regOption = 1;
        } else {
            regOption = 0;
        }
        System.out.println("Registration type changed.");
    }

    public String register() {

        return "/authorization/welcome.xhtml?faces-redirect=true";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRegOption() {
        return regOption;
    }

    public void setRegOption(int regOption) {
        this.regOption = regOption;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }
}
