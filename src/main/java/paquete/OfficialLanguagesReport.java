package paquete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.cj.jdbc.CallableStatement;
import com.mysql.cj.protocol.Resultset;

public class OfficialLanguagesReport {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String basedatos = "world";
		String host = "localhost";
		String port = "3306";
		String parAdic = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
		String user = "root";
		String pwd = "root";
		
		Connection c = null;
		PreparedStatement statement = null;
		
		ArrayList<String> lista = new ArrayList<String>();
		
		Scanner leeTeclado = new Scanner(System.in);
		System.out.println("Introduce el nombre del continente para ver sus idiomas oficiales:");
		String continente = leeTeclado.next();
		
		
		try {
			c = DriverManager.getConnection(urlConnection, user, pwd);
			System.out.println("Conexion relaizada");
			
			statement = c.prepareStatement("SELECT CODE, NAME FROM COUNTRY WHERE CONTINENT =?");
			statement.setString(1, continente);
			ResultSet rs =  statement.executeQuery();
			
			System.out.printf("%-35s%-15s%-40s%n", "Pais", "Idioma Oficial", "Porcentaje");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------");
			while(rs.next()) {
				String name= rs.getString("NAME");
				String codigo = rs.getString("CODE");
				
				
				PreparedStatement ps = c.prepareStatement("SELECT LANGUAGE, PERCENTAGE FROM COUNTRYLANGUAGE WHERE COUNTRYCODE =?");
				ps.setString(1, codigo);
				ResultSet rsPais = ps.executeQuery();
				
				while(rsPais.next()) {
					double porcentaje = rsPais.getDouble("PERCENTAGE");
					String lengua = rsPais.getString("LANGUAGE");
					//System.out.println(name + " " + lengua.toUpperCase() + " " +  porcentaje);
					System.out.printf("%-35s%-15s%-40s%n", name, lengua, porcentaje);
				}
			}
			
			
			
			
		} catch (SQLException e) {
			e.getMessage();
		}catch(Exception e) {
			e.printStackTrace(System.err);
		}finally {
			try {
				if(c != null) {
					c.close();
				}	
			}catch (Exception ex) {
				// TODO: handle exception
			}
		}
	}

}
