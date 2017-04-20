	package br.com.acc.cronometro.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;

import br.com.acc.cronometro.classesBasicas.Demanda;
import br.com.acc.cronometro.classesBasicas.Demanda_User;
import br.com.acc.cronometro.classesBasicas.Localidade;
import br.com.acc.cronometro.classesBasicas.Users;
import br.com.acc.cronometro.facade.FacadeCrono;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
public class Main_Acomp_horas {
	public static Users pUser2 = new Users();
	 private Timer timer;  
	    private int currentSegundo = 0;
	    private int currentMinuto = 0;
	    private int currentHora = 0;
	    private int velocidade = 1000;
	    private String tempf;
	    static Users checkUser;
	    JLabel labelTotal = new JLabel("TOTAL:");
	    
	public JFrame frmSmartTimesheet;
	private JTable table;
	ArrayList<Localidade> arrLoc=null;
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
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public Main_Acomp_horas(Users pUser) {
		checkUser=pUser;
		//JOptionPane.showMessageDialog(null,"Bem vindo Fulano"+checkUser.getNome());
		//frame.setTitle("Apontador de Horas - Usuário Logado: "+ checkUser.getNome() +"- "+ checkUser.getEid());
		preencheJcomboLocalidade();
		initialize();
		iniciarContagem();
		stopTime();
		frmSmartTimesheet.setTitle("Smart TimeSheet - Usuário Logado: "+ checkUser.getNome() +" - "+ checkUser.getEid());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		/*if (checkUser.getTipoAcesso()==1) {
			btnAdmFuncionarios.setVisible(false);
			btnAdmLocalidade.setVisible(false);
		}
		*/
		
	    
		frmSmartTimesheet = new JFrame();
		frmSmartTimesheet.setIconImage(Toolkit.getDefaultToolkit().getImage(Main_Acomp_horas.class.getResource("/img/icon_final.png")));
		frmSmartTimesheet.setResizable(false);
		frmSmartTimesheet.setBounds(100, 100, 477, 353);
		frmSmartTimesheet.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSmartTimesheet.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		final JPanel panel = new JPanel();
		panel.setBorder(null);
		frmSmartTimesheet.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblAcompanharApontamento = new JLabel("Hist\u00F3rico de Apontamento");
		lblAcompanharApontamento.setBounds(140, 0, 227, 50);
		lblAcompanharApontamento.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		panel.add(lblAcompanharApontamento);
		
		final JDateChooser dataEnvioInicio = new JDateChooser();
		dataEnvioInicio.setBounds(32, 75, 87, 20);
		panel.add(dataEnvioInicio);
		
		final JDateChooser dataEnvioTermino = new JDateChooser();
		dataEnvioTermino.setBounds(141, 75, 108, 20);
		
		panel.add(dataEnvioTermino);
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); //DEFINE FORMATO DE DATA
			   
