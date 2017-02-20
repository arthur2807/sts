package br.com.acc.cronometro.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.acc.cronometro.classesBasicas.Demanda;
import br.com.acc.cronometro.classesBasicas.Demanda_User;
import br.com.acc.cronometro.classesBasicas.Localidade;
import br.com.acc.cronometro.classesBasicas.Users;
import br.com.acc.cronometro.conexao.ConexaoBD;

public class DemandaUserDao {

	// Cria componentes
	private Connection conn = null;
	private Statement query;
	private String sql;
	ConexaoBD fv = new ConexaoBD();

	public DemandaUserDao() {

		
	}

	public ArrayList<Demanda_User> buscaApontamentos(Demanda_User user) throws Exception {

		// Cria novo objeto
		
ArrayList<Demanda_User> arrDemand = new ArrayList<>();
		// Define SQL

		if (user.getUser().getPid()!=0) {
			//sql = "SELECT * FROM T_DEMANDA_USER WHERE pid = " + pDemanda.getUser().getPid();
			//sql = "SELECT * FROM T_DEMANDA_USER";
			/*sql="select pid,cd_demanda, tempo, data_envio,localidade,dc_localidade from t_demanda_user inner join t_localidade "
					+ "					on t_demanda_user.localidade = t_localidade.cd_localidade";*/
			
			sql="select t_demanda_user.pid as 'pid',nome,cd_demanda, tempo, "
					+ "(SELECT CONVERT(VARCHAR(10), data_envio, 103) + ' ' + CONVERT(VARCHAR(8), data_envio, 108)) "
					+ " as  'dt_envio',localidade,dc_localidade"
					+ " from t_demanda_user inner join t_localidade on t_demanda_user.localidade = t_localidade.cd_localidade"
					+ " inner join t_usuario on t_demanda_user.pid=t_usuario.pid"
					+ " where t_demanda_user.pid="+user.getUser().getPid() + " order by data_envio asc";
			
		}
		try {
			// ConexaoBD nb = new ConexaoBD();
			// Associa conexão e executa SQL
			conn=fv.getConexao();
			 query = (Statement) conn.createStatement();
	            ResultSet rs = query.executeQuery(sql);

			// Recupera dados do set
			while (rs.next()) {
				Demanda_User user1 = new Demanda_User();
				Users u = new Users();
				u.setPid(rs.getInt("pid"));
				u.setNome(rs.getString("nome"));
				user1.setUser(u);
				Demanda d = new Demanda();
				d.setCd_demanda(rs.getString("cd_demanda"));
				d.setData(rs.getString("dt_envio"));
				d.setTempo(rs.getString("tempo"));
				Localidade l = new Localidade();
				l.setCd_localidade(rs.getInt("localidade"));
				l.setDc_localidade(rs.getString("dc_localidade"));
				d.setLocalidade(l);
				user1.setDemanda(d);

				
				user1.getDemanda().setLocalidade(l);
				arrDemand.add(user1);
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Retorna objeto
		return arrDemand;
	}

	public String buscaApontamentosSomaHoras(Demanda_User user) throws Exception{
		String soma = "00:00:00";
		if (user.getUser().getPid()!=0) {
			//sql = "SELECT * FROM T_DEMANDA_USER WHERE pid = " + pDemanda.getUser().getPid();
			//sql = "SELECT * FROM T_DEMANDA_USER";
			/*sql="select pid,cd_demanda, tempo, data_envio,localidade,dc_localidade from t_demanda_user inner join t_localidade "
					+ "					on t_demanda_user.localidade = t_localidade.cd_localidade";*/
			 
			sql="select convert(varchar(8),"+
					" (SELECT DATEADD(ms, SUM(DATEDIFF(ms, '00:00:00', (convert(Time, tempo) ))), '00:00:00') as time   FROM"
					+ " T_DEMANDA_USER where pid="+user.getUser().getPid()+"),108) 'SOMA' ";

			
		}
		
		
		
		
		
		
		try {
			// ConexaoBD nb = new ConexaoBD();
			// Associa conexão e executa SQL
			
			conn=fv.getConexao();
			 query = (Statement) conn.createStatement();
	            ResultSet rs = query.executeQuery(sql);
	           
			// Recupera dados do set
			while (rs.next()) {
			soma=rs.getString("SOMA");
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Retorna objeto
		return soma;

	}
	
	
	public ArrayList<Demanda_User> buscaApontamentos(Demanda_User user,String dataFiltro1) {

		// Cria novo objeto
		
ArrayList<Demanda_User> arrDemand = new ArrayList<>();
		// Define SQL

		if (user.getUser().getPid()!=0) {
			//sql = "SELECT * FROM T_DEMANDA_USER WHERE pid = " + pDemanda.getUser().getPid();
			//sql = "SELECT * FROM T_DEMANDA_USER";
			/*sql="select pid,cd_demanda, tempo, data_envio,localidade,dc_localidade from t_demanda_user inner join t_localidade "
					+ "					on t_demanda_user.localidade = t_localidade.cd_localidade";*/
			
			sql="select t_demanda_user.pid as 'pid',nome,cd_demanda, tempo,	(SELECT CONVERT(VARCHAR(10), data_envio, 103) + ' ' + CONVERT(VARCHAR(8), data_envio, 108))	as 'dt_envio'"
					+ ",localidade,dc_localidade from t_demanda_user inner join t_localidade on t_demanda_user.localidade = t_localidade.cd_localidade "
					+ "inner join t_usuario on t_demanda_user.pid=t_usuario.pid"
					+ "	where t_demanda_user.pid="+user.getUser().getPid()+" and data_envio >= (select CONVERT(SMALLDATETIME,'"+dataFiltro1+"',103)) order by data_envio asc";
			
		}
		try {
			// ConexaoBD nb = new ConexaoBD();
			// Associa conexão e executa SQL
			conn=fv.getConexao();
			 query = (Statement) conn.createStatement();
	            ResultSet rs = query.executeQuery(sql);

			// Recupera dados do set
			while (rs.next()) {
				Demanda_User user1 = new Demanda_User();
				Users u = new Users();
				u.setPid(rs.getInt("pid"));
				u.setNome(rs.getString("nome"));
				user1.setUser(u);
				Demanda d = new Demanda();
				d.setCd_demanda(rs.getString("cd_demanda"));
				d.setData(rs.getString("dt_envio"));
				d.setTempo(rs.getString("tempo"));
				Localidade l = new Localidade();
				l.setCd_localidade(rs.getInt("localidade"));
				l.setDc_localidade(rs.getString("dc_localidade"));
				d.setLocalidade(l);
				user1.setDemanda(d);

				
				user1.getDemanda().setLocalidade(l);
				arrDemand.add(user1);
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Retorna objeto
		return arrDemand;
	}
	
	public String buscaApontamentosSomaHoras(Demanda_User user,String dataFiltro1) throws Exception{
		String soma = "00:00:00";
		if (user.getUser().getPid()!=0) {
			//sql = "SELECT * FROM T_DEMANDA_USER WHERE pid = " + pDemanda.getUser().getPid();
			//sql = "SELECT * FROM T_DEMANDA_USER";
			/*sql="select pid,cd_demanda, tempo, data_envio,localidade,dc_localidade from t_demanda_user inner join t_localidade "
					+ "					on t_demanda_user.localidade = t_localidade.cd_localidade";*/
			 
			sql="select convert(varchar(10),"+
					" (SELECT DATEADD(ms, SUM(DATEDIFF(ms, '00:00:00', (convert(Time, tempo) ))), '00:00:00') as time   FROM"
					+ " T_DEMANDA_USER where t_demanda_user.pid="+user.getUser().getPid()+" and data_envio >= (select CONVERT(SMALLDATETIME,'"+dataFiltro1+"',103))),108) 'SOMA'";

			
	}
		
	
	
	

		try {
			// ConexaoBD nb = new ConexaoBD();
			// Associa conexão e executa SQL
			
			conn=fv.getConexao();
			 query = (Statement) conn.createStatement();
	            ResultSet rs = query.executeQuery(sql);
	           
			// Recupera dados do set
			while (rs.next()) {
			soma=rs.getString("SOMA");
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Retorna objeto
		return soma;
	}
	
		public String buscaApontamentosSomaHoras(Demanda_User user,String dataFiltro1,String dataFiltro2) throws Exception{
			String soma = "00:00:00";
			if (user.getUser().getPid()!=0) {
				//sql = "SELECT * FROM T_DEMANDA_USER WHERE pid = " + pDemanda.getUser().getPid();
				//sql = "SELECT * FROM T_DEMANDA_USER";
				/*sql="select pid,cd_demanda, tempo, data_envio,localidade,dc_localidade from t_demanda_user inner join t_localidade "
						+ "					on t_demanda_user.localidade = t_localidade.cd_localidade";*/
				if (dataFiltro1.equals(dataFiltro2)){ 
					sql="select convert(varchar(10),"+
							" (SELECT DATEADD(ms, SUM(DATEDIFF(ms, '00:00:00', (convert(Time, tempo) ))), '00:00:00') as time   FROM"
							+ " T_DEMANDA_USER where t_demanda_user.pid="+user.getUser().getPid()+" and convert( varchar(11),data_envio) = (select CONVERT(SMALLDATETIME,'"+dataFiltro1+"',103))),108) 'SOMA'";

			}else {
					
					sql="select convert(varchar(10),"+
							" (SELECT DATEADD(ms, SUM(DATEDIFF(ms, '00:00:00', (convert(Time, tempo) ))), '00:00:00') as time   FROM"
							+ " T_DEMANDA_USER where t_demanda_user.pid="+user.getUser().getPid()+" and data_envio BETWEEN (select CONVERT(SMALLDATETIME,'"+dataFiltro1+"',103)) and (select CONVERT(SMALLDATETIME,'"+dataFiltro2+"',103))),108) 'SOMA'";
						
				}
				
			}
		
		
		
		try {
			// ConexaoBD nb = new ConexaoBD();
			// Associa conexão e executa SQL
			
			conn=fv.getConexao();
			 query = (Statement) conn.createStatement();
	            ResultSet rs = query.executeQuery(sql);
	           
			// Recupera dados do set
			while (rs.next()) {
			soma=rs.getString("SOMA");
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Retorna objeto
		return soma;

	}
	
	public ArrayList<Demanda_User> buscaApontamentos(Demanda_User user,String dataFiltro1,String dataFiltro2) throws Exception {

		// Cria novo objeto
		
ArrayList<Demanda_User> arrDemand = new ArrayList<>();
		// Define SQL

		if (user.getUser().getPid()!=0) {
			//sql = "SELECT * FROM T_DEMANDA_USER WHERE pid = " + pDemanda.getUser().getPid();
			//sql = "SELECT * FROM T_DEMANDA_USER";
			/*sql="select pid,cd_demanda, tempo, data_envio,localidade,dc_localidade from t_demanda_user inner join t_localidade "
					+ "					on t_demanda_user.localidade = t_localidade.cd_localidade";*/
			
			sql="select t_demanda_user.pid as 'pid',nome,cd_demanda, tempo,	(SELECT CONVERT(VARCHAR(10), data_envio, 103) + ' ' + CONVERT(VARCHAR(8), data_envio, 108))	as 'dt_envio',"
					+ " localidade,dc_localidade from t_demanda_user inner join t_localidade on t_demanda_user.localidade = t_localidade.cd_localidade"
					+ " inner join t_usuario on t_demanda_user.pid=t_usuario.pid"
					+ " where t_demanda_user.pid="+user.getUser().getPid()+" and data_envio"
					+ "	between (select CONVERT(SMALLDATETIME,'"+dataFiltro1+"',103)) and (select CONVERT(SMALLDATETIME,'"+dataFiltro2+"',103)+1) order by data_envio asc";
			
		}
		try {
			// ConexaoBD nb = new ConexaoBD();
			// Associa conexão e executa SQL
			
			 query = (Statement) fv.getConexao().createStatement();
	            ResultSet rs = query.executeQuery(sql);

			// Recupera dados do set
			while (rs.next()) {
				Demanda_User user1 = new Demanda_User();
				Users u = new Users();
				u.setPid(rs.getInt("pid"));
				u.setNome(rs.getString("nome"));
				user1.setUser(u);
				Demanda d = new Demanda();
				d.setCd_demanda(rs.getString("cd_demanda"));
				d.setData(rs.getString("dt_envio"));
				d.setTempo(rs.getString("tempo"));
				Localidade l = new Localidade();
				l.setCd_localidade(rs.getInt("localidade"));
				l.setDc_localidade(rs.getString("dc_localidade"));
				d.setLocalidade(l);
				user1.setDemanda(d);

				
				user1.getDemanda().setLocalidade(l);
				arrDemand.add(user1);
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Retorna objeto
		return arrDemand;
	}
	
	
	
	
	
	
	
	public void insereDemandaUser(Users du, Demanda dd) throws Exception {

		// Define SQL
		sql = "INSERT INTO T_DEMANDA_USER VALUES (?, ?,?,SYSdatetime(),?)";

		try {

			// Prepara SQL e alimenta parametros
			PreparedStatement query = fv.getConexao().prepareStatement(sql);
			query.setLong(1, du.getPid());
			query.setString(2, dd.getCd_demanda());
			query.setString(3, dd.getTempo());
			query.setInt(4, dd.getLocalidade().getCd_localidade());

			// Executa SQL
			query.execute();
			query.close();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			String url_arq="C:\\ControleHoras\\log_erro.txt";
		 	File arq1 = new File(url_arq);
		 	
		 	  FileWriter arq = new FileWriter("C:\\ControleHoras\\log_erro.txt");
			    PrintWriter gravarArq = new PrintWriter(arq);
			    Calendar data ; 
			    String gd=Calendar.getInstance().toString();
			    			
			    gravarArq.printf(gd+"ERRO = "+ e.getMessage());
			    gravarArq.printf("+-------------+%n");
			 
			    
		 		arq.close();
		 	
			e.printStackTrace();
			throw new Exception("ERRO AO ENVIAR HORAS!\n Tente Novamente! E se necessário, \n Informe ao Admnistrador do sistema!\n" +e.getMessage());
		}

	}

	/*
	 * @Override public boolean updateFuncionario(Funcionario funcionario) {
	 * 
	 * // Define SQL sql =
	 * "UPDATE FUNCIONARIOS SET ID_FUNCIONARIO = ?, NOME = ?, SOBRENOME = ?," +
	 * " CARGO = ?, SALARIO = ? WHERE ID_FUNCIONARIO = ?";
	 * 
	 * try {
	 * 
	 * // Prepara SQL e alimenta parametros PreparedStatement query =
	 * conn.prepareStatement(sql); query.setLong(1, funcionario.getId());
	 * query.setString(2, funcionario.getNome()); query.setString(3,
	 * funcionario.getSobrenome()); query.setString(4, funcionario.getCargo());
	 * query.setDouble(5, funcionario.getSalario()); query.setLong(6,
	 * funcionario.getId());
	 * 
	 * // Executa SQL query.execute(); query.close(); return true;
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * return false; }
	 */
	/*
	 * @Override public boolean deletaFuncionario(Funcionario funcionario) {
	 * 
	 * // Define SQL sql = "DELETE FROM FUNCIONARIOS WHERE ID_FUNCIONARIO = ?";
	 * 
	 * // Prepara SQL e alimenta parametros PreparedStatement query; try { //
	 * Executa SQL query = conn.prepareStatement(sql); query.setLong(1,
	 * funcionario.getId()); query.execute(); query.close();
	 * 
	 * return true; } catch (SQLException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); }
	 * 
	 * return false;
	 * 
	 * }
	 * 
	 * }
	 */
}
