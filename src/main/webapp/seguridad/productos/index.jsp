<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/includes/header.jsp" %>
  
  <h1>TABLA</h1>
  
  <main class="container">
  		
  		<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">  			
  			<thead>
  				<th>ID</th>
  				<th>Imagen</th>  				
  				<th>Producto</th>
  				<th>Precio</th>
  				<th>Descuento</th>
  				<th>Descripcion</th>
  				<th>Usuario</th>
  				<th>Acciones</th>
  			</thead>
  			
  			<c:forEach items="${productos}" var="producto">
	  			<tbody>
	  				<td>${producto.id}</td>
	  				<td><img style="heigh:100px; width:100px;" src="${producto.imagen}"></td>
	  				<td>${producto.nombre}</td>
	  				<td>${producto.precio}</td>
	  				<td>${producto.descuento}</td>
	  				<td>${producto.descripcion}</td>	  				
	  				<td>${producto.usuario.nombre}</td>	  				
	  				<td><a href="seguridad/productos?accion=formulario&id=${producto.id}">Editar</a></td>	  				
	  			</tbody>
  			</c:forEach>
  		</table>
  
  </main>
  
  
<%@ include file="/includes/footer.jsp" %>