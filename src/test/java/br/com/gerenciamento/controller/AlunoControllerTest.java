package br.com.gerenciamento.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void pesquisarAlunoPorNome() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno1");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        aluno.setTurno(Turno.NOTURNO);
        this.serviceAluno.save(aluno);

        MvcResult resultado = this.mockMvc.perform(post("/pesquisar-aluno").param("nome", "Aluno1"))
        .andExpect(status().isOk())
        .andExpect(view().name("Aluno/pesquisa-resultado"))
        .andExpect(model().attributeExists("ListaDeAlunos"))
        .andReturn();

        ModelAndView modelAndView = resultado.getModelAndView();
        Assert.assertNotNull(modelAndView);

        @SuppressWarnings("unchecked")
        List<Aluno> alunos = (List<Aluno>) modelAndView.getModel().get("ListaDeAlunos");
        Assert.assertFalse(alunos.isEmpty());
        Assert.assertTrue(alunos.stream().anyMatch(a -> "Aluno1".equals(a.getNome())));

    }

    @Test
    public void pesquisarAlunosComNomeVazioDeveRetornarListaCompleta() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("AlunoTeste");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        aluno.setTurno(Turno.NOTURNO);
        this.serviceAluno.save(aluno);

        MvcResult resultado = this.mockMvc.perform(post("/pesquisar-aluno").param("nome", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/pesquisa-resultado"))
                .andExpect(model().attributeExists("ListaDeAlunos"))
                .andReturn();

        ModelAndView modelAndView = resultado.getModelAndView();
        Assert.assertNotNull(modelAndView);

        @SuppressWarnings("unchecked")
        List<Aluno> alunos = (List<Aluno>) modelAndView.getModel().get("ListaDeAlunos");
        Assert.assertFalse(alunos.isEmpty());
        Assert.assertTrue(alunos.stream().anyMatch(a -> "AlunoTeste".equals(a.getNome())));
    }

    @Test
    public void pesquisarAlunoComNomeInexistente() throws Exception {
        MvcResult resultado = this.mockMvc.perform(post("/pesquisar-aluno").param("nome", "NomeInexistente"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/pesquisa-resultado"))
                .andExpect(model().attributeExists("ListaDeAlunos"))
                .andReturn();

        ModelAndView modelAndView = resultado.getModelAndView();
        Assert.assertNotNull(modelAndView);

        @SuppressWarnings("unchecked")
        List<Aluno> alunos = (List<Aluno>) modelAndView.getModel().get("ListaDeAlunos");
        Assert.assertTrue(alunos.isEmpty());
    }

    @Test
    public void pesquisarAlunoPorNomeComEspacos() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno Com Espacos");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        aluno.setTurno(Turno.NOTURNO);
        this.serviceAluno.save(aluno);

        MvcResult resultado = this.mockMvc.perform(post("/pesquisar-aluno").param("nome", "Aluno Com Espacos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/pesquisa-resultado"))
                .andExpect(model().attributeExists("ListaDeAlunos"))
                .andReturn();

        ModelAndView modelAndView = resultado.getModelAndView();
        Assert.assertNotNull(modelAndView);

        @SuppressWarnings("unchecked")
        List<Aluno> alunos = (List<Aluno>) modelAndView.getModel().get("ListaDeAlunos");
        Assert.assertFalse(alunos.isEmpty());
        Assert.assertTrue(alunos.stream().anyMatch(a -> "Aluno Com Espacos".equals(a.getNome())));
    }
}
