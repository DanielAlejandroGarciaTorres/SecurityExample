package com.digis01.Securityandjwt.Service;

import com.digis01.Securityandjwt.DAO.IUsuario;
import com.digis01.Securityandjwt.JPA.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService{

    private final IUsuario iUsuario;

    public CustomerDetailsService(IUsuario iUsuario) {
        this.iUsuario = iUsuario;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Usuario usuario = iUsuario.findByUserName(username);
        
        return User.withUsername(usuario.getUserName())
                .password(usuario.getPassword())
                .roles(usuario.Rol.getNombre())
                .accountLocked(!(usuario.getStatus() == 1))
                .disabled(!(usuario.getStatus() == 1))
                .build();
    }
}
