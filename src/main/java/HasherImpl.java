import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class HasherImpl implements Hasher {

    Argon2 argon2 = Argon2Factory.create();

    @Override
    public String hashPassword(String password) {
        char[] passwordToHash = password.toCharArray();

        String hash;
        try {
            // Hash password
            hash = argon2.hash(10, 65536, 1, passwordToHash);

        } finally {
            // Wipe confidential data
            argon2.wipeArray(passwordToHash);
        }
        System.out.println(hash);
        return hash;
    }

    @Override
    public boolean validatePassword(String password, String storedPassword) {

        if (argon2.verify(storedPassword, password)) {
            return true;
        }

        return false;

    }
}
