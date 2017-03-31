package br.com.acc.cronometro.conexao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

import com.microsoft.sqlserver.jdbc.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ConexaoBD {

	public ConexaoBD() {
	}

	// Cria string de conexão de banco de dados
	private static String conexao;

	private static Connection con; // Variável de conexão que passará a url de conexão

	final String jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	final String databaseUrl = "jdbc:sqlserver://"+/*"10.2.119.206:1433"*/pegabanco()+";databaseName=controlehora_hml";
	final String user = "controlh";
	final String password = "nAtura00";
/*
	final String jdbcDriver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	final String databaseUrl2 = "jdbc:sqlserver://controlehora.mssql.somee.com";
	final String user2 = "arthur2807_SQLLogin_1";
	final String password2 = "to7mm1clee";
	
	final String jdbcDriver3 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	final String databaseUrl3 = "jdbc:sqlserver://choras.database.windows.net:1433;";
	final String user3 = "arthur2807@choras";
	final String password3 = "nAtura@12";*/

	public Connection getConexao() throws Exception {
		// Class.forName(jdbcDriver);
		//if (con == null) {
			
			con=retConn();
			return con;
	//}else{
//	return con;	}
	}
	
	public Connection retConn() throws Exception{
		try {
			System.out.println("Entrou no reConn o que nao pode");
			
			con = DriverManager.getConnection(databaseUrl, user, password);
			// con=
			// DriverManager.getConnection("jdbc:sqlserver://bd2ch.database.windows.net:1433;database=controleh2;user=arthur2807@bd2ch;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

			/*
			 * con = DriverManager.getConnection(
			 * "jdbc:sqlserver://choras.database.windows.net;" +
			 * "database=choras;" + "user=yourusername@yourserver;" +
			 * "password=nAtura@12;" + "encrypt=true;" +
			 * "trustServerCertificate=false;" +
			 * "hostNameInCertificate=*.database.windows.net;" +
			 * "loginTimeout=30;");
			 */
			System.out.println("Connectado rede accenture!!!");
			
		} catch (SQLException ex) {
			// Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE,
			// null, ex);[
			
			 	String url_arq="C:\\ControleHoras\\log_erro.txt";
			 	File arq1 = new File(url_arq);
			 	
			 	  FileWriter arq = new FileWriter("C:\\ControleHoras\\log_erro.txt");
				    PrintWriter gravarArq = new PrintWriter(arq);
				    Calendar data ; 
				    String gd=Calendar.getInstance().toString();
				    			
				    gravarArq.printf(gd+"Falha na conexão\n "+ ex.getMessage());
				    gravarArq.printf("+-------------+%n");
				 
				    
			 		arq.close();
			 		System.out.println(ex.getMessage());
			 	throw new Exception("Falha na conexão\n "+ex.getMessage());
			 
			 
			    //System.out.printf("\nTabuada do %d foi gravada com sucesso em \"d:\\tabuada.txt\".\n", n);
			
			
			
			
			//---------------------------------------------------
			
			//throw new Exception("Problema com SQL! Informar erro ao administrator do sistema! \n"+ ex.getMessage());
			
		}
		return con;
	} 
	 private static String pegabanco() {
		 class ipb{
			 String ip;
		 }
			FileReader reader = null;
			try {
				//carrega o arquivo XML para um objeto reader
				reader = new FileReader("C:\\ControleHoras\\configserver.xml");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			//Cria o objeto xstream
			XStream xStream = new XStream(new DomDriver());
			//informamos as tags que serao lidas
			xStream.alias("banco", ipb.class);// 1º tag
			xStream.aliasField("ip", ipb.class, "ip");// tag filha
			//cria um objeto Banco,
			//contendo os dados do xml
			ipb banco =  (ipb) xStream.fromXML(reader);
			//Exibimos no console o resultado
			System.out.println(banco.ip);
			return banco.ip;
		}
}
