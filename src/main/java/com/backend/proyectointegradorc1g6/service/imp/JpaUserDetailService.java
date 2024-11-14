package com.backend.proyectointegradorc1g6.service.imp;

import com.backend.proyectointegradorc1g6.entity.Usuario;
import com.backend.proyectointegradorc1g6.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByUserName(username);
        if (usuarioOptional.isEmpty())
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));

        Usuario usuario = usuarioOptional.orElseThrow();
        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());

        return new User(usuario.getUserName(),
                usuario.getPassword(),
                usuario.isEstaActivo(),
                true,
                true,
                true,
                authorities);

    }
}
