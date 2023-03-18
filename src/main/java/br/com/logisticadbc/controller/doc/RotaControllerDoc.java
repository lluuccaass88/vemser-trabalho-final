package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.RotaComPostosDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface RotaControllerDoc {

    @Operation(summary = "Listar Rotas", description = "Lista todas as rotas no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de rotas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<RotaDTO>> listAll();

    @Operation(summary = "Listar Rota a partir de um id",
            description = "Lista uma Rota do banco de dados a partir de um id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma rota"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-por-id")
    ResponseEntity<RotaDTO> findById(@RequestParam("idRota") Integer idRota) throws RegraDeNegocioException;

    @Operation(summary = "Adicionar Rotas", description = "Adicionar uma rota ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados da rota adicionada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idUsuario}")
    ResponseEntity<RotaDTO> create(@RequestParam("idUsuario") Integer idUsuario,
                                          @Valid @RequestBody RotaCreateDTO rotaCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Editar Rota", description = "Edita uma rota ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a rota editada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idRota}")
    ResponseEntity<RotaDTO> update(@RequestParam("idRota") Integer idRota,
                                          @Valid @RequestBody RotaCreateDTO rotaCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Deletar Rota", description = "Deleta uma rota ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorno positivo ao deletar a rota"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idRota}")
    ResponseEntity<Void> delete(@RequestParam("idRota") Integer idRota) throws RegraDeNegocioException;

    @Operation(summary = "Cadastrar Posto na Rota",
            description = "Cadastra posto na rota passando id de ambos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro positivo"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/cadastrar-posto")
    ResponseEntity<Void> linkEntities(@RequestParam("idRota") Integer idRota,
                                    @RequestParam("idPosto") Integer idPosto)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar Postos cadastrados na rota",
            description = "Lista postos da rota passando id da rota")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com postos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-postos-cadastrados")
    ResponseEntity<RotaComPostosDTO> listLink(@RequestParam("idRota") Integer idRota)
            throws RegraDeNegocioException;
}
