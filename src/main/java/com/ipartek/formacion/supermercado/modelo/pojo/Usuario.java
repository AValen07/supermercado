package com.ipartek.formacion.supermercado.modelo.pojo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Usuario {
	
	private int id;
	
	@NotBlank
	@Size(min=2, max=50)
	private String nombre;
	
	@NotBlank
	@Size(min=2, max=50)
	private String contrasena;
	
	
	
	public Usuario() {
		super();
		this.id=0;
		this.nombre="";
		this.contrasena="";
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
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	
	

}
