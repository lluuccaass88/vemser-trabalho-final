//package br.com.logisticadbc.controller;
//
//import br.com.logisticadbc.controller.doc.MotoristaControllerDoc;
//import br.com.logisticadbc.dto.in.MotoristaCreateDTO;
//import br.com.logisticadbc.dto.out.*;
//import br.com.logisticadbc.dto.in.MotoristaUpdateDTO;
//import br.com.logisticadbc.exceptions.RegraDeNegocioException;
//import br.com.logisticadbc.service.MotoristaService;
//import br.com.logisticadbc.service.ValidacaoService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/motorista")
//@Validated
//@Slf4j
//public class MotoristaController implements MotoristaControllerDoc {
//
//    private final MotoristaService motoristaService;
//    private final ValidacaoService validacaoService;
//
//    @GetMapping
//    public ResponseEntity<List<MotoristaDTO>> listAll() {
//        return ResponseEntity.ok(motoristaService.listar());
//    }
//
//    @GetMapping("/buscar-por-id")
//    public ResponseEntity<MotoristaDTO> findById(@RequestParam("idMotorista") Integer idMotorista)
//            throws RegraDeNegocioException {
//        return new ResponseEntity<>(motoristaService.listarPorId(idMotorista), HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<MotoristaDTO> create(@Valid @RequestBody MotoristaCreateDTO motoristaCreateDTO)
//            throws RegraDeNegocioException {
//        return new ResponseEntity<>(motoristaService.criar(motoristaCreateDTO), HttpStatus.CREATED);
//    }
//
//    @PutMapping
//    public ResponseEntity<MotoristaDTO> update(@RequestParam("idMotorista") Integer idMotorista,
//                                               @Valid @RequestBody MotoristaUpdateDTO motoristaUpdateDTO)
//            throws RegraDeNegocioException {
//
//        validacaoService.validacao(idMotorista, "motorista");
//        return new ResponseEntity<>(motoristaService.editar(idMotorista, motoristaUpdateDTO), HttpStatus.OK);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Void> delete(@RequestParam("idMotorista") Integer idMotorista) throws RegraDeNegocioException {
//
//        validacaoService.validacao(idMotorista, "motorista");
//        motoristaService.deletar(idMotorista);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/relatorio-completo")
//    public ResponseEntity<PageDTO<MotoristaCompletoDTO>> generateReportComplete(
//            @RequestParam(value = "page") Integer pagina,
//            @RequestParam(value = "size") Integer tamanho) {
//        return new ResponseEntity<>(motoristaService.gerarRelatorioMotoristasInformacoesCompletas(pagina, tamanho), HttpStatus.OK);
//    }
//
//    @GetMapping("/listar-ativo-disponivel/paginacao")
//    public ResponseEntity<PageDTO<MotoristaDTO>> listAllPagination(
//            @RequestParam(value = "page") Integer pagina,
//            @RequestParam(value = "size") Integer tamanho) {
//        return new ResponseEntity<>(
//                motoristaService.listarMotoristaDisponivelEAtivoOrdenadoPorNomeAsc(pagina, tamanho),
//                HttpStatus.OK);
//    }
//
//    @GetMapping("/listar-ativos")
//    public ResponseEntity<List<MotoristaDTO>> listActive() {
//        return new ResponseEntity<>(motoristaService.listarAtivos(), HttpStatus.OK);
//    }
//
//    @GetMapping("/listar-inativos")
//    public ResponseEntity<List<MotoristaDTO>> listInactive() {
//        return new ResponseEntity<>(motoristaService.listarInativos(), HttpStatus.OK);
//    }
//}