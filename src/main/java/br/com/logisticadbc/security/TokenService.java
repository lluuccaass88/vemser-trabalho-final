package br.com.logisticadbc.security;

import br.com.logisticadbc.entity.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class TokenService {

    private static final String CHAVE_LOGIN = "login";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String CARGOS_TOKEN = "cargos ";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String gerarToken(UsuarioEntity usuarioEntity) {
        Date exp = new Date(System.currentTimeMillis() + Long.parseLong(expiration));

        List<String> cargos = usuarioEntity.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .toList();

        String token =
                Jwts.builder()
                        .claim(Claims.ID, usuarioEntity.getIdUsuario())
                        .claim(CHAVE_LOGIN, usuarioEntity.getLogin())
                        .claim(CARGOS_TOKEN, cargos)
                        .setIssuedAt(Date.valueOf(LocalDate.now()))
                        .setExpiration(exp)
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
        return token;
    }


    public UsernamePasswordAuthenticationToken validarToken(String token) {
        if (token != null) {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            String id = body.getId();

            if (id != null) {
                List<String> cargos = body.get(CARGOS_TOKEN, List.class);
                List<SimpleGrantedAuthority> cargosUsuario = cargos.stream()
                        .map(authority -> new SimpleGrantedAuthority(authority))
                        .toList();
                return new UsernamePasswordAuthenticationToken(id, null, cargosUsuario);
            }
        }
        return null;
    }
}