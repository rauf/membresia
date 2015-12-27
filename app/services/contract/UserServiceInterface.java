package services.contract;

import models.User;

public interface UserServiceInterface {

    /**
     * Gets a user by a specific field and string value
     *
     * @param key   Field name to search at
     * @param value Value to look for
     * @return User
     */
    User getUser(String key, String value);

    /**
     * Send password recovery email with new password to user
     *
     * @param user User to send mail to
     */
    void sendPasswordRecoveryEmail(User user);

    /**
     * Authenticates user email and password against database for login purposes
     *
     * @param email    User email
     * @param password User raw password
     * @return User
     */
    User authenticate(String email, String password);

    /**
     * Change user password for a new randomly generated one
     *
     * @param email User email to change password for
     * @return User
     */
    User setPassword(String email);

    /**
     * Unset user authentication session
     */
    void logout();

    /**
     * Generates a random password
     *
     * @return String
     */
    String generatePassword();

    /**
     * Encrypts a clear text password
     *
     * @param clearString Password to encrypt
     * @return String
     */
    String encryptPassword(String clearString);

    /**
     * Checks if a clear text password is the same as privided encrypted password
     *
     * @param candidate         Clear text password
     * @param encryptedPassword Encrypted password
     * @return boolean
     */
    boolean checkPassword(String candidate, String encryptedPassword);

}
