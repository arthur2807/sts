package br.com.acc.cronometro.conexao;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import br.com.acc.cronometro.classesBasicas.Demanda;
import br.com.acc.cronometro.classesBasicas.Demanda_User;
import br.com.acc.cronometro.classesBasicas.Users;
import br.com.acc.cronometro.dao.DemandaDAO;
import br.com.acc.cronometro.dao.DemandaUserDao;
import br.com.acc.cronometro.dao.UsersDAO;
import br.com.acc.cronometro.facade.FacadeCrono;

public class TesteBD {
public static void main(String[] args) {
	ConexaoBD bd = new ConexaoBD() ;
	try {
		bd.getConexao();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Users ud = new Users();
	ud.setEid("pedro.cordeiro@accenture.com");
	ud.setNome("Pedro Cordeiro");
	ud.setPid(11043019);
	ud.setSenha("natura00");
	ud.setTelefone("978456123");
	ud.setTipoAcesso(1);
	String s;
	s=JOptionPane.showInputDialog("insoi").toString();
	System.out.println(s);
	UsersDAO udd = new UsersDAO();
	//udd.inserirUser(ud);
	Demanda d = new Demanda();
	d.setCd_demanda("656987");
	d.setTempo("13:15:25");
	d.setData("1");
	DemandaDAO ddao = new DemandaDAO();
	//ddao.insereDemanda(d);
	
	
	DemandaUserDao uds = new DemandaUserDao();
	//uds.insereFuncionario(ud,d);
	
	FacadeCrono fc = new FacadeCrono();
	Demanda_User u = new Demanda_User();
	// continuar aqui, falta dar o set dos sub items para realizar a consulta do 
	u.setUser(ud);
	u.setDemanda(d);
	//System.out.println(fc.buscaDemandas(u).size());
	
	
}
}
