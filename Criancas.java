package criancas;

import java.awt.GridLayout;
import java.awt.Image;
//import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.MalformedURLException;
import java.net.URL;
//import java.net.spi.InetAddressResolverProvider;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Dimension;

public class Criancas extends JFrame{
    private JTextField tfIdentificador, tfTempoBrincadeira, tfTempoQuieta;
    private JLabel lblIdentificador, lblTempoBrincadeira, lblTempoQuieta;
    private JButton btnCriar;
    private JPanel panel;

    public Criancas(){
        setTitle("Criação de criança");
        setSize(400,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));

        // tfBolas = new JTextField(10);
        // tfSemBolas = new JTextField(10);
        // lblBola = new JLabel("Quantidade de crianças com bola:");
        // lblSemBolas = new JLabel("Quantidade de crianças sem bola:");


        tfIdentificador = new JTextField(20);
        lblIdentificador = new JLabel("Nome da criança:");
        tfTempoBrincadeira = new JTextField(20);
        lblTempoBrincadeira = new JLabel("Tempo de brincadeira (s):");
        lblTempoQuieta = new JLabel("Tempo quieta (s):");
        tfTempoQuieta = new JTextField(20);
        String[] opcoes = {"Com bola", "Sem bola"};
        JComboBox comboBoxBolas = new JComboBox(opcoes);


        btnCriar = new JButton("Criar");
        btnCriar.setPreferredSize(new Dimension(400, 40));
        panel = new JPanel();

        // panel.add(lblBola);
        // panel.add(tfBolas);
        // panel.add(lblSemBolas);
        // panel.add(tfSemBolas);
        panel.add(lblIdentificador);
        panel.add(tfIdentificador);
        panel.add(lblTempoBrincadeira);
        panel.add(tfTempoBrincadeira);
        panel.add(lblTempoQuieta);
        panel.add(tfTempoQuieta);
        panel.add(comboBoxBolas);
        panel.add(btnCriar);

        add(panel);
        

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
                    JFrame novaJanela = new JFrame();
                    novaJanela.setSize(600,300);
                    novaJanela.setTitle("Brincadeira");
                    novaJanela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    novaJanela.setVisible(true);
                    try{
                        URL imageURL = new URL("https://static.vecteezy.com/ti/vetor-gratis/p1/2850244-fundo-de-estadio-de-futebol-vetor.jpg");
                        ImageIcon icon = new ImageIcon(imageURL);
                        JLabel label = new JLabel(icon);
                        novaJanela.addComponentListener(new ComponentAdapter(){
                            @Override
                            public void componentResized(ComponentEvent e){
                                Image img = icon.getImage();
                                img = img.getScaledInstance(novaJanela.getWidth(),novaJanela.getHeight(),Image.SCALE_SMOOTH);
                                icon.setImage(img);
                                label.repaint();
                            }
                        });
                        novaJanela.add(label);
                }catch(MalformedURLException ex){
                    ex.printStackTrace();
                }
            } else if(comboBoxBolas.getSelectedItem() == "Sem bola"){
                System.out.printf("Identificador: %s\n", identificador);
                System.out.printf("Tempo de brincadeira: %f\n", tempoBrincadeira);
                System.out.printf("Tempo quieta: %f\n", tempoQuieta);
                
                Thread semBolasThread = new SemBolasThread();
                semBolasThread.start();
            }}
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



