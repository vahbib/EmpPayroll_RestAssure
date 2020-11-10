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
}
