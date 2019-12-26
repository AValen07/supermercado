package com.ipartek.formacion.supermercado.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.supermercado.model.ConnectionManager;
import com.ipartek.formacion.supermercado.modelo.dao.ProductoDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

/**
 * Servlet implementation class InicioController
 */
@WebServlet("/inicio")
public class InicioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static ProductoDAO dao;
    

    @Override
    public void init(ServletConfig config) throws ServletException {
    	// TODO Auto-generated method stub
    	super.init(config);
    	dao=ProductoDAO.getInstance();
    }
    
    @Override
    public void destroy() {
    	// TODO Auto-generated method stub
    	super.destroy();
    	dao=null;
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	if(null==ConnectionManager.getConnection()) {
    		resp.sendRedirect(req.getContextPath()+"/error.jsp");
    	}else {    	
	    	// LLama get o post
	    	super.service(req, resp);
    	}
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//TODO LLamar al DAO capa modelo.
		ArrayList<Producto> productos=(ArrayList<Producto>) dao.getAll();
		request.setAttribute("productos", productos);
		request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY,"Bienvenido"));
		request.getRequestDispatcher("index.jsp").forward(request, response);;
	}

}
