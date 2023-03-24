package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.UsuarioCreateDTO;
import br.com.logisticadbc.dto.in.UsuarioUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
public interface MotoristaControllerDoc {
    @Operation(summary = "Listar motoristas", description = "Lista todos os motoristas do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de motoristas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<UsuarioDTO>> listAll();

    @Operation(summary = "Listar Motorista a partir de um id",
            description = "Lista um Motorista do banco de dados a partir de um id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um Motorista"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-por-id")
    ResponseEntity<UsuarioDTO> findById(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException;

    @Operation(summary = "Adicionar motorista", description = "Adicionar um motorista no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do motorista adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Editar motorista", description = "Edita um motorista no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o motorista editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    ResponseEntity<UsuarioDTO> update(@RequestParam("idMotorista") Integer idMotorista,
                                               @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Deletar motorista", description = "Realiza um soft delete do motorista")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorno positivo ao deletar motorista"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    ResponseEntity<Void> delete(@RequestParam("idMotorista") Integer idMotorista) throws RegraDeNegocioException;

    @Operation(summary = "Listar motoristas ativos e disponiveis",
            description = "Lista todos os motoristas com esse filtro do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de motoristas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/ativo-disponivel/paginacao")
    ResponseEntity<PageDTO<UsuarioDTO>> listAllPagination(
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho);

    @Operation(summary = "Listar motoristas ativos",
            description = "Lista todos os motoristas ativos do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de motoristas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-ativos")
    ResponseEntity<List<UsuarioDTO>> listActive();

    @Operation(summary = "Listar motoristas inativos",
            description = "Lista todos os motoristas inativos do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de motoristas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-inativos")
    ResponseEntity<List<UsuarioDTO>> listInactive();
}
