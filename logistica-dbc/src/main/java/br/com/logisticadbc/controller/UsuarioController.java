package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.impl.IUsuarioControllerDoc;
import br.com.logisticadbc.dto.UsuarioCreateDTO;
import br.com.logisticadbc.dto.UsuarioDTO;
import br.com.logisticadbc.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuario") // http://localhost:8080/usuario
@Validated
@Slf4j
public class UsuarioController implements IUsuarioControllerDoc {

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

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() throws Exception {
        log.info("Recebendo requisição para listar todos os usuários");
        return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editar(@PathVariable Integer id,
                                             @Valid @RequestBody UsuarioCreateDTO usuario) throws Exception {
        log.info("Recebendo requisição para atualizar um usuário");
        UsuarioDTO usuarioDTO = usuarioService.editar(id, usuario);
        log.info("Usuário atualizado com sucesso" + usuarioDTO);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws Exception {
        log.info("Recebendo requisição para remover um usuário");
        usuarioService.deletar(id);
        log.info("Usuário removido com sucesso");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}