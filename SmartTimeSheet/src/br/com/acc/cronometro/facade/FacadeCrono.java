package br.com.acc.cronometro.facade;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.acc.cronometro.classesBasicas.Atividade;
import br.com.acc.cronometro.classesBasicas.Demanda;
import br.com.acc.cronometro.classesBasicas.Demanda_User;
import br.com.acc.cronometro.classesBasicas.Localidade;
import br.com.acc.cronometro.classesBasicas.Tag;
import br.com.acc.cronometro.classesBasicas.Users;
import br.com.acc.cronometro.dao.AtividadeDAO;
import br.com.acc.cronometro.dao.DemandaUserDao;
import br.com.acc.cronometro.dao.LocalidadeDAO;
import br.com.acc.cronometro.dao.UsersDAO;

public class FacadeCrono {
	UsersDAO udd = new UsersDAO();
	DemandaUserDao dud = new DemandaUserDao();
	ArrayList<Users> listau = null;
	ArrayList<Demanda_User> listademand = null;
	public void enviarhoras(int pid, String demanda, String time, int localidade) throws Exception{
		
		Users u = new Users();
		u.setPid(pid);
		Demanda d = new Demanda();
		d.setCd_demanda(demanda);
		d.setTempo(time);
		Localidade l = new Localidade();
		l.setCd_localidade(localidade);
		d.setLocalidade(l);
		DemandaUserDao ud = new DemandaUserDao();
		ud.insereDemandaUser(u,d);
		
		
	}

public ArrayList<Demanda_User> buscaDemandas(Demanda_User user) throws Exception{
		
		listademand= dud.buscaApontamentos(user);
		
		
		return listademand;
		
		
	}
	
public String buscaDemandasSoma(Demanda_User user) throws Exception{
	
	return dud.buscaApontamentosSomaHoras(user);
}
public ArrayList<Demanda_User> buscaDemandas(Demanda_User user, String dataInicio){
	
	listademand= dud.buscaApontamentos(user,dataInicio);
	
	
	return listademand;
	
}

public String buscaDemandasSoma(Demanda_User user,String dataInicio) throws Exception{
	
	return dud.buscaApontamentosSomaHoras(user,dataInicio);
}


public String buscaDemandasSoma(Demanda_User user,String dataInicio,String dataFim) throws Exception{
	
	return dud.buscaApontamentosSomaHoras(user,dataInicio,dataFim);
}

public ArrayList<Demanda_User> buscaDemandas(Demanda_User user, String dataInicio,String dataFim) throws Exception{
	
	listademand= dud.buscaApontamentos(user,dataInicio,dataFim);
	
	
	return listademand;
	
}
	
	public ArrayList<Users> buscaUsersLog(Users user) throws Exception{
		
		listau= udd.buscaUser(user);
		
		
		return listau;
		
		
	}

	public void inserirLocalidade(Localidade localidade){
		
		LocalidadeDAO locdao = new LocalidadeDAO();
		locdao.insereLocalidade(localidade);
	}
	
public ArrayList<Localidade> listarLocalidadesALL(){
		
		LocalidadeDAO locdao = new LocalidadeDAO();
		return locdao.buscaTodasLocalidades();
	}


public ArrayList<Atividade> listarAtividadesALL(){
	
	AtividadeDAO ativdao = new AtividadeDAO();
	return ativdao.buscaTodasAtividades();
}

public ArrayList<Tag> listarTagsALL(){
	
	AtividadeDAO ativdao = new AtividadeDAO();
	return ativdao.buscaTodasTags();
}
	
	public String validaVersao() throws Exception{
		return udd.validaVersao();
		//TESTECOMMIT
		
	}

}
