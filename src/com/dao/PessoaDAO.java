package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.banco.ConexaoHSQLDB;
import com.entity.Pessoa;

public class PessoaDAO extends ConexaoHSQLDB{
	
	final String SQL_INSERT_PESSOA = "INSERT INTO PESSOA( NOME, DATA, ALTURA ) VALUES ( ?,?,? )";
	final String SQL_SELECT_PESSOA = "SELECT * FROM PESSOA";
	final String SQL_SELECT_PESSOA_ID = "SELECT * FROM PESSOA WHERE ID = ?";
	final String SQL_SELECT_ALTERA_PESSOA = "UPDATE PESSOA SET  NOME = ?, DATA = ?, ALTURA = ? WHERE ID = ?"; 
	final String SQL_DELETA_PESSOA = "DELETE FROM PESSOA WHERE ID = ?";
	
	
	
	public int inserir (Pessoa pessoa)
	{
		int quantidade = 0;
		
		
		try (Connection connection = this.conectar();
			PreparedStatement pst	= connection.prepareStatement(SQL_INSERT_PESSOA);)
		{

			pst.setString(1, pessoa.getNome());
			pst.setDate(2,pessoa.getData());
			pst.setDouble(3, pessoa.getAltura());
			quantidade = pst.executeUpdate();			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
		return quantidade;
	}
	
	public List<Pessoa> listALL()
	{
		List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
		try(Connection connection = this.conectar();
				PreparedStatement pst	= connection.prepareStatement(SQL_SELECT_PESSOA);)
		{
			ResultSet rs = pst.executeQuery();		
			while(rs.next())
			{
				Pessoa pessoa = new Pessoa();				
				pessoa.setId(rs.getInt("ID"));
			    pessoa.setNome(rs.getString("NOME"));
				pessoa.setData(rs.getDate("DATA"));
				pessoa.setAltura(rs.getFloat("ALTURA"));
				listaPessoas.add(pessoa);
			}			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
		return listaPessoas;
	}

	public Pessoa findByID(int id)
	{
		Pessoa pessoa = null;
		try(Connection connection = this.conectar();
				PreparedStatement pst	= connection.prepareStatement(SQL_SELECT_PESSOA_ID);)
		{
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				pessoa = new Pessoa();				
     			pessoa.setId(rs.getInt("ID"));
			    pessoa.setNome(rs.getString("NOME"));
			    pessoa.setData(rs.getDate("DATA"));
				pessoa.setAltura(rs.getFloat("ALTURA"));			
				
			}		
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	  return pessoa;
	}

	public int alterar(Pessoa pessoa) {

		int quantidade = 0;		
		try (Connection connection = this.conectar();
			PreparedStatement pst	= connection.prepareStatement(SQL_SELECT_ALTERA_PESSOA);)
		{
			pst.setString(1, pessoa.getNome());
			pst.setDate(2, pessoa.getData());
			pst.setDouble(3, pessoa.getAltura());
			pst.setInt(4, pessoa.getId());
			quantidade = pst.executeUpdate();			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return quantidade;
	}
	
	public int deleta(Pessoa pessoa)
	{
		int quantidade = 0;
		
		try (Connection connection = this.conectar();
			PreparedStatement pst = connection.prepareStatement(SQL_DELETA_PESSOA);)
		{
			pst.setInt(1, pessoa.getId());
			quantidade = pst.executeUpdate();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}	 
		
		return quantidade;
	}	
}
