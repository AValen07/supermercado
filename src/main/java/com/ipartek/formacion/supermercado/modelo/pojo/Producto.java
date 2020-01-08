package com.ipartek.formacion.supermercado.modelo.pojo;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Producto {

	public static final int DESCUENTO_MIN=0;
	public static final int DESCUENTO_MAX=100;
	
	
	private int id;
	
	@NotNull
	@NotBlank
	@Size(min=2, max=50)
	private String nombre;	
	
	@Digits(fraction=2, integer = 6)
	@Min(value=0,message="Tiene que ser mayor o igual que 0")
	private float precio;
	
	@NotNull
	@NotBlank
	@Size(min=0, max=5000)
	private String imagen;
	
	@NotNull
	@NotBlank
	@Size(min=0, max=150)
	private String descripcion;
	
	@Digits(fraction = 0, integer = 3)
	@Min(message="Tiene que ser un valor entre 0 y 100", value=0)
	@Max(message="Tiene que ser un valor entre 0 y 100",value=100)
	private int descuento;
	
	private Usuario usuario;
	
	private Categoria categoria;
	
	public Producto() {
		super();
		this.id=0;
		this.nombre="";
		this.precio=0;
		this.imagen="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9MJfiNi1BGC6eaEIDMp6H1tRv1BELm5Lrh-6Go7r3U-a51hh2LA&s";
		this.descripcion="";
		this.descuento=DESCUENTO_MIN;
		this.setUsuario(new Usuario());
		this.setCategoria(new Categoria());
	}

	
	
	public Categoria getCategoria() {
		return categoria;
	}



	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}



	public Producto(int id, String nombre, float precio, String imagen, String descripcion, int descuento, Usuario usuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.descuento = descuento;
		this.usuario = usuario;
	}

	public float precioCalculado() {
		
		float nuevoPrecio=0;
		
		nuevoPrecio= (precio*(100-descuento))/100;
		
		return (nuevoPrecio);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDescuento() {
		return descuento;
	}
	
	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", imagen=" + imagen
				+ ", descripcion=" + descripcion + ", descuento=" + descuento + "]";
	}


	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
}
