package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.out.CaminhaoDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface CaminhaoControllerDoc {

    @Operation(summary = "Listar Caminhões", description = "Listar os caminhões do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os dados dos Caminões  do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<CaminhaoDTO>> listAll() ;

    @Operation(summary = "Listar caminhão a partir de um id",
            description = "Lista um caminhão do banco de dados a partir de um id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um caminhão"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-por-id")
    ResponseEntity<CaminhaoDTO> findById(@RequestParam("idCaminhao") Integer idCaminhao)
            throws RegraDeNegocioException;

    @Operation(summary = "Adicionar Caminhão", description = "Adicionar um caminhão no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do Caminhão adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<CaminhaoDTO> create(@RequestParam("idColaborador") Integer idColaborador,
                                              @Valid @RequestBody CaminhaoCreateDTO caminhaoCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Abastecer caminhão", description = "Abastecer o caminhão")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o caminhão abastecido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/abastecer")
    ResponseEntity<CaminhaoDTO> update(@RequestParam("idMotorista") Integer idMotorista,
                                              @RequestParam("idCaminhao") Integer idCaminhao,
                                              @RequestParam("Quantidade de gasolina") Integer gasolina)
            throws RegraDeNegocioException;

    @Operation(summary = "Deletar Caminhão", description = "Deletar Caminhão do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorno positivo ao deletar motorista"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    ResponseEntity<Void> delete(@RequestParam("idColaborador") Integer idColaborador,
                                       @RequestParam("idCaminhao") Integer idCaminhao) throws RegraDeNegocioException;

    @Operation(summary = "Listar caminhões de um colaborador",
            description = "Lista todos caminhões de um colaborador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com caminhões"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-colaborador")
    ResponseEntity<List<CaminhaoDTO>> listByIdUser(@RequestParam("idColaborador") Integer idColaborador)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar caminhões disponiveis", description = "Listar caminhões disponiveis no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os dados do caminhões disponiveis"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-disponiveis")
    ResponseEntity<List<CaminhaoDTO>> listAllAvaiablesTrucks();

    @Operation(summary = "Listar caminhões ativos", description = "Listar caminhões ativos no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com caminhões"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-ativos")
    ResponseEntity<List<CaminhaoDTO>> listActive();

    @Operation(summary = "Listar caminhões inativos", description = "Listar caminhões inativos no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com caminhões"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-inativos")
    ResponseEntity<List<CaminhaoDTO>> listInactive();
}
