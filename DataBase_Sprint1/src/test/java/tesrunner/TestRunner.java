package tesrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(
		features="/Users/pallavisadhu/Desktop/DataBaseAssignment/DataBase_Sprint1/src/test/java/features"
		,glue={"stepdef"}
		,plugin={"pretty","html:target/html","json:target/report.json"}
		,tags= {"@regression"}
		,monochrome=true,dryRun=false,strict=true
		)

public class TestRunner {
	
	

}
