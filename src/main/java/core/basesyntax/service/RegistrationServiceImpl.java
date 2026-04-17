package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_LOGIN_LENGTH = 6;
    static final int MIN_PASSWORD_LENGTH = 6;
    static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new InvalidUserDataException("User cannot be null");
        }

        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login cannot be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserDataException("Login '" + user.getLogin()
                    + "' is too short. Minimum length allowed: " + MIN_LOGIN_LENGTH);
        }

        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password cannot be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Password is too short. Minimum length allowed: "
                    + MIN_PASSWORD_LENGTH);
        }

        if (user.getAge() == null) {
            throw new InvalidUserDataException("Age cannot be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("Age " + user.getAge()
                    + " is not allowed. Minimum age required: " + MIN_AGE);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("User with login '" + user.getLogin()
                    + "' already exists in the system");
        }

        return storageDao.add(user);
    }
}
