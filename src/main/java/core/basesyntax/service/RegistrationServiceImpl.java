package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User is null");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new InvalidUserDataException("Invalid login");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidUserDataException("Password invalid");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidUserDataException("Age invalid");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("Such login already exist");
        }
        return storageDao.add(user);
    }
}
