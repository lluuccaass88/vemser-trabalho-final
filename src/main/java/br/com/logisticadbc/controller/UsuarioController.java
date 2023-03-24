package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.in.UsuarioCreateDTO;
import br.com.logisticadbc.dto.in.UsuarioUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.enums.StatusViagem;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // FIXME
    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.criar(usuarioCreateDTO), HttpStatus.CREATED);
    }

    // FIXME
    @PutMapping
    public ResponseEntity<UsuarioDTO> update(@RequestParam("idUsuario") Integer idUsuario,
                                                 @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(usuarioService.editar(idUsuario, usuarioUpdateDTO), HttpStatus.OK);
    }

    // FIXME
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException {

        usuarioService.deletar(idUsuario);
        return ResponseEntity.ok().build();
    }

    // FIXME listar todos com paginaçao
    @GetMapping
    public ResponseEntity<PageDTO<UsuarioDTO>> listAll() {
        return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
    }

    // FIXME buscar por id
    @GetMapping("/buscar-por-id")
    public ResponseEntity<UsuarioDTO> findById(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.listarPorId(idUsuario), HttpStatus.OK);
    }

    // FIXME listar por cargo
    @GetMapping("/listar-por-cargo")
    public ResponseEntity<PageDTO<UsuarioDTO>> listByRole(
            @RequestParam(value = "cargo") String cargo,
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) {
        return new ResponseEntity<>(
                usuarioService.listarPorCargo(cargo, pagina, tamanho), HttpStatus.OK);
    }

    // FIXME listar por cargo e status (aitvos/inativos)
    @GetMapping("/listar-por-cargo-status/paginacao")
    public ResponseEntity<PageDTO<UsuarioDTO>> listByRoleAndStatus(
            @RequestParam(value = "cargo") String cargo,
            @RequestParam(value = "status") StatusViagem statusViagem,
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) {
        return new ResponseEntity<>(
                usuarioService.listarPorCargoEStatus(cargo, statusViagem, pagina, tamanho), HttpStatus.OK);
    }

    // FIXME relatorio completo com paginado
    @GetMapping("/relatorio-completo")
    public ResponseEntity<PageDTO<UsuarioDTO>> generateReportComplete(
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) {
        return new ResponseEntity<>(usuarioService.gerarRelatorioCompleto(pagina, tamanho)
                , HttpStatus.OK);
    }
}
