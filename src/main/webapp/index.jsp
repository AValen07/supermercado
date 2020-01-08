<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/includes/header.jsp" %>

	
        <div class="row contenedor-productos">
        	<p>${categorias}</p>
			<c:forEach items="${productos}" var="producto">
		            <div class="col">
		
		                <!-- producto -->
		                <div class="producto">
		                    <span class="descuento">${producto.descuento}</span>
		                    <img src= ${producto.imagen} alt="imagen de bottela de vodka">
		
		                    <div class="body">
		                        <p>
		                            <span class="precio-descuento"><fmt:formatNumber type="currency" currencySymbol="€" value="${producto.precio}" /></span>
		                            <span class="precio-original"><fmt:formatNumber type="currency" currencySymbol="€" value="${producto.precioCalculado()}" /></span>
		                        </p>
		                        <p class="text-muted precio-unidad"></p>
		                        <p class="descripcion text-truncate" >${producto.nombre }</p>
		                        <p class="descripcion text-truncate" >Creado por: ${producto.usuario.nombre }</p>
		                        <p class="descripcion text-truncate" >Categoria:  ${producto.categoria.nombre }</p>
		                    </div> 
	                    </div>
		                <!-- /.producto -->
		
		            </div>            
			</c:forEach>
            
        </div>

   <%@ include file="/includes/footer.jsp" %>
   