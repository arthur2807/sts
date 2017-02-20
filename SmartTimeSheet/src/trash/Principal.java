package trash;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import javax.swing.Timer;
import javax.swing.JTextField;

import br.com.acc.cronometro.classesBasicas.Demanda;
import br.com.acc.cronometro.classesBasicas.Users;
import br.com.acc.cronometro.dao.UsersDAO;
import br.com.acc.cronometro.facade.FacadeCrono;


public class Principal {
	 private Timer timer;  
	    private int currentSegundo = 0;
	    private int currentMinuto = 0;
	    private int currentHora = 0;
	    private int velocidade = 1000;
	    private String tempf;
	private JFrame frame;
	JLabel lblNewLabel = new JLabel("00:00:00");
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
		iniciarContagem();
		stopTime();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 297, 241);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel.setBounds(90, 11, 118, 72);
		frame.getContentPane().add(lblNewLabel);
		
		final JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				timer.restart();
				btnIniciar.setEnabled(false);
				
				
				
				//ud.persist(users);
			}
		});
		btnIniciar.setBounds(24, 166, 118, 23);
		frame.getContentPane().add(btnIniciar);
		
		JButton btnPausar = new JButton("Enviar Horas");
		btnPausar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTime();
				JOptionPane.showMessageDialog(null,tempf );
				btnIniciar.setEnabled(true);
				
				FacadeCrono fc = new FacadeCrono();
				//fc.enviarhoras(10959310, textField.getText() , tempf,1);
				
			}
		});
		btnPausar.setBounds(149, 166, 118, 23);
		frame.getContentPane().add(btnPausar);
		
		textField = new JTextField();
		textField.setBounds(96, 95, 112, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblDemanda = new JLabel("Demanda");
		lblDemanda.setBounds(25, 97, 55, 16);
		frame.getContentPane().add(lblDemanda);
	}
	
	private void iniciarContagem() {
        ActionListener action = new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                currentSegundo++;
                
                if(currentSegundo==60){
                    currentMinuto++;
                    currentSegundo = 0;
                }
                
                if(currentMinuto==60){
                    currentHora++;
                    currentMinuto = 0;
                }
                
                String hr = currentHora <= 9? "0"+currentHora:currentHora+"";
                String min = currentMinuto <= 9? "0"+currentMinuto:currentMinuto+"";
                String seg = currentSegundo <= 9? "0"+currentSegundo:currentSegundo+"";
                
                lblNewLabel.setText(hr+":"+min+":"+seg);  
                tempf=hr+":"+min+":"+seg;
            }  
        };  
        this.timer = new Timer(velocidade, action);  
        this.timer.start();
    }
	
	private void stopTime() {
        timer.stop();
        currentHora = 0;
        currentMinuto = 0;
        currentSegundo = 0;
        lblNewLabel.setText("00:00:00");
}
}
