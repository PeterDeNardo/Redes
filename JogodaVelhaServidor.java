package senac.jogos.trabalho2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
 
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public class JogodaVelhaServidor extends javax.swing.JFrame implements ActionListener {
    
    private JButton[] b = new JButton[9];
    private GridLayout game_layout; 
    private BorderLayout main_layout;
    private JLabel  labelPlacar = new JLabel("Cliente: 0  Servidor:0");
    private JOptionPane op = new JOptionPane();
    private int gameCounter = 0;
    private ServerSocket serverSocket;
    
    private Socket              socket;
    private InputStream         i;
    private OutputStream        o;
    private InputStreamReader   reader;
    private BufferedReader      br;
    private PrintWriter         pw;
    
    private Map<Integer, String> positions = new HashMap<Integer, String>();

    public JogodaVelhaServidor() {        
        //Configura botoes
        for (int i=0; i < 9; i++){
            b[i] = new javax.swing.JButton();                       
            b[i].setText("");
            b[i].addActionListener(this);
        }

        //Define o layout do jogo da velha
        JPanel panel_game = new JPanel();
        game_layout = new GridLayout( 3, 3 );
        panel_game.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel_game.setLayout(game_layout);
        //Adiciona os botoes ao layout do jogo
        for (int i=0; i < 9; i++){
            panel_game.add(b[i],0);
        }

        //Define o layou do score
        JPanel panel_score = new JPanel();  
        panel_score.add(labelPlacar);


        //Define o layout principal
        //Jogo da velha no centro, placar ao lado direito           
        main_layout = new BorderLayout();
        getContentPane().setLayout(main_layout);
        add(panel_game, BorderLayout.CENTER);
        add(panel_score, BorderLayout.EAST);
    }         

    public void actionPerformed(ActionEvent e) {
        
        
        
        for (int i=0; i < 9; i++){
            if (e.getSource() == b[i]) {
                b[i].setText("X");
                pw.println(i);
                System.out.println("Botao " + i + " pressionado");     
                try {
                    int po = Integer.parseInt(br.readLine());
                    b[po].setText("O");
                } catch (IOException ex) {
                    Logger.getLogger(JogodaVelhaServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }  

    public void init(){
        
        //Servidor
        try {
            serverSocket = new ServerSocket(4567);
            socket = serverSocket.accept();
            i = socket.getInputStream();
            o = socket.getOutputStream();
            reader = new InputStreamReader(i);
            br = new BufferedReader(reader);
            pw = new PrintWriter(o, true);
            
            //Chama OptionPane
//            String servPort = Integer.toString(serverSocket.getLocalPort());
//            String servIp = serverSocket.getInetAddress().toString();
//            JOptionPane.showMessageDialog(null, "Aguardando a conexão do programa cliente. IP do Servidor: " + servIp + ", Porta do Servidor: " + servPort);
//         
            int i = Integer.parseInt(br.readLine());
            b[i].setText("O");
              
        } catch(Exception err) {
            System.out.println("servidor erro" + err);
        }
        


    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JogodaVelhaServidor jogo = new JogodaVelhaServidor();
        jogo.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        jogo.setSize( 300, 200 ); // set frame size
        jogo.setVisible( true );
        jogo.setTitle("Servidor");
        jogo.init();
    }
    
}
