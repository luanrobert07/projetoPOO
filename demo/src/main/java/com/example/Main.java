package com.example;

import DAO.createTablesInDatabase.createAlunoDisciplinaTable;
import DAO.createTablesInDatabase.createAlunoTable;
import DAO.createTablesInDatabase.createBruxoTable;
import DAO.createTablesInDatabase.createDisciplinaTable;
import DAO.createTablesInDatabase.createProfessorTable;
import DAO.createTablesInDatabase.createUserTable;
import DAO.createTablesInDatabase.createmagiaTable;
import DAO.createTablesInDatabase.exceptions.disciplicaJaExisteException;
import DAO.login.LoginDAO;
import DAO.cadastro.UserDAO;
import DAO.cadastro.alunoDAO;
import DAO.cadastro.BruxoDAO;
import DAO.cadastro.DisciplinaDAO;
import Model.User;
import Model.Aluno;
import Model.AlunoDisciplinaInfo;
import Model.Bruxo;
import Model.Disciplina;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static int idUsuarioLogado;

    public static void main(String[] args) {

        //Criando as tabelas no banco de dados
        createUserTable userTableCreator = new createUserTable();
        createBruxoTable bruxoTableCreator = new createBruxoTable();
        createAlunoTable alunoTableCreator = new createAlunoTable();
        createProfessorTable professorTableCreator = new createProfessorTable();
        createmagiaTable magiaTableCreator = new createmagiaTable();
        createDisciplinaTable disciplinaTableCreator = new createDisciplinaTable();
        try {
            disciplinaTableCreator.adicionarDisciplinasIniciais();
        } catch (disciplicaJaExisteException e) {
            e.printStackTrace();
        }
        createAlunoDisciplinaTable alunoDisciplinaTableCreator = new createAlunoDisciplinaTable();

        boolean tabelaUserCriadaComSucesso = userTableCreator.createTable();
        boolean tabelaBruxoCriadaComSucesso = bruxoTableCreator.createTable();
        boolean tabelaAlunoCriadaComSucesso = alunoTableCreator.createTable();
        boolean tabelaDisciplinaCriadaComSucesso = disciplinaTableCreator.createTable();
        boolean tabelaAlunoDisciplicaCriadaComSucesso = alunoDisciplinaTableCreator.createTable();
        boolean tabelaProfessorCriadoComSucesso = professorTableCreator.createTable();
        boolean tabelaMagiaCriadaComSucesso = magiaTableCreator.createTable();


        if (tabelaUserCriadaComSucesso && tabelaBruxoCriadaComSucesso && tabelaAlunoCriadaComSucesso && tabelaDisciplinaCriadaComSucesso && tabelaAlunoDisciplicaCriadaComSucesso && tabelaMagiaCriadaComSucesso && tabelaProfessorCriadoComSucesso) {
            System.out.println("Tabelas criadas com sucesso!");
        } else {
            System.out.println("Falha ao criar as tabelas. Verifique os logs para detalhes.");
            return;
        }

        
        //Iniciando o Scanner
        Scanner scanner = new Scanner(System.in);

        //Painel inicial
        System.out.println("*********** Bem-vindo a Hogwarts! ***********");
        System.out.println("Você sente um calor repentino em seu bolso, como se algo estivesse prestes a acontecer.");
        System.out.println("Ao tocar o interior do seu bolso, você encontra uma carta lacrada com cera vermelha, com o selo de Hogwarts.");
        System.out.println("A carta parece pulsar com uma energia mágica, como se estivesse viva.");
        System.out.println();

        System.out.println("Ao abrir a carta, você vê palavras se formando diante de seus olhos, como se escritas por uma mão invisível.");
        System.out.println("A mensagem diz: 'Parabéns! Você foi aceito em Hogwarts, a Escola de Magia e Bruxaria!'");
        System.out.println("A carta continua a detalhar as maravilhas que aguardam, os desafios que virao e a magia que será explorada.");
        System.out.println();
        
        System.out.println();

        System.out.println("Bem-vindo(a) ao início de sua jornada mágica, jovem bruxo ou bruxa!");
        System.out.println("***********");

        // Opção para escolher entre cadastro e login
        System.out.println("Escolha uma opçao:");
        System.out.println("1. Cadastro");
        System.out.println("2. Login");

        int opcao = scanner.nextInt();
        scanner.nextLine(); 

        switch (opcao) {
            case 1:
                cadastrarUsuario(scanner);

            case 2:
            
                while ((idUsuarioLogado = realizarLogin(scanner)) == -1) {
                    System.out.println("Falha no login. Tente novamente.");
                }
                System.out.println("Bem-vindo ao sistema!");

                if (verificarPrimeiroLogin(idUsuarioLogado)) {
                    cadastrarNovoBruxo(idUsuarioLogado, scanner);
                    exibirOpcoesAdicionais(scanner);
                } else {
                    exibirOpcoesAdicionais(scanner);
                }
                break;
            default:
                System.out.println("Opçao inválida. Encerrando o programa.");
        }

        scanner.close();
    }


    private static void exibirOpcoesAdicionais(Scanner scanner) {
        BruxoDAO bruxoDAO = new BruxoDAO();
        alunoDAO alunoDAO = new alunoDAO();
        Aluno novoAluno = new Aluno();
        createmagiaTable magiaTableCreator = new createmagiaTable();
        createProfessorTable professorTableCreator = new createProfessorTable();
        
    
        boolean continuar = true;
    
        do {
            System.out.println("Escolha uma opçao:");
            System.out.println("1. Associar disciplinas a aluno");
            System.out.println("2. Mostrar informaçoes do Bruxo");
            System.out.println("3. Mostrar informaçoes dos Alunos");
            System.out.println("4. Levar aluno para estudar");
            System.out.println("5. Levar aluno para treinar habilidades");
            System.out.println("6. Mostrar as disciplinas dos alunos");
            System.out.println("7. Adicionar Professores na tabela");
            System.out.println("8. Adicionar Magias na tabela");
            System.out.println("9. Mostrar Professores");
            System.out.println("10. Mostrar Magias e os professores que ensinam");
						 System.out.println("11. Excluir Professor");
						 System.out.println("12. Atualizar Professor");
            System.out.println("13. Sair");
    
            int opcao = scanner.nextInt();
            scanner.nextLine(); 
    
            switch (opcao) {
                case 1:
                    associarDisciplinasAAluno(idUsuarioLogado, scanner);
                    break;
                case 2:
                    bruxoDAO.imprimirTodosBruxos();
                    break;
                case 3:
                    alunoDAO.imprimirTodosAlunos();
                    break;
                case 4:
                    novoAluno.estudar();
                    break;
                case 6:
                    exibirAlunosComDisciplinas();
                    break;
                case 5:
                    novoAluno.aprendendo();
                    break;
                case 7:
                    professorTableCreator.adicionarProfessoresIniciais();
                    break;
                case 8:
                    magiaTableCreator.adicionarMagiasIniciais();
                    break; 
                case 9:
                    professorTableCreator.mostrarProfessores();
                    break;
                case 10:
                    magiaTableCreator.mostrarMagiasEProfessores();
                    break;
									case 11:
		                System.out.println("Digite o ID do professor que deseja excluir:");
		                int idProfessorParaExcluir = scanner.nextInt();
		                boolean sucessoExclusao = professorTableCreator.excluirProfessor(idProfessorParaExcluir);
		
		                if (sucessoExclusao) {
		                    System.out.println("Professor excluído com sucesso!");
		                } else {
		                    System.out.println("Falha ao excluir professor.");
		                }
                break;

		            case 12:
		                System.out.println("Digite o ID do professor que deseja atualizar:");
		                int idProfessorParaAtualizar = scanner.nextInt();
		                scanner.nextLine(); 
		
		                System.out.println("Digite o novo nome do professor:");
		                String novoNomeProfessor = scanner.nextLine();
		
		                boolean sucessoAtualizacao = professorTableCreator.atualizarProfessor(idProfessorParaAtualizar, novoNomeProfessor);
		
		                if (sucessoAtualizacao) {
		                    System.out.println("Professor atualizado com sucesso!");
		                } else {
		                    System.out.println("Falha ao atualizar professor.");
		                }
		                break;
                case 13:
                    System.out.println("Bem-vindo à escola de magia e bruxaria de Hogwarts, jovem bruxo ou bruxa!");
                    System.out.println("Você acabou de atravessar a barreira da Plataforma 9 3/4 e embarcar no Hogwarts Express.");
                    System.out.println("O vapor da locomotiva escoa ao seu redor, enquanto o brilho da magia permeia o ar.");
                    System.out.println("Diante de você está o majestoso Castelo de Hogwarts, com suas torres imponentes e janelas iluminadas.");
                    System.out.println("A emoçao e o mistério pairam no ar, pois você se prepara para adentrar um mundo repleto de encantamentos e descobertas.");
                    System.out.println();
                
                    System.out.println("Ao cruzar os portoes do castelo, você é saudado pelo caloroso salao principal, onde as quatro mesas das casas se estendem até o horizonte.");
                    System.out.println("As velas flutuantes iluminam o ambiente, revelando centenas de rostos curiosos e ansiosos.");
                    System.out.println();
                
                    System.out.println("Os professores, cada um especialista em sua disciplina mágica, estao prontos para compartilhar seus conhecimentos e desafios.");
                    System.out.println("Você sentirá a magia nos corredores, nas salas de aula e nas inúmeras passagens secretas que Hogwarts guarda.");
                    System.out.println("Prepare-se para encarar desafios, aprimorar suas habilidades e criar laços duradouros com seus colegas de casa.");
                    System.out.println();
                
                    System.out.println("Lembre-se, o caminho que se desenha à sua frente está repleto de enigmas e aventuras.");
                    System.out.println("As criaturas mágicas que habitam os terrenos da escola, os desafios nas aulas de Transfiguração, Poçoes e Defesa Contra as Artes das Trevas.");
                    System.out.println("Mas nao se preocupe, você nao está sozinho nesta jornada. A amizade e a coragem sao armas poderosas.");
                    System.out.println();
                
                    System.out.println("Enquanto o semestre está prestes a começar, aproveite para explorar o incrível mundo mágico que Hogwarts oferece.");
                    System.out.println("Faça amizades, desvende mistérios, aprimore seus feitiços e prepare-se para os desafios que aguardam todos os bruxos e bruxas nesta incrível jornada.");
                    System.out.println();
                
                    System.out.println("Mas, por ora, o descanso merecido está à sua frente. O próximo semestre inicia no ano seguinte.");
                    System.out.println("Descanse, se divirta e esteja pronto para abraçar a magia que Hogwarts tem a oferecer.");
                    System.out.println();
                
                    System.out.println("Encerrando o programa. Adeus e até breve, jovem bruxo ou bruxa!");
                    continuar = false;
                    break;
                
                default:
                    System.out.println("Opçao inválida. Tente novamente.");
            }
    
        } while (continuar);
    }
    

