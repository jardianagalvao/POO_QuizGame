import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


interface Pontuavel {
    int getPontuacao();
    String getName();
}

class Jogador implements Pontuavel {
    public Jogador(Pontuavel pontuavel) {
    }

    @Override
    public int getPontuacao() {
        return 10;
    }

    @Override
    public String getName() {
        return "Bem vindo Aluno de POO";
    }
}

abstract class Jogo implements ActionListener,Pontuavel  {
    protected JFrame frame;
    protected JPanel panel;
    protected int perguntaAtual = 0;
    protected int pontuacao = 0;
    protected String nomeUsuario;

    public void iniciarJogo() {
        SwingUtilities.invokeLater(() -> {
            criarGUI();
        });
    }

    protected abstract void criarGUI();
    protected abstract void atualizarConteudoPainel();
    protected abstract void exibirPlacarLideres();
    protected abstract void salvarPontuacaoNoArquivo();

    @Override
    public abstract void actionPerformed(ActionEvent e);
}





public class Quiz extends Jogo {

    private JFrame frame;
    private JPanel panel;
    private int perguntaAtual = 0;
    private int pontuacao = 0;
    private String nomeUsuario; 

    String[] perguntas = {
            "Quais desses não está entre os pilares de POO?",
            "O que é um objeto em programação orientada a objetos?",
            "O que é polimorfismo?",
            "O que é a palavra-chave \"super\" usada em POO?",
            "Como o encapsulamento contribui para a segurança em POO?",
            "O que é composição em POO?",
            "O que é um método estático em POO?",
            "Na sobrecarga de método, o que é levado em consideração para determinar qual versão do método será chamada?",
            "O que é uma interface em POO?",
            "O que é um construtor em POO?"
    };

    String[][] respostas = {
            {"Herança", "Polimorfismo", "Abstração", "Interface"},
            {"Uma variável", "Um bloco de código", " Uma instância de uma classe", "Um operador"},
            {"Uma técnica para esconder a implementação de uma classe", "A capacidade de uma classe ter múltiplos métodos com o mesmo nome", "A capacidade de uma classe herdar de várias classes", "A capacidade de uma classe criar instâncias de outras classes"},
            {"Referenciar uma classe pai", "Denotar que um método é estático", " Iniciar uma variável estática", "Chamar um método na mesma classe"},
            {"Tornando os métodos privados", "Tornando os métodos públicos", "Tornando os atributos públicos", "Tornando os atributos privadas e controlando o acesso através de métodos"},
            {"Uma forma de herança múltipla", "Uma técnica para criar objetos dentro de outros objetos", "Um método para ocultar a implementação de uma classe", "Um tipo de encapsulamento"},
            {"Um método que pode ser acessado sem criar uma instância da classe", "Um método que só pode ser acessado por subclasses", "Um método que só pode ser acessado por métodos da mesma classe", "Um método que é virtual e pode ser sobreposto"},
            {"A ordem de declaração dos métodos na classe", "A visibilidade dos métodos", "A quantidade e os tipos de parâmetros", "A quantidade de vezes que um método foi chamado anteriormente"},
            {"Uma classe concreta", "Um conjunto de métodos abstratos", "Um tipo de dado", "Um operador lógico"},
            {"Um método que remove objetos", "Um método que cria objetos", "Um método que realiza cálculos matemáticos", "Um método que altera o estado de um objeto"}
    };

    char[] itens = {'A', 'B', 'C', 'D'};

