<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="servlets.salida"%>
<%@page import="model.datatypes.DTSalida"%>
<%@page import="model.datatypes.DTActividad"%>
<%@page import="model.datatypes.DTTurista"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.GregorianCalendar"%>
<!DOCTYPE html>
<html lang="es">

<head>
	<jsp:include page="/WEB-INF/templates/Head.jsp" />
    <title>Turismo.uy</title>
</head>

<body>
    <jsp:include page="/WEB-INF/templates/Navbar.jsp" />
    
    <% DTSalida salida = (DTSalida) request.getAttribute("salida"); %>
    
    <div class="row mt-5 mt-lg-0 container-principal">
    
        <jsp:include page="/WEB-INF/templates/AccesoCasosDeUso.jsp" />

        <!-- contenido individual -->
		<div class="col-sm-6 text-center">
			<div class="card mb-3" style="max-width: 800px;">
				<div class="row g-0">
					<div class="col-md-4">
						<img src="<%= salida.getImgDir() %>" class="img-fluid rounded-start" alt="...">
					</div>
					<div class="col-md-8">
						<div class="card-body">
							<h5 class="card-title"><%= salida.getNombre() %></h5>
							
							<% String nombreActividad = (String) request.getAttribute("nombreActividadSalida"); %>
							
							<div><a href="consultaActividad?nombreAct=<%= nombreActividad %>">Consulta de Actividad Turistica</a></div>
							<%
							if(session.getAttribute("usuario_logueado") instanceof DTTurista && salida.getfechaSalida().after(new GregorianCalendar())){
							%>
							<div><a href="inscripcionSalida?nombreSalida=<%= salida.getNombre() %>">Inscripcion a Salida Turistica</a></div>
							<%
							}
							%>
							<p class="card-text"><small class="text-muted">Fecha de alta: 
								<%= 
									new SimpleDateFormat("dd/MM/yyyy").format(salida.getfechaAlta().getTime())
								%>
							</small></p>
						</div>
					</div>
				</div>
			</div>
			
			<div class="card mb-3" style="max-width: 800px;">
				<div class="card-body">
				
					<form>
		                    
		            	<fieldset disabled>
		                	<div class="row g-3 align-items-center pt-3">
		                    	<div class="col-auto">
		                        	<i class="fa fa-calendar prefix white-text"></i>
		                        	<label for="inputPassword6" class="col-form-label disabled'">Dia:</label>
		                        </div>
		                        <div class="col-auto">
		                        	<input type="password" id="inputPassword6" class="form-control disabled" aria-describedby="disabled" placeholder=
		                        	<%= 
									new SimpleDateFormat("dd/MM/yyyy").format(salida.getfechaSalida().getTime())
									%>>
		                    	</div>
		                    </div>
						</fieldset>
						
		                <fieldset disabled>
							<div class="row g-3 align-items-center pt-3">
								<div class="col-auto">
									<i class="fa fa-clock-o prefix white-text"></i>
									<label for="inputPassword6" class="col-form-label">Hora:</label>
								</div>
								<div class="col-auto">
									<input type="password" id="inputPassword6" class="form-control disabled" aria-describedby="disabled" placeholder=
									<%= 
									new SimpleDateFormat("HH:mm").format(salida.getfechaSalida().getTime())
									%>>
								</div>
							
							</div>
						</fieldset>
						
						<fieldset disabled>
							<div class="row g-3 align-items-center pt-3">
								<div class="col-auto">
									<i class="fa fa-male prefix white-text"></i>
									<label for="inputPassword6" class="col-form-label">Cantidad Turistas maxima:</label>
								</div>
								<div class="col-auto">
									<input type="password" id="inputPassword6" class="form-control disabled" aria-describedby="disabled" placeholder="<%= salida.getcantidadMaximaDeTuristas() %>">
								</div>
							              
							</div>
						</fieldset>
		                
						<fieldset disabled>
							<div class="row g-3 align-items-center pt-3">
								<div class="col-auto">
									<i class="fa fa-location-arrow prefix white-text"></i>
									<label for="inputPassword6" class="col-form-label disabled'">Lugar:</label>
								</div>
								<div class="col-md-8">
									<input type="text" id="disabledTextInput" class="form-control" placeholder="<%= salida.getlugarSalida() %>">
								</div>
							</div>
						</fieldset>
				
		  			</form>
				</div>
			</div>
		</div>

		<div class="col-sm-3">
            
        </div>
        <!-- contenido individual -->

    </div>

    <jsp:include page="/WEB-INF/templates/Footer.jsp" />
</body>

</html>