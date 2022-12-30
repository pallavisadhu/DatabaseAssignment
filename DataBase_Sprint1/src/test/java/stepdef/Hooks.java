package stepdef;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks{
	static WebDriver driver;
	Connection conn;
	Statement stmt;
	
	
	@Before
	public void beforeScenario() throws ClassNotFoundException, SQLException {
		System.out.println("beforeSecnario()");
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hr?useLegacyDatetimeCode=false&serverTimezone=America/New_York","root","Database@2");
		System.out.println("Start Test");
		
	}
	
	public static Connection getConnect() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hr?useLegacyDatetimeCode=false&serverTimezone=America/New_York","root","Database@2");
	}
	
	@After
	public void afterScenario() {
		driver.quit();
	}
	

}
