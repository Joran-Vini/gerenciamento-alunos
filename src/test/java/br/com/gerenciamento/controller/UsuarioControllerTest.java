package br.com.gerenciamento.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void loginCredenciaisValidas() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario1");
        usuario.setEmail("123@gmail.com");
        usuario.setSenha("123");

        serviceUsuario.salvarUsuario(usuario);

        this.mockMvc.perform(post("/login")
                .param("user", "usuario1")
                .param("senha", "123"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"));
    }    

    @Test
    public void loginComCredenciaisInvalidas() throws Exception {
        this.mockMvc.perform(post("/login")
                .param("user", "usuario_inexistente")
                .param("senha", "senha_errada"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("msg"));
    }

    @Test
    public void loginComSenhaInvalida() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario2");
        usuario.setEmail("usuario2@gmail.com");
        usuario.setSenha("123");
        serviceUsuario.salvarUsuario(usuario);

        this.mockMvc.perform(post("/login")
                .param("user", "usuario2")
                .param("senha", "senha_errada"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("msg"));
    }

    @Test
    public void loginComUsuarioInvalido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario1");
        usuario.setEmail("usuario1@gmail.com");
        usuario.setSenha("123");
        serviceUsuario.salvarUsuario(usuario);
        this.mockMvc.perform(post("/login")
                .param("user", "usuario_inexistente")
                .param("senha", "123"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("msg"));
    }
}
