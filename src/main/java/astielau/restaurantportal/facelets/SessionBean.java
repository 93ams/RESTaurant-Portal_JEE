package astielau.restaurantportal.facelets;

import astielau.restaurantportal.entities.DishEntity;
import astielau.restaurantportal.entities.RestaurantEntity;
import astielau.restaurantportal.entities.UserEntity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionBean {
    private static ExternalContext getContext(){
        return FacesContext.getCurrentInstance()
                           .getExternalContext();
    }
    
    public static HttpSession getSession() {
        return (HttpSession) getContext().getSession(false);
    }
 
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) getContext().getRequest();
    }
 
    public static UserEntity getUser() {
        HttpSession session = getSession();
        if (session != null)
            return (UserEntity) session.getAttribute("user");
        else
            return null;
    }
    
    public static RestaurantEntity getRestaurant() {
        HttpSession session = getSession();
        if (session != null)
            return (RestaurantEntity) session.getAttribute("restaurant");
        else
            return null;
    }
    
    public static DishEntity getDish() {
        HttpSession session = getSession();
        if (session != null)
            return (DishEntity) session.getAttribute("dish");
        else
            return null;
    }

}
