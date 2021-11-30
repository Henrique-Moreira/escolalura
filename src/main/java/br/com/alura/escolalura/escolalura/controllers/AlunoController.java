package br.com.alura.escolalura.escolalura.controllers;

import br.com.alura.escolalura.escolalura.models.Aluno;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlunoController {

    @GetMapping("/aluno/cadastrar")
    public String cadastrar(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "aluno/cadastrar";
    }
}
