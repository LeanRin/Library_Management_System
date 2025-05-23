public class User {
    private String name;//用户名
    private String password;//密码
    private String role;//权限

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}