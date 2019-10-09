package lab.illfact.javawebapps;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class VinylDbUtilTest  {

	@Mock
	private DataSource ds;
	
	@Mock
	private Connection c;
	
	@Mock PreparedStatement stmt;
	
	@Mock
	private ResultSet rs;
	
	private Vinyl v;
	
	@Before
	public void setUp() throws Exception {
		assertNotNull(ds);
		when(c.prepareStatement(any(String.class))).thenReturn(stmt);
		when(ds.getConnection()).thenReturn(c);
		
		v = new Vinyl(1, "The Wall", "Pink Floyd");
		
	/*	when(rs.first()).thenReturn(true);
		when(rs.getInt(1)).thenReturn(1);
		when(rs.getString(2)).thenReturn(v.getAlbum());
		when(rs.getString(3)).thenReturn(v.getArtist());
		when(stmt.executeQuery()).thenReturn(rs);
	*/	
	}
	
	@Test(expected=NullPointerException.class)
	public void nullCreateThrowsException() throws Exception {
		new VinylDbUtil(ds).addVinyl(null);
	}
	
	@Test
	public void testAddVinyl() throws Exception {
		new VinylDbUtil(ds).addVinyl(v);
	}
	
	@Test
	public void testUpdateVinyl() throws Exception {
		new VinylDbUtil(ds).updateVinyl(v);
		
		
	}
	@Test
	public void testGetVinyl() throws Exception {
		VinylDbUtil vinyl = new VinylDbUtil(ds);
		Vinyl u = new Vinyl(1, "The Wall", "Pink Floyd");
		vinyl.addVinyl(u);
		assertEquals(v, u);
		
	}
	@Test
	public void testDeleteVinyl() throws Exception {
		new VinylDbUtil(ds).deleteVinyl("1");
	}
	
}