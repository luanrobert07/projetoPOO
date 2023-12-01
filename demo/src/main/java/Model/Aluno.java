package Model;

import Model.interfaces.estudarDisciplina;
import Model.interfaces.aprenderHabilidades;

import java.util.Scanner;

public class Aluno implements estudarDisciplina, aprenderHabilidades {
    private int idBruxo;
    private String casa;

    public Aluno() {
    }

    public Aluno(int idBruxo, String casa) {
        this.idBruxo = idBruxo;
        this.casa = casa;
    }

    public int getIdBruxo() {
        return idBruxo;
    }

    public void setIdBruxo(int idBruxo) {
        this.idBruxo = idBruxo;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    @Override
    public void aprendendo() {
        System.out.println("Aluno aprendendo novas habilidades");
    }

    @Override
    public void estudar() {
        System.out.println("Aluno estudando");
    }

    public void selecionarCasa(Scanner scanner) {

        System.out.println("Bem-vindo à seleção de casas em Hogwarts!");
        System.out.println("Eu sou o Chapéu Seletor");
        System.out.println("Irei fazer algumas perguntas para determinar a sua casa.");

        System.out.println("*********************");
        System.out.println("1. Qual qualidade você valoriza mais?");
        System.out.println("*********************");
        System.out.println("a) Coragem");
        System.out.println("b) Lealdade");
        System.out.println("c) Inteligência");
        System.out.println("d) Astúcia");

        char resposta1 = scanner.nextLine().toLowerCase().charAt(0);

        System.out.println("Huummm... Interessante..");

        System.out.println("*********************");
        System.out.println("2. Como você reage sob pressão?");
        System.out.println("*********************");
        System.out.println("a) Agir rapidamente");
        System.out.println("b) Manter a calma");
        System.out.println("c) Pensar estrategicamente");
        System.out.println("d) Adaptação");

        char resposta2 = scanner.nextLine().toLowerCase().charAt(0);

        System.out.println("Ok, já sei em qual casa você vai ficar...");
        if (resposta1 == 'a' && resposta2 == 'a') {
            this.casa = "Gryffindor";
        } else if (resposta1 == 'b' && resposta2 == 'b') {
            this.casa = "Hufflepuff";
        } else if (resposta1 == 'c' && resposta2 == 'c') {
            this.casa = "Ravenclaw";
        } else if (resposta1 == 'd' && resposta2 == 'd') {
            this.casa = "Slytherin";
        } else if (resposta1 == 'a' && resposta2 == 'b') {
            this.casa = "Gryffindor";
        } else if (resposta1 == 'a' && resposta2 == 'c') {
            this.casa = "Gryffindor";
        } else if (resposta1 == 'a' && resposta2 == 'd') {
            this.casa = "Gryffindor";
        } else if (resposta1 == 'b' && resposta2 == 'a') {
            this.casa = "Hufflepuff";
        } else if (resposta1 == 'b' && resposta2 == 'c') {
            this.casa = "Hufflepuff";
        } else if (resposta1 == 'b' && resposta2 == 'd') {
            this.casa = "Hufflepuff";
        } else if (resposta1 == 'c' && resposta2 == 'a') {
            this.casa = "Ravenclaw";
        } else if (resposta1 == 'c' && resposta2 == 'b') {
            this.casa = "Ravenclaw";
        } else if (resposta1 == 'c' && resposta2 == 'd') {
            this.casa = "Ravenclaw";
        } else if (resposta1 == 'd' && resposta2 == 'a') {
            this.casa = "Slytherin";
        } else if (resposta1 == 'd' && resposta2 == 'b') {
            this.casa = "Slytherin";
        } else if (resposta1 == 'd' && resposta2 == 'c') {
            this.casa = "Slytherin";
        } else {
            System.out.println("Não foi possível determinar a casa. Talvez o Chapéu Seletor esteja confuso!");
        }

        System.out.println("Você foi selecionado para a casa " + this.casa + "!");
    }
}
