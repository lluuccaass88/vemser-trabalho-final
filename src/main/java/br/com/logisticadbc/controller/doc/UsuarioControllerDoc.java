package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.UsuarioCreateDTO;
import br.com.logisticadbc.dto.in.UsuarioUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.UsuarioCompletoDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface UsuarioControllerDoc {

    @Operation(summary = "Adicionar usuário", description = "Adicionar um usuário ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do usuário adicionada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Editar usuário", description = "Edita um usuário do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o usuário editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    public ResponseEntity<UsuarioDTO> update(@RequestParam("idUsuario") Integer idUsuario,
                                             @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Deletar usuário", description = "Deleta um usuário do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorno positivo ao deletar usuário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException;

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
