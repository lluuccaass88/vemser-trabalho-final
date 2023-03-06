package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.PostoCreateDTO;
import br.com.logisticadbc.dto.PostoDTO;
import br.com.logisticadbc.entity.Posto;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.PostoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostoService {
    private  final PostoRepository postoRepository;
    private final ObjectMapper objectMapper;

    //private final


    public PostoService(PostoRepository postoRepository, ObjectMapper objectMapper) {
        this.postoRepository = postoRepository;
        this.objectMapper = objectMapper;
    }

//    //Criar
    public PostoDTO create(PostoCreateDTO posto) throws RegraDeNegocioException {
        Posto postoEntity = objectMapper.convertValue(posto, Posto.class);

        Posto postoSalvo = postoRepository.create(postoEntity);

        return objectMapper.convertValue(postoSalvo, PostoDTO.class);
    }
//
//    //Listat por  id de endereco
//    public List<EnderecoDTO> listByIdEndereco(int id) throws RegraDeNegocioException {
//        return enderecoRepository.listByIdendereco(id).stream()
//                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    //Listar tudo
//    public List<EnderecoDTO> list(){
//        return enderecoRepository.list().stream()
//                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    //Listar por id de pessoa
//    public List<EnderecoDTO> listByIdPessoa(int id) throws RegraDeNegocioException {
//        return enderecoRepository.listByIdPessoa(id).stream()
//                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    //Deletar item
//    public void delete(Integer id) throws RegraDeNegocioException {
//        Endereco enderecoRecuperado = getEndereco(id);
//        enderecoRepository.delete(enderecoRecuperado);
//    }
//
//    public EnderecoDTO update(Integer id, EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException {
//        Endereco enderecoRecuperado = getEndereco(id);
//
//        enderecoRecuperado.setIdPessoa(enderecoAtualizar.getIdPessoa());
//        enderecoRecuperado.setCep(enderecoAtualizar.getCep());
//        enderecoRecuperado.setNumero(enderecoAtualizar.getNumero());
//        enderecoRecuperado.setCidade(enderecoAtualizar.getCidade());
//        enderecoRecuperado.setEstado(enderecoAtualizar.getEstado());
//        enderecoRecuperado.setComplemento(enderecoAtualizar.getComplemento());
//        enderecoRecuperado.setLogradouro(enderecoAtualizar.getLogradouro());
//        enderecoRecuperado.setPais(enderecoAtualizar.getPais());
//        enderecoRecuperado.setTipo(enderecoAtualizar.getTipo());
//
//        return objectMapper.convertValue(enderecoRecuperado, EnderecoDTO.class);
//    }
//
//    private Endereco getEndereco(Integer id) throws RegraDeNegocioException {
//        Endereco enderecoRecuperado = enderecoRepository.list().stream()
//                .filter(endereco -> endereco.getIdEndereco() == id)
//                .findFirst()
//                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrada!"));
//        return enderecoRecuperado;
//    }

}
