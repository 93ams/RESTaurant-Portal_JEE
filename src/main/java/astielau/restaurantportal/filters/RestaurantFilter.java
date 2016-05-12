package astielau.restaurantportal.filters;

import astielau.restaurantportal.entities.ManagerEntity;
import astielau.restaurantportal.entities.RestaurantEntity;
import astielau.restaurantportal.entities.UserEntity;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
                                                               // *.xhtml
@WebFilter(filterName = "RestaurantFilter", urlPatterns = { "dish.xhtml", "restaurant.xhtml", "newdish.xhtml", "newrestaurant.xhtml" })
public class RestaurantFilter implements Filter {
    
    public RestaurantFilter (){}
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void doFilter( ServletRequest request, 
                          ServletResponse response,
                          FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String reqURI = req.getRequestURI();
            HttpSession session = req.getSession(false);
            
            if(reqURI.contains("/restaurant.xhtml")){
                if(session.getAttribute("restaurant") == null){
                    res.sendRedirect(req.getContextPath() + "/index.xhtml");
                }else{
                    chain.doFilter(request, response);
                }
            } else if( reqURI.contains("/dish.xhtml")){
                if( session.getAttribute("dish") == null ){
                    if(session.getAttribute("restaurant") == null){
                        res.sendRedirect(req.getContextPath() + "/index.xhtml");
                    } else {
                        res.sendRedirect(req.getContextPath() + "/restaurant.xhtml");
                    }
                }else{
                    chain.doFilter(request, response);
                }
            } else if(reqURI.contains("/newrestaurant.xhtml")){
                UserEntity user = (UserEntity) session.getAttribute("user");
                if(user == null || !("manager".equals(user.getUserType()))
                || ((ManagerEntity) user).getRestaurant() != null){
                    System.out.println("Redirecting to index; User is not a manager or already has a restaurant");
                    res.sendRedirect(req.getContextPath() + "/index.xhtml");
                }else{
                    chain.doFilter(request, response);
                }
            } else if(reqURI.contains("/newdish.xhtml")){
                UserEntity user = (UserEntity) session.getAttribute("user");
                RestaurantEntity restaurant = (RestaurantEntity) session.getAttribute("restaurant");
                if(user == null || !("manager".equals(user.getUserType()))
                || !restaurant.getName().equals(((ManagerEntity) user).getRestaurant()) ){
                    System.out.println("Redirecting to restaurant; User is not manager or is not of this restaurant");
                    res.sendRedirect(req.getContextPath() + "/restaurant.xhtml");
                }else{
                    chain.doFilter(request, response);
                }
            } else {
                chain.doFilter(request, response);
            }
        } catch ( IOException | ServletException e ) {
            System.out.println("Error @ RestaurantFilter: doFilter");
            System.out.println(e.getMessage());
        } catch( Exception e ){
            System.out.println("Error @ RestaurantFilter: doFilter");
            System.out.println( e.getMessage() );
        }
    }
    
    @Override
    public void destroy() {}
}
