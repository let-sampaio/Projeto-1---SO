import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Semaphore;


public class Jogo extends JFrame {

    public static Semaphore cestoVazio = new Semaphore(0); // capacidade máxima do cesto (K)
    public static Semaphore cestoCheio = new Semaphore(0); // quantidade de bolas no cesto
    public static Semaphore mutex = new Semaphore(1);


    private JTextField tfCapacidadeCesto, tfIdentificador, tfTempoBrincadeira, tfTempoQuieta;
    private JLabel lbCapacidadeCesto, lblIdentificador, lblTempoBrincadeira, lblTempoQuieta, lblCestoAtual;
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
        
                try {
                    JPanel cestoPanel = new JPanel(new BorderLayout());
                
                    int cestoAtual = Integer.parseInt(tfCapacidadeCesto.getText());
                    cestoVazio.release(cestoAtual); //atribui a capacidade do cesto ao semáforo

                    lblCestoAtual = new JLabel(String.valueOf(cestoAtual));
                    lblCestoAtual.setForeground(Color.WHITE);
                
                    ImageIcon cestoIcon = new ImageIcon("projeto/src/background.png");
                    JLabel cestoLabel = new JLabel(cestoIcon);
                    cestoLabel.setLayout(new BorderLayout());
                    cestoLabel.add(lblCestoAtual, BorderLayout.NORTH);
                    lblCestoAtual.setFont(new Font("Arial", Font.BOLD, 80));
                    lblCestoAtual.setBorder(BorderFactory.createEmptyBorder(80, 680, 10, 10)); //temporário: alterar modo de centralização
                
                    cestoPanel.add(cestoLabel, BorderLayout.CENTER);
                    novaJanela.add(cestoPanel, BorderLayout.CENTER);
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

        public void brincar() throws InterruptedException {
            status = "Brincando com a bola";
            int qtdBrincando = 0;
            long startTime = System.currentTimeMillis();
            while(System.currentTimeMillis() - startTime < (tempoBrincadeira*1000)) {
                qtdBrincando++; //simula processo cpu-bound
                
            }
        }

        public void pegar_uma_bola() throws InterruptedException {
            cestoCheio.acquire();
            cestoVazio.release();
            mutex.acquire();
            bola = true;
            mutex.release();
            System.out.println("Criança " + identificador + " pegou uma bola");
        }

        public void inserir_uma_bola() throws InterruptedException {
            bola = false;
            mutex.acquire();
            cestoVazio.acquire();
            cestoCheio.release();
            mutex.release();
        }

        public void ficar_quieta(){
            status = "Quieta";
            int qtdQuieta = 0;
            long startTime = System.currentTimeMillis();
            while(System.currentTimeMillis() - startTime < (tempoBrincadeira*1000)) {
                qtdQuieta++; //simula processo cpu-bound
            }
        }

        public void run(){
            try{
                while(true){
                    if(bola){
                        System.out.println("Criança " + identificador + " está brincando com a bola");
                        brincar();
                    }
                    else {
                        System.out.println("Aguardando que outra criança coloque uma bola no cesto");
                        pegar_uma_bola();
                        System.out.println("Criança " + identificador + " pegou uma bola");
                        brincar();
                    }
                    System.out.println("Criança " + identificador + " devolveu a bola para o cesto.");
                    inserir_uma_bola();
                    System.out.println("Criança " + identificador + " está quieta.");
                    ficar_quieta();
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args){
        Jogo jogo = new Jogo();
        jogo.setVisible(true);
    }

}

