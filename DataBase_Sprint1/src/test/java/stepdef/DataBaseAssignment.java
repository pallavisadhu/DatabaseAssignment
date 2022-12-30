package stepdef;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DataBaseAssignment{


	Connection conn;
	Statement stmt;
	ResultSet resultSet;
	int colcount;
	int rowcount;
	ArrayList<String> al;
	ArrayList<String> dbal;
	String db_empid, db_empname,db_sal;

	
	
	@Given("^the url \"([^\"]*)\"$")
	public void the_url(String arg1) throws Throwable {
		Hooks.driver.get(arg1);
	    
	}

	@When("^the values are retrieved from the data base$")
	public void the_values_are_retrieved_from_the_data_base() throws Throwable {
		stmt = Hooks.getConnect().createStatement();
        resultSet = stmt.executeQuery("select d.department_name from hr.locations l join hr.departments d on l.location_id=d.location_id join hr.countries c on c.country_id=l.country_id and c.country_name!='United States of America'");
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        colcount = rsMetaData.getColumnCount();
        System.out.println(colcount);
        
        while (resultSet.next())
        {
        System.out.println(resultSet.getString(1));
        rowcount++;
        }
        System.out.println(rowcount);

	}

	@Then("^it should match with the values on the application$")
	public void it_should_match_with_the_values_on_the_application() throws Throwable {
		 int uirows = Hooks.driver.findElements(By.xpath("//select[@name='departments']//option")).size();
		    
		    System.out.println("ui rows==>"+uirows);
		    System.out.println("database row==>"+rowcount);
		    if(uirows==rowcount) 
		    	Assert.assertTrue(true);
		    else
		    	Assert.assertFalse(false);		
	   
	}
	@When("^the total number of departments present in each city are retrieved from the data base$")
	public void the_total_number_of_departments_present_in_each_city_are_retrieved_from_the_data_base() throws Throwable {
		stmt = Hooks.getConnect().createStatement();
        resultSet = stmt.executeQuery("select count(department_id) as TotalNumberofdepartments ,city from hr.departments d join hr.locations l on d.location_id=l.location_id group by l.city");
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        colcount = rsMetaData.getColumnCount();
        System.out.println(colcount);
        al = new ArrayList<String>();
        while (resultSet.next())
        {
        System.out.println(resultSet.getString(1));
        System.out.println(resultSet.getString(2));
        al.add(resultSet.getString(2));

        rowcount++;
        }
        
        System.out.println(rowcount);
        System.out.println(al);

	    
	}

	@Then("^the data should match with the table on the application$")
	public void the_data_should_match_with_the_table_on_the_application() throws Throwable {
		 List<WebElement>list = Hooks.driver.findElements(By.xpath("//table[@class='adap-table']//tbody//tr//child::td[2]"));
		 dbal = new ArrayList<String>();
		 for (WebElement l:list) {
			 dbal.add(l.getText()); 
			 
		 }		 
		 System.out.println(dbal);
		 al.removeAll(dbal);
		 System.out.println(al);
		 Assert.assertEquals(al.get(0),"Seattle");
	   
	}
	
	@When("^the details are retrieved from the database$")
	public void the_details_are_retrieved_from_the_database() throws Throwable {
		stmt = Hooks.getConnect().createStatement();
        resultSet = stmt.executeQuery("select employee_id,concat(first_name,' ',last_name) as Name,truncate(salary,0) from hr.employees where salary=(select distinct salary from hr.employees order by salary desc limit 1 offset 2)");
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        colcount = rsMetaData.getColumnCount();
        System.out.println(colcount);
        while (resultSet.next())
        {
        	db_empid=resultSet.getString(1);
            db_empname = resultSet.getString(2);
            db_sal = resultSet.getString(3);
            System.out.println(db_empid);
            System.out.println(db_empname);
            System.out.println(db_sal);
            rowcount++;
      
        }
	   
	}

	@Then("^the data should be mathed with the application$")
	public void the_data_should_be_mathed_with_the_application() throws Throwable {
		String ui_empid =  Hooks.driver.findElement(By.xpath("//table[@class='salary_employee']//tbody//tr//td[1]")).getText();
		
		Assert.assertEquals(ui_empid, "Employee_id :"+db_empid);
		String ui_empname = Hooks.driver.findElement(By.xpath("//table[@class='salary_employee']//tbody//tr//td[2]")).getText();	
		Assert.assertEquals(ui_empname, "Name:"+db_empname);
		
		String ui_sal = Hooks.driver.findElement(By.xpath("//table[@class='salary_employee']//tbody//tr//td[3]")).getText();
		Assert.assertEquals(ui_sal, "Salary:"+db_sal);
	}


}
