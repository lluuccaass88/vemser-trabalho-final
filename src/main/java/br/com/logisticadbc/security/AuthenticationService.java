package br.com.logisticadbc.security;

import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuario = usuarioService.buscarPorLogin(username);
        return usuario
                .orElseThrow(() -> new UsernameNotFoundException("Usuário inválido!"));
    }
}