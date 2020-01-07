package com.ipartek.formacion.supermercado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.model.ConnectionManager;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

public class ProductoDAO implements IProductoDAO{

	private final static Logger LOG = Logger.getLogger(ProductoDAO.class);

	
	private static ProductoDAO INSTANCE;
	
	private static final String SQL_GET_ALL = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', descripcion, imagen, descuento, precio, u.id 'id_usuario', u.nombre 'nombre_usuario' "
			+ " FROM producto p, usuario u " + " WHERE p.id_usuario = u.id " + " ORDER BY p.id DESC LIMIT 500;";	
	private static final String SQL_GET_ALL_BY_USER = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', descripcion, imagen, descuento, precio, u.id 'id_usuario', u.nombre 'nombre_usuario' "
			+ " FROM producto p, usuario u " + " WHERE p.id_usuario = u.id AND u.id = ? "
			+ " ORDER BY p.id DESC LIMIT 500;";
	private static final String SQL_GET_BY_ID = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', descripcion, imagen, descuento, precio, u.id 'id_usuario', u.nombre 'nombre_usuario' FROM producto p, usuario u WHERE id = ?;";	
	private static final String SQL_INSERT = "INSERT INTO producto (nombre, descripcion, precio, descuento, imagen, id_usuario) VALUES ( ? , ?, ?, ?, ?, ?);";
	private static final String SQL_UPDATE = "UPDATE producto SET nombre= ?, descripcion= ?, precio=?, descuento=?, imagen=?,  WHERE id = ?;";
	private static final String SQL_DELETE = "DELETE FROM producto WHERE id = ?";
	
	private static final String SQL_UPDATE_BY_USER = "UPDATE producto SET nombre= ?, descripcion= ?, precio=?, descuento=?, imagen=?  WHERE id = ? AND id_usuario = ?;";
	private static final String SQL_DELETE_BY_USER = "DELETE FROM producto WHERE id = ? AND id_usuario = ?;";
	private static final String SQL_GET_BY_ID_BY_USER = "SELECT p.id 'id_producto', p.nombre 'nombre_producto', descripcion, imagen, descuento, precio, u.id 'id_usuario', u.nombre 'nombre_usuario'  FROM producto p, usuario u WHERE p.id_usuario = u.id AND p.id = ? AND p.id_usuario = ?;";	

	
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
				
				Producto p = mapper(rs);			
				
				lista.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
	
	public List<Producto> getAllByUser(int idUsuario) {

		ArrayList<Producto> lista = new ArrayList<Producto>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_USER);) {

			pst.setInt(1, idUsuario);
			LOG.debug(pst);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					lista.add(mapper(rs));
				}
			} // executeQuery

		} catch (SQLException e) {
			LOG.error(e);
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
	public Producto getByIdByUser(int idPRoducto, int idUsuario) throws ProductoException {
		Producto resul = null;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID_BY_USER)) {

			pst.setInt(1, idPRoducto);
			pst.setInt(2, idUsuario);

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
	public Producto deleteByUser(int idProducto, int idUsuario) throws ProductoException {
		Producto resultado=null;
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_USER)) {
		
			pst.setInt(1, idProducto);
			pst.setInt(2, idUsuario);
			resultado = getByIdByUser(idProducto, idUsuario);

			int affectedRows = pst.executeUpdate();
			if (affectedRows != 1) {
				resultado = null;
				throw new ProductoException(ProductoException.EXCEPTION_UNAUTORIZED);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public Producto updateByUser(int idProducto, int idUsuario, Producto pojo) throws ProductoException {
		Producto resultado=null;
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE_BY_USER)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getDescripcion());
			pst.setFloat(3, pojo.getPrecio());
			pst.setInt(4, pojo.getDescuento());
			pst.setString(5, pojo.getImagen());
			pst.setInt(6, idProducto);
			pst.setInt(7, idUsuario);

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				resultado = pojo;
			}else {
				throw new ProductoException("No se encontro el producto");
			}

		} catch (ProductoException e) {
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}

	@Override
	public Producto create(Producto pojo) throws Exception {
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getDescripcion());
			pst.setFloat(3, pojo.getPrecio());
			pst.setInt(4, pojo.getDescuento());
			pst.setString(5, pojo.getImagen());
			pst.setInt(6, pojo.getUsuario().getId());

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
		p.setId( rs.getInt("id_producto"));
		p.setNombre(rs.getString("nombre_producto"));
		p.setDescripcion(rs.getString("descripcion"));
		p.setImagen(rs.getString("imagen"));
		p.setDescuento(rs.getInt("descuento"));
		p.setPrecio(rs.getFloat("precio"));		
		
		Usuario u = new Usuario();
		u.setId(rs.getInt("id_usuario"));
		u.setNombre(rs.getString("nombre_usuario"));
		p.setUsuario(u);
		return p;
	}	
}
