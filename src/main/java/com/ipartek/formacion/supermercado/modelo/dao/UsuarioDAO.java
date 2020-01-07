package com.ipartek.formacion.supermercado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ipartek.formacion.supermercado.model.ConnectionManager;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;
import com.ipartek.formacion.supermercado.modelo.pojo.Rol;
import com.ipartek.formacion.supermercado.modelo.pojo.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

	private static final String SQL_EXIST = " SELECT u.id 'id_usuario', u.nombre 'nombre_usuario', contrasena, r.id 'id_rol', r.nombre 'nombre_rol' "
			+ " FROM usuario u, rol r " + " WHERE u.id_rol = r.id AND " + " u.nombre = ? AND contrasena = ? ; ";
	private static final String SQL_GET_ALL = " SELECT u.id 'id_usuario', u.nombre 'nombre_usuario', contrasena, r.id 'id_rol', r.nombre 'nombre_rol' "
			+ " FROM usuario u, rol r " + " WHERE u.id_rol = r.id " + " ORDER BY u.id DESC LIMIT 500;";
	private static final String SQL_GET_BY_ID = "SELECT id 'id_usuario', nombre 'nombre_usuario', contrasena FROM usuario WHERE id=?";
	private static final String SQL_INSERT = "INSERT INTO usuario (nombre, contrasena) VALUES ( ? , ?);";
	private static final String SQL_UPDATE = "UPDATE usuario SET nombre= ?, contrasena= ?  WHERE id = ?;";
	private static final String SQL_DELETE = "DELETE FROM usuario WHERE id = ?";
	
	private static UsuarioDAO INSTANCE;

	public static synchronized UsuarioDAO getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new UsuarioDAO();
		}

		return INSTANCE;
	}

	private UsuarioDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Usuario> getAll() {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {			
				
				lista.add(mapper(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public Usuario getById(int id) {
		Usuario resultado = null;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID)) {

			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					resultado = mapper(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	

	@Override
	public Usuario delete(int id) throws Exception {
		Usuario resultado=null;
		
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
	public Usuario update(int id, Usuario pojo) throws Exception {
		Usuario resultado=null;
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getContrasena());			
			pst.setInt(3, id);

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
	public Usuario create(Usuario pojo) throws Exception {
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getContrasena());
			

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

	@Override
	public Usuario exist(String nombre, String contrasenia) {
		Usuario resultado = null;
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_EXIST);) {

			pst.setString(1, nombre);
			pst.setString(2, contrasenia);

			try (ResultSet rs = pst.executeQuery();) {
				if (rs.next()) {
					resultado = mapper(rs);
				}
			}

		} catch (Exception e) {
		}
		return resultado;
	}

	private Usuario mapper(ResultSet rs) throws SQLException {
		
		Usuario u = new Usuario();
		u.setId(rs.getInt("id_usuario"));
		u.setNombre(rs.getString("nombre_usuario"));
		u.setContrasena(rs.getString("contrasena"));

		Rol r = new Rol();
		r.setId(rs.getInt("id_rol"));
		r.setNombre(rs.getString("nombre_rol"));

		u.setRol(r);

		return u;
	}
}
