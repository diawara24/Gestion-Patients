package org.sid.springmvc.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sid.springmvc.dao.UsersRepository;
import org.sid.springmvc.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@SuppressWarnings("unused")
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  protected Log logger = LogFactory.getLog(this.getClass());

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, 
    HttpServletResponse response, Authentication authentication)
    throws IOException {

      handle(request, response, authentication);
      clearAuthenticationAttributes(request);
  }

  protected void handle(HttpServletRequest request, 
    HttpServletResponse response, Authentication authentication)
    throws IOException {

      String targetUrl = determineTargetUrl(authentication);

      if (response.isCommitted()) {
          logger.debug(
            "Response has already been committed. Unable to redirect to "
            + targetUrl);
          return;
      }

      redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  @Autowired
  private UsersRepository usersRepository;
  @Autowired
  private Utils utils;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private CustumUserDetailsService userDetailsService;
  
  protected String determineTargetUrl(Authentication authentication) {
      boolean isAdmin = false;
      boolean isUser = false;
      Collection<? extends GrantedAuthority> authorities
       = authentication.getAuthorities();
      String role = "";
      for (GrantedAuthority grantedAuthority : authorities) {
          if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
        	  role = grantedAuthority.getAuthority();
              isAdmin = true;
              break;
          }
          else if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
              role = grantedAuthority.getAuthority();
              isUser = true;
              break;
          }
          
          System.out.println("role : "+role);
      }

      final UserDetails userDetails = userDetailsService
              .loadUserByUsername(utils.getConnectedUser());
      final String token = jwtTokenUtil.generateToken(userDetails);

      Utils.setToken(token);
      System.out.println("Token :"+token);

      Utilisateur u = usersRepository.findByUsername(utils.getConnectedUser());
      if (isAdmin) {
          return "/index";
      } else if (isUser) {
        return "/index";
      } else  {
          throw new IllegalStateException();
      }
  }

  protected void clearAuthenticationAttributes(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      if (session == null) {
          return;
      }
      session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }

  public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
      this.redirectStrategy = redirectStrategy;
  }
  protected RedirectStrategy getRedirectStrategy() {
      return redirectStrategy;
  }

}