import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDB {
    public List<User> db = new ArrayList<User>();
    private Hasher hasher = new HasherImpl();

//    TODO: add some comments, man!!!!
    public UserDB() {
    }

    public boolean register(String email, String password) {
        boolean isPresent = db.stream().anyMatch(person -> person.getEmail().equals(email));
        if (isPresent) {
            System.out.println("is present line 16");
            return false;
        }
        if (password.isEmpty()) {
            System.out.println("is empty line 20");
            return false;
        }

        String hashedPassword = hasher.hashPassword(password);

        if (hashedPassword != null) {
            User user = new User(email, hashedPassword);
            db.add(user);
            db.forEach(userin -> {
                System.out.println(userin.getEmail());
                System.out.println(userin.getPassword());
            });
            return true;
        }

        return false;
    }

    public int login(String email, String password) {
        Optional<User> userOpt = db.stream().filter(person -> person.getEmail().equals(email)).findFirst();

        if(userOpt.isEmpty()) {
            System.out.println("no user");
            return -1;
        }
        User user = userOpt.get();
        boolean isMatch = hasher.validatePassword(password, user.getPassword());

        if (isMatch) {
            System.out.println("good psw");
            return 1;
        } else {
            System.out.println("bad psw");
            return -2;
        }
    }
}
