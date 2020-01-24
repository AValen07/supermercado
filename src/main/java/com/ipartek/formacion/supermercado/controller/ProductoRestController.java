package com.ipartek.formacion.supermercado.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.ipartek.formacion.supermercado.modelo.dao.ProductoDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;
import com.ipartek.formacion.supermercado.pojo.ResponseMensaje;
import com.ipartek.formacion.supermercado.utils.Utilidades;

/**
 * Servlet implementation class ProductoRestController
 */
@WebServlet({ "/producto/*" })
public class ProductoRestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(ProductoRestController.class);

	private String jsonResponseBody;
	private ProductoDAO productoDao;
	private PrintWriter out;
	private String pathInfo;
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		productoDao = ProductoDAO.getInstance();
	}

	@Override
	public void destroy() {
		productoDao = null;
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json"); // por defecto => text/html;charset=UTF-8
		response.setCharacterEncoding("utf-8");
		
		super.service(request, response); // llama a doGet, doPost, doPut o doDelete
		
		out.print(jsonResponseBody);
		out.flush();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		pathInfo = request.getPathInfo();
		int id = 0;
		out = response.getWriter();

		String pathInfo = request.getPathInfo();

		LOG.debug("mirar pathInfo:" + pathInfo + " para saber si es listado o detalle");
		try {
			id = Utilidades.obtenerId(pathInfo);

			if (id == -1) {
				// response body
				ArrayList<Producto> lista = (ArrayList<Producto>) productoDao.getAll();

				if (lista.size() != 0) {
					jsonResponseBody = new Gson().toJson(lista);
					

					response.setStatus(HttpServletResponse.SC_OK);
				} else {
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				}

			} else {

				LOG.debug("id producto es " + id);

				Producto producto = productoDao.getById(id);
				if (producto != null) {
					jsonResponseBody = new Gson().toJson(producto);
					response.setStatus(HttpServletResponse.SC_OK);
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					jsonResponseBody = new Gson().toJson(new ResponseMensaje("Producto no encontrado")).toString();
				}
				LOG.debug(producto);
			}
		} catch (Exception e) {
			jsonResponseBody = new Gson().toJson(new ResponseMensaje("Peticion invalida.")).toString();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOG.debug("POST crear recurso");

		// convertir json del request body a Objeto
		BufferedReader reader = request.getReader();
		Gson gson = new Gson();
		Producto producto = gson.fromJson(reader, Producto.class);

		// TODO validar objeto

		LOG.debug("Json convertido a Objeto: " + producto);
		out = response.getWriter();

		try {
			productoDao.create(producto);

			response.setStatus(HttpServletResponse.SC_CREATED);
			jsonResponseBody = new Gson().toJson(new ResponseMensaje("Producto creado"));

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			jsonResponseBody = new Gson().toJson(new ResponseMensaje("Peticion denegada"));

		}

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader reader = request.getReader();
		pathInfo = request.getPathInfo();
		out = response.getWriter();
		int id = 0;
		
		try {
			id=Utilidades.obtenerId(pathInfo);
			
			if(id!=-1) {
				// id valido
				Producto producto=productoDao.getById(id);
				if(producto!=null) {
					// el producto existe, podemos hacer la update.
					Gson gson = new Gson();
					producto = gson.fromJson(reader, Producto.class);
					productoDao.update(id, producto);
					
					jsonResponseBody = new Gson().toJson(new ResponseMensaje("Actualizacion realizada.")).toString();
					response.setStatus(HttpServletResponse.SC_OK);
					
				} else {
					jsonResponseBody = new Gson().toJson(new ResponseMensaje("El producto no existe.")).toString();
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
			}else {
				jsonResponseBody = new Gson().toJson(new ResponseMensaje("La url esta aml formada")).toString();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} catch (Exception e) {
			jsonResponseBody = new Gson().toJson(new ResponseMensaje("Peticion invalida.")).toString();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		pathInfo = request.getPathInfo();
		out = response.getWriter();
		int id = 0;

		try {
			id = Utilidades.obtenerId(pathInfo);
			if (id != -1) {
				productoDao.delete(id);
				jsonResponseBody = new Gson().toJson(new ResponseMensaje("Producto eliminado")).toString();
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				jsonResponseBody = new Gson().toJson(new ResponseMensaje("Producto Inexistente")).toString();
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception e) {
			jsonResponseBody = new Gson().toJson(new ResponseMensaje("Producto Inexistente")).toString();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

	}

}
