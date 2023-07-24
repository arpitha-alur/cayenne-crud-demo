package org.example.cayenne;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.ObjectSelect;
import org.example.cayenne.persistent.Department;
import org.example.cayenne.persistent.Employee;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a Cayenne Runtime using the 'cayenne-project.xml' configuration file
        ServerRuntime cayenneRuntime = ServerRuntime.builder()
                .addConfig("cayenne-project.xml")
                .build();

        // Obtain ObjectContext to interact with the database
        ObjectContext context = cayenneRuntime.newContext();

        // Create
        // Create a new Department
        Department department = context.newObject(Department.class);
        department.setName("Engineering");
        department.setLocation("New York");

        // Create new Employees
        Employee employee1 = context.newObject(Employee.class);
        employee1.setName("John Doe");
        employee1.setEmail("john.doe@example.com");

        Employee employee2 = context.newObject(Employee.class);
        employee2.setName("Jane Smith");
        employee2.setEmail("jane.smith@example.com");

        // Establish the one-to-many relationship
        department.addToDepartmentemployee(employee1);
        department.addToDepartmentemployee(employee2);

        // Save the objects to the database
        context.registerNewObject(department);
        context.commitChanges();

        // Read
        // Fetch Department by Name
        //Fetch all department

        List<Department> dept1 = ObjectSelect.query(Department.class).select(context);

        Department fetchedDepartment = Cayenne.objectForPK(context, Department.class, department.getName());
        System.out.println("Fetched Department: " + fetchedDepartment.getName() + ", Location: " + fetchedDepartment.getLocation());

        // Fetch Employees associated with the Department
        for (Employee employee : fetchedDepartment.getDepartmentemployee()) {
            System.out.println("Employee: " + employee.getName() + ", Email: " + employee.getEmail());
        }

        // Update
        // Change Department name and update
        fetchedDepartment.setName("Research");
        context.commitChanges();

        // Delete
        // Delete an Employee
        Employee employeeToDelete = fetchedDepartment.getDepartmentemployee().get(0);
        fetchedDepartment.removeFromDepartmentemployee(employeeToDelete);
        context.deleteObjects(employeeToDelete);
        context.commitChanges();

        // Verify Deletion
        Department updatedDepartment = Cayenne.objectForPK(context, Department.class, department.getObjectId());
        System.out.println("Updated Department: " + updatedDepartment.getName() + ", Location: " + updatedDepartment.getLocation());

        // Close the Cayenne Runtime
        cayenneRuntime.shutdown();
    }
}

