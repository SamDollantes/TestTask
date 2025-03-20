import example.dao.DBUtill;

public class ConsoleUI {
    public static void main(String[] args) {
        DBUtill.createDatabase();
        EmployeeController controller = new EmployeeController();

        if (args.length < 1) {
            System.out.println("Usage:");
            System.out.println("1 → Create database");
            System.out.println("2 \"LastName FirstName MiddleName\" YYYY-MM-DD Gender → Add employee");
            System.out.println("3 → Show all employees");
            System.out.println("4 → Generate employees");
            System.out.println("5 → Find employees by gender and last name prefix");
            return;
        }

        try {
            Integer mode = Integer.parseInt(args[0]);

            switch (mode) {
                case 1:
                    controller.createDB();
                    System.out.println("Database created successfully.");
                    break;
                case 2:
                    if (args.length < 5) {
                        System.out.println("Usage: 2 \"LastName FirstName MiddleName\" YYYY-MM-DD Gender");
                        return;
                    }
                    String fullInput = args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5];
                    controller.createEmployee(fullInput);
                    System.out.println("Employee added.");
                    break;
                case 3:
                    controller.getEmployees();
                    break;
                case 4:
                    controller.generateEmployee();
                    break;
                case 5:
                    controller.findByGenderAndNamePrefix();
                    break;
                default:
                    System.out.println("Invalid mode. Use a number between 1-6.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Mode should be a number.");
        }
    }
}
