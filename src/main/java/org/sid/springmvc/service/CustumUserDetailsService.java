package org.sid.springmvc.service;

import org.sid.springmvc.dao.UsersRepository;
import org.sid.springmvc.entities.Role;
import org.sid.springmvc.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustumUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = usersRepository.findByUsername(username);
        if (user != null){
            User u = new User(user.getUsername(), user.getPassword(),true, true, true, true, getAuthorities(user.getRoles()));
            return u;
        }
        return null;
    }

    private Collection getAuthorities(List roles) {
        List authorities = new ArrayList();
        for(Object role : roles)
        {
            Role l = (Role) role;
            authorities.add(new SimpleGrantedAuthority(l.getRole()));
        }
        return authorities ;
    }
}
