package astielau.restaurantportal.filters;

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

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthFilter implements Filter {
    
    public AuthFilter() {}
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void doFilter( ServletRequest request, 
                          ServletResponse response,
                          FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession session = req.getSession(false);
            String reqURI = req.getRequestURI();
            
            if(req.getRequestURI().substring(req.getContextPath().length()).equals("/")){
                res.sendRedirect(req.getContextPath() + "/index.xhtml");
            } else if ( reqURI.contains("/login.xhtml")
                     || reqURI.contains("/index.xhtml")
                     || reqURI.contains("/register.xhtml")
                     || (session != null && session.getAttribute("user") != null)
                     || reqURI.contains("/public/")
                     || reqURI.contains("javax.faces.resource")){
                System.out.println("AuthFilter is chill");
                chain.doFilter(request, response);
            } else {
                System.out.println("AuthFilter not so chill");
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            }
        } catch (IOException | ServletException e) {
            System.out.println(e.getMessage());
        } catch( Exception e ){
            System.out.println("Error @ AuthFilter: doFilter");
            System.out.println( e.getMessage() );
        }
    }
    
    @Override
    public void destroy() {}
}
