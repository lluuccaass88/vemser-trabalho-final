package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.UsuarioCreateDTO;
import br.com.logisticadbc.dto.UsuarioDTO;
import br.com.logisticadbc.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuario") // http://localhost:8080/usuario
@Validated
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping // http://localhost:8080/usuario
    public ResponseEntity<UsuarioDTO> adicionar(@Valid @RequestBody UsuarioCreateDTO usuario) throws Exception {

        log.info("Recebendo requisição para adicionar um novo usuário");
        UsuarioDTO usuarioDTO = usuarioService.adicionar(usuario);
        log.info("Usuário adicionado com sucesso" + usuarioDTO);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.CREATED);

    }
}