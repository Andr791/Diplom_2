package pojo;

public class TestDataLogin {

    private String email;
    private String password;

    public TestDataLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public TestDataLogin() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
