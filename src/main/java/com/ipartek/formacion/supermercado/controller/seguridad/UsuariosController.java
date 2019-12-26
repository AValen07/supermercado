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

import com.ipartek.formacion.supermercado.controller.Alerta;
import com.ipartek.formacion.supermercado.modelo.dao.UsuarioDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

/**
 * Servlet implementation class UsuariosController
 */
@WebServlet("/seguridad/usuarios")
public class UsuariosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String VIEW_TABLA = "usuarios/index.jsp";
	private static final String VIEW_FORM = "usuarios/formulario.jsp";
	private static UsuarioDAO dao;
	private static String vistaSeleccionada = VIEW_TABLA;

	// Acciones
	public static final String ACCION_LISTAR = "lista";
	public static final String ACCION_IR_FORMULARIO = "formulario";
	public static final String ACCION_GUARDAR = "guardar"; // crear y modificar
	public static final String ACCION_ELIMINAR = "eliminar";

	
	//Crear Factoria y Validador
	ValidatorFactory factory;
	Validator validator;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		dao = UsuarioDAO.getInstance();
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		dao = null;
		factory=null;
		validator=null;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doAction(request, response);
	}

	private void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recoger parametros
		String uAccion = request.getParameter("accion");

		String uId = request.getParameter("id");
		String uNombre = request.getParameter("nombre");
		String uContrasena = request.getParameter("contrasena");
	
		try {
			// TODO logica de negocio

			switch (uAccion) {
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

			}

		} catch (Exception e) {

			// TODO log

		} finally {

			request.getRequestDispatcher(vistaSeleccionada).forward(request, response);

		}
	
	}

	private void listar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("usuarios", dao.getAll());
		vistaSeleccionada = VIEW_TABLA;		
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int uId = Integer.parseInt(request.getParameter("id"));
		dao.delete(uId);
		request.setAttribute("usuarios", dao.getAll());
		vistaSeleccionada = VIEW_TABLA;
		
	}

	private void guardar(HttpServletRequest request, HttpServletResponse response) {
		try {
			int uId = Integer.parseInt(request.getParameter("id"));
			
			String uNombre = request.getParameter("nombre");
			String uContrasena = request.getParameter("contrasena");
			
			Usuario usuario = new Usuario();
			usuario.setNombre(uNombre);
			usuario.setContrasena(uContrasena);
			
			
			Set<ConstraintViolation<Usuario>> validaciones = validator.validate(usuario);
			if(validaciones.size()>0) {
					
				mensajeValidacion(request, validaciones);
			
			}else {  // validacion de campos del formuarlio incorrectos			
				
				try {
					
					if ( uId > 0 ) {  // modificar
						
						dao.update(uId, usuario);		
						
					}else {            // crear
						dao.create(usuario);
					}
					
				}catch (Exception e) {  // validacion a nivel de base datos
					
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "El nombre ya existe, selecciona otro"));
				}
			}
			request.setAttribute("producto", usuario);
			vistaSeleccionada = VIEW_FORM;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	private void mensajeValidacion(HttpServletRequest request, Set<ConstraintViolation<Usuario>> validaciones) {
		StringBuilder mensaje = new StringBuilder();
		for (ConstraintViolation<Usuario> cv : validaciones) {
			
			mensaje.append("<p>");
			mensaje.append(cv.getPropertyPath()).append(": ");
			mensaje.append(cv.getMessage());
			mensaje.append("</p>");
			
		}
		
		request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, mensaje.toString() ));
		
	}

	private void irFormulario(HttpServletRequest request, HttpServletResponse response) {
		try {
			int uId = Integer.parseInt(request.getParameter("id"));
			if (uId != 0) {
				Usuario usuario = dao.getById(uId);
				request.setAttribute("usuario", usuario);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("usuario", new Usuario());
		}
		vistaSeleccionada = VIEW_FORM;		
	}
	

}
