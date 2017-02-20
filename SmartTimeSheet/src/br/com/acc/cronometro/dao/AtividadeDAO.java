package br.com.acc.cronometro.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.acc.cronometro.classesBasicas.Atividade;
import br.com.acc.cronometro.classesBasicas.Localidade;
import br.com.acc.cronometro.classesBasicas.Tag;
import br.com.acc.cronometro.conexao.ConexaoBD;

public class AtividadeDAO {
	
	
	    private Statement query;
	    private String sql;
		ConexaoBD fv = new ConexaoBD();
	
	
	public ArrayList<Atividade> buscaTodasAtividades() {
		 
        // Cria novo objeto
		
		ArrayList<Atividade> arrAtividade = new ArrayList<Atividade>();
 
        // Define SQL
        sql = "SELECT * FROM T_tipo_ATIVIDADE order by id_ordenador asc";
 
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
            	Atividade vAtividade = new Atividade();
            	vAtividade.setId(rs.getInt("id_atividade"));
            	vAtividade.setTag(rs.getString("tag"));
            	vAtividade.setDescrição(rs.getString("dc_atividade"));
            	vAtividade.setIdbox(rs.getInt("id_habilita_txb"));
            	
                arrAtividade.add(vAtividade);
            }
 
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        // Retorna objeto
        return arrAtividade;
    }
	
	
	public ArrayList<Tag> buscaTodasTags() {
		 
        // Cria novo objeto
		
		ArrayList<Tag> arrAtividade = new ArrayList<Tag>();
 
        // Define SQL
        sql = "SELECT * FROM T_TAG order by id desc";
 
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
            	Tag vTag = new Tag();
            	vTag.setId(rs.getInt("id"));
            	vTag.setDcTag(rs.getString("dcTag"));
            	
            	
                arrAtividade.add(vTag);
            }
 
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        // Retorna objeto
        return arrAtividade;
    }
	
	
	
}
