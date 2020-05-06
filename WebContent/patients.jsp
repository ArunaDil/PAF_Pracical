<%@page import="model.PatientsAPI"%>
<%@page import="model.Patient"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient management</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-6">

				<h1>Patient management</h1>
				<form id="formPatient" name="formPatient" method="" action="">
					NIC: 
					<input id="NIC" name="NIC" type="text"
						class="form-control form-control-sm"> <br> 
					fname:
					<input id="fname" name="fname" type="text"
						class="form-control form-control-sm"> <br> 
					lname: 
					<input id="lname" name="lname" type="text"
						class="form-control form-control-sm"> <br> 
					gender: 
					<input id="gender" name="gender" type="text"
						class="form-control form-control-sm"> <br> 
					<input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> 
					<input type="hidden"
						id="hidPatientIDSave" name="hidPatientIDSave" value="">
				</form>

				<div id="alertSuccess" class = "alert alert-success">
					<%
						out.print(session.getAttribute("statusMsg"));
					%>
				</div>

				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<%
					Patient patientobj = new Patient();
					out.print(patientobj.readPatient());
				%>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.5.0.min.js" integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="./components/patient.js"></script>
</body>
</html>