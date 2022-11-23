package com.estoqueapi.EstoqueApi.Servicos;

import com.estoqueapi.EstoqueApi.Entidades.Usuario;
import com.estoqueapi.EstoqueApi.Enums.PerfilUsuario;
import com.estoqueapi.EstoqueApi.Exceptions.AlteracaoNaoPermitidaException;
import com.estoqueapi.EstoqueApi.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    private boolean usuarioExiste(String email) {
        return usuarioRepository.findByEmail(email).orElse(null) != null;
    }

    public Usuario consultarByEmail(String email){
       return usuarioRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioById(long idUsuario) {
        Usuario u = usuarioRepository.findById(idUsuario).orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));
        return u;
    }

    public Usuario salvar(Usuario usuario) {
        Usuario vUsuario = validarUsuario(usuario);
        if(vUsuario.getIdUsuario() == 0 && this.usuarioExiste(usuario.getEmail())){
            throw new AlteracaoNaoPermitidaException("Email ja existe");
        }

        return usuarioRepository.save(vUsuario);
    }

    private Usuario validarUsuario(Usuario usuarios){

        if(usuarios.getNome().isEmpty() || usuarios.getNome().isBlank()){
            throw new AlteracaoNaoPermitidaException("nome vazio");
        }else if(usuarios.getPerfil() == null){
            throw new AlteracaoNaoPermitidaException("perfil vazio");
        }else if(usuarios.getSenha().isEmpty() || usuarios.getSenha().isBlank()){
            throw new AlteracaoNaoPermitidaException("senha vazia");
        }else if(usuarios.getSenha().isEmpty() || usuarios.getSenha().isBlank()){
            throw new AlteracaoNaoPermitidaException("senha vazia");
        }else {
            return usuarios;
        }

    }



    public Usuario alterar(String email, Usuario usuario) {

        Usuario vUsuario = consultarByEmail(email);

        if(!usuario.getNome().isEmpty() && !usuario.getNome().isBlank()){
            vUsuario.setNome(usuario.getNome());
        }
        if(usuario.getPerfil() != null){
            vUsuario.setPerfil(usuario.getPerfil());
        }
        if(!usuario.getSenha().isEmpty() && !usuario.getSenha().isBlank()){
            vUsuario.setSenha(usuario.getSenha());
        }
        if(!usuario.getEmail().isEmpty() && !usuario.getEmail().isBlank()){
            vUsuario.setEmail(usuario.getEmail());
        }

        return this.salvar(vUsuario);

    }

    public Usuario desabilitarUsuario(String email) {

        Usuario vUsuario = this.consultarByEmail(email);

        vUsuario.setPerfil(PerfilUsuario.DESABILITADO);

        return this.salvar(vUsuario);
    }
}