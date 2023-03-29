package br.com.logisticadbc.controller;

//import br.com.logisticadbc.controller.doc.UsuarioControllerDoc;
import br.com.logisticadbc.dto.in.UsuarioCreateDTO;
import br.com.logisticadbc.dto.in.UsuarioUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.UsuarioCompletoDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Usuário")
@RequestMapping("/usuario")
public class UsuarioController {//implements UsuarioControllerDoc {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.criar(usuarioCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> update(@RequestParam(value = "idUsuario", required = false) Integer idUsuario,
                                             @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(usuarioService.editar(idUsuario, usuarioUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException {

        usuarioService.deletar(idUsuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listAll() {
        return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<UsuarioDTO> findById(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.listarPorId(idUsuario), HttpStatus.OK);
    }

    @GetMapping("/listar-por-cargo")
    public ResponseEntity<PageDTO<UsuarioDTO>> listByRole(
            @RequestParam(value = "cargo") String cargo,
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) throws RegraDeNegocioException {
        return new ResponseEntity<>(
                usuarioService.listarPorCargo(cargo, pagina, tamanho), HttpStatus.OK);
    }

    @GetMapping("/listar-por-cargo-status/paginacao")
    public ResponseEntity<PageDTO<UsuarioDTO>> listByRoleAndStatus(
            @RequestParam(value = "cargo") String cargo,
            @RequestParam(value = "status") StatusGeral status,
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) {
        return new ResponseEntity<>(
                usuarioService.listarPorCargoEStatus(cargo, status, pagina, tamanho), HttpStatus.OK);
    }

    @GetMapping("/relatorio-completo")
    public ResponseEntity<PageDTO<UsuarioCompletoDTO>> generateReportComplete(
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) {
        return new ResponseEntity<>(usuarioService.gerarRelatorioCompleto(pagina, tamanho)
                , HttpStatus.OK);
    }

    @GetMapping("/motoristas-livres")
    public ResponseEntity<PageDTO<UsuarioDTO>> listAvailableDriver(
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) {
        return new ResponseEntity<>(usuarioService.listarMotoristasLivres(pagina, tamanho), HttpStatus.OK);
    }
}
