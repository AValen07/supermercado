package com.ipartek.formacion.supermercado.modelo.dao;

import java.sql.SQLException;
import java.util.List;

import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

public interface IProductoDAO extends IDAO<Producto> {
	/**
	 * Lista los productos de un Usuario
	 * 
	 * @param idUsuario idUSuario int identificador del usuario
	 * @return List<Producto>, lista inicializada en caso de que no tenga productos.
	 */
	List<Producto> getAllByUser(int idUsuario);
	
	/**
	 * Recupera un producto de un usuario concreto.
	 * @param idPRoducto
	 * @param idUsuario
	 * @return Producto si encuentra, null si no encuentra.
	 * @throws ProductoException Cuando intenta recuperar un producto que no pertenece al usuario.
	 */
	Producto getByIdByUser(int idPRoducto, int idUsuario) throws ProductoException;
	
	/**
	 * Modifica un producto de un Usuario concreto
	 * @param idPRoducto
	 * @param idUsuario
	 * @param pojo
	 * @return
	 * @throws ProductoException Cuando intenta modificar un producto que no pertenece al usuario 
	 */
	Producto updateByUser(int idProducto, int idUsuario, Producto pojo) throws SQLException, ProductoException;
	
	/**
	 * Elimina un producto de un Usuario concreto
	 * @param idProducto
	 * @param idUsuario
	 * @return el producto eliminado si lo encuentra, si no lo encuentra lanza ProductoException
	 * @throws ProductoException
	 * 					<ol>
	 * 						<li>Cuando  intenta eliminar un producto que no pertenece al usuario</li>
	 * 						<li>Cuando no encuentra el producto por su id para idUsuario</li>
	 * 					</ol>
	 */
	Producto deleteByUser(int idProducto, int idUsuario) throws SQLException, ProductoException;
}
