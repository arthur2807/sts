package br.com.acc.cronometro.gui;

import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.JButton;

import br.com.acc.cronometro.classesBasicas.Atividade;
import br.com.acc.cronometro.classesBasicas.Demanda_User;
import br.com.acc.cronometro.classesBasicas.Localidade;
import br.com.acc.cronometro.classesBasicas.Tag;
import br.com.acc.cronometro.classesBasicas.Users;
import br.com.acc.cronometro.facade.FacadeCrono;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.toedter.calendar.JDayChooser;

import javax.swing.JPasswordField;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Main_principal {
	public static Users pUser2 = new Users();
	 private Timer timer;  
	    private int currentSegundo = 0;
	    private int currentMinuto = 0;
	    private int currentHora = 0;
	    private int currentSegundobck = 0;
	    private int currentMinutobck = 0;
	    private int currentHorabck = 0;
	    private int velocidade = 1000;
	    private String tempf;
	    JLabel lblNewLabel = new JLabel("00:00:00");
	    JButton btn_enviar_horas2 = new JButton("Enviar Horas");
	    JButton btnAdmLocalidade = new JButton("ADM Localidade");
	    JButton btnAdmFuncionarios = new JButton("ADM Funcionarios");
	    static Users checkUser;
	static Users us = new Users();
	public JFrame jFrame;
	FacadeCrono facadeCrono = new FacadeCrono();
	private JTextField txbDemanda;
	JComboBox comboLocalidade = new JComboBox();
	JComboBox comboAtividade = new JComboBox();
	JComboBox comboTags = new JComboBox();
	ArrayList<Localidade> arrLoc=null;
	ArrayList<Atividade> arrAtiv=null;
	ArrayList<Tag> arrTag=null;

	ArrayList<String> listatempos;
	FacadeCrono fc2 = new FacadeCrono();
	ArrayList<Demanda_User> listademand = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_principal window = new Main_principal(us);
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
	public Main_principal(Users pUser) {
		
		checkUser=pUser;
		pUser2=pUser;
		//JOptionPane.showMessageDialog(null,"Bem vindo Fulano"+checkUser.getNome());
		//frame.setTitle("Apontador de Horas - Usuário Logado: "+ checkUser.getNome() +"- "+ checkUser.getEid());
		preencheJcomboLocalidade();
		preencheJcomboAtividade();
		//preencheJcomboTag();
		initialize();
		iniciarContagem();
		stopTime();
		validaAtiv();
		jFrame.setTitle("Smart TimeSheet - Usuário Logado: "+ pUser2.getNome() +" - "+ pUser2.getEid());
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		if (pUser2.getTipoAcesso()==1) {
			btnAdmFuncionarios.setVisible(false);
			btnAdmLocalidade.setVisible(false);
		}
		
		jFrame = new JFrame();
		jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main_principal.class.getResource("/img/icon_final.png")));
		jFrame.setTitle("Smart TimeSheet");
		jFrame.setResizable(false);
		jFrame.setBounds(100, 100, 352, 331);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().setLayout(null);

		JLabel lblControleDeHoras = new JLabel("Smart TimeSheet");
		lblControleDeHoras.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 18));
		lblControleDeHoras.setBounds(0, 1, 213, 28);
		jFrame.getContentPane().add(lblControleDeHoras);
		
		final JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txbDemanda.getText().isEmpty()){
					JOptionPane.showMessageDialog(null,"Informe a demanda antes de iniciar o Report!","ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}else{
				timer.restart();
				btnIniciar.setEnabled(false);
				btn_enviar_horas2.setEnabled(true);
				jFrame.setDefaultCloseOperation(0);}
			}
		});
		btnIniciar.setBounds(62, 189, 71, 23);
		jFrame.getContentPane().add(btnIniciar);
		
		
		comboLocalidade.setBounds(127, 164, 134, 20);
		jFrame.getContentPane().add(comboLocalidade);
		
		JLabel label = new JLabel("Demanda");
		label.setBounds(62, 135, 55, 16);
		jFrame.getContentPane().add(label);
		
		txbDemanda = new JTextField();
		txbDemanda.setColumns(10);
		txbDemanda.setBounds(127, 133, 134, 20);
		jFrame.getContentPane().add(txbDemanda);
		
		JLabel label_1 = new JLabel("Apontar Horas");
		label_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		label_1.setBounds(106, 21, 134, 50);
		jFrame.getContentPane().add(label_1);
		
		
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel.setBounds(122, 50, 118, 72);
		jFrame.getContentPane().add(lblNewLabel);
		
		
		btnAdmLocalidade.setBounds(28, 269, 111, 23);
		jFrame.getContentPane().add(btnAdmLocalidade);
		
		
		btnAdmFuncionarios.setBounds(149, 269, 135, 23);
		jFrame.getContentPane().add(btnAdmFuncionarios);
		
		btn_enviar_horas2.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				try {
					String hj = txbDemanda.getText();
					if (hj.isEmpty() || tempf==null){
						
						JOptionPane.showMessageDialog( null,"Insira uma demanda para comecar a apontar horas","ERRO",JOptionPane.ERROR_MESSAGE);
						return;
					} 
					stopTime();
					btnIniciar.setEnabled(true);
					jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					FacadeCrono fc = new FacadeCrono();
					try {
						fc.enviarhoras(pUser2.getPid(), txbDemanda.getText() , tempf, arrLoc.get(comboLocalidade.getSelectedIndex()).getCd_localidade());
					tempf=null;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						 currentHora= currentHorabck;
						 currentMinuto= currentMinutobck;
						 currentSegundo= currentSegundobck;
						 iniciarContagem();
throw new Exception (e1.getMessage());					

					
					}
					txbDemanda.setText(null);
					JOptionPane.showMessageDialog(null, "Horas enviadas com sucesso!");
					btn_enviar_horas2.enable(false);
					tempf=null;
					txbDemanda.setText("");
					
					validaAtiv();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		
		btn_enviar_horas2.setBounds(137, 189, 124, 23);
		jFrame.getContentPane().add(btn_enviar_horas2);
		
		JLabel label_3 = new JLabel("Localidade");
		label_3.setBounds(62, 162, 71, 16);
		jFrame.getContentPane().add(label_3);
		
		JButton btnHistricoDeApontamentos = new JButton("Hist\u00F3rico de Apontamento");
		btnHistricoDeApontamentos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnHistricoDeApontamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main_Acomp_horas cop = new Main_Acomp_horas(pUser2);
				//puxa o frame da tela listar e exibe
				cop.frmSmartTimesheet.show();
			}
		});
		btnHistricoDeApontamentos.setBounds(62, 223, 199, 23);
		jFrame.getContentPane().add(btnHistricoDeApontamentos);
		
		JLabel lblAtividade = new JLabel("Atividade");
		lblAtividade.setBounds(62, 110, 71, 16);
		jFrame.getContentPane().add(lblAtividade);
		comboAtividade.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				validaAtiv();
			}
		});
		
		
		
		comboAtividade.setBounds(127, 108, 134, 20);
		jFrame.getContentPane().add(comboAtividade);
		comboTags.setBounds(127, 133, 124, 20);
		jFrame.getContentPane().add(comboTags);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(Main_principal.class.getResource("/img/background_2.jpg")));
		lblNewLabel_1.setBounds(0, 1, 347, 301);
		jFrame.getContentPane().add(lblNewLabel_1);
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
	            currentHorabck= currentHora;
	            currentMinutobck=currentMinuto;
	            currentSegundobck= currentSegundo;
	            lblNewLabel.setText(hr+":"+min+":"+seg);  
	            tempf=hr+":"+min+":"+seg;
	        }  
	    };  
	    this.timer = new Timer(velocidade, action);  
	    this.timer.start();
	}

	private void preencheJcomboLocalidade(){
			ArrayList<Localidade> al = new ArrayList<Localidade>();
	FacadeCrono fcn = new FacadeCrono();
			
		
		//JOptionPane.showMessageDialog(null, al.size());
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			al= fcn.listarLocalidadesALL();
			
			//Configurando o combobox
		comboLocalidade.removeAllItems();
			for (int i = 0; i <al.size(); i++) {
				
			comboLocalidade.addItem(al.get(i).getDc_localidade());
		
			}
			arrLoc=al;
	//		comboLocalidade.setModel(model);
			//comboLocalidade.setSelectedIndex(0);
		//JOptionPane.showMessageDialog(null,comboLocalidade.getSelectedIndex());
			
						
			
			
		}
	
	
	private void preencheJcomboAtividade(){
		ArrayList<Atividade> al = new ArrayList<Atividade>();
FacadeCrono fcn = new FacadeCrono();
		
	
	//JOptionPane.showMessageDialog(null, al.size());
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		al= fcn.listarAtividadesALL();
		
		//Configurando o combobox
	comboAtividade.removeAllItems();
		for (int i = 0; i <al.size(); i++) {
			
			comboAtividade.addItem(al.get(i).getTag());
	if(al.get(i).getTag().equals("CHAMADO")){
		comboAtividade.setSelectedIndex(i);
	}
		}
		arrAtiv=al;
		
//		comboLocalidade.setModel(model);
		//comboLocalidade.setSelectedIndex(0);
	//JOptionPane.showMessageDialog(null,comboLocalidade.getSelectedIndex());
				
		
		
	}
	
	
	private void preencheJcomboTag(){
		ArrayList<Tag> al = new ArrayList<Tag>();
FacadeCrono fcn = new FacadeCrono();
		
	
	//JOptionPane.showMessageDialog(null, al.size());
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		al= fcn.listarTagsALL();
		
		//Configurando o combobox
	comboTags.removeAllItems();
		for (int i = 0; i <al.size(); i++) {
			
			comboTags.addItem(al.get(i).getDcTag());
	
		}
		arrTag=al;
//		comboLocalidade.setModel(model);
		//comboLocalidade.setSelectedIndex(0);
	//JOptionPane.showMessageDialog(null,comboLocalidade.getSelectedIndex());
				
		
		
	}
	
	
	private void validaAtiv(){
		int jk=arrAtiv.get(comboAtividade.getSelectedIndex()).getIdbox();
		if (jk==1){
						
			//txbDemanda.hide();
			txbDemanda.enable(false);
			txbDemanda.setText(arrAtiv.get(comboAtividade.getSelectedIndex()).getTag());
			//preenchercombotags
			
			
		}
		else{
			txbDemanda.show();
			txbDemanda.enable(true);

			txbDemanda.setText("");
			
		}
	}

	private void stopTime() {
	        timer.stop();
	        currentHora = 0;
	        currentMinuto = 0;
	        currentSegundo = 0;
	        lblNewLabel.setText("00:00:00");
	}
}
