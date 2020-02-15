package cn.itcast.haoke.dubbo.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("graphql")
@Controller
@CrossOrigin
public class GraphQLController {

    @Autowired
    private GraphQL graphQL;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * 实现GraphQL查询
     *
     * @param query
     * @return
     */
    @GetMapping
    @ResponseBody
    public Map<String, Object> query(@RequestParam("query") String query,
                                     @RequestParam(value = "variables", required = false) String variablesJson,
                                     @RequestParam(value = "operationName", required = false) String operationName) {
        try {
            Map<String, Object> variables = MAPPER.readValue(variablesJson, MAPPER.getTypeFactory()
                    .constructMapType(HashMap.class, String.class, Object.class));

            return this.executeQuery(query, operationName, variables);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> error = new HashMap<>();
        error.put("status", 500);
        error.put("msg", "查询出错");
        return error;
    }

    @PostMapping
    @ResponseBody
    public Map<String, Object> postQuery(@RequestBody Map<String, Object> param) {

        try {
            String query = (String) param.get("query");
            Map variables = (Map) param.get("variables");
            String operationName = (String) param.get("operationName");

            return this.executeQuery(query, operationName, variables);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> error = new HashMap<>();
        error.put("status", 500);
        error.put("msg", "查询出错");
        return error;
    }

    private Map<String, Object> executeQuery(String query, String operationName, Map<String, Object> variables){
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .operationName(operationName)
                .variables(variables)
                .build();
        return this.graphQL.execute(executionInput).toSpecification();
    }

}
