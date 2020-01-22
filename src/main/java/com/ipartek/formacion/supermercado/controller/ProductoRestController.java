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

	private ProductoDAO productoDao;
	private PrintWriter out;
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
		super.service(request, response); // llama a doGet, doPost, doPut o doDelete

		response.setContentType("application/json"); // por defecto => text/html;charset=UTF-8
		response.setCharacterEncoding("utf-8");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		out = response.getWriter();

		String pathInfo = request.getPathInfo();

		LOG.debug("mirar pathInfo:" + pathInfo + " para saber si es listado o detalle");

		if (pathInfo.length() <= 1) {
			// response body
			ArrayList<Producto> lista = (ArrayList<Producto>) productoDao.getAll();

			if (lista.size() != 0) {
				String jsonResponseBody = new Gson().toJson(lista);
				out.print(jsonResponseBody.toString()); // retornamos un array vacio dentro del body
				out.flush();

				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
			
		} else {
			int id=0;
			try {
				id = Utilidades.obtenerId(pathInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOG.debug("id producto es "+id);
			
			Producto producto = productoDao.getById(id);
			if(producto!=null) {
				String jsonResponseBody = new Gson().toJson(producto);
				out.print(jsonResponseBody.toString()); // retornamos un array vacio dentro del body
				out.flush();

				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
			LOG.debug(producto);
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
		
		//TODO validar objeto
		
		LOG.debug(" Json convertido a Objeto: " + producto);
		out = response.getWriter();		  

		try {
			productoDao.create(producto);
			
			response.setStatus( HttpServletResponse.SC_CREATED );
			
			String jsonResponseBody = new Gson().toJson( new ResponseMensaje("Producto creado"));		  
			out.print(jsonResponseBody.toString()); 	
			out.flush();
			
		} catch (Exception e) {
			response.setStatus( HttpServletResponse.SC_NOT_IMPLEMENTED );
			String jsonResponseBody = new Gson().toJson( new ResponseMensaje("Producto creado"));		  
			out.print(jsonResponseBody.toString()); 	
			out.flush();
		}
		
		
		  
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
