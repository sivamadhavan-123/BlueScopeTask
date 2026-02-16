package org.example.DTO;

public class User {
    private int id;
    private String name;
    private  int age;
    private String username;
    private String password;
    private String mobile;
    private String role;


    public User() {
    }

    public User(String role ,String password) {
        this.role = role;
        this.password = password;
    }

    public User(String name, int age, String username, String password, String mobile) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        this.role = "USER";
    }

    public String getRole() {
        return role;
    }
    public void setRole( String role){
        this.role=role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
