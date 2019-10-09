package lab.illfact.javawebapps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class VinylDbUtil {

	private DataSource dataSource;

	public VinylDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Vinyl> getVinyls() throws Exception {
		
		List<Vinyl> vinyls = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			// create sql statement
			String sql = "select * from vinyl order by artist";
			
			myStmt = myConn.createStatement();
			
			// execute query
			myRs = myStmt.executeQuery(sql);
			
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id");
				String album = myRs.getString("album");
				String artist = myRs.getString("artist");
				
				// create new vinyl object
				Vinyl tempVinyl = new Vinyl(id, album, artist);
				
				// add it to the list of vinyls
				vinyls.add(tempVinyl);				
			}
			
			return vinyls;		
		}
		finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}		
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();   // doesn't really close it ... just puts back in connection pool
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void addVinyl(Vinyl theVinyl) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create sql for insert
			String sql = "insert into vinyl "
					   + "(album, artist) "
					   + "values (?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set the param values for the vinyl
			myStmt.setString(1, theVinyl.getAlbum());
			myStmt.setString(2, theVinyl.getArtist());
			
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public Vinyl getVinyl(String theVinylId) throws Exception {

		Vinyl theVinyl = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int vinylId;
		
		try {
			// convert vinyl id to int
			vinylId = Integer.parseInt(theVinylId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected vinyl
			String sql = "select * from vinyl where id=?";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, vinylId);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row
			if (myRs.next()) {
				String album = myRs.getString("album");
				String artist = myRs.getString("artist");
				
				// use the vinylId during construction
				theVinyl = new Vinyl(vinylId, album, artist);
			}
			else {
				throw new Exception("Could not find vinyl id: " + vinylId);
			}				
			
			return theVinyl;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void updateVinyl(Vinyl theVinyl) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create SQL update statement
			String sql = "update vinyl "
						+ "set album=?, artist=?"
						+ "where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theVinyl.getAlbum());
			myStmt.setString(2, theVinyl.getArtist());
			myStmt.setInt(3, theVinyl.getId());
			
			// execute SQL statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void deleteVinyl(String theVinylId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// convert vinyl id to int
			int vinylId = Integer.parseInt(theVinylId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to delete vinyl
			String sql = "delete from vinyl where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, vinylId);
			
			// execute sql statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}	
	}

	
}