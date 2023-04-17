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

public class Criancas extends JFrame{
    private JTextField tfBolas, tfSemBolas;
    private JLabel lblBola, lblSemBolas;
    private JButton btnCriar;
    private JPanel panel;

    public Criancas(){
        setTitle("Crianças com e sem bola");
        setSize(400,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,2));

        tfBolas = new JTextField(10);
        tfSemBolas = new JTextField(10);
        lblBola = new JLabel("Quantidade de crianças com bola:");
        lblSemBolas = new JLabel("Quantidade de crianças sem bola:");
        btnCriar = new JButton("Criar");
        panel = new JPanel();

        panel.add(lblBola);
        panel.add(tfBolas);
        panel.add(lblSemBolas);
        panel.add(tfSemBolas);
        panel.add(btnCriar);

        add(panel);
        

        btnCriar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int numBolas = Integer.parseInt(tfBolas.getText());
                int numSemBolas = Integer.parseInt(tfSemBolas.getText());

                if(numBolas > 0 && numSemBolas > 0){
                    Thread bolasThread = new BolasThread(numBolas);
                    Thread semBolasThread = new SemBolasThread(numSemBolas);
                    bolasThread.start();
                    semBolasThread.start();
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
            }else{
                System.out.println("Quantidade inválida!");
            }}
        });
    }
    public static void main (String[]args){
        Criancas projeto = new Criancas();
        projeto.setVisible(true);
    }
}
class BolasThread extends Thread{
    private int numBolas;
    

    public BolasThread(int numBolas){
        this.numBolas = numBolas;

    }
    @Override
    public void run(){
        for (int i=0; i<numBolas;i++){
            System.out.println("Criança com bola criada!");
        }   
}
}
class SemBolasThread extends Thread{
    private int numSemBolas;
    
    public SemBolasThread(int numSemBolas){
        this.numSemBolas = numSemBolas;
    }
    @Override 
    public void run(){
        for(int i =0;i<numSemBolas;i++){
            System.out.println("Crianças sem bola criada!");
        }
     
        
    }
}



