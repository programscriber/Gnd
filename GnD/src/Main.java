import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=dbsbidebitcard", "sa", "P@ssword1234567");
		PreparedStatement pstmt = con.prepareStatement("select max(substring(FILENAME,19,2)) as seqval from master_core_files where FILENAME like ? and core_file_Id =? order by seqval" );
		pstmt.setString(1, "SBTNCGND7071019012017%");
		pstmt.setString(2, "127510");
		ResultSet rs= pstmt.executeQuery();
		
				
		if(rs.next()){
			System.out.println(rs.getString(1));
			
		}
	
	}

}
