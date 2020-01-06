package com.ipartek.formacion.supermercado.modelo.pojo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Usuario {

	private int id;

	@NotBlank
	@Size(min = 2, max = 50)
	private String nombre;

	@NotBlank
	@Size(min = 2, max = 50)
	private String contrasena;

	private Rol rol;

	public Usuario() {
		super();
		this.id = 0;
		this.nombre = "";
		this.contrasena = "";
		this.rol = new Rol();
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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", contrasena=" + contrasena + ", rol=" + rol + "]";
	}

}
