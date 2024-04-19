package com.example.LibraryManagement.Security;

import com.example.LibraryManagement.Models.Role;
import com.example.LibraryManagement.Models.User;
import com.example.LibraryManagement.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Método para traer lista de autoridades por medio de lista de Roles
    public Collection<GrantedAuthority> mapToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    //Método para traer usuario (Con todos sus datos) por medio de su 'username'
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User with UserName " + username + " Not Found"));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), mapToAuthorities(user.getRoles()));
    }

}
