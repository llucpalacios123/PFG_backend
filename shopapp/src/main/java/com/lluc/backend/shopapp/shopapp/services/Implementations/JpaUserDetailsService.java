package com.lluc.backend.shopapp.shopapp.services.Implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lluc.backend.shopapp.shopapp.auth.CustomUserDetails;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.repositories.UsersRepository;

@Service
@Transactional
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<User> userFinded = usersRepository.findByUsername(username);

        if(!userFinded.isPresent()){
            throw new UsernameNotFoundException("User not found");
        }
        User user = userFinded.orElseThrow();

        List<GrantedAuthority> authorities = user.getRoles()
        .stream()
        .map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());

        return new CustomUserDetails(user.getId(),
                         user.getUsername(), 
                        user.getPassword(), 
                        true,
                        false,
                        true,
                        true,
                        authorities,
                        user.getEmpresa() != null
                        );   
    }
    
}
