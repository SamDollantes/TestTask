import example.model.Employee;
import example.model.Gender;
import example.services.EmployeeService;

import java.time.LocalDate;
import java.util.List;

public class EmployeeController {

    public EmployeeController() {}

    private final EmployeeService employeeService = new EmployeeService();

    public void createDB(){
        try {
            employeeService.createTable();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createEmployee(String fullInput){
        String[] parts = fullInput.split(" ");

        if (parts.length < 5) {
            System.out.println("Invalid input format. Expected: 'LastName FirstName MiddleName YYYY-MM-DD Gender'");
            return;
        }

        String lastName = parts[0];
        String firstName = parts[1];
        String middleName = parts[2];

        LocalDate birthDate = LocalDate.parse(parts[3]);

        Gender gender = Gender.valueOf(parts[4]);

        Employee employee =  new Employee(firstName, lastName, middleName, birthDate, gender);
        try {
            employeeService.saveEmployee(employee);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getEmployees(){
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            for (Employee employee : employees) {
                System.out.println(employee.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateEmployee(){
        try {
            employeeService.generateEmployees();
            System.out.println("Employee generated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findByGenderAndNamePrefix(){
        try {
            Long startTime = System.nanoTime();
            List<Employee> res = employeeService.findByGenderAndNamePrefix();
            Long endTime = System.nanoTime();
            Long executionTime = (endTime - startTime) / 1_000_000;
            for (Employee employee : res) {
                System.out.println(employee.toString());
            }
            System.out.println("Execution time" + executionTime);
            createIndex();
            startTime = System.nanoTime();
            employeeService.findByGenderAndNamePrefix();
            endTime = System.nanoTime();
            executionTime = (endTime - startTime) / 1_000_000;
            System.out.println("Execution time after indexing" + executionTime);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void createIndex(){
        try {
            employeeService.Indexing();
            System.out.println("Employee indexed");
            findByGenderAndNamePrefix();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