//métodos para cadastrar

    private static void cadastrarUsuario(Scanner scanner) {
        System.out.println("*********************");
        System.out.println("Cadastro de Novo Usuario:");
        System.out.println("*********************");

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Digite o endereço: ");
        String endereco = scanner.nextLine();

        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        User newUser = new User(nome, email, senha, telefone, endereco, cpf);

        UserDAO cadastrarDAO = new UserDAO();
        boolean cadastroSucesso = cadastrarDAO.cadastrarUsuario(newUser);

        if (cadastroSucesso) {
            System.out.println("Cadastro realizado com sucesso!");
        } else {
            System.out.println("Falha ao cadastrar usuario. Verifique os logs para detalhes.");
        }
    }

    private static void cadastrarNovoBruxo(int idUsuarioLogado, Scanner scanner) {
    BruxoDAO cadastrarBruxoDAO = new BruxoDAO();

    if (cadastrarBruxoDAO.existeBruxoParaUsuario(idUsuarioLogado)) {
        System.out.println("Você já possui um bruxo cadastrado. Não é possível cadastrar mais de um.");
    } else {
        Bruxo novoBruxo = new Bruxo();

        System.out.print("Digite a varinha do bruxo: ");
        novoBruxo.setVarinha(scanner.nextLine());

        System.out.print("Digite o patrono do bruxo: ");
        novoBruxo.setPatrono(scanner.nextLine());

        System.out.print("Digite a habilidade mágica do bruxo: ");
        novoBruxo.setHabilidadeMagica(scanner.nextLine());

        novoBruxo.setIdUsuario(idUsuarioLogado);

        boolean cadastroBruxoSucesso = cadastrarBruxoDAO.cadastrarBruxo(novoBruxo);

        if (cadastroBruxoSucesso) {
            System.out.println("Bruxo cadastrado com sucesso!");

            cadastrarAluno(idUsuarioLogado, scanner);

        } else {
            System.out.println("Falha ao cadastrar o bruxo. Verifique os logs para detalhes.");
        }
    }
}

