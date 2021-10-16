package com.finalProject.server;
/*
 * AUTHOR : VRAJESH BHIMAJIANI
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/eComQuery")
public class eComQuery {
	
	private Connection connection = null;
	private Statement statment = null;
	private ResultSet resultSet  = null;
	JSONObject colObject = new JSONObject();
	JSONArray arrayCol = new JSONArray();

	
	// GET(1)
	//https://finalproject-288123.uc.r.appspot.com/api/eComQuery/query1/181
	// http://localhost:8080/api/eComQuery/query1/181
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/query1/{id}")
	public Response oderHistory(@PathParam("id") int id) {

		DatabaseConnection db = new DatabaseConnection();

		try {
			connection = db.getConnection();
			statment = connection.createStatement();

			/* vQuery1 : to see the oderHistory Of Customer using Cid */
			String query = "select customers.customerNumber,customers.customerName,orderdetails.orderNumber,products.productName,products.buyPrice,orders.shippedDate,orders.status from orders join customers on customers.customerNumber = orders.customerNumber join orderdetails on orders.orderNumber = orders.orderNumber join products on orderdetails.productCode = products.productCode where orders.customerNumber = "
					+ id;
			resultSet = statment.executeQuery(query);

			while (resultSet.next()) {

				colObject.accumulate("customerNumber", resultSet.getInt("customerNumber"));
				colObject.accumulate("customerName", resultSet.getString("customerName"));
				colObject.accumulate("orderNumber", resultSet.getInt("orderNumber"));
				colObject.accumulate("productName", resultSet.getString("productName"));
				colObject.accumulate("buyPrice", resultSet.getBigDecimal("buyPrice"));
				colObject.accumulate("shippedDate", resultSet.getString("shippedDate"));
				colObject.accumulate("status", resultSet.getString("status"));

			}
			if (colObject.isEmpty()) {
				colObject.accumulate("Error", "No value found!");
			}
		} catch (SQLException e)

		{
			System.out.println("SQL Exception :" + e.getMessage());
		} finally {
			try {
				connection.close();
				statment.close();
				resultSet.close();
			} catch (Exception e) {
				System.out.println("Finally Block Exception :" + e.getMessage());
			}
		}
		return Response.status(200).entity(colObject.toString()).build();
	}
	
	
	
	
	
	// GET(2)
	//https://finalproject-288123.uc.r.appspot.com/api/eComQuery/query2/181
	// http://localhost:8080/api/eComQuery/query2/181
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/query2/{id}")
	public Response PaymentHistory(@PathParam("id") int id) {

		DatabaseConnection db = new DatabaseConnection();

		try {
			connection = db.getConnection();
			statment = connection.createStatement();

			/*vQuery2 : to see the PaymentHistory Of Particular Customer using Cid*/
			String query = "select customers.customerNumber,customers.customerName,payments.checkNumber,payments.paymentDate,payments.amount from customers join payments on customers.customerNumber = payments.customerNumber where customers.customerNumber = "+id;
			resultSet = statment.executeQuery(query);

			while (resultSet.next()) {

				colObject.accumulate("customerNumber", resultSet.getInt("customerNumber"));
				colObject.accumulate("customerName", resultSet.getString("customerName"));
				colObject.accumulate("checkNumber", resultSet.getString("checkNumber"));
				colObject.accumulate("paymentDate", resultSet.getString("paymentDate"));
				colObject.accumulate("amount", resultSet.getBigDecimal("amount"));
				
			}
			if (colObject.isEmpty()) {
				colObject.accumulate("Error", "No value found!");
			}
		} catch (SQLException e)

		{
			System.out.println("SQL Exception :" + e.getMessage());
		} finally {
			try {
				connection.close();
				statment.close();
				resultSet.close();
			} catch (Exception e) {
				System.out.println("Finally Block Exception :" + e.getMessage());
			}
		}
		return Response.status(200).entity(colObject.toString()).build();
	}
	
	
	// GET(3)
	// https://finalproject-288123.uc.r.appspot.com/api/eComQuery/query3/181
	// http://localhost:8080/api/eComQuery/query3/181
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/query3/{id}")
	public Response spentByCustomer(@PathParam("id") int id) {

		DatabaseConnection db = new DatabaseConnection();

		try {
			connection = db.getConnection();
			statment = connection.createStatement();

			/*vQuery3 : total money spent By Customer using Cid*/
			String query = "select SUM(orderdetails.priceEach) As totalSpending from orders join orderdetails on orderdetails.orderNumber = orders.orderNumber join customers on customers.customerNumber = orders.customerNumber where customers.customerNumber = "+ id;
			resultSet = statment.executeQuery(query);

			while (resultSet.next()) {

				colObject.accumulate("totalSpending", resultSet.getBigDecimal("totalSpending"));

			}
			if (colObject.isEmpty()) {
				colObject.accumulate("Error", "No value found!");
			}
		} catch (SQLException e)

		{
			System.out.println("SQL Exception :" + e.getMessage());
		} finally {
			try {
				connection.close();
				statment.close();
				resultSet.close();
			} catch (Exception e) {
				System.out.println("Finally Block Exception :" + e.getMessage());
			}
		}
		return Response.status(200).entity(colObject.toString()).build();
	}	

	
	
	// GET(4)
	//https://finalproject-288123.uc.r.appspot.com/api/eComQuery/query4/'S10_1678'
	// http://localhost:8080/api/eComQuery/query4/'S10_1678'
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/query4/{pid}")
	public Response availStock(@PathParam("pid") String pid) {

		DatabaseConnection db = new DatabaseConnection();

		try {
			connection = db.getConnection();
			statment = connection.createStatement();

			/*Query4 : check the avail stock of product on given pid*/
			String query = "select quantityInStock from products where productCode = "+ pid;
			resultSet = statment.executeQuery(query);

			while (resultSet.next()) {

				colObject.accumulate("quantityInStock", resultSet.getInt("quantityInStock"));

			}
			if (colObject.isEmpty()) {
				colObject.accumulate("Error", "No value found!");
			}
		} catch (SQLException e)

		{
			System.out.println("SQL Exception :" + e.getMessage());
		} finally {
			try {
				connection.close();
				statment.close();
				resultSet.close();
			} catch (Exception e) {
				System.out.println("Finally Block Exception :" + e.getMessage());
			}
		}
		return Response.status(200).entity(colObject.toString()).build();
	}
	
	
	
	
	// POST(5)
	// https://finalproject-288123.uc.r.appspot.com/api/eComQuery/query5/'S10_1678'
	// http://localhost:8080/api/eComQuery/query5/'S10_1678'
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/query5/{pid}")
	public Response customerswhoBought(@PathParam("pid") String pid) {

		DatabaseConnection db = new DatabaseConnection();

		try {
			connection = db.getConnection();
			statment = connection.createStatement();

			/*Query5 : List of Customers who Bought Particular item using productCode */
			
			String query = "select customers.customerName from orders join orderdetails on orderdetails.orderNumber = orders.orderNumber join customers on orders.customerNumber = customers.customerNumber join products on orderdetails.productCode = products.productCode where products.productCode = " + pid; 
			resultSet = statment.executeQuery(query);

			while (resultSet.next()) {

				colObject.accumulate("customerName", resultSet.getString("customerName"));

			}
			if (colObject.isEmpty()) {
				colObject.accumulate("Error", "No value found!");
			}
		} catch (SQLException e)

		{
			System.out.println("SQL Exception :" + e.getMessage());
		} finally {
			try {
				connection.close();
				statment.close();
				resultSet.close();
			} catch (Exception e) {
				System.out.println("Finally Block Exception :" + e.getMessage());
			}
		}
		return Response.status(200).entity(colObject.toString()).build();
	}
	
	
	
	
	// POST(6)
	// https://finalproject-288123.uc.r.appspot.com/api/eComQuery/query6
	// http://localhost:8080/api/eComQuery/query6
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/query6")
	public Response priceFillter() {

		DatabaseConnection db = new DatabaseConnection();

		try {
			connection = db.getConnection();
			statment = connection.createStatement();

			/*Query6 : List of product from highest to lowest price. (CAN BE USED IN E-COM TO FILTER DATA.) */
			String query = "select * from products order by buyPrice asc;";
			resultSet = statment.executeQuery(query);

			while (resultSet.next()) {

				colObject.accumulate("productCode", resultSet.getString("productCode"));
				colObject.accumulate("productName", resultSet.getString("productName"));
				colObject.accumulate("productLine", resultSet.getString("productLine"));
				colObject.accumulate("productScale", resultSet.getString("productScale"));
				colObject.accumulate("productVendor", resultSet.getString("productVendor"));
				colObject.accumulate("productDescription", resultSet.getString("productDescription"));
				colObject.accumulate("quantityInStock", resultSet.getInt("quantityInStock"));
				colObject.accumulate("buyPrice", resultSet.getBigDecimal("buyPrice"));
				colObject.accumulate("MSRP", resultSet.getBigDecimal("MSRP"));

			}
			if (colObject.isEmpty()) {
				colObject.accumulate("Error", "No value found!");
			}
		} catch (SQLException e)

		{
			System.out.println("SQL Exception :" + e.getMessage());
		} finally {
			try {
				connection.close();
				statment.close();
				resultSet.close();
			} catch (Exception e) {
				System.out.println("Finally Block Exception :" + e.getMessage());
			}
		}
		return Response.status(200).entity(colObject.toString()).build();
	}
	
	
	
	
	// POST(7)
	// https://finalproject-288123.uc.r.appspot.com/api/eComQuery/query7/'S10_1678'
	// http://localhost:8080/api/eComQuery/query7/'S10_1678'
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/query7/{pid}")
	public Response productLineDetail(@PathParam("pid") String pid) {

		DatabaseConnection db = new DatabaseConnection();

		try {
			connection = db.getConnection();
			statment = connection.createStatement();

			/* Query7 : Find the product line details for particualr products using pid */
			
			String query = "select  products.productCode,products.productName,productlines.productLine,productlines.textDescription,productlines.htmlDescription,productlines.image from products join productlines on products.productLine = productlines.productLine where products.productCode = "+ pid;
			resultSet = statment.executeQuery(query);

			while (resultSet.next()) {

				colObject.accumulate("productCode", resultSet.getString("productCode"));
				colObject.accumulate("productName", resultSet.getString("productName"));
				colObject.accumulate("productLine", resultSet.getString("productLine"));
				colObject.accumulate("textDescription", resultSet.getString("textDescription"));
				colObject.accumulate("htmlDescription", resultSet.getString("htmlDescription"));
				colObject.accumulate("image", resultSet.getString("image"));

			}
			if (colObject.isEmpty()) {
				colObject.accumulate("Error", "No value found!");
			}
		} catch (SQLException e)

		{
			System.out.println("SQL Exception :" + e.getMessage());
		} finally {
			try {
				connection.close();
				statment.close();
				resultSet.close();
			} catch (Exception e) {
				System.out.println("Finally Block Exception :" + e.getMessage());
			}
		}
		return Response.status(200).entity(colObject.toString()).build();
	}
	
	
	
	// POST(8)
	// https://finalproject-288123.uc.r.appspot.com/api/eComQuery/query8/'Classic Metal Creations'
	// http://localhost:8080/api/eComQuery/query8/'Classic Metal Creations'
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/query8/{productVendor}")
	public Response productsSoldByVendor(@PathParam("productVendor") String productVendor) {

		DatabaseConnection db = new DatabaseConnection();

		try {
			connection = db.getConnection();
			statment = connection.createStatement();

			/*Query 8 : finding the number of products sold by particular Vendor */
			
			String query = "select count(productCode) as numberOfItems from products where productVendor = "+productVendor;
			resultSet = statment.executeQuery(query);

			while (resultSet.next()) {

				colObject.accumulate("numberOfItems", resultSet.getInt("numberOfItems"));

			}
			if (colObject.isEmpty()) {
				colObject.accumulate("Error", "No value found!");
			}
		} catch (SQLException e)

		{
			System.out.println("SQL Exception :" + e.getMessage());
		} finally {
			try {
				connection.close();
				statment.close();
				resultSet.close();
			} catch (Exception e) {
				System.out.println("Finally Block Exception :" + e.getMessage());
			}
		}
		return Response.status(200).entity(colObject.toString()).build();
	}
	/*
	 * AUTHOR : VRAJESH BHIMAJIANI
	 */

	
}