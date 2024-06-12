package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class EmployeeController {
    public String filePath = "D:\\UNI\\Year 4\\SOA\\Assignments\\demo\\src\\Employees.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void createOrLoadFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            JSONArray ja = new JSONArray();
            try {
                objectMapper.writeValue(file, ja);
            } catch (StreamWriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (DatabindException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/read")
    public ModelAndView readJson() throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        ArrayList<Employee> employees = new ArrayList<Employee>();
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        JsonNode jsonArrayNode = objectMapper.readTree(jsonContent);
        if (jsonArrayNode.isArray()) {
            for (JsonNode jsonNode : jsonArrayNode) {
                TypeReference<ArrayList<KnownLanguages>> knownLanguagesType = new TypeReference<>() {
                };
                ArrayList<KnownLanguages> knownLanguages = objectMapper.readValue(
                        jsonNode.get("KnownLanguages").toString(), knownLanguagesType);
                Employee emp = new Employee(jsonNode.get("FirstName").asText(), jsonNode.get("LastName").asText(),
                        jsonNode.get("EmployeeID").asText(), jsonNode.get("Designation").asText(),
                        knownLanguages);
                emp.setFirstName(jsonNode.get("FirstName").asText());
                emp.setLastName(jsonNode.get("LastName").asText());
                emp.setEmployeeID(jsonNode.get("EmployeeID").asText());
                emp.setDesignation(jsonNode.get("Designation").asText());
                emp.setKnownLanguages(knownLanguages);
                employees.add(emp);
            }

        }
        if (!employees.isEmpty()) {
            String result = "Json file is result is: ";
            modelAndView.addObject("result", result);
            modelAndView.addObject("employees", employees);
        } else {
            String result = "Json file is empty! ";
            modelAndView.addObject("result", result);
        }
        modelAndView.setViewName("readResult.html");
        return modelAndView;
    }

    @PostMapping("/search")
    ModelAndView search() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("search.html");
        return modelAndView;
    }

    @PostMapping("/search-employee")
    ModelAndView search(@RequestParam("searchBy") String attributeName, @RequestParam("searchData") String data)
            throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        ArrayList<Employee> employees = new ArrayList<Employee>();
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        JsonNode jsonArrayNode = objectMapper.readTree(jsonContent);
        if (jsonArrayNode.isArray()) {
            for (JsonNode jsonNode : jsonArrayNode) {
                if (jsonNode.get(attributeName).asText().equals(data)) {
                    TypeReference<ArrayList<KnownLanguages>> knownLanguagesType = new TypeReference<>() {
                    };
                    ArrayList<KnownLanguages> knownLanguages = objectMapper.readValue(
                            jsonNode.get("KnownLanguages").toString(), knownLanguagesType);
                    Employee emp = new Employee(jsonNode.get("FirstName").toString(),
                            jsonNode.get("LastName").asText(),
                            jsonNode.get("EmployeeID").asText(),
                            jsonNode.get("Designation").asText(),
                            knownLanguages);
                    emp.setFirstName(jsonNode.get("FirstName").asText());
                    emp.setLastName(jsonNode.get("LastName").asText());
                    emp.setEmployeeID(jsonNode.get("EmployeeID").asText());
                    emp.setDesignation(jsonNode.get("Designation").asText());
                    emp.setKnownLanguages(knownLanguages);
                    employees.add(emp);

                }
            }

        }
        if (!employees.isEmpty()) {
            String result = "Search result : ";
            modelAndView.addObject("result", result);
            modelAndView.addObject("employees", employees);
        } else {
            String result = "No Match Found! ";
            modelAndView.addObject("result", result);
        }
        modelAndView.setViewName("searchResult.html");
        return modelAndView;
    }

    @PostMapping("/delete-employee")
    ModelAndView deleteStudentRecord(@RequestParam("deleteEmployeeID") String id) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        if (id.isEmpty()) {
            String noID = "No ID has been entered!";
            modelAndView.addObject("noID", noID);
            modelAndView.setViewName("deleteEmployee.html");

            return modelAndView;

        }
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        ArrayNode jsonArray = (ArrayNode) objectMapper.readTree(jsonContent);
        for (int i = 0; i < jsonArray.size(); i++) {
            if (jsonArray.get(i).get("EmployeeID").asText().equals(id)) {

                jsonArray.remove(i);
                String updatedJsonArray = jsonArray.toString();
                FileWriter fileWriter = new FileWriter(filePath);
                fileWriter.write(updatedJsonArray);
                fileWriter.close();
                String deleteSucces = "Employee with ID " + id + " has been deleted successfully!";
                modelAndView.addObject("deleteSucces", deleteSucces);

                modelAndView.setViewName("deleteEmployee.html");

                return modelAndView;
            }
        }

        Boolean deleteFail = true;
        modelAndView.addObject("deleteFail", deleteFail);

        modelAndView.setViewName("deleteEmployee.html");
        return modelAndView;

    }

    void updateJson(String updatedJsonArray) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        fileWriter.write(updatedJsonArray);
        fileWriter.close();
    }

    @GetMapping("/get-update-employee-page")
    public ModelAndView getUpdateEmployeePage(@RequestParam("updateEmployeeID") String ID) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        if (ID.isEmpty()) {
            String noIdEntered = "No ID entered!";
            modelAndView.addObject("noIdEntered", noIdEntered);
            modelAndView.setViewName("updateEmployeeAtt.html");
            return modelAndView;
        }
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        JsonNode jsonArrayNode = objectMapper.readTree(jsonContent);
        if (jsonArrayNode.isArray()) {
            for (JsonNode jsonNode : jsonArrayNode) {
                if (jsonNode.get("EmployeeID").asText().equals(ID)) {
                    modelAndView.addObject("ID", ID);
                    modelAndView.setViewName("updateEmployeeAtt.html");
                    return modelAndView;
                }
            }

        }
        String noIdFound = "No ID found!";
        modelAndView.addObject("noIdFound", noIdFound);
        modelAndView.setViewName("updateEmployeeAtt.html");

        return modelAndView;
    }

    @PostMapping("/update-employee-data")
    public ModelAndView updateEmployeeData(@RequestParam("ID") String ID,
            @RequestParam("Designation") String Designation) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        ArrayNode jsonArray = (ArrayNode) objectMapper.readTree(jsonContent);
        for (int i = 0; i < jsonArray.size(); i++) {
            if (jsonArray.get(i).get("EmployeeID").asText().equals(ID)) {

                ObjectNode objectToUpdate = (ObjectNode) jsonArray.get(i);
                objectToUpdate.put("Designation", Designation);
                // String updatedJsonArray = jsonArray.toString();
                FileWriter fileWriter = new FileWriter(filePath);
                fileWriter.write(jsonArray.toPrettyString());
                fileWriter.close();
                String updateSuccess = "Employee with ID: " + ID + " has been updated successfully!";
                modelAndView.addObject("updateSuccess", updateSuccess);
                modelAndView.setViewName("updateResult.html");
                return modelAndView;
            }
        }
        String updateFail = "Something went wrong while updating, please try again.";
        modelAndView.addObject("updateFail", updateFail);

        modelAndView.setViewName("updateResult.html");
        return modelAndView;

    }

    @PostMapping("/add")
    ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add.html");
        return modelAndView;
    }

    @PostMapping("/add-employee")
    public ModelAndView addEmployee(@RequestParam("FirstName") String FirstName,
            @RequestParam("LastName") String LastName,
            @RequestParam("EmployeeID") String EmployeeID,
            @RequestParam("Designation") String Designation,
            @RequestParam("numberOfKnownLanguages") int numberOfKnownLanguages,
            @RequestParam Map<String, String> allParams) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        ArrayList<KnownLanguages> knownLanguages = new ArrayList<KnownLanguages>();
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        ArrayNode jsonArray = (ArrayNode) objectMapper.readTree(jsonContent);
        System.out.println(numberOfKnownLanguages);
        for (int i = 1; i <= numberOfKnownLanguages; i++) {
            String knownLang = allParams.get("knownLanguage" + i);
            int score = Integer.parseInt(allParams.get("score" + i));
            KnownLanguages kl = new KnownLanguages(knownLang, score);
            kl.setLanguageName(knownLang);
            kl.setScore(score);
            knownLanguages.add(kl);
        }
        Employee emp = new Employee(FirstName, LastName, EmployeeID, Designation, knownLanguages);
        ObjectNode empJsonNode = objectMapper.createObjectNode();
        empJsonNode.put("FirstName", emp.getFirstName());
        empJsonNode.put("LastName", emp.getLastName());
        empJsonNode.put("EmployeeID", emp.getEmployeeID());
        empJsonNode.put("Designation", emp.getDesignation());

        ArrayNode knownLanguagesArray = objectMapper.createArrayNode();
        for (KnownLanguages kl : emp.getKnownLanguages()) {
            ObjectNode knownLangNode = objectMapper.createObjectNode();
            knownLangNode.put("LanguageName", kl.getLanguageName());
            knownLangNode.put("ScoreOutof100", kl.getScore());
            knownLanguagesArray.add(knownLangNode);
        }
        empJsonNode.set("KnownLanguages", knownLanguagesArray);

        jsonArray.add(empJsonNode);
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(jsonArray.toPrettyString());
        fileWriter.close();
        String successMessage = "New employee added successfully!";
        modelAndView.addObject("successMessage", successMessage);
        modelAndView.setViewName("addSuccessfully.html");
        return modelAndView;
    }

    @PostMapping("/retreive")
    ModelAndView retreive() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("retreiveEmployee.html");
        return modelAndView;
    }

    @PostMapping("/retreive-employee")
    ModelAndView retreiveEmployee(@RequestParam("language") String language,
            @RequestParam("operation") String operation, @RequestParam("score") String score,
            @RequestParam("sortType") String sortType)
            throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        ArrayList<Employee> employees = new ArrayList<>();
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        JsonNode jsonArrayNode = objectMapper.readTree(jsonContent);
        if (jsonArrayNode.isArray()) {
            for (JsonNode jsonNode : jsonArrayNode) {
                TypeReference<ArrayList<KnownLanguages>> knownLanguagesType = new TypeReference<>() {
                };
                ArrayList<KnownLanguages> knownLanguages = objectMapper.readValue(
                        jsonNode.get("KnownLanguages").toString(), knownLanguagesType);
                Employee emp = new Employee(jsonNode.get("FirstName").asText(),
                        jsonNode.get("LastName").asText(),
                        jsonNode.get("EmployeeID").asText(),
                        jsonNode.get("Designation").asText(),
                        knownLanguages);
                emp.setFirstName(jsonNode.get("FirstName").asText());
                emp.setLastName(jsonNode.get("LastName").asText());
                emp.setEmployeeID(jsonNode.get("EmployeeID").asText());
                emp.setDesignation(jsonNode.get("Designation").asText());
                emp.setKnownLanguages(knownLanguages);
                for (KnownLanguages kl : emp.getKnownLanguages()) {
                    if (kl.getLanguageName().equals(language)) {
                        if (operation.equals("Greater")) {
                            if (kl.getScore() > Integer.parseInt(score)) {
                                employees.add(emp);
                            }
                        } else if (operation.equals("Less")) {
                            if (kl.getScore() < Integer.parseInt(score)) {
                                employees.add(emp);
                            }
                        } else {
                            if (kl.getScore() == Integer.parseInt(score)) {
                                employees.add(emp);
                            }
                        }
                    }
                }
            }
        }
        if (!employees.isEmpty()) {
            if (sortType.equals("Ascending")) {
                Comparator<Employee> javaScoreComparator = Comparator
                        .comparingInt(employee -> employee.getKnownLanguages().stream()
                                .filter(lang -> lang.getLanguageName().equalsIgnoreCase(language))
                                .mapToInt(KnownLanguages::getScore)
                                .findFirst()
                                .orElse(0));
                employees.sort(javaScoreComparator);
            } else {
                Comparator<Employee> javaScoreComparator = Comparator
                        .comparingInt(employee -> employee.getKnownLanguages().stream()
                                .filter(lang -> lang.getLanguageName().equalsIgnoreCase(language))
                                .mapToInt(KnownLanguages::getScore)
                                .findFirst()
                                .orElse(0));
                employees.sort(javaScoreComparator.reversed());
            }
            String result = "Retreive result : ";
            modelAndView.addObject("result", result);
            modelAndView.addObject("employees", employees);
        } else {
            String result = "No Match Found! ";
            modelAndView.addObject("result", result);
        }
        modelAndView.setViewName("retreiveResult.html");
        return modelAndView;

    }
}