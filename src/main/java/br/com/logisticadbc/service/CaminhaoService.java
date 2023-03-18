
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.out.CaminhaoDTO;
import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.CaminhaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CaminhaoService {

    private final CaminhaoRepository caminhaoRepository;
    private final ColaboradorService colaboradorService;
    private final ObjectMapper objectMapper;

    public CaminhaoDTO criar(Integer idUsuario, CaminhaoCreateDTO caminhaoCreateDTO)
            throws RegraDeNegocioException {
        try{
            ColaboradorEntity colaboradorEntity = colaboradorService.buscarPorId(idUsuario);

            CaminhaoEntity caminhaoEntity = objectMapper.convertValue(caminhaoCreateDTO, CaminhaoEntity.class);
            caminhaoEntity.setStatusCaminhao(StatusCaminhao.ESTACIONADO);

            caminhaoEntity.setColaborador(colaboradorEntity);

            caminhaoRepository.save(caminhaoEntity);

            return objectMapper.convertValue(caminhaoEntity, CaminhaoDTO.class);
        }catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public CaminhaoDTO abastecer(Integer idCaminhao, Integer gasolina) throws RegraDeNegocioException {
        try{
            CaminhaoEntity caminhaoRecuperado = buscarPorId(idCaminhao);

            if(gasolina <= 0){
                throw new RegraDeNegocioException("A gasolina informada não pode ser menor ou igual a 0");
            }else if(caminhaoRecuperado.getNivelCombustivel() + gasolina > 100){
                throw new RegraDeNegocioException("Limite de gasolina excedido, por favor digite outro valor");
            }

            caminhaoRecuperado.setNivelCombustivel(caminhaoRecuperado.getNivelCombustivel() + gasolina);

            caminhaoRepository.save(caminhaoRecuperado);

            return objectMapper.convertValue(caminhaoRecuperado, CaminhaoDTO.class);
        }catch (Exception e){
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    public void deletar(Integer idCaminhao) throws Exception {
        try{
            buscarPorId(idCaminhao);
            caminhaoRepository.deleteById(idCaminhao);

        }catch (Exception e){
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão");
        }
    }

    public List<CaminhaoDTO> listar() {
        return caminhaoRepository.findAll()
                .stream()
                .map(caminhao -> objectMapper.convertValue(caminhao, CaminhaoDTO.class))
                .toList();
    }

    public CaminhaoDTO ListarCaminhoesLivres(Integer idCaminhao) throws RegraDeNegocioException {



        return null;
    }

//    public CaminhaoDTO listarPorId(Integer idCaminhao) throws RegraDeNegocioException {
//        CaminhaoEntity caminhaoRecuperado = buscarPorId(idCaminhao);
//        CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhaoRecuperado, CaminhaoDTO.class);
//        return caminhaoDTO;
//    }

    public CaminhaoEntity buscarPorId(Integer idCaminhao) throws RegraDeNegocioException{
        return caminhaoRepository.findById(idCaminhao)
                .orElseThrow(() -> new RegraDeNegocioException("Caminhao não encontrado"));
    }

    public void mudarStatus(CaminhaoEntity caminhao, StatusCaminhao status) {
        caminhao.setStatusCaminhao(status);
        caminhaoRepository.save(caminhao);
    }

}
