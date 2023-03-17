package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.out.CaminhaoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface CaminhaoControllerDoc {

    @Operation(summary = "Adicionar Caminhão", description = "Adicionar um caminhão no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do Caminhão adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<CaminhaoDTO> adicionar(@Valid @RequestBody CaminhaoCreateDTO caminhao) throws Exception;

    @Operation(summary = "Listar Caminhões", description = "Listar os caminhões do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os dados dos Caminões  do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<CaminhaoDTO>> listar() throws Exception;

    @Operation(summary = "Editar Caminhão", description = "Edita os dados do caminhão do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os dados do Caminhão editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CaminhaoDTO> editar(@PathVariable Integer id, @Valid @RequestBody CaminhaoCreateDTO caminhao) throws Exception;

    @Operation(summary = "Deletar Caminhão", description = "Deletar Caminhão do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorna a confirmação da exclusão do caminhão"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Abastecer caminhão", description = "Abastecer caminhão")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a quantidade de gasolina alterada do caminhao no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/abastecer/{id}")
    public ResponseEntity<CaminhaoDTO> abastecer(@PathVariable Integer id,  Integer gasolina) throws Exception;

    @Operation(summary = "Listar caminhões disponiveis", description = "Listar caminhões disponiveis no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os dados do caminhões disponiveis"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/caminhoesdisponiveis")
    public ResponseEntity<List<CaminhaoDTO>> listarCaminhoesDisponiveis() throws Exception;
}
