import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Jogo extends JFrame {
    private JTextField tfCapacidadeCesto, tfIdentificador, tfTempoBrincadeira, tfTempoQuieta;
    private JLabel lbCapacidadeCesto, lblIdentificador, lblTempoBrincadeira, lblTempoQuieta;
    private JButton btnIniciar, btnCriar;
    private JPanel panel;

    public Jogo(){
        setTitle("Brincadeira de criança");
        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));
        setLocationRelativeTo(null);

        tfCapacidadeCesto = new JTextField(15);
        lbCapacidadeCesto = new JLabel("Capacidade do cesto:");


        btnIniciar = new JButton("Iniciar");
        btnIniciar.setPreferredSize(new Dimension(200, 40));
        panel = new JPanel();
        
        panel.add(lbCapacidadeCesto);
        panel.add(tfCapacidadeCesto);
        panel.add(btnIniciar);

        add(panel);

        btnIniciar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame novaJanela = new JFrame();
                novaJanela.setLayout(new BorderLayout());
                novaJanela.setSize(600,600);
                novaJanela.setTitle("Brincadeira");
                novaJanela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                novaJanela.setExtendedState(JFrame.MAXIMIZED_BOTH);
                novaJanela.setVisible(true);
        
                // Cria o painel com os text fields e o botão
                panel = new JPanel();
                tfIdentificador = new JTextField(20);
                lblIdentificador = new JLabel("Nome da criança:");
                tfTempoBrincadeira = new JTextField(20);
                lblTempoBrincadeira = new JLabel("Tempo de brincadeira (s):");
                lblTempoQuieta = new JLabel("Tempo quieta (s):");
                tfTempoQuieta = new JTextField(20);
                String[] opcoes = {"Com bola", "Sem bola"};
                JComboBox<String> comboBoxBolas = new JComboBox<>(opcoes);
                btnCriar = new JButton("Criar");
        
                panel.add(lblIdentificador);
                panel.add(tfIdentificador);
                panel.add(lblTempoBrincadeira);
                panel.add(tfTempoBrincadeira);
                panel.add(lblTempoQuieta);
                panel.add(tfTempoQuieta);
                panel.add(comboBoxBolas);
                panel.add(btnCriar);
                novaJanela.add(panel, BorderLayout.NORTH);
        
                // Adiciona a imagem do campo no centro do painel
                try {
                    ImageIcon icon = new ImageIcon("projeto/src/cesto.png");
                    JLabel campoLabel = new JLabel(icon);
                    panel = new JPanel(new BorderLayout());
                    panel.add(campoLabel, BorderLayout.CENTER);
                    novaJanela.add(panel, BorderLayout.CENTER);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                    btnCriar.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){
                            String identificador = tfIdentificador.getText();
                            float tempoBrincadeira = Float.parseFloat(tfTempoBrincadeira.getText());
                            float tempoQuieta = Float.parseFloat(tfTempoQuieta.getText());
                            boolean bola;
                            if(comboBoxBolas.getSelectedItem() == "Com bola"){
                                bola = true;
                            } else {
                                bola = false;
                            }

                            System.out.printf("Identificador: %s\n", identificador);
                            System.out.printf("Tempo de brincadeira: %f\n", tempoBrincadeira);
                            System.out.printf("Tempo quieta: %f\n", tempoQuieta);
                            Thread crianca = new Crianca(identificador,tempoBrincadeira,tempoQuieta, bola);
                            crianca.start();

                        }

                    });
            }
        });
    }

    class Crianca extends Thread {
        public String identificador;
        public float tempoBrincadeira;
        public float tempoQuieta;
        public boolean bola;
        public String status;

        public Crianca(String identificador, float tempoBrincadeira, float tempoQuieta, boolean bola){
            this.identificador = identificador;
            this.tempoBrincadeira = tempoBrincadeira;
            this.tempoQuieta = tempoQuieta;
            this.bola = bola;
        }

        public void run(){
            if(bola == true){
                System.out.println("criança com bola rodando");
            }else{
                System.out.println("criança sem bola rodando");
            }
        }

    }

    public static void main(String[] args){
        Jogo jogo = new Jogo();
        jogo.setVisible(true);
    }

}

