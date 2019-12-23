<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/includes/header.jsp" %>
  
  <h1>FORMULARIO</h1>
  <form action="seguridad/productos" method="post">
  	<input type="hidden" name="accion" value="guardar">
  	<label for="id">Id:</label>
  	<input type="text" id="id" name="id" value="${producto.id}">
  	<br>
  	<label for="nombre">Nombre:</label>  	
  	<input type="text" id="nombre" name="nombre" value="${producto.nombre}">
  	<br>
  	<label for="precio">Precio:</label>  	
  	<input type="text" id="precio" name="precio" value="${producto.precio}">
  	<br>
  	<label for="descuento">Descuento:</label>  	
  	<input type="text" id="descuento" name="descuento" value="${producto.descuento}">
  	<br>
  	<label for="descripcion">Descripcion:</label>  	
  	<input type="text" id="descripcion" name="descripcion" value="${producto.descripcion}">
  	<br>
  	<label for="imagen">Imagen:</label>  	
  	<input type="text" id="imagen" name="imagen" value="${producto.imagen}">
  	<br>
  	<input type="submit" value="Enviar">
  </form>
  <a class="btn btn-danger btn-user btn-block" href="seguridad/productos?accion=eliminar&id=${producto.id}">Eliminar</a>
<%@ include file="/includes/footer.jsp" %>