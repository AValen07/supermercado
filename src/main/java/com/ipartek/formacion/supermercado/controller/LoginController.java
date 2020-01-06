package com.ipartek.formacion.supermercado.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.supermercado.modelo.dao.UsuarioDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Rol;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {	
	private static final long serialVersionUID = 1L;
	
	
	private static UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
	
	private static final String USUARIO ="admin";
	private static final String PASSWORD ="admin";

	
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String view="login.jsp";
		String nombre=request.getParameter("usuario");
		String password=request.getParameter("password");
		
		//TODO POJO y DAO Usuario
		
		try {
			
			Usuario usuario = usuarioDAO.exist(nombre, password);
			
			if(usuario!=null)
			{
				// login correcto.
				HttpSession session=request.getSession();
				session.setAttribute("usuarioLogueado",usuario);
				session.setMaxInactiveInterval(60*3);
				
				view="seguridad/index.jsp";
				
				if ( usuario.getRol().getId() == Rol.ROL_ADMIN ) {
					
					view = "seguridad/index.jsp";   // accedemos la BACK-OFFICE
					
				}else {
					
					view = "mipanel/index.jsp";    // accedemos la FRONT-OFFICE
				}
			}else {
				// login incorrecto.
				request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "Credenciales incorrectas. Intentelo de nuevo."));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			request.getRequestDispatcher(view).forward(request, response);
		}
	}

}