			   //System.out.println(data);
			     
			   
				Demanda_User du = new Demanda_User();
				Demanda d = new Demanda();
				du.setUser(checkUser);
				du.setDemanda(d);
				//se datas vem nulas pega todos os registros do usuario
				if(dataEnvioInicio.getDate()==null && dataEnvioTermino.getDate()==null){
			    	 System.out.println("deu certo o nullo");
			    	 	 
			    	 try {
						listademand=fc2.buscaDemandas(du);
						labelTotal.setText("TOTAL: "+ fc2.buscaDemandasSoma(du));
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	preencherTabelaRegistros();
			    	return;
			     }
				//se apenas data inicio vem preenchida pega resgistros a partir da dataEnvioInicio
				if (dataEnvioInicio.getDate()!=null && dataEnvioTermino.getDate() ==null) {
				//System.out.println(listademand.size() + "foi o array");
					String data = formato.format(dataEnvioInicio.getDate());
					listademand=fc2.buscaDemandas(du,data);
					try {
						labelTotal.setText("TOTAL: "+ fc2.buscaDemandasSoma(du,data));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					preencherTabelaRegistros();
					return;
				}
				
				// se ambas datas estão preechidas faz o "between" das datas
				if (dataEnvioInicio.getDate()!=null && dataEnvioInicio.getDate() !=null) {
					String dataInicio = formato.format(dataEnvioInicio.getDate());
					String dataFim = formato.format(dataEnvioTermino.getDate());
					try {
						listademand=fc2.buscaDemandas(du,dataInicio,dataFim);
						labelTotal.setText("TOTAL: "+ fc2.buscaDemandasSoma(du,dataInicio,dataFim));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					preencherTabelaRegistros();
					return;
				}
				
				
				
				//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		    //    frame.getContentPane().add(new JScrollPane(table));
		        /*frame.pack();
		        frame.setLocationRelativeTo(null);
		        frame.setVisible(true);*/
			}
		});
		btnBuscar.setBounds(260, 72, 76, 23);
		panel.add(btnBuscar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 106, 400, 178);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(true);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		table.setColumnSelectionAllowed(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"NOME", "DEMANDA", "TEMPO", "DATA ENVIO", "LOCALIDADE"
			}
		));
		
		JLabel lblIncio = new JLabel("Data In\u00EDcio:");
		lblIncio.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblIncio.setBounds(32, 50, 87, 14);
		panel.add(lblIncio);
		
		JLabel label_1 = new JLabel("Data \r\nT\u00E9rmino:");
		label_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		label_1.setBounds(144, 48, 105, 14);
		panel.add(label_1);
		labelTotal.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		labelTotal.setVisible(false);
		labelTotal.setBounds(153, 295, 175, 14);
		panel.add(labelTotal);
		
		JButton btnZerar = new JButton("Zerar");
		btnZerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			//	int ghj=table.setValueAt(0, arg1, arg2);
				int rowSelect;
				rowSelect = table.getSelectedRow();
				JOptionPane.showMessageDialog(null,table.getValueAt(rowSelect, 0));
				//JOptionPane.showMessageDialog(null, );
				
				
			}
		});
		btnZerar.setBounds(356, 72, 76, 23);
		panel.add(btnZerar);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(Main_Acomp_horas.class.getResource("/img/background_2.jpg")));
		lblNewLabel.setBounds(0, 0, 524, 325);
		panel.add(lblNewLabel);
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
                
            //    lblNewLabel.setText(hr+":"+min+":"+seg);  
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
}
	
	
	public void preencherTabelaRegistros() {
		// TODO Auto-generated constructor stub]
		
		//crie um defaultablemodel com as informações acima
		  String[] nomesColunas = {"NOME", "DEMANDA", "TEMPO","DATA ENVIO","LOCALIDADE"};
			//essa lista terá as linhas da sua JTable, preenchi abaixo apenas como exemplo
		DefaultTableModel modelt = new DefaultTableModel();
		       modelt.setColumnIdentifiers(nomesColunas);
		       //System.out.println(al.size()+"tamanho do array se nullo?");
				if(listademand.size()==0){
					JOptionPane.showMessageDialog(null, "Sua busca não obteve resultados!\n Verifique as condições informadas!");
				}
				ArrayList<Time> listadatas = new ArrayList<>();
				
		        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		        long auxTime=0;
		for (int i = 0; i < listademand.size(); i++) {
			
			
			modelt.addRow(new Object[]{listademand.get(i).getUser().getNome(), listademand.get(i).getDemanda().getCd_demanda()
			, listademand.get(i).getDemanda().getTempo(), listademand.get(i).getDemanda().getData(),listademand.get(i).getDemanda().getLocalidade().getDc_localidade()});// adiciona na jtbale
			
		}
		
		//define o model da sua tabela
		//JScrollPane scroll = new JScrollPane(table);
	//	scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		labelTotal.setVisible(true);
		table.setModel(modelt);
		// Instala o TableRowSorter.
	    TableRowSorter<TableModel> sorter;
	    sorter = new TableRowSorter<TableModel>(modelt);
	    table.setRowSorter(sorter);
		
		
	}
	
	private void preencheJcomboLocalidade(){
		ArrayList<Localidade> al = new ArrayList<Localidade>();
FacadeCrono fcn = new FacadeCrono();
		
	
	//JOptionPane.showMessageDialog(null, al.size());
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		al= fcn.listarLocalidadesALL();
		for (int i = 0; i <al.size(); i++) {
			
		//comboLocalidade.addItem(al.get(i).getDc_localidade());
	
		}
		arrLoc=al;
//		comboLocalidade.setModel(model);
		//comboLocalidade.setSelectedIndex(0);
	//JOptionPane.showMessageDialog(null,comboLocalidade.getSelectedIndex());
		
		
		
		
		
		
		
	}
}
