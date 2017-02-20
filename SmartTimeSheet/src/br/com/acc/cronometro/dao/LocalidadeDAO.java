package br.com.acc.cronometro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import br.com.acc.cronometro.classesBasicas.Localidade;
import br.com.acc.cronometro.conexao.ConexaoBD;

public class LocalidadeDAO {
	
	 private Connection conn = null;
	    private Statement query;
	    private String sql;
		ConexaoBD fv = new ConexaoBD();
		
	public LocalidadeDAO(){
		
		 
		//this.conn = fv.getConexao();
	}
	
	
	public boolean insereLocalidade(Localidade localidade) {
		 String sql;
        // Define SQL
        sql = "INSERT INTO T_LOCALIDADE VALUES (?)";
 
        try {
 
            // Prepara SQL e alimenta parametros
            PreparedStatement query = null;
			try {
				query = fv.getConexao().prepareStatement(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            query.setString(1, localidade.getDc_localidade());
            
            
            // Executa SQL
            query.execute();
            query.close();
            return true;
 
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        return false;
    }
 
	
	public ArrayList<Localidade> buscaTodasLocalidades() {
		 
        // Cria novo objeto
		
		ArrayList<Localidade> arrLocalidade = new ArrayList<Localidade>();
 
        // Define SQL
        sql = "SELECT * FROM T_LOCALIDADE";
 
        try {
 
            // Associa conexão e executa SQL
            try {
				query = (Statement) fv.getConexao().createStatement();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ResultSet rs = query.executeQuery(sql);
 
            // Recupera dados do set
            while (rs.next()) {
            	Localidade vLocalidade = new Localidade();
                vLocalidade.setCd_localidade(rs.getInt("cd_localidade"));
                vLocalidade.setDc_localidade(rs.getString("dc_localidade")); 
                arrLocalidade.add(vLocalidade);
            }
 
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        // Retorna objeto
        return arrLocalidade;
    }
	
}
