package br.com.gerenciamento.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test 
    public void cadastrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario1");
        usuario.setEmail("123@gmail.com");
        usuario.setSenha("123");
        usuarioRepository.save(usuario);
        Assert.assertNotNull(usuario.getId());
    }
    
    @Test
    public void buscarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario1");
        usuario.setEmail("123@gmail.com");
        usuario.setSenha("123");
        usuarioRepository.save(usuario);
        Assert.assertNotNull(usuarioRepository.buscarLogin("usuario1", "123"));
    }

    @Test
    public void buscarUsuarioInexistente() {
        Assert.assertNull(usuarioRepository.buscarLogin("usuarioInexistente", "123"));
    }

    @Test
    public void deletarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario1");
        usuario.setEmail("123@gmail.com");
        usuario.setSenha("123");
        usuarioRepository.save(usuario);
        usuarioRepository.deleteById(usuario.getId());
        Assert.assertNull(usuarioRepository.buscarLogin("usuario1", "123"));
    }
}
