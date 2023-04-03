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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
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

    @Operation(summary = "Listar usuários", description = "Lista todos os usuários no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de usuários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listAll();

    @Operation(summary = "Listar usuário a partir de um id",
            description = "Lista um usuário do banco de dados a partir de um id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um usuário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-por-id")
    public ResponseEntity<UsuarioDTO> findById(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar usuários de um cargo",
            description = "Lista todos usuários de um cargo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com usuários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-cargo")
    public ResponseEntity<PageDTO<UsuarioDTO>> listByRole(
            @RequestParam(value = "cargo") String cargo,
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) throws RegraDeNegocioException ;

    @Operation(summary = "Listar usuários por cargo e status",
            description = "Lista todos usuários por cargo e status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com usuários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-cargo-status/paginacao")
    public ResponseEntity<PageDTO<UsuarioDTO>> listByRoleAndStatus(
            @RequestParam(value = "cargo") String cargo,
            @RequestParam(value = "status") StatusGeral status,
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho);

    @Operation(summary = "Listar usuários com informações completas",
            description = "Lista todos usuários com informações completas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com usuários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/relatorio-completo")
    public ResponseEntity<PageDTO<UsuarioCompletoDTO>> generateReportComplete(
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho);

    @Operation(summary = "Listar Motoristas livres",
            description = "Lista todos Motoristas livres")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com usuários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/motoristas-livres")
    public ResponseEntity<PageDTO<UsuarioDTO>> listAvailableDriver(
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho);


    @Operation(summary = "Enviar e-mail para usuário",
            description = "Envia e-mail para usuário interessado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna status"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("envia-email-possivel-cliente")
    public ResponseEntity<Void> update(@Email @RequestParam("emailCliente") String emailCliente,
                                             @RequestParam("nomeCliente") String nomeCliente)
            throws RegraDeNegocioException;
}

