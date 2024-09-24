package com.example.mytaxapp;



import android.content.Context;
import androidx.room.Room;

import java.util.List;
import java.util.function.Consumer;

public class UserService {
    private static UserService instance;
    private final UserDao userDao;

    private UserService(Context context) {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "userdb").build();
        userDao = db.userDao();
    }

    public static synchronized UserService getInstance(Context context) {
        if(instance == null) {
            instance = new UserService(context);
        }
        return instance;
    }

    public interface OperationCallback {
        void onOperationCompleted();
        void onError(Exception e);
    }

    public void insertUser(User user, OperationCallback callback) {
        new Thread(() -> {
            try {
                userDao.insert(user);
                if(callback != null) {
                    callback.onOperationCompleted();
                }
            } catch (Exception e) {
                if(callback != null) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void updateUser(User user, OperationCallback callback) {
        new Thread(() -> {
            try {
                userDao.update(user);
                if(callback != null) {
                    callback.onOperationCompleted();
                }
            } catch (Exception e) {
                if(callback != null) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void deleteUser(User user, OperationCallback callback) {
        new Thread(() -> {
            try {
                userDao.delete(user);
                if(callback != null) {
                    callback.onOperationCompleted();
                }
            } catch (Exception e) {
                if(callback != null) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void fetchAllUsers(Consumer<List<User>> onResult) {
        new Thread(() -> {
            try {
                List<User> users = userDao.getAll();
                if(onResult != null) {
                    onResult.accept(users);
                }
            } catch (Exception e) {

            }
        }).start();
    }

    public void fetchAllCustomers(Consumer<List<User>> onResult) {
        new Thread(() -> {
            try {
                List<User> users = userDao.getAllCustomers();
                if(onResult != null) {
                    onResult.accept(users);
                }
            } catch (Exception e) {

            }
        }).start();
    }


    public void handleRegistration(String username, UserRegistrationCallback callback) {
        new Thread(() -> {
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    callback.onResult(true);
                    return;
                }
            }
            callback.onResult(false);
        }).start();
    }

    public void logout(OperationCallback callback) {
        new Thread(() -> {
            try {
//                userDao.clearSession();
                if (callback != null) {
                    callback.onOperationCompleted();
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        }).start();
    }

}

