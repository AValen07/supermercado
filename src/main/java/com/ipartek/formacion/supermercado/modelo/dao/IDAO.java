package com.ipartek.formacion.supermercado.modelo.dao;

import java.util.List;


public interface IDAO<P> {
	
	
	/**
	 * 
	 * @return Lista de todos los pojos
	 */
	List<P> getAll();
	
	/**
	 * Recupera un pojo por su identificador
	 * @param id identificador
	 * @return pojo si lo encuentra si no null.
	 */
	P getById(int id);
	
	/**
	 * Elimina
	 * @param id identificador
	 * @return pojo eliminado.
	 * @throws Exception si no se puede eliminar o no encontrado.
	 */
	P delete(int id) throws Exception;
	
	/**
	 * Modifica un pojo
	 * @param id identificador
	 * @param pojo datos a modificar
	 * @return pojo modificado
	 * @throws Exception si no puede modificar o no lo encuentra
	 */
	P update(int id, P pojo) throws Exception;
	
	/**
	 * crea un nuevo pojo.
	 * @param pojo.
	 * @return pojo creado con el id nuevo.
	 * @throws Exception si no puede crear.
	 */
	P create(P pojo) throws Exception;
}
