package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.PostoCreateDTO;
import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface PostoControllerDoc {

    @Operation(summary = "Listar Postos", description = "Lista todas os postos no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de postos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<PostoDTO>> listAll();

    @Operation(summary = "Listar Posto a partir de um id",
            description = "Lista um Posto do banco de dados a partir de um id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um posto"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-por-id")
    ResponseEntity<PostoDTO> findById(@RequestParam("idPosto") Integer idPosto) throws RegraDeNegocioException;

    @Operation(summary = "Adicionar Posto", description = "Adicionar uma viagem no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do posto adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<PostoDTO> create(@RequestParam("idColaborador") Integer idColaborador,
                                           @Valid @RequestBody PostoCreateDTO postoCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Editar Posto", description = "Edita um posto do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o posto editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    ResponseEntity<PostoDTO> update(@RequestParam("idColaborador") Integer idColaborador,
                                           @RequestParam("idPosto") Integer idPosto,
                                           @Valid @RequestBody PostoCreateDTO postoCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Deletar Posto", description = "Deleta um posto do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorno positivo ao deletar a viagem"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    ResponseEntity<Void> delete(@RequestParam("idColaborador") Integer idColaborador,
                                @RequestParam("idPosto") Integer idPosto) throws RegraDeNegocioException ;

    @Operation(summary = "Listar postos de um colaborador",
            description = "Lista todos postos de um colaborador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com postos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-colaborador")
    ResponseEntity<List<PostoDTO>> listByIdUser(@RequestParam("idColaborador") Integer idColaborador)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar postos ativos",
            description = "Lista todos postos ativos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com postos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-ativos")
    ResponseEntity<List<PostoDTO>> listByActiveGassStation();

    @Operation(summary = "Listar postos inativos",
            description = "Lista todos postos inativos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com postos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-inativos")
    ResponseEntity<List<PostoDTO>> listByInactiveGassStation();

}
