package com.ipartek.formacion.supermercado.controller.seguridad;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.supermercado.modelo.dao.ProductoDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

/**
 * Servlet implementation class ProductosController
 */
@WebServlet("/seguridad/productos")
public class ProductosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW_TABLA = "productos/index.jsp";
	private static final String VIEW_FORM = "productos/formulario.jsp";
	private static ProductoDAO dao;
	private static String vistaSeleccionada = VIEW_TABLA;

	// Acciones
	public static final String ACCION_LISTAR = "lista";
	public static final String ACCION_IR_FORMULARIO = "formulario";
	public static final String ACCION_GUARDAR = "guardar"; // crear y modificar
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

	private void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// recoger parametros
		String pAccion = request.getParameter("accion");

		String pId = request.getParameter("id");
		String pNombre = request.getParameter("nombre");
		String pPrecio = request.getParameter("precio");
		String pImagen = request.getParameter("imagen");
		String pDescripcion = request.getParameter("descripcion");
		String pDescuento = request.getParameter("descuento");

		try {
			// TODO logica de negocio

			switch (pAccion) {
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

	private void irFormulario(HttpServletRequest request, HttpServletResponse response) {
		// TODO pregutar por pID > 0 recuperar del DAO
		// si no New Producto()
		try {
			int pId = Integer.parseInt(request.getParameter("id"));
			if (pId != 0) {
				Producto producto = dao.getById(pId);
				request.setAttribute("producto", producto);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("producto", new Producto());
		}
		vistaSeleccionada = VIEW_FORM;
		// dao.getById(id) => implementar
	}

	private void guardar(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			int pId = Integer.parseInt(request.getParameter("id"));
			int pDescuento = Integer.parseInt(request.getParameter("descuento"));
			float pPrecio = Float.parseFloat(request.getParameter("precio"));
			String pNombre = request.getParameter("nombre");
			String pImagen = request.getParameter("imagen");
			String pDescripcion = request.getParameter("descripcion");
			
			Producto producto = new Producto();
			producto.setNombre(pNombre);
			producto.setDescripcion(pDescripcion);
			producto.setDescuento(pDescuento);
			producto.setImagen(pImagen);
			producto.setPrecio(pPrecio);
			if (dao.getById(pId)==null) {
				dao.create(producto);
			} else {
				dao.update(pId, producto);
			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("productos", dao.getAll());
		vistaSeleccionada = VIEW_TABLA;

	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int lId = Integer.parseInt(request.getParameter("id"));
		dao.delete(lId);
		request.setAttribute("productos", dao.getAll());
		vistaSeleccionada = VIEW_TABLA;

	}

	private void listar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("productos", dao.getAll());
		vistaSeleccionada = VIEW_TABLA;

	}

}
