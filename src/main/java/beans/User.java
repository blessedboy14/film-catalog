package beans;

import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

public class User {
    private String email;
    private final String uuid;
    private String phoneNumber;
    private String password;

    private String passHash;
    private STATUS userStatus = STATUS.STATUS_DEFAULT;

    private int privilege;

    public User() {
        this.uuid = generateId();
    }

    public User(String email, String password, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.generatePassHash();
        this.phoneNumber = phoneNumber;
        this.uuid = generateId();
        this.privilege = 1;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }
    public int getPrivilege() {
        return this.privilege;
    }

    private void generatePassHash() {
        this.passHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public STATUS getUserStatus() {
        return userStatus;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return this.uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassHash() {
        if (passHash != null) {
            return passHash;
        } else {
            return null;
        }
    }

    public void setPassword(String password) {
        this.password = password;
        this.generatePassHash();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password)
                && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int sec_prime = 7;
        int result = email != null ? email.hashCode() : 0;
        result = prime * result + (password != null ? password.hashCode() : 0);
        result = sec_prime * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("User [").append("name='").append(email).append("' ").append(", phoneNumber='").append(phoneNumber)
                .append("', passHash=").append(passHash).append("' ]");
        return toString.toString();
    }
}
