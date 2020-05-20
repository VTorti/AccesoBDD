package modelo;

import java.sql.Date;
import java.sql.Timestamp;

import excepciones.CamposVaciosException;
import excepciones.ISBNException;

public class Libro {
	
	int idLibro;
	String titulo,autor,isbn,editorial;
	boolean prestado;
	java.sql.Date fechaPrestamo,fechaDevolucion;
	java.sql.Timestamp fechaAlta;
	
	public Libro(int idLibro, String titulo, String autor, String editorial, String isbn, boolean prestado,
			Date fechaPrestamo, Date fechaDevolucion, Timestamp fechaAlta) throws CamposVaciosException, ISBNException {
		super();
		this.setIdLibro(idLibro);
		this.setTitulo(titulo);
		this.setAutor(autor);
		this.setEditorial(editorial);
		this.setIsbn(isbn);
		this.setPrestado(prestado);
		this.setFechaPrestamo(fechaPrestamo);
		this.setFechaDevolucion(fechaDevolucion);
		this.setFechaAlta(fechaAlta);
	}
	public Libro(String titulo, String autor, String editorial, String isbn, boolean prestado,
			Date fechaPrestamo, Date fechaDevolucion, Timestamp fechaAlta) throws CamposVaciosException, ISBNException {
		super();
		this.setTitulo(titulo);
		this.setAutor(autor);
		this.setIsbn(isbn);
		this.setEditorial(editorial);
		this.setPrestado(prestado);
		this.setFechaPrestamo(fechaPrestamo);
		this.setFechaDevolucion(fechaDevolucion);
		this.setFechaAlta(fechaAlta);
	}
	public Libro(String titulo, String autor, String editorial, String isbn, boolean prestado) throws CamposVaciosException, ISBNException {
		super();
		this.setTitulo(titulo);
		this.setAutor(autor);
		this.setIsbn(isbn);
		this.setEditorial(editorial);
		this.setPrestado(prestado);
	}

	
	public Libro(int id, String nombre, String autor2, String editorial2) throws CamposVaciosException {
		this.setIdLibro(id);
		this.setTitulo(nombre);
		this.setAutor(autor2);
		this.setEditorial(editorial2);
		
	}
	public int getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(int idLibro2) {
		this.idLibro = idLibro2;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) throws CamposVaciosException {
		if(titulo.isEmpty()) throw new CamposVaciosException();
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) throws CamposVaciosException {
		if(autor.isEmpty())throw new CamposVaciosException();
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) throws CamposVaciosException, ISBNException {
		if(isbn.isEmpty())throw new CamposVaciosException();
		if(!compruebaISBN(isbn)) throw new ISBNException();
		this.isbn = isbn;
	}

	private boolean compruebaISBN(String isbn2) throws ISBNException {
		boolean correcto=false;
		String []campos;
		campos=isbn2.split("-");
		if (campos.length!=5) {
			throw new ISBNException();
		}
		isbn2=campos[0]+campos[1]+campos[2]+campos[3];
		int DC=Integer.parseInt(campos[4]);
		boolean impar=true;
		int acu=0,num=0;
		for (int i = 0; i < isbn2.length(); i++) {
			if(impar) {
				num=Integer.parseInt(Character.toString(isbn2.charAt(i)));
				acu+=num;
			}else {
				num=Integer.parseInt(Character.toString(isbn2.charAt(i)));
				acu+=(num*3);
			}
			impar=!impar;
		}
		int dc=10-(acu%10);
		if(dc==10)dc=0;
		if (dc==DC) correcto=true;
		
		return correcto;
	}
	
	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) throws CamposVaciosException {
		if(editorial.isEmpty())throw new CamposVaciosException();
		this.editorial = editorial;
	}

	public boolean isPrestado() {
		return prestado;
	}

	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}

	public java.sql.Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(java.sql.Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public java.sql.Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(java.sql.Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public java.sql.Timestamp getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(java.sql.Timestamp fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Libro= " + idLibro + "," + titulo + "," + autor + "," + editorial + "," + isbn +"," + prestado + "," + fechaPrestamo
				+ "," + fechaDevolucion + "," + fechaAlta;
	}
	


	
	
}
