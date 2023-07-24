package org.example.cayenne.persistent.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.property.EntityProperty;
import org.apache.cayenne.exp.property.PropertyFactory;
import org.apache.cayenne.exp.property.StringProperty;
import org.example.cayenne.persistent.Department;

/**
 * Class _Employee was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Employee extends BaseDataObject {

    private static final long serialVersionUID = 1L;

    public static final String ID_PK_COLUMN = "ID";

    public static final StringProperty<String> EMAIL = PropertyFactory.createString("email", String.class);
    public static final StringProperty<String> NAME = PropertyFactory.createString("name", String.class);
    public static final EntityProperty<Department> EMPLOYEES = PropertyFactory.createEntity("employees", Department.class);

    protected String email;
    protected String name;

    protected Object employees;

    public void setEmail(String email) {
        beforePropertyWrite("email", this.email, email);
        this.email = email;
    }

    public String getEmail() {
        beforePropertyRead("email");
        return this.email;
    }

    public void setName(String name) {
        beforePropertyWrite("name", this.name, name);
        this.name = name;
    }

    public String getName() {
        beforePropertyRead("name");
        return this.name;
    }

    public void setEmployees(Department employees) {
        setToOneTarget("employees", employees, true);
    }

    public Department getEmployees() {
        return (Department)readProperty("employees");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "email":
                return this.email;
            case "name":
                return this.name;
            case "employees":
                return this.employees;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "email":
                this.email = (String)val;
                break;
            case "name":
                this.name = (String)val;
                break;
            case "employees":
                this.employees = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.email);
        out.writeObject(this.name);
        out.writeObject(this.employees);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.email = (String)in.readObject();
        this.name = (String)in.readObject();
        this.employees = in.readObject();
    }

}