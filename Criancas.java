package criancas;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.BorderLayout;

public class Criancas extends JFrame{
    private JTextField tfCapacidadeCesto, tfIdentificador, tfTempoBrincadeira, tfTempoQuieta;
    private JLabel lbCapacidadeCesto, lblIdentificador, lblTempoBrincadeira, lblTempoQuieta;
    private JButton btnIniciar, btnCriar;
    private JPanel panel;

    public Criancas(){
        setTitle("Cesto");
        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));

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
                    panel = new JPanel();
                    btnCriar = new JButton("Criar");
                    novaJanela.setSize(600,600);
                    novaJanela.setTitle("Brincadeira");
                    novaJanela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    novaJanela.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    novaJanela.setVisible(true);

                    tfIdentificador = new JTextField(20);
                    lblIdentificador = new JLabel("Nome da criança:");
                    tfTempoBrincadeira = new JTextField(20);
                    lblTempoBrincadeira = new JLabel("Tempo de brincadeira (s):");
                    lblTempoQuieta = new JLabel("Tempo quieta (s):");
                    tfTempoQuieta = new JTextField(20);
                    String[] opcoes = {"Com bola", "Sem bola"};
                    JComboBox comboBoxBolas = new JComboBox(opcoes);

                    panel.add(lblIdentificador);
                    panel.add(tfIdentificador);
                    panel.add(lblTempoBrincadeira);
                    panel.add(tfTempoBrincadeira);
                    panel.add(lblTempoQuieta);
                    panel.add(tfTempoQuieta);
                    panel.add(comboBoxBolas);
                    panel.add(btnCriar);
                    add(panel);
                    
                    novaJanela.add(panel, BorderLayout.NORTH);

                    try {
                        URL imageURL = new URL("https://static.vecteezy.com/ti/vetor-gratis/p1/2850244-fundo-de-estadio-de-futebol-vetor.jpg");
                        ImageIcon icon = new ImageIcon(imageURL);
                        JLabel campoLabel = new JLabel(icon);
                        novaJanela.add(campoLabel, BorderLayout.SOUTH);
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }

                    btnCriar.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){
                            String identificador = tfIdentificador.getText();
                            float tempoBrincadeira = Float.parseFloat(tfTempoBrincadeira.getText());
                            float tempoQuieta = Float.parseFloat(tfTempoQuieta.getText());
                            
                            if(comboBoxBolas.getSelectedItem() == "Com bola"){
                                System.out.printf("Identificador: %s\n", identificador);
                                System.out.printf("Tempo de brincadeira: %f\n", tempoBrincadeira);
                                System.out.printf("Tempo quieta: %f\n", tempoQuieta);
                                Thread bolasThread = new BolasThread();
                                bolasThread.start();
                            } else if(comboBoxBolas.getSelectedItem() == "Sem bola"){
                                System.out.printf("Identificador: %s\n", identificador);
                                System.out.printf("Tempo de brincadeira: %f\n", tempoBrincadeira);
                                System.out.printf("Tempo quieta: %f\n", tempoQuieta);
                                Thread semBolasThread = new SemBolasThread();
                                semBolasThread.start();
                            }
                        }

                    });
            }
        });
    }
    public static void main (String[]args){
        Criancas projeto = new Criancas();
        projeto.setVisible(true);
    }
}
class BolasThread extends Thread{
    

    public BolasThread(){

    }
    @Override
    public void run(){
        System.out.println("Criança com bola criada!");
}
}
class SemBolasThread extends Thread{
    
    public SemBolasThread(){
    }
    @Override 
    public void run(){
        System.out.println("Criança sem bola criada!");
    }
}



