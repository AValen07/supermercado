<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/includes/header.jsp" %>

<div class="row justify-content-center">	
	<div class="col-4 mt-6">
		<form action="login" method="post">
			<div class="form-group">
				<label for="nombre">Nombre</label>
				<input type="text" class="form-control" id="nombre" name="usuario" placeholder="Nombre de usuario">
			</div>
			<div class="form-group">	
				<label for="password">Contraseña</label>
				<input type="password" class="form-control" id="password" name="password"  placeholder="contraseña">
				
			</div>
			<button type="submit" class=" btn btn-block btn-primary" >Entrar</button>
		</form>
	</div>	
</div>



<%@ include file="/includes/footer.jsp" %>