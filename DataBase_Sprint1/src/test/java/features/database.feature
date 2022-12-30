Feature: Verify HR Schema

Background: 
Given the url "http://databasetesting.s3-website-us-west-2.amazonaws.com"

@regression
Scenario: Verify if the dropdown values are populated correctly from hrschema

When the values are retrieved from the data base
Then it should match with the values on the application

@regression
Scenario: Validate if the data is correctly populated from hr_schema

When the total number of departments present in each city are retrieved from the data base
Then the data should match with the table on the application

@regression
Scenario: Validate the details of the employee who is earning the 3rd highest salary from hrschema

When the details are retrieved from the database
Then the data should be mathed with the application

