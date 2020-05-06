package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/patientnew?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}


	public String insertPatient(String nic, String fname, String lname, String gender)

	{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			 // create a prepared statement
			 String query = " insert into patient(PatientID,NIC,fname,lname,gender)" + " values (?, ?, ?, ?, ?)";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 
				 preparedStmt.setInt(1, 0);
				 preparedStmt.setString(2, nic);
				 preparedStmt.setString(3, fname);
				 preparedStmt.setString(4, lname);
				 preparedStmt.setString(5, gender);
				 
				// execute the statement
				 preparedStmt.execute();
			
				 String newpatient = readPatient();    
				 output = "{\"status\":\"success\", \"data\": \"" +  newpatient + "\"}";
			
		} catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the patient.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String readPatient() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'>"
					+ "<tr>"
					+ "<th>NIC</th>"
					+ "<th>fname</th>"
					+ "<th>lname</th>"
					+ "<th>gender</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th>"
					+ "</tr>";

			String query = "select * from patient";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) 
			{
				String PatientID = Integer.toString(rs.getInt("PatientID"));
				String NIC = rs.getString("NIC");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String gender = rs.getString("gender");
				
// Add into the html table
				
				output += "<tr><td><input id='hidPatientIDUpdate'name='hidPatientIDUpdate'type='hidden' value='" + PatientID+ "'>" + NIC + "</td>";
				output += "<td>" + fname + "</td>";
				output += "<td>" + lname + "</td>";
				output += "<td>" + gender + "</td>";
// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"+ 
						 "<td><input name='patientID' type='button' value='Remove' class='btnRemove btn btn-danger' data-patientid='" + PatientID + "'>" + "</td></tr>";
			}
			con.close();
			
// Complete the html table
			output += "</table>";
			
			}
				catch (Exception e) 
		{
			output = "Error while reading the patients.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePatient(String ID, String nic, String fname, String lname, String gender) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
// create a prepared statement
			String query = "UPDATE patient SET NIC=?,fname=?,lname=?,gender=? WHERE PatientID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
// binding values
			preparedStmt.setString(1, nic);
			preparedStmt.setString(2, fname);
			preparedStmt.setString(3, lname);
			preparedStmt.setString(4, gender);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			
// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPatients = readPatient();
			output = "{\"status\":\"success\", \"data\": \"" + newPatients + "\"}";
			
		} catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the patient.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePatient(String PatientID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

// create a prepared statement
			String query = "delete from patient where PatientID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
// binding values
			preparedStmt.setInt(1, Integer.parseInt(PatientID));
			
// execute the statement
			preparedStmt.execute();
			con.close();
			String newPatients = readPatient();
			
			output = "{\"status\":\"success\", \"data\": \"" + newPatients + "\"}";
			}
				catch (Exception e)
		{
				output = "{\"status\":\"error\", \"data\":\"Error while deleting the patient.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public static void main(String[] args) {
		new Patient().insertPatient("sdf", "safsaf", "123.23", "sdfadsfsadf");
		//new Patient().deletePatient("1");
	}
}
