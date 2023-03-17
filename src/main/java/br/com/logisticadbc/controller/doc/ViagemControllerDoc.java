package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.ViagemCreateDTO;
import br.com.logisticadbc.dto.out.ViagemDTO;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface ViagemControllerDoc {
    // EXEMPLO
    @Operation(summary = "Adicionar Viagem", description = "Adicionar uma viagem")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados da viagem adicionada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ViagemDTO> create (@Valid @RequestBody ViagemCreateDTO viagemCreateDTO) throws Exception;

    @Operation(summary = "Listar Viagens", description = "Lista todas as viagens do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de Viagens"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<List<ViagemDTO>> listar() throws Exception;

    @Operation(summary = "Listar Viagens finalizadas", description = "Lista todas as viagens finalizadas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de Viagens finalizadas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<List<ViagemDTO>> listarViagensFinalizadas() throws Exception;


    @Operation(summary = "Listar Viagem a partir de um id", description = "Lista uma Viagem do banco a partir de um id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma Viagem"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ViagemDTO> listarPorId(@PathVariable("idViagem") Integer id) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Atualizar Viagem", description = "Atualiza uma Viagem no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a Viagem atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ViagemDTO> update(@PathVariable("idViagem") Integer id,
                                     @Valid @RequestBody ViagemCreateDTO viagemUpdateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Deletar Viagem", description = "Deleta uma Viagem no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Informa que deu certo deletar uma Viagem no banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ViagemDTO> delete(@PathVariable("idViagem") Integer id) throws RegraDeNegocioException;
}
