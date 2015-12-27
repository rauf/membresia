package views.formData;

import models.SelectOptionItem;
import models.User;
import views.formData.MailMessageFormData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that handles form events and data feeds from user model
 */
public class UserFormData {

    /**
     * Generates a list of users to select from when choosing a mail recipient
     *
     * @param messageFormData Message form object to associate selection to
     * @return Map
     */
    public Map<SelectOptionItem, Boolean> makeUserMap(MailMessageFormData messageFormData) {
        User user = new User();
        List<User> allUsers = user.getAll();
        Map<SelectOptionItem, Boolean> userMap = new HashMap<>();
        for (User userItem : allUsers) {
            SelectOptionItem selectOptionItem = new SelectOptionItem(userItem.toString() + " <" + userItem.getEmail() + ">", userItem.getToken());
            userMap.put(selectOptionItem, (messageFormData != null && messageFormData.getRecipients().contains(userItem.getToken())));
        }

        return userMap;
    }
}
