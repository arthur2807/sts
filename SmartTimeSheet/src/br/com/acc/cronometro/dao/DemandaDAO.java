package br.com.acc.cronometro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.acc.cronometro.classesBasicas.Demanda;
import br.com.acc.cronometro.conexao.ConexaoBD;


public class DemandaDAO {

	ConexaoBD fv = new ConexaoBD();
	public DemandaDAO() {
		 
        // Como o exemplo n�o possui uma tela de login
        // utilizamos uma adapta��o no construtor da classe
        // voc� pode utilizar um login para que o factory da conex�o
        // use a string de conex�o completa.
 
		//	this.conn = fv.getConexao();
		
    }
 
	
	
	
	 // Cria componentes
	   private Connection conn = null;
	    private Statement query;
	    private String sql;
	
	
	 public boolean insereDemanda(Demanda demanda) throws SQLException {
		 
	        // Define SQL
	        sql = "INSERT INTO demanda VALUES (?)";
	 
	        try {
	 
	            // Prepara SQL e alimenta parametros
	        	
	        	PreparedStatement query = fv.getConexao().prepareStatement(sql);
	            ((PreparedStatement) query).setString(1, demanda.getCd_demanda());
	            
	            
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
}
