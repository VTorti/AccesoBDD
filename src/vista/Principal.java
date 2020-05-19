package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.LibrosController;
import excepciones.CamposVaciosException;
import excepciones.ISBNException;
import modelo.Libro;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	private JPanel contentPane;
	private Libro libro;
	private LibrosController controlador;
	private List<Libro> lista;
	private int puntero=0;
	private JTextField txtId,txtNombre,txtAutor,txtEditorial,txtFechaPrestamo,txtFechaDevolucion,txtIsbn,txtFechaAlta;
	private JLabel lblLibros,lblId,lblNombre,lblAutor,lblEditorial,lblFechaPrestamo,lblFechaDevolucion,lblIsbn,lblFechaAlta,lblPrestado;
	private JCheckBox checkPrestado;
	private JButton btnPrimero,btnAtras,btnAdelante,btnUltimo;
	private JButton btnNuevo, btnEditar, btnBorrar, btnDeshacer, btnGuardar;
	private JLabel lblObligatorios;

	/**
	 * Create the frame.
	 * @throws ISBNException 
	 * @throws CamposVaciosException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Principal() throws ClassNotFoundException, SQLException, CamposVaciosException, ISBNException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		controlador=new LibrosController();
		controlador.abrirConexion();
		lista=new ArrayList<Libro>();
		lista=controlador.getConsultaLibrosPst("select * from libros order by titulo");
		controlador.cerrarConexion();
		
		
		
		definirVentana();
		mostrarLibro(lista.get(puntero));
		definirEvento();
		setVisible(true);
	}



	private void mostrarLibro(Libro libro2) {
		txtId.setText(Integer.toString(libro2.getIdLibro()));
		txtId.setEditable(false);
		txtNombre.setText(libro2.getTitulo());
		txtNombre.setEditable(false);
		txtAutor.setText(libro2.getAutor());
		txtAutor.setEditable(false);
		txtEditorial.setText(libro2.getEditorial());
		txtEditorial.setEditable(false);
		txtIsbn.setText(libro2.getIsbn());
		txtIsbn.setEditable(false);
		txtFechaPrestamo.setText(libro2.getFechaPrestamo()+"");
		txtFechaPrestamo.setEditable(false);
		txtFechaDevolucion.setText(libro2.getFechaDevolucion()+"");
		txtFechaDevolucion.setEditable(false);
		txtFechaAlta.setText(libro2.getFechaAlta()+"");
		txtFechaAlta.setEditable(false);
		
		if(libro2.isPrestado()) {
			checkPrestado.setSelected(true);
		}else {
			checkPrestado.setSelected(false);
		}
		checkPrestado.setEnabled(false);
		
		
	}



	private void definirEvento() {
		btnPrimero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				puntero=0;
				libro=lista.get(puntero);
				mostrarLibro(libro);
			}

			
		});
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (puntero!=0) {
					puntero--;
				}
				libro=lista.get(puntero);
				mostrarLibro(libro);
			}
		});
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				puntero=lista.size()-1;
				libro=lista.get(puntero);
				mostrarLibro(libro);
			}
		});
		btnAdelante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (puntero!=(lista.size()-1)) {
					puntero++;
				}
				libro=lista.get(puntero);
				mostrarLibro(libro);
			}
		});
		
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitaBotones(false);
				lblNombre.setText("Nombre*:");
				lblAutor.setText("Autor*:");
				lblEditorial.setText("Editorial*:");
				lblIsbn.setText("ISBN*:");
				lblObligatorios.setText("*Campos obligatorios");
				lblNombre.setForeground(Color.RED);
				lblAutor.setForeground(Color.RED);
				lblEditorial.setForeground(Color.RED);
				lblIsbn.setForeground(Color.RED);
				borraPanel();
				
				
			}

		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitaBotones(false);
			}
		});
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(contentPane, "¿Desea borrar "+txtNombre.getText()+"?");
			}
		});
		btnDeshacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitaBotones(true);
				habilitaNavegacion(true);
				lblNombre.setText("Nombre:");
				lblAutor.setText("Autor:");
				lblEditorial.setText("Editorial:");
				lblIsbn.setText("ISBN:");
				lblObligatorios.setText("");
				lblNombre.setForeground(Color.black);
				lblAutor.setForeground(Color.black);
				lblEditorial.setForeground(Color.black);
				lblIsbn.setForeground(Color.black);
				mostrarLibro(lista.get(puntero));
				
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitaBotones(true);
				boolean correcto=true;
				boolean ok=false;
				
				try {
				
				String nombre=txtNombre.getText();
				String autor=txtAutor.getText();
				String editorial=txtEditorial.getText();
				String isbn=txtIsbn.getText();
				
					Libro libro=new Libro(nombre, autor, editorial, isbn, false);
					controlador.abrirConexion();
					ok=controlador.agregarLibroPst(libro);
					
				} catch (CamposVaciosException | ISBNException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(contentPane, e1.getMessage());
					correcto=false;
				}
				
				if (correcto) {
					if (ok) {
						JOptionPane.showMessageDialog(contentPane, "Libro agregado con éxito");
						
					}
					
					try {
						lista=controlador.getConsultaLibrosPst("select * from libros");
						controlador.cerrarConexion();
						
					} catch (SQLException | CamposVaciosException | ISBNException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				
				habilitaBotones(true);
				habilitaNavegacion(true);
				
				puntero=lista.size()-1;
				mostrarLibro(lista.get(puntero));
				lblNombre.setText("Nombre:");
				lblAutor.setText("Autor:");
				lblEditorial.setText("Editorial:");
				lblIsbn.setText("ISBN:");
				lblObligatorios.setText("");
				lblNombre.setForeground(Color.black);
				lblAutor.setForeground(Color.black);
				lblEditorial.setForeground(Color.black);
				lblIsbn.setForeground(Color.black);
				}else {
					habilitaBotones(false);
					habilitaNavegacion(false);
				}
			}
				
		});
		
	}
		
	protected void habilitaNavegacion(boolean b) {
		btnPrimero.setEnabled(b);
		btnAtras.setEnabled(b);
		btnAdelante.setEnabled(b);
		btnUltimo.setEnabled(b);
		
	}



	protected void borraPanel() {
		txtNombre.setText(""); 
		txtNombre.setEditable(true);
		txtAutor.setText("");
		txtAutor.setEditable(true);
		txtEditorial.setText("");
		txtEditorial.setEditable(true);
		txtIsbn.setText("");
		txtIsbn.setEditable(true);
		txtFechaAlta.setText("");
		txtFechaDevolucion.setText("");
		txtFechaPrestamo.setText("");
		txtId.setText("");
		checkPrestado.setSelected(false);
		
		
	}



	private void habilitaBotones(boolean b) {
		btnPrimero.setEnabled(false);
		btnAtras.setEnabled(false);
		btnAdelante.setEnabled(false);
		btnUltimo.setEnabled(false);
		btnNuevo.setEnabled(b);
		btnEditar.setEnabled(b);
		btnBorrar.setEnabled(b);
		btnDeshacer.setEnabled(!b);
		btnGuardar.setEnabled(!b);
		
	}

	private void definirVentana() {
		lblLibros = new JLabel("LIBROS");
		lblLibros.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 19));
		lblLibros.setHorizontalAlignment(SwingConstants.CENTER);
		lblLibros.setBounds(5, 5, 567, 56);
		contentPane.add(lblLibros);
		
		lblId = new JLabel("ID: ");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblId.setBounds(15, 139, 90, 14);
		contentPane.add(lblId);
		
		lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombre.setBounds(15, 166, 90, 14);
		contentPane.add(lblNombre);
		
		lblAutor = new JLabel("Autor:");
		lblAutor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAutor.setBounds(15, 194, 90, 14);
		contentPane.add(lblAutor);
		
		lblEditorial = new JLabel("Editorial:");
		lblEditorial.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEditorial.setBounds(15, 222, 90, 14);
		contentPane.add(lblEditorial);
		
		lblFechaPrestamo = new JLabel("Fecha Prestamo:");
		lblFechaPrestamo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFechaPrestamo.setBounds(15, 250, 111, 14);
		contentPane.add(lblFechaPrestamo);
		
		lblFechaDevolucion = new JLabel("Fecha Devolucion:");
		lblFechaDevolucion.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFechaDevolucion.setBounds(15, 278, 111, 14);
		contentPane.add(lblFechaDevolucion);
		
		lblIsbn = new JLabel("ISBN:");
		lblIsbn.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIsbn.setBounds(15, 306, 90, 14);
		contentPane.add(lblIsbn);
		
		lblFechaAlta = new JLabel("Fecha Alta:");
		lblFechaAlta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFechaAlta.setBounds(15, 334, 90, 14);
		contentPane.add(lblFechaAlta);
		
		txtId = new JTextField();
		txtId.setBounds(139, 136, 263, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(139, 163, 263, 20);
		contentPane.add(txtNombre);
		
		txtAutor = new JTextField();
		txtAutor.setColumns(10);
		txtAutor.setBounds(139, 191, 263, 20);
		contentPane.add(txtAutor);
		
		txtEditorial = new JTextField();
		txtEditorial.setColumns(10);
		txtEditorial.setBounds(139, 219, 263, 20);
		contentPane.add(txtEditorial);
		
		txtFechaPrestamo = new JTextField();
		txtFechaPrestamo.setColumns(10);
		txtFechaPrestamo.setBounds(139, 247, 263, 20);
		contentPane.add(txtFechaPrestamo);
		
		txtFechaDevolucion = new JTextField();
		txtFechaDevolucion.setColumns(10);
		txtFechaDevolucion.setBounds(139, 275, 263, 20);
		contentPane.add(txtFechaDevolucion);
		
		txtIsbn = new JTextField();
		txtIsbn.setColumns(10);
		txtIsbn.setBounds(139, 303, 263, 20);
		contentPane.add(txtIsbn);
		
		txtFechaAlta = new JTextField();
		txtFechaAlta.setColumns(10);
		txtFechaAlta.setBounds(139, 331, 263, 20);
		contentPane.add(txtFechaAlta);
		
		lblPrestado = new JLabel("Prestado:");
		lblPrestado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrestado.setBounds(15, 358, 90, 14);
		contentPane.add(lblPrestado);
		
		checkPrestado = new JCheckBox("");
		checkPrestado.setHorizontalAlignment(SwingConstants.LEFT);
		checkPrestado.setVerticalAlignment(SwingConstants.BOTTOM);
		checkPrestado.setBounds(139, 349, 192, 23);
		contentPane.add(checkPrestado);
		
		btnPrimero = new JButton();
		btnPrimero.setIcon(new ImageIcon("C:\\Users\\torti\\Desktop\\Desarrollos Eclipse\\AccesoBDD\\imagenes\\navPri.jpg"));
		btnPrimero.setBounds(15, 379, 45, 27);
		contentPane.add(btnPrimero);
		
		btnAtras = new JButton();
		btnAtras.setIcon(new ImageIcon("C:\\Users\\torti\\Desktop\\Desarrollos Eclipse\\AccesoBDD\\imagenes\\navIzq.jpg"));
		btnAtras.setBounds(81, 379, 45, 27);
		contentPane.add(btnAtras);
		
		btnAdelante = new JButton();
		btnAdelante.setIcon(new ImageIcon("C:\\Users\\torti\\Desktop\\Desarrollos Eclipse\\AccesoBDD\\imagenes\\navDer.jpg"));
		btnAdelante.setBounds(146, 379, 45, 27);
		contentPane.add(btnAdelante);
		
		btnUltimo = new JButton();
		btnUltimo.setIcon(new ImageIcon("C:\\Users\\torti\\Desktop\\Desarrollos Eclipse\\AccesoBDD\\imagenes\\navUlt.jpg"));
		btnUltimo.setBounds(215, 379, 45, 27);
		contentPane.add(btnUltimo);
		
		
		btnNuevo = new JButton();
		btnNuevo.setIcon(new ImageIcon("C:\\Users\\torti\\Desktop\\Desarrollos Eclipse\\AccesoBDD\\imagenes\\botonAgregar.jpg"));
		btnNuevo.setBounds(15, 49, 83, 79);
		contentPane.add(btnNuevo);
		
		btnEditar = new JButton();
		btnEditar.setIcon(new ImageIcon("C:\\Users\\torti\\Desktop\\Desarrollos Eclipse\\AccesoBDD\\imagenes\\botonEditar.jpg"));
		btnEditar.setBounds(108, 49, 83, 79);
		contentPane.add(btnEditar);
		
		btnBorrar = new JButton();
		btnBorrar.setIcon(new ImageIcon("C:\\Users\\torti\\Desktop\\Desarrollos Eclipse\\AccesoBDD\\imagenes\\borrar.jpg"));
		btnBorrar.setBounds(201, 49, 83, 79);
		contentPane.add(btnBorrar);
		
		btnDeshacer = new JButton();
		btnDeshacer.setIcon(new ImageIcon("C:\\Users\\torti\\Desktop\\Desarrollos Eclipse\\AccesoBDD\\imagenes\\botonDeshacer.jpg"));
		btnDeshacer.setBounds(294, 49, 83, 79);
		btnDeshacer.setEnabled(false);
		contentPane.add(btnDeshacer);
		
		btnGuardar = new JButton();
		btnGuardar.setIcon(new ImageIcon("C:\\Users\\torti\\Desktop\\Desarrollos Eclipse\\AccesoBDD\\imagenes\\botonGuardar.jpg"));
		btnGuardar.setBounds(387, 49, 83, 79);
		btnGuardar.setEnabled(false);
		contentPane.add(btnGuardar);
		
		lblObligatorios = new JLabel("");
		lblObligatorios.setForeground(Color.RED);
		lblObligatorios.setBounds(412, 139, 136, 14);
		contentPane.add(lblObligatorios);
		
	}
}
