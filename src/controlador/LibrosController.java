package controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import excepciones.CamposVaciosException;
import excepciones.ISBNException;
import modelo.Libro;

public class LibrosController {
	private final static String drv="com.mysql.jdbc.Driver";
	private final static String db="jdbc:mysql://localhost:3306/biblioteca?useSSL=false";
	private final static String user="root";
	private final static String Pass="";
	private Connection cn; //importamos la libreria de java.sql
	private Statement st; //para realizar las consultas, importamos de java.sql
	private ResultSet rs;
	private List<Libro> libros;
	private PreparedStatement pst;
	
	public LibrosController() throws ClassNotFoundException, SQLException, CamposVaciosException, ISBNException {
		super();
		//abrirConexion();
		//libros=getConsultaLibrosPst("select * from libros order by titulo");
		
	}

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}

	public List<Libro> getConsultaLibrosPst(String consulta) throws SQLException, CamposVaciosException, ISBNException {
		
		ArrayList<Libro>lista=null;//lista que vamos a devolver
		pst=cn.prepareStatement(consulta);//cn tiene que estar activo
		rs=pst.executeQuery();//ejecuta la consulta
		rs.last();//se va a la ultima fila para saber el tamaño de las columnas
		int tam=rs.getRow();//filas en la consulta
		
		rs.beforeFirst();//se va al principio
		if (tam>0) {
			lista=new ArrayList<Libro>();
			while (rs.next()) {
				int id=rs.getInt("idLibros");
				String titulo=rs.getString("titulo");
				String autor=rs.getString("autor");
				String editorial=rs.getString("editorial");
				String isbn=rs.getString("isbn");
				boolean prestado=rs.getBoolean("prestado");
				java.sql.Date fechaPrestamo=rs.getDate("fechaPrestamo");
				java.sql.Date fechaDevolucion=rs.getDate("fechaDevolucion");
				java.sql.Timestamp fechaAlta=rs.getTimestamp("fechaAlta");
				Libro libro=new Libro(id,titulo,autor,editorial,isbn,prestado,fechaPrestamo,fechaDevolucion,fechaAlta);
				lista.add(libro);
				libro=null;
			}
		}
		
		
		return lista;
	}

	public void abrirConexion() throws SQLException, ClassNotFoundException {
		
		Class.forName(drv);//Inicializa e instancia el driver
		cn=DriverManager.getConnection(db,user,Pass);//ruta, ususario, contraseña
		
		
	}

	public void cerrarConexion() throws SQLException {
		if (rs!=null) rs.close();
		if (st!=null) st.close();
		if (pst!=null) pst.close();
		if (cn!=null) cn.close();
		
		
		
	}
	
	public boolean agregarLibroPst(Libro libro) throws SQLException {
		boolean agregado=false;
		String titulo=libro.getTitulo();
		String autor=libro.getAutor();
		String editorial=libro.getEditorial();
		String isbn=libro.getIsbn();
		boolean prestado=libro.isPrestado();
		String sql="insert into libros values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement=cn.prepareStatement(sql);
		java.util.Date date=new java.util.Date();//fecha del sistema
		Timestamp time=new Timestamp(date.getTime());//pasa a sql la fecha de sistema
		preparedStatement.setInt(1, 0);
		preparedStatement.setString(2, titulo);
		preparedStatement.setString(3, autor);
		preparedStatement.setString(4, editorial);
		preparedStatement.setBoolean(5, prestado);
		preparedStatement.setDate(6, null);
		preparedStatement.setDate(7, null);
		preparedStatement.setString(8, isbn);
		preparedStatement.setTimestamp(9, time);
		preparedStatement.executeUpdate();//ejecuta la sentencia
		preparedStatement=null;
		
		agregado=true;
		
		return agregado;
	}
	
	public ArrayList<Libro> buscarLibroPst(String campo, String cadenaBusqueda) throws SQLException, CamposVaciosException, ISBNException{
		ArrayList<Libro>lista=null;
		String sql="select * from libros where "+campo+" = ?";
		PreparedStatement preparedStatement=cn.prepareStatement(sql);
		preparedStatement.setString(1, cadenaBusqueda);
		
		rs=preparedStatement.executeQuery();
		
		rs.last();
		int tam=rs.getRow();
		rs.beforeFirst();
		if(tam>0) {
			lista=new ArrayList<Libro>();
			while (rs.next()) {
				int id=rs.getInt("idLibros");
				String titulo=rs.getString("titulo");
				String autor=rs.getString("autor");
				String editorial=rs.getString("editorial");
				String isbn=rs.getString("isbn");
				boolean prestado=rs.getBoolean("prestado");
				java.sql.Date fechaPrestamo=rs.getDate("fechaPrestamo");
				java.sql.Date fechaDevolucion=rs.getDate("fechaDevolucion");
				java.sql.Timestamp fechaAlta=rs.getTimestamp("fechaAlta");
				Libro libro=new Libro(id,titulo,autor,editorial,isbn,prestado,fechaPrestamo,fechaDevolucion,fechaAlta);
				lista.add(libro);
				libro=null;
			}
		}
		
		return lista;
	}
	
	public int borrarLibroPst(Libro libro) throws SQLException {
		int rows=0;
		String campo="isbn";
		String sql="delete from libros where "+campo+"=?";
		PreparedStatement preparedStatement=cn.prepareStatement(sql);
		preparedStatement.setString(1, libro.getIsbn());
		rows=preparedStatement.executeUpdate();//filas afectadas
		
		return rows;
	}
	
	public int prestarLibroPst(Libro libro) throws SQLException {
		int rows=0;//filas afectadas
		String sql="Update libros set prestado=?,fechaPrestamo=?,fechaDevolucion=? where isbn=?";
		PreparedStatement preparedStatement =cn.prepareStatement(sql);
		//Obtenemos la fecha de sistema y calculamos la devolucion
		java.util.Date fecha=new java.util.Date();//fecha del sistema
		Calendar cale=new GregorianCalendar();
		cale.setTime(fecha);//objeto fecha
		cale.add(Calendar.DAY_OF_MONTH, 5);//añadimos 5 dias
		java.util.Date fechaDev=cale.getTime();//date a partir de un calendar
		java.sql.Date fechaPrestamo=new java.sql.Date(fecha.getTime());
		java.sql.Date fechaDevolucion=new java.sql.Date(fechaDev.getTime());
		preparedStatement.setBoolean(1, true);
		preparedStatement.setDate(2, fechaPrestamo);
		preparedStatement.setDate(3, fechaDevolucion);
		preparedStatement.setString(4, libro.getIsbn());
		rows=preparedStatement.executeUpdate();
		
		return rows;
	}
	public boolean modificar(Libro libro) throws SQLException {
		int rows=0;//filas afectadas
		boolean ok=false;
		String sql="Update libros set titulo=?,autor=?,editorial=? where idlibros=?";
		PreparedStatement preparedStatement =cn.prepareStatement(sql);
		//Obtenemos la fecha de sistema y calculamos la devolucion
		/*
		 * java.util.Date fecha=new java.util.Date();//fecha del sistema Calendar
		 * cale=new GregorianCalendar(); cale.setTime(fecha);//objeto fecha
		 * cale.add(Calendar.DAY_OF_MONTH, 5);//añadimos 5 dias java.util.Date
		 * fechaDev=cale.getTime();//date a partir de un calendar java.sql.Date
		 * fechaPrestamo=new java.sql.Date(fecha.getTime()); java.sql.Date
		 * fechaDevolucion=new java.sql.Date(fechaDev.getTime());
		 */
		preparedStatement.setString(1, libro.getTitulo());
		preparedStatement.setString(2, libro.getAutor());
		preparedStatement.setString(3, libro.getEditorial());
		preparedStatement.setFloat(4, libro.getIdLibro());
		rows=preparedStatement.executeUpdate();
		if (rows>0) {
			ok=true;
		}
		
		return ok;
		
		
	}

	public Date devolverLibroPst(Libro libro) throws SQLException {
		int rows=0;//filas afectadas
		String sql="Update libros set prestado=?,fechaPrestamo=?,fechaDevolucion=? where isbn=?";
		PreparedStatement preparedStatement =cn.prepareStatement(sql);
		//Obtenemos la fecha de sistema y calculamos la devolucion
		java.util.Date fecha=new java.util.Date();//fecha del sistema
		Calendar cale=new GregorianCalendar();
		cale.setTime(fecha);//objeto fecha
		java.util.Date fechaDev=cale.getTime();//date a partir de un calendar
		java.sql.Date fechaDevolucion=new java.sql.Date(fecha.getTime());
		preparedStatement.setBoolean(1, false);
		preparedStatement.setDate(2, null);
		preparedStatement.setDate(3, null);
		preparedStatement.setString(4, libro.getIsbn());
		rows=preparedStatement.executeUpdate();
		
		return fechaDevolucion;
	}
	
}
