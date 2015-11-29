package services;

import models.AdminUser;
import play.data.validation.ValidationError;
import play.i18n.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles AdminUsers form submission and validation and relates submitted data to the model
 */
public class AdminUserFormData {

    protected Long id;
    protected String name;
    protected String lastName;
    protected String email;
    protected String password;
    protected String token;
    protected Integer mode;

    protected AdminUser adminUser = new AdminUser();

    protected AdminUserService adminUserService = new AdminUserService();

    /**
     * Creates a new AdminUserFormData instance for admin user create action
     */
    public AdminUserFormData() {
        this.token = this.adminUser.generateToken();
    }

    /**
     * Created a new AdminUserFormData instance for admin user editing action
     *
     * @param adminUser AdminUser model object
     */
    public AdminUserFormData(AdminUser adminUser) {
        this.id = adminUser.getId();
        this.name = adminUser.getName();
        this.lastName = adminUser.getLastName();
        this.email = adminUser.getEmail();
        this.password = adminUser.getPassword();
        this.token = adminUser.getToken();
    }

    /**
     * Server side validation method
     *
     * @return List<ValidationError>
     */
    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if (name == null || name.length() == 0) {
            errors.add(new ValidationError("name", Messages.get("adminUser.form.validation.name")));
        }

        if (lastName == null || lastName.length() == 0) {
            errors.add(new ValidationError("lastName", Messages.get("adminUser.form.validation.lastName")));
        }

        if (email == null || email.length() == 0) {
            errors.add(new ValidationError("email", Messages.get("adminUser.form.validation.email")));
        }

        if (adminUserService.isAdminUserEmailUsed(email, token)) {
            errors.add(new ValidationError("email", Messages.get("adminUser.form.validation.emailUsed")));
        }

        if (errors.size() > 0)

            return errors;

        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }
}
