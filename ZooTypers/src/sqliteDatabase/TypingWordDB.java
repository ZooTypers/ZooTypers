package sqliteDatabase;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class TypingWordDB {
	private Properties configProps = new Properties();
	private String DBDriver;
	private String DBUrl;
	private String DBUser;
	private String DBPassword;
	
	private Connection DBcon;
	
	private PreparedStatement getStatement;
	
	private static final String GET_STATEMENT =
			"SELECT a.fname FROM actor a WHERE a.lname = 'Oak';";
	
	public void openConnection() throws Exception {
		Class.forName("org.postgresql.Driver");
		String DBUrl = "jdbc:postgresql://ec2-107-22-182-174.compute-1.amazonaws.com:5432/d8s215lh2ft9ql?user=dlzfvuftoxjgap&password=FIyzZb2vl8pTSaVMJCcXmu8Px9&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
//		configProps.setProperty("user","dlzfvuftoxjgap");
//		configProps.setProperty("password","FIyzZb2vl8pTSaVMJCcXmu8Px9");
//		configProps.setProperty("ssl","true");
		DBcon = DriverManager.getConnection(DBUrl);
		DBcon.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);		
		
		
	}
	
	public void closeConnection() throws Exception {
		DBcon.close();
	}
	
	public void prepareStatements() throws Exception {
		getStatement = DBcon.prepareStatement(GET_STATEMENT);
	}
	
	public String getSomething() throws Exception {
		ResultSet result_set = getStatement.executeQuery();
		String firstName = "";
		if (result_set.next()) {
			firstName = result_set.getString(1);
		}
		result_set.close();
		return firstName;
	}
}
