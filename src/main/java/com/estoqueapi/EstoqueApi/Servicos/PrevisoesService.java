package com.estoqueapi.EstoqueApi.Servicos;

import com.estoqueapi.EstoqueApi.Entidades.Previsoes;
import com.estoqueapi.EstoqueApi.Repositorios.PrevisoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class PrevisoesService {

    @Autowired
    private PrevisoesRepository previsoesRepository;
    @Autowired


    // Listar todas as previsões cadastradas
    public Iterable<Previsoes> listarPrevisoes(){
       return previsoesRepository.findAll();
    }
    //Cadastrar previsões
    @Transactional
    public Previsoes cadastrarPrevisoes(Previsoes pr){
        return previsoesRepository.save(pr);
  }

    //Filtrar previsão por idPrevisao
    public Previsoes filtrarId(Long idPrevisao){
        Optional<Previsoes> obj = previsoesRepository.findById(idPrevisao);
        Previsoes prev = null;
        try{
            prev = obj.get();
        }
        catch (NoSuchElementException exception){
            throw  new EntityNotFoundException("Previsão não localizada");
        }
        return prev;
    }

    //Filtrar por OC realizada ou não
    public Iterable<Previsoes> findByFinalizada(boolean finalizada){
        return previsoesRepository.findByFinalizada(finalizada);
    }

    //Alterar previsões - Alterar somente se tiver ativo (não realizado)
    public Previsoes alterarPrevisao(Long idPrevisao, Previsoes previsao){
        Previsoes prev = this.filtrarId(idPrevisao);
        prev.setFinalizada(previsao.isFinalizada());
        prev.setQuantidadePrevista(previsao.getQuantidadePrevista());
        prev.setDataPrevista(previsao.getDataPrevista());
        prev.setOrdem(previsao.getOrdem());
        prev.setItem(prev.getItem());

        return this.cadastrarPrevisoes(prev);
    }

    //Excluir previsões - VALIDAÇÃO Não pode excluir previsão já realizada
    @Transactional
    public void excluirPrevisao(Long idPrevisao){
        Previsoes previsoes = this.filtrarId(idPrevisao);
        previsoesRepository.delete(previsoes);
    }






}
