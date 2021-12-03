package br.com.alura.escolalura.escolalura.controllers;

import br.com.alura.escolalura.escolalura.models.Aluno;
import br.com.alura.escolalura.escolalura.repositories.AlunoRepository;
import br.com.alura.escolalura.escolalura.service.GeolocalizacaoService;
import com.google.maps.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private GeolocalizacaoService geolocalizacaoservice;

    @GetMapping("/aluno/cadastrar")
    public String cadastrar(Model model){

        model.addAttribute("aluno", new Aluno());

        return "aluno/cadastrar";
    }

    @PostMapping("/aluno/salvar")
    public String salvar(@ModelAttribute Aluno aluno){
        System.out.println(aluno);
        try {
            List<Double> latELong = geolocalizacaoservice.obterLatELongPor(aluno.getContato());
            aluno.getContato().setCoordinates(latELong);
            repository.salvar(aluno);
        } catch (Exception e) {
            System.out.println("Endereço não localizado");
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping("/aluno/listar")
    public String listar(Model model) {
        List<Aluno> alunos = repository.obterTodosAlunos();
        model.addAttribute("alunos", alunos);
        return "aluno/listar";
    }

    @GetMapping("aluno/visualizar/{id}")
    public String visualizar(@PathVariable String id, Model model) {

        Aluno aluno = repository.obterAlunoPor(id);

        model.addAttribute("aluno", aluno);

        return "aluno/visualizar";
    }

    @GetMapping("/aluno/pesquisarnome")
    public String pesquisarNome() {
        return "aluno/pesquisarnome";
    }

    @GetMapping("/aluno/pesquisar")
    public String pesquisar(@RequestParam("nome") String nome, Model model) {
        List<Aluno> alunos = repository.pesquisarPor(nome);

        model.addAttribute("alunos", alunos);
        return "aluno/pesquisarnome";
    }
}
