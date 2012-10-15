/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletException;
import org.chessclan.businessTier.businessObjects.ClubBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.Role;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "rgsBean")
@SessionScoped
public class RegistrationBean {

    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private Integer sex;
    private String password;
    private String clubName;
    private String clubDescription;
    private long regOption;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;
    private boolean validEmail;
    private boolean invalidEmail;
    private boolean occupiedEmail;
    private boolean invalidFN;
    private boolean invalidLN;
    private boolean invalidBD;
    private boolean acceptedStatute;
    private boolean statuteError;
    private boolean invalidP;
    private boolean regError;
    private boolean invalidCN;
    private boolean regSucceeded;
    @ManagedProperty("#{UserManagementBO}")
    UserManagementBO umBO;
    @ManagedProperty("#{ClubBO}")
    ClubBO clubBO;
    @ManagedProperty("#{loginBean}")
    LoginBean loginBean;

    public RegistrationBean() {
        regOption = 0;
        validEmail = false;
        invalidEmail = false;
        this.invalidBD = false;
        this.invalidFN = false;
        this.invalidLN = false;
        this.invalidP = false;
        this.regError = false;
        this.statuteError = false;
        pattern = Pattern.compile(EMAIL_PATTERN);
        this.sex = 0;
    }

    public boolean validateFirstName() {
        if (firstName != null) {
            if (firstName.length() > 2) {
                this.invalidFN = false;
                return true;
            } else {
                this.invalidFN = true;
                return false;
            }
        } else {
            this.invalidFN = true;
            return false;
        }
    }

    public boolean validateClubName() {
        if (clubName != null) {
            if (clubName.length() > 0) {
                this.invalidCN = false;
                return true;
            } else {
                this.invalidCN = true;
                return false;
            }
        } else {
            this.invalidCN = true;
            return false;
        }
    }

    public boolean validateLastName() {
        if (lastName != null) {
            if (lastName.length() > 2) {
                this.invalidLN = false;
                return true;
            } else {
                this.invalidLN = true;
                return false;
            }
        } else {
            this.invalidLN = true;
            return false;
        }
    }

    public boolean validatePassword() {
        if (password != null) {
            if (password.length() > 4) {
                this.invalidP = false;
                return true;
            } else {
                this.invalidP = true;
                return false;
            }
        } else {
            this.invalidP = true;
            return false;
        }
    }

    public boolean validateBD() {

        if (this.birthDate == null) {
            this.invalidBD = true;
            return false;
        } else {
            this.invalidBD = false;
            return true;
        }
    }

    public boolean validateEmail() {
        if (umBO.isEmailRegistered(this.email)) {
            this.validEmail = false;
            this.invalidEmail = false;
            this.occupiedEmail = true;
            return false;
        } else {
            if (validate(this.email)) {
                this.validEmail = true;
                this.invalidEmail = false;
                this.occupiedEmail = false;
                return true;
            } else {
                this.validEmail = false;
                this.invalidEmail = true;
                this.occupiedEmail = false;
                return false;
            }
        }
    }

    public boolean validateStatute() {
        if (!acceptedStatute) {
            statuteError = true;
            return false;
        } else {
            statuteError = false;
            return true;
        }
    }

    private boolean validate(final String emailAddress) {
        matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

    public void registerClub() {
        boolean val1 = validateClubName();
        boolean val2 = validateEmail();
        boolean val3 = validatePassword();
        boolean val4 = validateStatute();
        if (val1 && val2 && val3 && val4) {
            User u = umBO.registerUser(email, email, true, password, null, null, birthDate, 0);
            umBO.assignRole(u.getId(), Role.Type.CLUB_OWNER);
        } else {
            regError = true;
        }
    }

    public void register() throws IOException, ServletException {

        boolean val1 = validateFirstName();
        boolean val2 = validateLastName();
        boolean val3 = validateBD();
        boolean val4 = validateEmail();
        boolean val5 = validateStatute();
        System.out.print("registrating...");
        System.out.println("params: " + val1 + " : " + val2 + " : " + val3 + " : " + val4 + " : " + val5 + " : ");
        if (val1 && val2 && val3 && val4 && val5) {
            User u = umBO.registerUser(email, email, true, password, firstName, lastName, birthDate, sex);
            umBO.assignRole(u.getId(), Role.Type.USER);
            this.regSucceeded = true;
        } else {
            this.regError = true;
        }

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

    public long getRegOption() {
        return regOption;
    }

    public void setRegOption(long regOption) {
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

    public UserManagementBO getUserBO() {
        return umBO;
    }

    public void setUserBO(UserManagementBO userBO) {
        this.umBO = userBO;
    }

    public boolean getValidEmail() {
        return validEmail;
    }

    public void setValidEmail(boolean validEmail) {
        this.validEmail = validEmail;
    }

    public boolean getInvalidEmail() {
        return invalidEmail;
    }

    public void setInvalidEmail(boolean invalidEmail) {
        this.invalidEmail = invalidEmail;
    }

    public boolean getOccupiedEmail() {
        return occupiedEmail;
    }

    public void setOccupiedEmail(boolean occupiedEmail) {
        this.occupiedEmail = occupiedEmail;
    }

    public boolean getInvalidFN() {
        return invalidFN;
    }

    public void setInvalidFN(boolean invalidFN) {
        this.invalidFN = invalidFN;
    }

    public boolean getInvalidLN() {
        return invalidLN;
    }

    public void setInvalidLN(boolean invalidLN) {
        this.invalidLN = invalidLN;
    }

    public boolean getInvalidBD() {
        return invalidBD;
    }

    public void setInvalidBD(boolean invalidBD) {
        this.invalidBD = invalidBD;
    }

    public boolean getAcceptedStatute() {
        return acceptedStatute;
    }

    public void setAcceptedStatute(boolean acceptedStatute) {
        this.acceptedStatute = acceptedStatute;
    }

    public boolean getInvalidP() {
        return invalidP;
    }

    public void setInvalidP(boolean invalidP) {
        this.invalidP = invalidP;
    }

    public boolean isRegError() {
        return regError;
    }

    public void setRegError(boolean regError) {
        this.regError = regError;
    }

    public boolean isStatuteError() {
        return statuteError;
    }

    public void setStatuteError(boolean statuteError) {
        this.statuteError = statuteError;
    }

    public boolean isInvalidCN() {
        return invalidCN;
    }

    public void setInvalidCN(boolean invalidCN) {
        this.invalidCN = invalidCN;
    }

    public ClubBO getClubBO() {
        return clubBO;
    }

    public void setClubBO(ClubBO clubBO) {
        this.clubBO = clubBO;
    }

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public boolean isRegSucceeded() {
        return regSucceeded;
    }

    public void setRegSucceeded(boolean regSucceeded) {
        this.regSucceeded = regSucceeded;
    }
    
    
}
