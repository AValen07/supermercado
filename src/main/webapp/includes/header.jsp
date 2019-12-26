<!doctype html>
<html lang="en">
  <head>
 	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	
	<base href="${pageContext.request.contextPath}/"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Aitor Valencia">
    <title>Supermercado</title>

   <!-- Bootstrap core CSS -->
   <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">

   <!-- nuestro css -->
   <link rel="stylesheet" href="css/custom.css">

  </head>
  <body id="top">
    <nav class="site-header sticky-top py-1">
        <div class="container d-flex flex-column flex-md-row justify-content-between">
            <a class="py-2" href="index.jsp">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="d-block mx-auto" role="img" viewBox="0 0 24 24" focusable="false"><title>Product</title><circle cx="12" cy="12" r="10"/><path d="M14.31 8l5.74 9.94M9.69 8h11.48M7.38 12l5.74-9.94M9.69 16L3.95 6.06M14.31 16H2.83m13.79-4l-5.74 9.94"/></svg>
            </a>
            <c:if test="${empty usuarioLogueado }">
            	<a class="py-2 d-none d-md-inline-block" href="login.jsp">Login</a>
            </c:if>
            <c:if test="${not empty usuarioLogueado }">
            <div>
	            <a class="nav-link dropdown-toggle" href="#" id="productos" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	         	 Producto
	       		</a>
	       		<div class="dropdown-menu" aria-labelledby="productos">       		
		            <a class="dropdown-item" href="seguridad/productos?accion=lista">Tabla</a>
		            <a class="dropdown-item" href="seguridad/productos?accion=formulario">Formulario</a>
	            </div>  
            </div>
            <div>
	            <a class="nav-link dropdown-toggle" href="#" id="usuarios" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	         	 Usuario
	       		</a>
	       		<div class="dropdown-menu" aria-labelledby="usuarios">       		
		            <a class="dropdown-item" href="seguridad/usuarios?accion=lista">Lista</a>
		            <a class="dropdown-item" href="seguridad/usuarios?accion=formulario">Nuevo</a>
	            </div>
            </div> 
            <div>  
            	<a class="nav-link dropdown-toggle" href="#" id="usuario" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            	${usuarioLogueado.nombre}
            	</a>  
            	<div class="dropdown-menu" aria-labelledby="usuario">       		
                    <a class="dropdown-item" href="logout"> Cerrar sesion</a>
	            </div>      
            </div>            
            </c:if>
        </div>
    </nav>

    <main class="container">
    
    <c:if test="${not empty mensajeAlerta }">
	    <div class="alert alert-${mensajeAlerta.tipo } alert-dismissible fade show mt-3" role="alert">
		  ${mensajeAlerta.texto}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
    </c:if>
    