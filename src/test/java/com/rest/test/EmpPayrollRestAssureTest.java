package com.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

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
    // UC1
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
    // UC2
    @Test
    public void CheckMultiple_Post_Method_Threads() {
        String[] name = {"Abhinav", "Sumit", "Abhishek"};
        String[] salary = {"1000", "2000", "3000"};

        for (int i = 0; i < 3; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", name[i]);
            map.put("salary", salary[i]);
            Runnable task = () -> {
                RestAssured.given().contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(map)
                        .when().post("/employees/create");
            };
            Thread t = new Thread(task);
            t.start();
            try {
                t.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //UC3
    @Test
    public void givenEmployee_WhenUpdate_ShouldReturnUpdatedEmployee() {
        RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\": \"xyz\", \"salary\":\"550000\"}")
                .when()
                .put("/employees/update/" +empId)
                .then()
                .body("id", Matchers.any(Integer.class))
                .body("name", Matchers.is("xyz"))
                .body("salary", Matchers.is("550000"));
    }
    // UC4
    @Test
    public void ListEmployee() {
        Response employeeList = getEmployeeList();
        System.out.println("string is " + employeeList.asString());
    }
    // UC5
    @Test
    public void givenEmployee_WhenDelete_ShouldDeleteGivenEmployee() {

        int id = 4;
        Response response = RestAssured.delete("/employees/delete/" + id);
        Assert.assertEquals(200, response.getStatusCode());
    }
}
