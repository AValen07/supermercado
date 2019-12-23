package com.ipartek.formacion.supermercado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ipartek.formacion.supermercado.model.ConnectionManager;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

public class ProductoDAO implements IDAO<Producto>{

	private static ProductoDAO INSTANCE;
	
	private static final String SQL_GET_ALL = "SELECT id, nombre, descripcion, precio, descuento, imagen  FROM producto ORDER BY id DESC LIMIT 500;";
	private static final String SQL_GET_BY_ID = "SELECT id, nombre, descripcion, precio, descuento, imagen FROM producto WHERE id = ?;";
	private static final String SQL_INSERT = "INSERT INTO producto (nombre, descripcion, precio, descuento, imagen) VALUES ( ? , ?, ?, ?, ?);";
	private static final String SQL_UPDATE = "UPDATE producto SET nombre= ?, descripcion= ?, precio=?, descuento=?, imagen=?  WHERE id = ?;";
	private static final String SQL_DELETE = "DELETE FROM producto WHERE id = ?";
	
	private ProductoDAO() {		
		super();			
	}
	
	public static synchronized ProductoDAO getInstance() {
		
		if ( INSTANCE == null ) {
			INSTANCE = new ProductoDAO(); 
		}
		
		return INSTANCE;
	}
	
	

	@Override
	public List<Producto> getAll() {		
		
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				
				Producto p = new Producto();
				p.setId( rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setDescripcion(rs.getString("descripcion"));
				p.setImagen(rs.getString("imagen"));
				p.setDescuento(rs.getInt("descuento"));
				p.setPrecio(rs.getFloat("precio"));
				lista.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public Producto getById(int id) {
		Producto resul = null;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID)) {

			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					resul = mapper(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resul;
	}

	@Override
	public Producto delete(int id) throws Exception {
		Producto resultado=null;
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
		
			pst.setInt(1, id);
			resultado = getById(id);

			int affectedRows = pst.executeUpdate();
			if (affectedRows != 1) {
				resultado = null;
				throw new Exception("No se puede eliminar " + resultado);
			}
		}
		
		return resultado;
	}

	@Override
	public Producto update(int id, Producto pojo) throws Exception {
		Producto resultado=null;
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getDescripcion());
			pst.setFloat(3, pojo.getPrecio());
			pst.setInt(4, pojo.getDescuento());
			pst.setString(5, pojo.getImagen());
			pst.setInt(6, id);

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				resultado = pojo;
			}else {
				throw new Exception("No se encontro registro para id=" + id);
			}

		}
		
		return resultado;
	}

	@Override
	public Producto create(Producto pojo) throws Exception {
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getDescripcion());
			pst.setFloat(3, pojo.getPrecio());
			pst.setInt(4, pojo.getDescuento());
			pst.setString(5, pojo.getImagen());

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				// conseguimos el ID que acabamos de crear
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					pojo.setId(rs.getInt(1));
				}

			}

		}

		return pojo;
	}
	
	private Producto mapper(ResultSet rs) throws SQLException {
		
		Producto p = new Producto();
		p.setId( rs.getInt("id"));
		p.setNombre(rs.getString("nombre"));
		p.setDescripcion(rs.getString("descripcion"));
		p.setImagen(rs.getString("imagen"));
		p.setDescuento(rs.getInt("descuento"));
		p.setPrecio(rs.getFloat("precio"));		
		
		return p;
	}

}
