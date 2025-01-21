package com.adobe.orderapp.api;

import com.adobe.orderapp.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    Employee employee = new Employee();
    public EmployeeController() {
        employee.setId(123);
        employee.setTitle("Sr. Programmer");

        var personal = new HashMap<String, String>();
        personal.put("firstName", "Smitha");
        personal.put("lastName", "Rao");
        personal.put("mobile", "1234567890");

        employee.setPersonal(personal);

        var skills = new ArrayList<String>();
        skills.add("Java");
        skills.add("AWS");

        employee.setSkills(skills);
    }

    // PATCH http://localhost:8080/api/employees/123
    // Content-type: application/json-patch+json
    /*
        [
        {"op": "replace" , "path" : "/title", "value" : "Team Lead"},
        {"op", "remove", "path": "/personal/mobile"},
        {"op": "add", "path": "/personal/email", "value": "smitha@adobe.com"},
        {"op" : "add", "path" : "/skills/1", "value": "Spring Boot" }
        ]
     */


    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public Employee updateEmployee(@PathVariable("id") int id, @RequestBody JsonPatch patch) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        //mapper.writeValueAsString(employee) Employee to JSON
        // patch.apply --> apply changes to the JSON and create a JSON Node
        var target = patch.apply(mapper.readTree(mapper.writeValueAsString(employee)));
        // convert JsonNode back to Employee JSON
        return mapper.treeToValue(target, Employee.class);
    }
}
