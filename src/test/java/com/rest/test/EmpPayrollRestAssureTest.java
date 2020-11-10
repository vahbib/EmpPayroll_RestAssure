package com.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class EmpPayrollRestAssureTest {

    private int empId;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
        empId = 4;
    }
    // http://localhost:3000/employess/list
    public Response getEmployeeList() {
        Response response = RestAssured.get("/employees/list");
        System.out.println(response.getBody());
        return response;
    }
    @Test
    public void OnCallingGetEmployeeList_ShouldReturnEmployeeList() {
        Response employeeList = getEmployeeList();
        System.out.println("String is "+ employeeList.asString());

        employeeList.then().body("id", Matchers.hasItems(2,3));
        employeeList.then().body("name", Matchers.hasItems("Marsha May", "Mia"));
    }
    @Test
    public  void givenEmployee_OnPostMethod_ShouldReturnAddedEmployee() {
        RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\": \"Modi\", \"salary\":\"10000000000\"}")
                .when()
                .post("/employees/create")
                .then()
                .body("id", Matchers.any(Integer.class))
                .body("name", Matchers.is("Modi"));
    }
}