private static void cadastrarAluno(int idUsuarioLogado, Scanner scanner) {
    alunoDAO alunoDAO = new alunoDAO();

    Aluno novoAluno = new Aluno(idUsuarioLogado, null);

    novoAluno.selecionarCasa(scanner);

    String casa = novoAluno.getCasa();

    novoAluno.setCasa(casa);

    boolean cadastroAlunoSucesso = alunoDAO.cadastrarAluno(novoAluno);

    if (cadastroAlunoSucesso) {
        System.out.println("Aluno cadastrado com sucesso!");
    } else {
        System.out.println("Falha ao cadastrar aluno. Verifique os logs para detalhes.");
    }
}


//Métodos adicionais

private static List<Disciplina> exibirDisciplinas() {
    DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    List<Disciplina> disciplinas = disciplinaDAO.listarDisciplinas();

    return disciplinas;
}

private static boolean verificarPrimeiroLogin(int idUsuario) {
        LoginDAO loginDAO = new LoginDAO();
        return loginDAO.isPrimeiroLogin(idUsuario);
    }

    public static void exibirAlunosComDisciplinas() {
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

        List<AlunoDisciplinaInfo> alunosDisciplinas = disciplinaDAO.listarAlunosComDisciplinas();

        for (AlunoDisciplinaInfo alunoDisciplinaInfo : alunosDisciplinas) {
            System.out.println("ID Aluno: " + alunoDisciplinaInfo.getIdAluno());
            System.out.println("ID Bruxo: " + alunoDisciplinaInfo.getIdBruxo());
            System.out.println("Casa: " + alunoDisciplinaInfo.getCasa());
            System.out.println("ID Disciplina: " + alunoDisciplinaInfo.getIdDisciplina());
            System.out.println("Nome Disciplina: " + alunoDisciplinaInfo.getNomeDisciplina());
            System.out.println("Código Disciplina: " + alunoDisciplinaInfo.getCodigoDisciplina());
            System.out.println("----------");
        }
    }

    private static int realizarLogin(Scanner scanner) {
        System.out.println("Login:");

        System.out.print("Digite o email: ");
        String emailLogin = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String senhaLogin = scanner.nextLine();

        LoginDAO loginDAO = new LoginDAO();

        int idUsuarioLogado = loginDAO.obterIdUsuarioPorLogin(emailLogin, senhaLogin);

        if (idUsuarioLogado != -1) {
            System.out.println("Login bem-sucedido! Bem-vindo, usuário com ID: " + idUsuarioLogado);
            return idUsuarioLogado;
        } else {
            System.out.println("Login falhou. Verifique suas credenciais.");
            return -1;
        }
    }

    private static List<Integer> associarDisciplinasAAluno(int idAluno, Scanner scanner) {
    List<Integer> disciplinasSelecionadas = new ArrayList<>();
    alunoDAO alunoDAO = new alunoDAO();

    boolean adicionandoDisciplinas = true;

    while (adicionandoDisciplinas) {
        List<Disciplina> disciplinas = exibirDisciplinas();

        System.out.println("*********************");
        System.out.println("Disciplinas Disponíveis:");
        System.out.println("*********************");
        
        for (Disciplina disciplina : disciplinas) {
            System.out.println(disciplina.getId() + ". " + disciplina.getNome() + " - Código: " + disciplina.getCodigo());
        }

        System.out.print("Digite o número da disciplina do aluno: ");
        int numeroDisciplina = scanner.nextInt();
        scanner.nextLine();

        disciplinasSelecionadas.add(numeroDisciplina);

        System.out.print("Deseja adicionar mais disciplinas? (S/N): ");
        char continuar = scanner.nextLine().charAt(0);

        if (continuar != 'S' && continuar != 's') {
            adicionandoDisciplinas = false;
        }
    }

    boolean associacaoSucesso = alunoDAO.DisciplinasAoAluno(idAluno, disciplinasSelecionadas);

    if (associacaoSucesso) {
        System.out.println("Disciplinas associadas ao aluno com sucesso!");
    } else {
        System.out.println("Falha ao associar disciplinas ao aluno. Verifique os logs para detalhes.");
    }

    return disciplinasSelecionadas;
}



}