package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void cadastrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario1");
        usuario.setEmail("usuario1@gmail.com");
        usuario.setSenha("123");
        try {
            this.serviceUsuario.salvarUsuario(usuario);
        } catch (Exception e) {
            Assert.fail("Não deveria lançar exceção");
        }
        Assert.assertTrue(usuario.getId() != null);
    }

    @Test 
    public void cadastrarEmailInvalido() {
        Usuario usuario = new Usuario();
        usuario.setEmail("emailInvalido");
        usuario.setUser("usuario1");
        usuario.setSenha("123");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }

    @Test
    public void cadastrarUsuarioSemSenha() {
        Usuario usuario = new Usuario();
        usuario.setEmail("123@gmail.com");
        usuario.setUser("usuario1");
        Assert.assertThrows(NullPointerException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }
    
    @Test
    public void cadastrarEmailRepetido() throws Exception {
        String email = "duplicado@gmail.com";

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setUser("usuario1");
        usuario.setSenha("123");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail(email);
        usuario2.setUser("usuario2");
        usuario2.setSenha("456");

        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario2);
        });
    }
}
