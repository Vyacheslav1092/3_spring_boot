package ru.agapov.changeToBoot.dao;


import ru.agapov.changeToBoot.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    void createUser(User user);

    void updateUser(User user);

    User readUser(long id);

    User deleteUser(long id);
}
