package br.com.acc.cronometro.gui;

import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.JButton;

import br.com.acc.cronometro.classesBasicas.Users;
import br.com.acc.cronometro.facade.FacadeCrono;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDayChooser;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class TelaLogin {
	static Users us = new Users();
	public JFrame jFrame;
	private JTextField txblogin;
	FacadeCrono facadeCrono = new FacadeCrono();
	private JPasswordField txbSenha;
	String versao= "1.6";
	
	private void validaVersaoSTS() {
		// TODO Auto-generated method stub
		if(versao != facadeCrono.validaVersao(versao));
		{
			JOptionPane.showMessageDialog(null,"Esta Versão do sistema está desatualizada!\n"
					+ "Contate o Administrador do sistema. \n"
					+ "arthur.v.camara@accenture.com","ERRO",JOptionPane.ERROR_MESSAGE);		
			System.exit(0);
		}
		
	}
	

	/**
	 * Launch the application.
	 */
	
	public static void main2(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin window = new TelaLogin(us);
					window.jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}




	/**
	 * Create the application.
	 */
	public TelaLogin(Users us2) {
		initialize();
		validaVersaoSTS();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin window = new TelaLogin(us);
					window.jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		jFrame = new JFrame();
		jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(TelaLogin.class.getResource("/img/icon_final.png")));
		jFrame.setTitle("Smart TimeSheet");
		jFrame.setResizable(false);
		jFrame.setBounds(100, 100, 325, 320);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().setLayout(null);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 16));
		lblLogin.setBounds(220, 75, 67, 28);
		jFrame.getContentPane().add(lblLogin);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 16));
		lblSenha.setBounds(220, 130, 67, 28);
		jFrame.getContentPane().add(lblSenha);

		txblogin = new JTextField();
		txblogin.setToolTipText("Insira o seu PID/EID");
		txblogin.setBounds(199, 104, 109, 20);
		jFrame.getContentPane().add(txblogin);
		txblogin.setColumns(10);

		final JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			if (txblogin.getText().isEmpty()) {
		//		JOptionPane.showMessageDialog(null,"Informe o seu PID/EID");
				//txblogin.setF
				return;
			}
			
			
				try {
					String loginTxt=txblogin.getText().toString();
				if(loginTxt.matches("^[0-9]*$")){
					// TODO: handle exception
					//JOptionPane.showMessageDialog(null, "entrou Pid");
					String tPid=txblogin.getText();
					us.setPid(Integer.parseInt(tPid));
					us.setSenha(txbSenha.getText());
					//System.out.println(facadeCrono.buscaUsersLog(us).size());
					ArrayList<Users> arruser2 = new ArrayList<>();
					
					try {
						arruser2=facadeCrono.buscaUsersLog(us);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						
						JOptionPane.showMessageDialog(null,e1.getMessage());
return;					}
					
					if(arruser2.size()>=1){
					Main_principal  telaPrincipal = new Main_principal(arruser2.get(0));
					telaPrincipal.pUser2=arruser2.get(0);
					jFrame.dispose();
					
				telaPrincipal.jFrame.show();
				
					}else{
						throw new Exception("Usuário ou senha inválidos! \n Tente Novamente");
						
					}						
					
				
					
				}else{//JOptionPane.showMessageDialog(null, "entrou EID");
				us.setEid(loginTxt);
				us.setSenha(txbSenha.getText());
				//System.out.println(facadeCrono.buscaUsersLog(us).size());
				ArrayList<Users> arruser2 = new ArrayList<>();
				try {
					arruser2=facadeCrono.buscaUsersLog(us);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					
					JOptionPane.showMessageDialog(null,e1.getMessage());
					return;			
				}
				
				if(arruser2.size()>=1){
					Main_principal telaPrincipal = new Main_principal(arruser2.get(0));
				telaPrincipal.pUser2=arruser2.get(0);
				jFrame.dispose();
				
			telaPrincipal.jFrame.show();
			
				}else{
					throw new Exception("Usuário ou senha inválidos! \n Tente Novamente");
					
				}						}
					/*
					if(loginTxt.contains("@")|loginTxt.contains(".")){
						JOptionPane.showMessageDialog(null, "entrou EID");
						us.setEid(loginTxt);
						us.setSenha(txbSenha.getText());
						System.out.println(facadeCrono.buscaUsersLog(us).size());
						ArrayList<Users> arruser2 = new ArrayList<>();
						arruser2=facadeCrono.buscaUsersLog(us);
						
						if(arruser2.size()>=1){
						Main telaPrincipal = new Main(arruser2.get(0));
						telaPrincipal.pUser2=arruser2.get(0);
						jFrame.dispose();
						
					telaPrincipal.frame.show();
					
						}else{
							JOptionPane.showMessageDialog(null,"Usuário ou senha inválidos! \n Tente Novamente");
							return;
						}						
						

					} else  {
						// TODO: handle exception
						//JOptionPane.showMessageDialog(null, "entrou Pid");
						us.setPid(Integer.parseInt(txblogin.getText()));
						us.setSenha(txbSenha.getText());
						System.out.println(facadeCrono.buscaUsersLog(us).size());
						ArrayList<Users> arruser2 = new ArrayList<>();
						arruser2=facadeCrono.buscaUsersLog(us);
						
						if(arruser2.size()>=1){
						Main telaPrincipal = new Main(arruser2.get(0));
						telaPrincipal.pUser2=arruser2.get(0);
						jFrame.dispose();
						
					telaPrincipal.frame.show();
					
						}else{
							JOptionPane.showMessageDialog(null,"Usuário ou senha inválidos! \n Tente Novamente");
							return;
						}						
						
					
					}*/
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,"ERRO NO LOGIN! \n"+e1.getMessage(),"ERRO",JOptionPane.ERROR_MESSAGE);	

					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,"ERRO NO LOGIN! \n"+e1.getMessage(),"ERRO",JOptionPane.ERROR_MESSAGE);	
					e1.printStackTrace();
					return;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,"ERRO NO LOGIN! \n"+e1.getMessage(),"ERRO",JOptionPane.ERROR_MESSAGE);	
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(198, 188, 110, 23);
		jFrame.getContentPane().add(btnLogin);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancelar.setBounds(199, 212, 109, 23);
		jFrame.getContentPane().add(btnCancelar);

		JLabel lblControleDeHoras = new JLabel("Smart TimeSheet");
		lblControleDeHoras.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 18));
		lblControleDeHoras.setBounds(61, 11, 213, 28);
		jFrame.getContentPane().add(lblControleDeHoras);
		
		txbSenha = new JPasswordField();
		txbSenha.setBounds(199, 156, 108, 20);
		jFrame.getContentPane().add(txbSenha);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(TelaLogin.class.getResource("/img/IMG_LOG_RESIZE.jpg")));
		lblNewLabel.setBounds(0, 1, 322, 291);
		jFrame.getContentPane().add(lblNewLabel);
	}
}
