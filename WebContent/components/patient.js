
$(document).ready(function()

 {
	 $("#alertSuccess").hide();
	 $("#alertError").hide();
 });


$(document).on("click", "#btnSave", function(event) 
		{  
			$("#alertSuccess").text("");  
			$("#alertSuccess").hide();  
			$("#alertError").text("");  
			$("#alertError").hide(); 
			
			
			var status = validatePatientForm();  
			if (status != true)  
			{   
				$("#alertError").text(status);   
				$("#alertError").show();   
				return;  
			} 
			
			var type = ($("#hidPatientIDSave").val() == "") ? "POST" : "PUT"; 
			
			$.ajax( 
			{  
				url : "PatientsAPI",  
				type : type,  
				data : $("#formPatient").serialize(),  
				dataType : "text",  
				complete : function(response, status)  
				{   
					onPatientSaveComplete(response.responseText, status);
				} 
			
		}); 
}); 

function onPatientSaveComplete(response, status)
{   
	if (status == "success")
	 {
		var resultSet = JSON.parse(JSON.stringify(response)); 
		console.log(resultSet);
		 
		 if (status == "success")
		 {
			 $("#alertSuccess").text("Successfully saved.");
			 $("#alertSuccess").show();
			 location.reload();
			 $("#divPatientsGrid").html(resultSet.data);
		 }	else if (status.trim() == "error") 
		 {
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		 }
	 } else if (status == "error")
	 {
		 $("#alertError").text("Error while saving.");
		 $("#alertError").show();
	 } else
	 {
		 $("#alertError").text("Unknown error while saving..");
		 $("#alertError").show();
	 }
		 $("#hidPatientIDSave").val("");
		 $("#formPatient")[0].reset(); 
} 

//remove

$(document).on("click", ".btnRemove", function(event)
		{
		 $.ajax(
		 {
			 url : "PatientsAPI",
			 type : "DELETE",
			 data : "PatientID=" + $(this).data("patientid"),
			 dataType : "text",
			 complete : function(response, status)
			 {
				 onPatientDeleteComplete(response.responseText, status);
			 }
		});
	});




function onPatientDeleteComplete(response, status)
{
	if (status == "success")
	 {
		// var resultSet = JSON.parse(response);
		
		 if (status.trim() == "success")
	 {
		 $("#alertSuccess").text("Successfully deleted.");
		 $("#alertSuccess").show();
		 location.reload();
		// $("#divPatientsGrid").html(resultSet.data);
	 } else if (status.trim() == "error")
	 {
		 $("#alertError").text(status);
		 $("#alertError").show();
	 }
	 } else if (status == "error")
		 {
		 $("#alertError").text("Error while deleting.");
		 $("#alertError").show();
	 } else
	 {
		 $("#alertError").text("Unknown error while deleting..");
		 $("#alertError").show();
	 }
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
 $("#hidPatientIDSave").val($(this).closest("tr").find('#hidPatientIDUpdate').val());
 $("#NIC").val($(this).closest("tr").find('td:eq(0)').text());
 $("#fname").val($(this).closest("tr").find('td:eq(1)').text());
 $("#lname").val($(this).closest("tr").find('td:eq(2)').text());
 $("#gender").val($(this).closest("tr").find('td:eq(3)').text());
});

//CLIENT-MODEL================================================================
function validatePatientForm()
{
// CODE
if ($("#NIC").val().trim() == "")
 {
 return "Insert Patient Nic.";
 }
// NAME
if ($("#fname").val().trim() == "")
 {
 return "Insert Patient Fname.";
 }
//LASTNAME
if ($("#lname").val().trim() == "")
 {
 return "Insert Patient Lname.";
 }
// DESCRIPTION------------------------
if ($("#gender").val().trim() == "")
 {
 return "Insert Patient Gender.";
 }
return true;
}


