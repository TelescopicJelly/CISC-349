// Imports the ability to make array/list and the ability for user input 
import java.util.ArrayList;
import java.util.Scanner;

// All employees at company
class Employee_management {
    public String fullName;
    public String jobTitle;
    public String SSN;

    public Employee_management(String fullName, String jobTitle, String SSN) {
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.SSN = SSN;
    }


// General base case for other classes to put their own information about their employees
    public void viewEmployee() {
        System.out.println("Full Name: " + fullName);
        System.out.println("Job Title: " + jobTitle);
        System.out.println("SSN: " + SSN);
    }
}


// Full-time employees (A full-time salaried employee is required to work 45 hours a week and is paid an annual salary for this.)
class Full_employee extends Employee_management {
    private int hoursworkedweekly = 45;
    public Full_employee(String fullName, String jobTitle, String SSN) {
        super(fullName, jobTitle, SSN);
    }
    public void employee() {
        super.viewEmployee();
        System.out.println("Full-time employees:");
        System.out.println("Number of hours worked: " + hoursworkedweekly);
    }
}


// Part-time employees (A part-time salaried employee is assigned a number of hours between 20 and 40 that they must work each week, and is paid an annual salary for this.)
class Part_employee extends Employee_management {
    private int hoursworkedweekly;

    public Part_employee(String fullName, String jobTitle, String SSN, int hoursworkedweekly) {
        super(fullName, jobTitle, SSN);
        this.hoursworkedweekly = hoursworkedweekly;

    }
    public void employee() {
        super.viewEmployee();
        System.out.println("Part-time employees:");
        System.out.println("Number of hours worked: " + hoursworkedweekly);
    }
}


// Contractor employees (Contract employees are not required to work any specific number of hours, and are paid in an hourly fashion.)
class Contractor extends Employee_management {
    private int hoursworkedweekly;
    public Contractor(String fullName, String jobTitle, String SSN, int hoursworkedweekly) {
        super(fullName, jobTitle, SSN); 
        this.hoursworkedweekly = hoursworkedweekly;
    }
    public void employee() {
        super.viewEmployee();
        System.out.println("Contractor employees:");
        System.out.println("Number of hours worked " + hoursworkedweekly);
    }
}


class Employee_Data_Management {
    // An array/list for all of the employees
    public ArrayList<Employee_management> allEmployeesList = new ArrayList<Employee_management>();
    // Grants user the ability to input new employees
    public Scanner userinput = new Scanner(System.in);

    public void addEmployee(Employee_management employee) {
        // If the userput is yes to the question, add the employee to the full employee list
        System.out.println("Do you want to add an employee?");
        String userInput = userinput.nextLine();
        if (userInput.equals("Yes")) {
            allEmployeesList.add(employee);
            System.out.println("New employee added");
        } else if (userInput.equals("No")) {
            System.out.println("New employee NOT added");
        }
    }
        
    public void deleteEmployee(int index) {
        // If the index chosen to delete is greater than or equal to zero and the index is less than the size of the entire list, ask them the question
        if (index >= 0 && index < allEmployeesList.size()) {
            System.out.println("Do you want to delete an employee?");
            String userInput = userinput.nextLine();

            // If the userput is yes to the question, delete the employee to the full employee list
            if (userInput.equals("Yes")) {
                allEmployeesList.remove(index);
                System.out.println("Employee has been deleted");
            } else if (userInput.equals("No")) {
                System.out.println("Employee NOT deleted");
            }

        } else {
            System.out.println("Can not delete employee");
        }
    }

    public static void main(String[] args) {
        Employee_Data_Management Data_Management = new Employee_Data_Management();
        // Testing
        Data_Management.addEmployee(new Full_employee("Samari Oglesby", "Software Engineer", "123-45-6789"));
        Data_Management.addEmployee(new Part_employee("Justin Tucker", "Designer", "987-65-4321", 30));
        Data_Management.addEmployee(new Contractor("Mike Johnson", "Project Manager", "345-67-8901", 30 ));
        
        Data_Management.deleteEmployee(1);

    }
}


