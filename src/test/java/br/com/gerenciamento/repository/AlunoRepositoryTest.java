package br.com.gerenciamento.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {
    @Autowired
    private AlunoRepository alunoRepository;

    @Test public void ChecarAlunosInativos() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Aluno1");
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123");
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setTurno(Turno.MATUTINO);
        alunoRepository.save(aluno1);
        Aluno aluno2 = new Aluno();
        aluno2.setNome("Aluno2");
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("1234");
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setTurno(Turno.MATUTINO);
        alunoRepository.save(aluno2);
        Assert.assertTrue(alunoRepository.findByStatusInativo().size() == 1);
    }

    @Test
    public void BuscarPorId() {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno1");
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setTurno(Turno.MATUTINO);
        alunoRepository.save(aluno);
        Aluno alunoRetorno = alunoRepository.findById(aluno.getId()).get();
        Assert.assertTrue(alunoRetorno.getNome().equals("Aluno1"));
    }

    @Test
    public void DeletarPorId() {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno1");
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setTurno(Turno.MATUTINO);
        alunoRepository.save(aluno);
        alunoRepository.deleteById(aluno.getId());
        Assert.assertFalse(alunoRepository.findById(aluno.getId()).isPresent());
    }

    @Test
    public void BuscarPorNome() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setTurno(Turno.MATUTINO);
        alunoRepository.save(aluno);
        Assert.assertTrue(alunoRepository.findByNomeContainingIgnoreCase("vinicius").size() == 1);
    }
}