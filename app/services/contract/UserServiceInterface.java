package services.contract;

import models.User;

public interface UserServiceInterface {

    User getUser(String token);

    boolean isUserEmailUsed(String email, String token);
}
