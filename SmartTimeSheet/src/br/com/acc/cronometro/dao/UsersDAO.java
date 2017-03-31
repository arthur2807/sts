package br.com.acc.cronometro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;






import br.com.acc.cronometro.classesBasicas.Demanda_User;
import br.com.acc.cronometro.classesBasicas.Users;
import br.com.acc.cronometro.conexao.ConexaoBD;

public class UsersDAO {
	ConexaoBD fv = new ConexaoBD();
	public UsersDAO() {
		 
        // Como o exemplo não possui uma tela de login
        // utilizamos uma adaptação no construtor da classe
        // você pode utilizar um login para que o factory da conexão
        // use a string de conexão completa.
 
     //   this.conn = fv.getConexao();
    }
 
	
	
	
	 // Cria componentes
	   private Connection conn = null;
	    private Statement query;
	    private String sql;
		
	    public String validaVersao(String versao) throws Exception{
	    	
	    	sql="select versao from t_versao_sts";
	    	 query = (Statement) fv.getConexao().createStatement();
	            ResultSet rs = query.executeQuery(sql);
	            while (rs.next()) {
	            	versao=rs.getString("versao");
	            }
	    	return versao;
	    }
	    
	    
	    
		public ArrayList<Users> buscaUser(Users users) throws Exception {
	 
	        // Cria novo objeto
			ArrayList<Users> arrUser = new ArrayList<Users>();
	 
	        // Define SQL
			if (users.getPid()!=0){
	        sql = "SELECT pid,eid,telefone,nome,senha,id_tipo_usuario FROM T_USUARIO WHERE pid = " + users.getPid() + " and senha='"+users.getSenha()+"'";
			}else{
				sql = "SELECT pid,eid,telefone,nome,senha,id_tipo_usuario FROM T_USUARIO WHERE eid like '%" + users.getEid()+"%' and senha='"+users.getSenha()+"'"; 
				
				
			}
	        try {
	            // Associa conexão e executa SQL
	            query = (Statement) fv.getConexao().createStatement();
	            ResultSet rs = query.executeQuery(sql);
	 
	            // Recupera dados do set
	            while (rs.next()) {
	            	Users user = new Users();
	                user.setPid(rs.getInt("pid"));
	                user.setEid(rs.getString("eid"));
	                user.setTelefone((rs.getString("telefone")));
	                user.setNome(rs.getString("nome"));
	                user.setSenha((rs.getString("senha")));
	                user.setTipoAcesso((rs.getInt("id_tipo_usuario")));
	                arrUser.add(user);
	            }
	 
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	        	
	        	  e.printStackTrace();
	        	throw new Exception(e.getMessage());
	          
	        }
	 
	        // Retorna objeto
	        return arrUser;
	    }
	 
	    public boolean inserirUser(Users user) throws SQLException {
	 
	        // Define SQL
	        sql = "INSERT INTO T_USUARIO VALUES (?, ?,?,?,?,?)";
	 
	        try {
	 
	            // Prepara SQL e alimenta parametros
	        	
	        	PreparedStatement query = fv.getConexao().prepareStatement(sql);
	            ((PreparedStatement) query).setLong(1, user.getPid());
	            ((PreparedStatement) query).setString(2, user.getEid());
	            ((PreparedStatement) query).setString(3, user.getTelefone());
	            ((PreparedStatement) query).setString(4, user.getNome());
	            ((PreparedStatement) query).setString(5, user.getSenha());
	            ((PreparedStatement) query).setInt(6, user.getTipoAcesso());
	            
	            // Executa SQL
	            query.execute();
	            query.close();
	            return true;
	 
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	 
	        return false;
	    }

	    public boolean updateUser(Users user) throws SQLException {
	 
	        // Define SQL
	        sql = "UPDATE T_USUARIO SET ID_FUNCIONARIO = ?, NOME = ?, SOBRENOME = ?,"
	                + " CARGO = ?, SALARIO = ? WHERE ID_FUNCIONARIO = ?";
	 
	        try {
	 
	            // Prepara SQL e alimenta parametros
	            PreparedStatement query = fv.getConexao().prepareStatement(sql);
	            query.setLong(1, user.getPid());
	            query.setString(2, user.getNome());
	           
	            // Executa SQL
	            query.execute();
	            query.close();
	            return true;
	 
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	 
	        return false;
	    }
	 
	 /*   @Override
	    public boolean deletaFuncionario(Funcionario funcionario) {
	 
	        // Define SQL
	        sql = "DELETE FROM FUNCIONARIOS WHERE ID_FUNCIONARIO = ?";
	 
	        // Prepara SQL e alimenta parametros
	        PreparedStatement query;
	        try {
	            // Executa SQL
	            query = conn.prepareStatement(sql);
	            query.setLong(1, funcionario.getId());
	            query.execute();
	            query.close();
	 
	            return true;
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	 
	        return false;
	 
	    }
	 
	}*/
}
