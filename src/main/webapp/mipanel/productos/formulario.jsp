<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/includes/header.jsp" %>   
    	
	<h1>FORMULARIO</h1>
	
	<form action="mipanel/productos" method="post" class="mb-4">
		
		<input type="hidden" name="accion" value="guardar">
	  	<div class="form-group">
		  	<label for="id">Id:</label>
		  	<input class="form-control form-control-user" type="text" id="id" name="id" value="${producto.id}">
	  	</div>
	  	<div class="form-group">
		  	<label for="nombre">Nombre:</label>  	
		  	<input class="form-control form-control-user" type="text" id="nombre" name="nombre" value="${producto.nombre}">
	  	</div>	  	
	  	<div class="form-group">
		  	<label for="precio">Precio:</label>  	
		  	<input class="form-control form-control-user" type="text" id="precio" name="precio" value="${producto.precio}">
	  	</div>
	  	<div class="form-group">
		  	<label for="descuento">Descuento:</label>  	
		  	<input class="form-control form-control-user" type="text" id="descuento" name="descuento" value="${producto.descuento}">
	  	</div>
	  	<div class="form-group">
		  	<label for="descripcion">Descripcion:</label>  	
		  	<input class="form-control form-control-user" type="text" id="descripcion" name="descripcion" value="${producto.descripcion}">
	  	</div>
	  	<div class="form-group">
		  	<label for="imagen">Imagen:</label>  	
		  	<input class="form-control form-control-user" type="text" id="imagen" name="imagen" value="${producto.imagen}">
	  	</div>
		
		<input type="submit" value="${(producto.id>0)?"Modificar":"Crear" }" class="btn btn-block btn-primary">
	
	</form>
	
	<c:if test="${producto.id > 0}">
	

		
	
			<!-- Button trigger modal -->
			<button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#exampleModal">
			  Eliminar
			</button>
			
			<!-- Modal -->
			<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
			        ...
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			        <a class="btn btn-danger" href="mipanel/productos?id=${producto.id}&accion=eliminar">Eliminar</a>
			      </div>
			    </div>
			  </div>
			</div>


	</c:if>
	

<%@ include file="/includes/footer.jsp" %> 