package com.ipartek.formacion.supermercado.controller.seguridad;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.controller.Alerta;
import com.ipartek.formacion.supermercado.controller.mipanel.ProductosController;
import com.ipartek.formacion.supermercado.modelo.dao.CategoriaDAO;
import com.ipartek.formacion.supermercado.modelo.dao.ProductoException;
import com.ipartek.formacion.supermercado.modelo.pojo.Categoria;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

/**
 * Servlet implementation class CategoriasController
 */
@WebServlet("/seguridad/categoria")
public class CategoriasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final static Logger LOG = Logger.getLogger(ProductosController.class);

	
	private static final String VIEW_TABLA = "categorias/index.jsp";
	private static final String VIEW_FORM = "categorias/formulario.jsp";
	private static String vistaSeleccionda = VIEW_TABLA;	
	private static CategoriaDAO daoCategoria;
	private Usuario uLogeado;
	//acciones
	public static final String ACCION_LISTAR = "lista";
	public static final String ACCION_IR_FORMULARIO = "formulario";
	public static final String ACCION_GUARDAR = "guardar";   // crear y modificar
	public static final String ACCION_ELIMINAR = "eliminar";
	
	//Crear Factoria y Validador
	ValidatorFactory factory;
	Validator validator;
	
	
	//parametros
	String cAccion;	
	String cId;
	String cNombre;
    
	@Override
	public void init(ServletConfig config) throws ServletException {		
		super.init(config);
		daoCategoria = CategoriaDAO.getInstance();
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
      
	@Override
	public void destroy() {	
		super.destroy();
		daoCategoria=null;
		factory = null;
		validator = null;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doAction(request,response);
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doAction(request,response);
	}

	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		cAccion = request.getParameter("accion");
		cId = request.getParameter("id");
		cNombre = request.getParameter("nombre");
		
		try {
			
			switch (cAccion) {
			case ACCION_LISTAR:
				listar(request, response);
				break;
			case ACCION_ELIMINAR:	
				eliminar(request, response);
				break;
			case ACCION_GUARDAR:	
				guardar(request, response);
				break;
			case ACCION_IR_FORMULARIO:	
				irFormulario(request, response);
				break;
			default:
				listar(request, response);
				break;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			request.getRequestDispatcher(vistaSeleccionda).forward(request, response);
		}
	}


	private void irFormulario(HttpServletRequest request, HttpServletResponse response) {
		
		Categoria cEditar = new Categoria();
		if(cId!=null) {
			int id=Integer.parseInt(cId);
			cEditar=daoCategoria.getById(id);
		}
		
		request.setAttribute("categoria", cEditar );
		vistaSeleccionda = VIEW_FORM;
	}

	private void guardar(HttpServletRequest request, HttpServletResponse response) {
		
		int id=Integer.parseInt(cId);
		Categoria cGuardar = new Categoria();
		cGuardar.setId(id);
		cGuardar.setNombre(cNombre);
		
		Set<ConstraintViolation<Categoria>> validaciones = validator.validate(cGuardar);
		if( validaciones.size() > 0 ) {			
			mensajeValidacion(request, validaciones);
		}else {	
		
				try {
				
					if ( id > 0 ) {  // modificar
						
						daoCategoria.update(id, cGuardar);		
						
					}else {            // crear
						daoCategoria.create(cGuardar);
					}
					
				}catch (Exception e) {  // validacion a nivel de base datos
					
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "El nombre ya existe, selecciona otro"));
				}			
		}	
		
		request.setAttribute("categoria", cGuardar );
		vistaSeleccionda = VIEW_FORM;
	}

	

	private void eliminar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(cId);
		
		Categoria cEliminado = daoCategoria.delete(id);
		request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Eliminado " + cEliminado.getNombre() ));
		
		listar(request, response);
		
	}

	private void listar(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("categorias", daoCategoria.getAll());
		vistaSeleccionda = VIEW_TABLA;
	}
	
	private void mensajeValidacion(HttpServletRequest request, Set<ConstraintViolation<Categoria>> validaciones) {
		StringBuilder mensaje = new StringBuilder();
		for (ConstraintViolation<Categoria> cv : validaciones) {
			
			mensaje.append("<p>");
			mensaje.append(cv.getPropertyPath()).append(": ");
			mensaje.append(cv.getMessage());
			mensaje.append("</p>");
			
		}
		
		request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, mensaje.toString() ));
		
	}
}