    char[] respostasCorretas = {'D', 'C', 'B', 'A', 'D', 'B', 'A', 'C', 'B', 'B'};

    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.iniciarJogo();
        Jogador jogador = new Jogador(quiz);
        System.out.println(jogador.getName());
        System.out.println("O quiz vale de 0 a " + jogador.getPontuacao());
    }
    
    @Override
    public int getPontuacao() {
        return pontuacao;
    }

    @Override
    public String getName() {
        return nomeUsuario;
    }

    @Override
    protected void criarGUI() {
        frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 650);
        frame.setResizable(false);

        panel = new JPanel(new GridBagLayout());
       
        JLabel titleLabel = new JLabel("POO Adventure - QuizGame");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 30));

        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.insets = new Insets(50, 0, 30, 0);
        panel.add(titleLabel, titleConstraints);

        JButton playButton = new JButton("Play");

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.insets = new Insets(10, 0, 10, 0);
        panel.add(playButton, buttonConstraints);

        buttonConstraints.gridy = 2;

        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                perguntaAtual = 0;
                pontuacao = 0;
                nomeUsuario = JOptionPane.showInputDialog(frame, "Digite seu nome:");
                if (nomeUsuario != null && !nomeUsuario.isEmpty()) {
                    atualizarConteudoPainel();
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira um nome válido.");
                }
            }
        });
    }

    protected void atualizarConteudoPainel() {
        panel.removeAll();

        JLabel novoTitleLabel = new JLabel("Questão " + (perguntaAtual + 1));
        novoTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        GridBagConstraints novoTitleConstraints = new GridBagConstraints();
        novoTitleConstraints.gridx = 0;
        novoTitleConstraints.gridy = 0;
        novoTitleConstraints.insets = new Insets(20, 0, 10, 0);
        panel.add(novoTitleLabel, novoTitleConstraints);

        JLabel perguntaLabel = new JLabel(perguntas[perguntaAtual]);
        perguntaLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        GridBagConstraints perguntaConstraints = new GridBagConstraints();
        perguntaConstraints.gridx = 0;
        perguntaConstraints.gridy = 1;
        perguntaConstraints.insets = new Insets(10, 0, 20, 0);
        panel.add(perguntaLabel, perguntaConstraints);

        ButtonGroup group = new ButtonGroup();

        for (int i = 0; i < respostas[perguntaAtual].length; i++) {
            JButton novoBotao = new JButton(itens[i] + ". " + respostas[perguntaAtual][i]);
            novoBotao.addActionListener(this);

            GridBagConstraints novoButtonConstraints = new GridBagConstraints();
            novoButtonConstraints.gridx = 0;
            novoButtonConstraints.gridy = i + 2;
            novoButtonConstraints.insets = new Insets(10, 0, 10, 0);

            panel.add(novoBotao, novoButtonConstraints);
            group.add(novoBotao);
        }

        if (perguntaAtual < perguntas.length - 1) {
            JButton proximaPerguntaButton = new JButton("Próxima Pergunta");
            proximaPerguntaButton.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            	    JButton sourceButton = (JButton) e.getSource();
            	    String respostaSelecionada = sourceButton.getText();

            	    char respostaCorreta = respostasCorretas[perguntaAtual];
            	    char respostaSelecionadaChar = respostaSelecionada.charAt(0);

            	    if (respostaSelecionadaChar == respostaCorreta) {
            	        pontuacao += 10;
            	    }

            	    perguntaAtual++;
            	    if (perguntaAtual < perguntas.length) {
            	        atualizarConteudoPainel();
            	    } else {
            	   
            	        JOptionPane.showMessageDialog(frame, "Quiz concluído! Pontuação: " + pontuacao);
            	        exibirPlacarLideres();
            	        frame.dispose();
            	    }
            	}
            });

            GridBagConstraints proximaButtonConstraints = new GridBagConstraints();
            proximaButtonConstraints.gridx = 0;
            proximaButtonConstraints.gridy = respostas[perguntaAtual].length + 2;
            proximaButtonConstraints.anchor = GridBagConstraints.NORTHEAST;
            proximaButtonConstraints.insets = new Insets(20, 0, 20, 10);

        }

        panel.revalidate();
        panel.repaint();
    }

    protected void exibirPlacarLideres() {
        salvarPontuacaoNoArquivo();
        JOptionPane.showMessageDialog(frame, "Nome: " + nomeUsuario + "\nPontuação: " + pontuacao);
        System.out.println("Nome do Jogador: " + nomeUsuario);
        System.out.println("Pontuação do Jogador: " + pontuacao/10 + "/10");
    }

    protected void salvarPontuacaoNoArquivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("D:\\Temp\\ws-eclipse\\Quiz\\src\\pontuacao.txt", true))) {
            writer.println("Nome: " + nomeUsuario + ", Pontuação: " + pontuacao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        String respostaSelecionada = sourceButton.getText();

        char respostaCorreta = respostasCorretas[perguntaAtual];
        char respostaSelecionadaChar = respostaSelecionada.charAt(0);

        if (respostaSelecionadaChar == respostaCorreta) {
            pontuacao += 10;
        }

        perguntaAtual++;
        if (perguntaAtual < perguntas.length) {
            atualizarConteudoPainel();
        } else {
            JOptionPane.showMessageDialog(frame, "Quiz concluído! Pontuação: " + pontuacao);
            exibirPlacarLideres();
            frame.dispose();
        }
    }
}
