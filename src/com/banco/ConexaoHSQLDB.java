package com.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoHSQLDB {
		
	private String usuario = "SA";
	private String senha = "";
	private String PathBase = "C:\\Users\\gabri\\OneDrive\\Documentos\\AULAS\\Programa��o Orientada a Objetos\\Prova1�Bi\\Dados\\pessoas";
	private String URL = "jdbc:hsqldb:file:" + PathBase + ";";
	
	public Connection conectar()
	{
		try {
			return DriverManager.getConnection(URL, usuario, senha);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new Error ("SQLException");
		}
	}
	
}
