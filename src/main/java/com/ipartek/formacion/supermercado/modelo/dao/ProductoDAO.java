package com.ipartek.formacion.supermercado.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

public class ProductoDAO implements IDAO<Producto>{

	private static ProductoDAO INSTANCE;
	private ArrayList<Producto> registros;
	private int indice=1;
	
	
	private ProductoDAO() {
		super();
		registros=new ArrayList<Producto>();
		
		//TODO 10 registros mas elaborados.
		registros.add(new Producto(indice++,"vodka", 13.30f ,"img/vodka.jpg","Vodka Bottella de Absolut version navidad 2020",20));
		registros.add(new Producto(indice++,"aguacates", 4.99f ,"img/aguacates.jpg","Aguacates maduros",0));
		registros.add(new Producto(indice++,"aceite",4.50f,"img/aceite.jpg","Aceite vrgen extra",10));
		registros.add(new Producto(indice++,"atun",3.99f,"img/atun.jpg","Atun en lata al natural",0));
		registros.add(new Producto(indice++,"champu",2.50f,"img/champu.jpg","Champu anticaspa",0));
		registros.add(new Producto(indice++,"galletas",2.00f,"img/galletas.jpg","Galletas rellenas de chocolate",15));
		registros.add(new Producto(indice++,"leche",0.60f,"img/leche.jpg","Leche entera",0));
		registros.add(new Producto(indice++,"pienso",8.40f,"img/pienso.jpg","Pienso para perros jovenes",0));
		registros.add(new Producto(indice++,"platanos",0.99f,"img/platanos.jpg","Platanos de canarias",0));
		registros.add(new Producto(indice++,"queso",5.60f,"img/queso.jpg","Queso idiazabal",20));
		//for (int i = 0; i <= 10; i++) {
		//	registros.add(new Producto());
		//	indice++;
		//}
		
	}

	public static synchronized ProductoDAO getInstance() {
		
		if(INSTANCE==null)
		{
			INSTANCE=new ProductoDAO();
		}
		
		return INSTANCE;
	}
	@Override
	public List<Producto> getAll() {
		
		return registros;
	}

	@Override
	public Producto getById(int id) {

		Producto resultado = null;
		
		for (Producto producto : registros) {
			
			if ( id == producto.getId() ) {
				resultado = producto;
				break;
			}
			
		}
		
		return resultado;
	}

	@Override
	public Producto delete(int id) throws Exception {
		Producto resultado = null;

		for (Producto producto : registros) {
			if (id == producto.getId()) {
				resultado = producto;
				registros.remove(producto);
				break;
			}
		}

		if (resultado == null) {
			throw new Exception("Perro no encontrado por su id " + id);
		}
		return resultado;
	}

	@Override
	public Producto update(int id, Producto pojo) throws Exception {
		Producto resultado = null;
		
		for (Producto producto : registros) {
			
			if ( id == producto.getId() ) {
				producto.setNombre(pojo.getNombre());
				producto.setImagen(pojo.getImagen());
				producto.setDescripcion(pojo.getDescripcion());
				producto.setDescuento(pojo.getDescuento());
				producto.setPrecio(pojo.getPrecio());
				break;
			}
			
		}

		
		return resultado;
	}

	@Override
	public Producto create(Producto pojo) throws Exception {
		Producto resultado = pojo;
		//TODO comprobar campos del pojo
		if ( pojo != null ) {
			pojo.setId(indice++);
			registros.add(pojo);			
		}else {
			throw new Exception("Perro NULL sin datos" );
		}
		
		return resultado;
	}

}
