<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/includes/header.jsp" %>
<div class="row justify-content-center">
  <div class="border border-primary col-sm-6">
  	  <h1>FORMULARIO</h1>
  	
	  <form action="seguridad/usuarios" method="post">
	  	<input type="hidden" name="accion" value="guardar">
	  	<div class="form-group">
		  	<label for="id">Id:</label>
		  	<input class="form-control form-control-user" type="text" id="id" name="id" value="${usuario.id}">
	  	</div>
	  	<div class="form-group">
		  	<label for="nombre">Nombre:</label>  	
		  	<input class="form-control form-control-user" type="text" id="nombre" name="nombre" value="${usuario.nombre}">
	  	</div>	  	
	  	<div class="form-group">
		  	<label for="precio">Contraseña:</label>  	
		  	<input class="form-control form-control-user" type="text" id="contrasena" name="contrasena" value="${usuario.contrasena}">
	  	</div>
	  	
	  	<input class="btn btn-primary btn-user btn-block" type="submit" value="Enviar">
	  	<c:if test="${usuario.id > 0}">		
				<!-- Button trigger modal -->
				<button type="button" class="btn btn-outline-danger btn-user btn-block" data-toggle="modal" data-target="#exampleModal">
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
				        <a class="btn btn-danger" href="seguridad/productos?id=${producto.id}&accion=eliminar">Eliminar</a>
				      </div>
				    </div>
				  </div>
				</div>	
		</c:if>
	  </form>
	  
  </div>
</div>
<%@ include file="/includes/footer.jsp" %>