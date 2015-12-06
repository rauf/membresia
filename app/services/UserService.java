package services;

import models.SelectOptionItem;
import models.User;
import services.contract.UserServiceInterface;
import services.formData.MailMessageFormData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Middleware class for controller model interaction and other member related business logic
 */
public class UserService implements UserServiceInterface {

    /**
     * {@inheritDoc}
     */
    public User getUser(String token) {

        return getModel().getUserByToken(token);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isUserEmailUsed(String email, String token) {

        return getModel().getUserEmailCount(email, token) > 0;
    }

    public Map<SelectOptionItem, Boolean> makeUserMap(MailMessageFormData messageFormData) {
        List<User> allUsers = getModel().getUserRawList();
        Map<SelectOptionItem, Boolean> userMap = new HashMap<SelectOptionItem, Boolean>();
        for (User user : allUsers) {
            SelectOptionItem selectOptionItem = new SelectOptionItem(user.toString()+" <"+user.getEmail()+">", user.getToken());
            userMap.put(selectOptionItem, (messageFormData != null && messageFormData.getRecipients().contains(user.getToken())));
        }
        return userMap;
    }

    /**
     * Creates User model object
     *
     * @return User
     */
    private User getModel() {

        return new User();
    }
}
