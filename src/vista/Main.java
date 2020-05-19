package vista;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import controlador.LibrosController;
import excepciones.CamposVaciosException;
import excepciones.ISBNException;
import modelo.Libro;

public class Main {

	public static void main(String[] args) {
		
		try {
			Principal principal=new Principal();
		} catch (ClassNotFoundException | SQLException | CamposVaciosException | ISBNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*LibrosController biblioteca=null;
		
		try {
			biblioteca=new LibrosController();
			List<Libro>biblio=biblioteca.getLibros();
			mostrar(biblio);
			biblioteca.cerrarConexion();
			//Agregar un libro
			biblioteca.abrirConexion();
			Libro libro=new Libro("Down and out in Paris and London","George Orwell","Penguin","978-0-141-04270-1",false);
			//biblioteca.agregarLibroPst(libro);
			List<Libro> resultadoBusqueda=biblioteca.buscarLibroPst("autor", "George Orwell");
			for (Libro libro2 : resultadoBusqueda) {
				System.out.println(libro2);
			}
			//int rows=biblioteca.borrarLibroPst(libro);
			//System.out.println(rows+" registro borrado");
			
			int rows=biblioteca.prestarLibroPst(libro);
			System.out.println(libro.getTitulo()+" ha sido prestado");
			//biblioteca=null; //desinstancia biblioteca
			
			Date devolucion=biblioteca.devolverLibroPst(libro);
			System.out.println(libro.getTitulo()+" ha sido devuelto el dia "+devolucion);
		} catch (ClassNotFoundException | SQLException | CamposVaciosException | ISBNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String titulo="Una columna de fuego";
		String autor="Ken Follet";
		String editorial="Libros magicos";
		String isbn="978-84-15120-14-8";
		boolean prestado=false;
		String fechaCadena="04/05/2020";
		SimpleDateFormat formateador=new SimpleDateFormat("dd/MM/yyyy");
		formateador.setLenient(false);
		
		try {
			java.util.Date fecha=formateador.parse(fechaCadena);
			java.sql.Date fechaPrestamo=new java.sql.Date(fecha.getTime());
			java.sql.Timestamp fechaAlta=new java.sql.Timestamp(fecha.getTime());
			Libro libro=new Libro(titulo,autor,editorial,isbn,prestado,fechaPrestamo,fechaPrestamo,fechaAlta);
		} catch (ParseException | CamposVaciosException | ISBNException e) {
			// TODO Auto-generated catch block
			System.err.println(e);;
		}		

	}

	private static void mostrar(List<Libro> biblio) {
		for (Libro libro : biblio) {
			System.out.println(libro);
		}*/
		
	}
	
	

}
