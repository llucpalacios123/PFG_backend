package com.lluc.backend.shopapp.shopapp.services.Implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lluc.backend.shopapp.shopapp.models.User;
import com.lluc.backend.shopapp.shopapp.repositories.UsersRepository;

@Service
@Transactional
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<User> userFinded = usersRepository.findByUsername(username);

        if(!userFinded.isPresent()){
            throw new UsernameNotFoundException("User not found");
        }
        User user = userFinded.orElseThrow();

        List<GrantedAuthority> authorities = new ArrayList();
        authorities.add(() -> "ROLE_ADMIN");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), 
                        user.getPassword(), 
                        true,
                        true,
                        true,
                        true,
                        authorities);   
    }
    
}
