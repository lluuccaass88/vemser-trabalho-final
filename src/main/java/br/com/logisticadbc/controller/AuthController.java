package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.AuthControllerDoc;
import br.com.logisticadbc.dto.in.LoginDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Validated
@RequiredArgsConstructor
@RestController
@Tag(name = "Autenticação")
@RequestMapping("/auth")
public class AuthController implements AuthControllerDoc {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        // adiciona mecanismo de autenticação para verificar se o usuário é válido e retornar o token
        return new ResponseEntity<>(usuarioService.autenticar(loginDTO), HttpStatus.ACCEPTED);
    }

    @PutMapping("recuperar-senha")
    public ResponseEntity<Void> recoverPassword(@Email @RequestParam("emailUsuario") String emailUsuario) throws RegraDeNegocioException {
        // adiciona mecanismo de autenticação para verificar se o usuário é válido e retornar o token
        usuarioService.recuperarSenha(emailUsuario);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/usuario-logado")
    public ResponseEntity<UsuarioDTO> findLoggedUser() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(), HttpStatus.OK);
    }
}
