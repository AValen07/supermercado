<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/includes/header.jsp" %>   
    	
	
	<a href="mipanel/productos?accion=formulario">Nuevo Producto</a>
	
	<table  class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
  				<th>Imagen</th>  				
  				<th>Producto</th>
  				<th>Precio</th>
  				<th>Descuento</th>
  				<th>Descripcion</th>
  				<th>Usuario</th>
  				<th>Acciones</th>                
            </tr>
        </thead>
        <tbody>
        	<c:forEach items="${productos}" var="producto">
            	<tr>
                	<td>${producto.id}</td>
	  				<td><img style="heigh:100px; width:100px;" src="${producto.imagen}"></td>
	  				<td>${producto.nombre}</td>
	  				<td>${producto.precio}</td>
	  				<td>${producto.descuento}</td>
	  				<td>${producto.descripcion}</td>	  				
	  				<td>${producto.usuario.nombre}</td>               	
                	<td><a href="mipanel/productos?accion=formulario&id=${producto.id}">Editar</a></td>
            	</tr>
            </c:forEach>	
        </tbody>    
    </table>
	
	

<%@ include file="/includes/footer.jsp" %> 