package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.ColaboradorCreateDTO;
import br.com.logisticadbc.dto.in.ColaboradorUpdateDTO;
import br.com.logisticadbc.dto.out.ColaboradorDTO;
import br.com.logisticadbc.dto.out.MotoristaDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ColaboradorControllerDoc {

    @Operation(summary = "Listar Colaboradores", description = "Lista todos os Colaboradores do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de Colaboradores"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<List<ColaboradorDTO>> listAll();

    @Operation(summary = "Listar colaborador a partir de um id",
            description = "Lista um colaborador do banco de dados a partir de um id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um colaborador"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-por-id")
    ResponseEntity<ColaboradorDTO> findById(@RequestParam("idUsuario") Integer idUsuario)
    throws RegraDeNegocioException;

    @Operation(summary = "Adicionar colaborador", description = "Adicionar um colaborador no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do colaborador adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<ColaboradorDTO> create(@Valid @RequestBody ColaboradorCreateDTO colaboradorCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Edita colaborador", description = "Editar um colaborador no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o colaborador atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    ResponseEntity<ColaboradorDTO> update(@RequestParam("idColaborador") Integer idColaborador,
                                                 @Valid @RequestBody ColaboradorUpdateDTO colaboradorUpdateDTO)
            throws RegraDeNegocioException;


    @Operation(summary = "Deletar colaborador", description = "Realiza um soft delete do colaborador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorno positivo ao deletar a colaborador"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    ResponseEntity<Void> delete(@RequestParam("idColaborador") Integer idColaborador)
            throws RegraDeNegocioException ;

    @Operation(summary = "Listar Colaboradores ativos",
            description = "Lista todos os Colaboradores ativos do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de Colaboradores"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-ativos")
    ResponseEntity<List<ColaboradorDTO>> listActive();

    @Operation(summary = "Listar Colaboradores inativos",
            description = "Lista todos os Colaboradores inativos do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de Colaboradores"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-inativos")
    ResponseEntity<List<ColaboradorDTO>> listInactive();
}
