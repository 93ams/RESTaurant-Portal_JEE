package astielau.restaurantportal.facelets;

import astielau.restaurantportal.dao.LoginDAO;
import astielau.restaurantportal.entities.UserEntity;
import astielau.restaurantportal.enums.UserTypeEnum;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@SessionScoped
@Named
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EJB
    LoginDAO loginDAO;
    
    private boolean logged;
    private UserTypeEnum userType;
    
    private String username;
    private String password;
    private String passwordConf;
    
    private String name;
    private String address;
    private String email;
    private String taxId;
    
    public LoginBean(){
        this.userType = convertUserType("client");
    }
    
    public boolean getLogged() { return logged; }

    public String getUsername() { return username; } 
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; } 
    public void setPassword(String password) { this.password = password; }
    
    public String getPasswordConf() { return passwordConf; } 
    public void setPasswordConf(String passwordConf) { this.passwordConf = passwordConf; }
    
    public String getUserType() { return userType.getType(); }
    public void setUserType( String value ) { this.userType = convertUserType(value); }
    private UserTypeEnum convertUserType(String value){
        switch(value){
            case "manager":
                return UserTypeEnum.MANAGER;
            case "client":
                return UserTypeEnum.CLIENT;
            default:
                return null;
        }
    }

    public String getName() { return name; } 
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; } 
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; } 
    public void setEmail(String email) { this.email = email; }

    public String getTaxId() { return taxId; } 
    public void setTaxId(String taxId) { this.taxId = taxId; }
    
    public void toggleUserType(){
        switch(userType.getType()){
            case "client":
                this.userType = UserTypeEnum.MANAGER;
                break;
            case "manager":
                this.userType = UserTypeEnum.CLIENT;
                break;
            default:
                this.userType = null;
                break;
        }
    }
    
    public String login(){
        if(validate()){
            this.logged = true;
            return "index.xhtml?faces-redirect=true";
        } else
            return "login.xhtml?faces-redirect=true";
    }
    
    public String register(){
        if(!password.equals(passwordConf)){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Passwords dont match",
                            "Please write the same passoword twice!"));
        }
        HttpSession session = SessionBean.getSession();
        UserEntity user = null;
        switch(userType){
            case MANAGER:
                user = loginDAO.registerManager ( username, password, name );
                break;
            case CLIENT:
                user = loginDAO.registerClient ( username, password, name, email, address, taxId );
                break;
            default:
                FacesContext.getCurrentInstance().addMessage( null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Invalid user type", "This shouldn't happen!"));
                break;
        }
        if(user != null){
            session.setAttribute("user", user);
            this.logged = true;
            return "index.xhtml?faces-redirect=true";
        }
        return "register.xhtml?faces-redirect=true";
    }
    
    public String logout() {
        HttpSession session = SessionBean.getSession();
        session.invalidate();
        this.logged = false;
        return "index.xhtml?faces-redirect=true";
    }
    
    private boolean validate() {
        HttpSession session = SessionBean.getSession();
        if(this.getLogged()){
            FacesContext.getCurrentInstance().addMessage( null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "User already logged in.",
                        "You are already logged, logout first to login with another account."));
            return false;
        }
        
        UserEntity user = loginDAO.validate(username, password);
        
        if(user != null){
            session.setAttribute("user", user);
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Incorrect Username and Passowrd",
                        "Please enter correct username and Password"));
            return false;
        }
    }
    
}
