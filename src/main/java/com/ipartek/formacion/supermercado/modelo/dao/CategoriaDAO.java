package com.ipartek.formacion.supermercado.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.model.ConnectionManager;
import com.ipartek.formacion.supermercado.modelo.pojo.Categoria;

public class CategoriaDAO implements ICategoriaDAO {

	private final static Logger LOG = Logger.getLogger(CategoriaDAO.class);

	private static CategoriaDAO INSTANCE;

	private CategoriaDAO() {
		super();
	}

	public static synchronized CategoriaDAO getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new CategoriaDAO();
		}

		return INSTANCE;
	}

	@Override
	public List<Categoria> getAll() {

		LOG.trace("recuperar todas las categorias");
		List<Categoria> registros = new ArrayList<Categoria>();

		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_getall() }");) {

			LOG.debug(cs);
			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					// TODO mapper
					Categoria c = new Categoria();
					c.setId(rs.getInt("id"));
					c.setNombre(rs.getString("nombre"));
					registros.add(c);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return registros;
	}

	@Override
	public Categoria getById(int id) {
		Categoria resultado = null;

		LOG.trace("encontrar una categoria po el id "+ id);
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_getbyid(?) }");) {

			cs.setInt(1, id);
			LOG.debug(cs);
			try (ResultSet rs = cs.executeQuery()) {
				if (rs.next()) {
					// TODO mapper
					Categoria c = new Categoria();
					c.setId(rs.getInt("id"));
					c.setNombre(rs.getString("nombre"));
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public Categoria delete(int id) throws Exception {
		Categoria resultado = null;

		LOG.trace("eliminar una categoria");
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_delete(?) }");) {

			cs.setInt(1, id);
			resultado = getById(id);
			LOG.debug(cs);

			int affectedRows = cs.executeUpdate();
			if (affectedRows != 1) {
				resultado = null;
			}
		}

		return null;
	}

	@Override
	public Categoria update(int id, Categoria pojo) throws Exception {
		Categoria resultado = null;

		LOG.trace("insertar nueva categoria" + pojo);
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_update(?,?) }");) {

			cs.setInt(1, id);
			cs.setString(2, pojo.getNombre());

			LOG.debug(cs);

			int affectedRows = cs.executeUpdate();

			if (affectedRows == 1) {

				resultado = pojo;
			}
		}

		return resultado;
	}

	@Override
	public Categoria create(Categoria pojo) throws Exception {

		Categoria resultado = pojo;

		LOG.trace("insertar nueva categoria" + pojo);
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall("{ CALL pa_categoria_insert(?,?) }");) {

			// Parametro de entrada
			cs.setString(1, pojo.getNombre());

			// Parametro de salida
			cs.registerOutParameter(2, java.sql.Types.INTEGER);

			LOG.debug(cs);

			// ejecutamos el procedimiento almacenado.
			cs.executeUpdate();

			// Una vez ejecutado podemos recuperar el parametro de salida
			pojo.setId(cs.getInt(2));
		}

		return resultado;
	}

}
