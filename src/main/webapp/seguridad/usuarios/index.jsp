<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/includes/header.jsp" %>
  
  <h1>TABLA</h1>
  
  <main class="container">
  		
  		<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">  			
  			<thead>
  				<th>ID</th>
  				<th>Nombre Usuario</th>
  				<th>Contraseña</th>  				
  				<th>Acciones</th>  				
  			</thead>
  			
  			<c:forEach items="${usuarios}" var="usuario">
	  			<tbody>
	  				<td>${usuario.id}</td>
	  				<td>${usuario.nombre}</td>
	  				<td>${usuario.contrasena}</td>	  				  				
	  				<td><a href="seguridad/usuarios?accion=formulario&id=${usuario.id}">Editar</a></td>	  				
	  			</tbody>
  			</c:forEach>
  		</table>
  
  </main>
  
  
<%@ include file="/includes/footer.jsp" %>