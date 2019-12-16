package com.ipartek.formacion.supermercado.controller.seguridad;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.supermercado.modelo.dao.ProductoDAO;

/**
 * Servlet implementation class ProductosController
 */
@WebServlet("/seguridad/productos")
public class ProductosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW_TABLA = "productos/index.jsp";
	private static final String VIEW_FORM = "productos/formulario.jsp";
	private static ProductoDAO dao;

	// Acciones
	public static final String ACCION_LISTAR = "listar";
	public static final String ACCION_IR_FORMULARIO = "formulario";
	public static final String ACCION_GUARDAR = "guardar"; //crear y modificar
	public static final String ACCION_ELIMINAR = "eliminar";

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		dao = ProductoDAO.getInstance();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		dao = null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doAction(request, response);
	}

	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//recoger parametros
		String pAccion=request.getParameter("accion");
		
		String pId=request.getParameter("id");
		String pNombre=request.getParameter("nombre");
		String pPrecio=request.getParameter("precio");
		String pImagen=request.getParameter("imagen");
		String pDescripcion=request.getParameter("descripcion");
		String pDescuento=request.getParameter("descuento");
		
		
		try {
			//TODO logica de negocio
			
			switch(pAccion) {
			case ACCION_LISTAR:
				listar(request,response);
				break;
			case ACCION_ELIMINAR:
				eliminar(request,response);
				break;
			case ACCION_GUARDAR:
				guardar(request,response);
				break;
			case ACCION_IR_FORMULARIO:
				irFormulario(request,response);
				break;
				
			}
			
			request.setAttribute("productos", dao.getAll());
		}catch(Exception e) {
			
			//TODO log
			
		}finally {
			
			request.getRequestDispatcher(VIEW_TABLA).forward(request, response);
			
		}
	}

	private void irFormulario(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void guardar(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void listar(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

}
