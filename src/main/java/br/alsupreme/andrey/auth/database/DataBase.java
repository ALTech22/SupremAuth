package main.java.br.alsupreme.andrey.auth.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;

import main.java.br.alsupreme.andrey.auth.SupremAuth;

public abstract class DataBase {
	protected Connection connection;
	protected SupremAuth plugin;

	protected abstract void genConnection();
	protected abstract void genDatabase();

	
	public DataBase(SupremAuth plugin) {
		this.plugin = plugin;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public boolean deleteData(String table, String condition) {
		String script = "DELETE FROM " + table + " WHERE " + condition;
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(script);
			st.close();
			return true;
		}catch (Exception e) {
			Error.execute(plugin, e);
			return false;
		}
	}
	public boolean updateData(String table, String column, String value, String compareColumn, String logicalOperator,
			String valueToCompare) {
		String script = "UPDATE " + table + " SET " + column + " = " + value + " WHERE " + compareColumn + logicalOperator + valueToCompare +";";
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(script);
			st.close();
			return true;
		}catch (Exception e) {
			Error.execute(plugin, e);
			return false;
		}
	}
	
	public boolean insertData(String table, @Nullable String columns, String values) {
		String script = null;
		if(columns == null) {
			script = "INSERT INTO "+table + " VALUES("+values+");";
		}else {
			script = "INSERT INTO "+table+" ("+columns+") VALUES("+values+");";
		}
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(script);
			st.close();
			return true;
		}catch (Exception e) {
			Error.execute(plugin, e);
			return false;
		}
		
	}
	
	public boolean replaceData(String table, String Columns, String values) {
		String script = "REPLATE INTO " + table + " ("+Columns+") VALUES("+values+");";
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(script);
			st.close();
			return true;
		}catch (Exception e) {
			Error.execute(plugin, e);
			return false;
		}
	}
	
	public boolean runCustomSQLScript(String script) {
		try {
			Statement st = getConnection().createStatement();
			st.execute(script);
			close(st, null);
			return true;
		} catch (SQLException e) {
			Error.execute(plugin, e);
			return false;
		}
	}
	public String getString(@Nonnull String table, @Nonnull String columnName, String condition) {
		String result = null;
		try {
			String script = "SELECT "+ columnName + 
					" FROM " + table;

			script += " WHERE " + condition + ";";
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(script);
			
			if(rs != null) {
				while(rs.next()) {
						result = rs.getString(columnName);
				}
			}
			close(st, rs);
			return result;
		} catch (SQLException e) {
			Error.execute(plugin, e);
			return result;
		}
	}
	public Integer getInt(@Nonnull String table, @Nonnull String columnName, String condition) {
		Integer result = null;
		try {
			String script = "SELECT "+ columnName + 
					" FROM " + table;

			script += " WHERE " + condition + ";";
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(script);
			
			if(rs != null) {
				while(rs.next()) {
						result = rs.getInt(columnName);
				}
			}
			close(st, rs);
			return result;
		} catch (SQLException e) {
			Error.execute(plugin, e);
			return result;
		}
	}
	public Double getAverage(@Nonnull String table, @Nonnull String columnName, String condition) {
		Double result = null;
		try {
			String script = "SELECT AVG("+columnName + 
					") FROM " + table;

			script += " WHERE " + condition + ";";
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(script);
			
			if(rs != null) {
				while(rs.next()) {
						result = rs.getDouble(columnName);
				}
			}
			close(st, rs);
			return result;
		} catch (SQLException e) {
			Error.execute(plugin, e);
			return result;
		}
	}
	public Integer getLevelSRUS(@Nonnull int countSkill, @Nonnull ArrayList<String> skills, @Nonnull String condition) {
		Integer result = null;
		try {
			String script = "SELECT (";
			for(int i=0; i<skills.size(); i++) {
				script += (i == (skills.size()-1)) ? skills.get(i) : skills.get(i) + " + "; 
			}
			script += ")/" + countSkill + " FROM SRUS "
					+ "WHERE "+condition+";";
			Bukkit.getConsoleSender().sendMessage(script);
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(script);
			
			if(rs != null) {
				while(rs.next()) {
						result = rs.getInt(1);
				}
			}
			close(st, rs);
			return result;
		} catch (SQLException e) {
			Error.execute(plugin, e);
			return result;
		}
	}
	public Double getDouble(@Nonnull String table, @Nonnull String columnName, String condition) {
		Double result = null;
		try {
			String script = "SELECT "+ columnName + 
					" FROM " + table;

			script += " WHERE " + condition + ";";
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(script);
			
			if(rs != null) {
				while(rs.next()) {
						result = rs.getDouble(columnName);
				}
			}
			close(st, rs);
			return result;
		} catch (SQLException e) {
			Error.execute(plugin, e);
			return result;
		}
	}
	public boolean getBool(@Nonnull String table, @Nonnull String columnName, String condition) {
		boolean result = false;
		try {
			String script = "SELECT "+ columnName + 
					" FROM " + table;

			script += " WHERE " + condition + ";";
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(script);
			
			if(rs != null) {
				while(rs.next()) {
						result = rs.getBoolean(columnName);
				}
			}
			close(st, rs);
			return result;
		} catch (SQLException e) {
			Error.execute(plugin, e);
			return result;
		}
	}
	public Date getDate(@Nonnull String table, @Nonnull String columnName, String condition) {
		Date result = null;
		try {
			String script = "SELECT "+ columnName + 
					" FROM " + table;

			script += " WHERE " + condition + ";";
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(script);
			
			if(rs != null) {
				while(rs.next()) {
						result = rs.getDate(columnName);
				}
			}
			close(st, rs);
			return result;
		} catch (SQLException e) {
			Error.execute(plugin, e);
			return result;
		}
	}
	public List<Double> getDoubleList(@Nonnull String table, @Nonnull String columnName) {
		List<Double> result = new ArrayList<Double>();
		try {
			String script = "SELECT "+ columnName + 
					" FROM " + table
					+ " ORDER BY " + columnName + " ASC;";

			//script += " WHERE " + condition + ";";
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(script);
			
			if(rs != null) {
				while(rs.next()) {
						result.add(rs.getDouble(columnName));
				}
			}
			close(st, rs);
			return result;
		} catch (SQLException e) {
			Error.execute(plugin, e);
			return result;
		}
	}
	
	public List<String> seeMoneyTop() {
		List<String> Messages = new ArrayList<String>();
		List<String> player = new ArrayList<String>();
		List<Double> money = new ArrayList<Double>();
		try {
			String script = "SELECT player, money FROM Player ORDER BY money DESC;";
			PreparedStatement ps = connection.prepareStatement(script);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				player.add(rs.getString("player"));
				money.add(rs.getDouble("money"));
			}
			
		}catch (Exception e) {
			Error.execute(plugin, e);
		}
		
		for(int i=0; i<10; i++) {
			if(i>=player.size())
				break;
			Messages.add(player.get(i) + ": " + money.get(i));
		}
		
		return Messages;
	}
	public void close(Statement st, ResultSet rs) {
		
		try {
			st.close();
			rs.close();
		} catch (SQLException e) {
			Error.close(plugin, e);
		}
	}
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			Error.close(plugin, e);
		}
	}
	
}
