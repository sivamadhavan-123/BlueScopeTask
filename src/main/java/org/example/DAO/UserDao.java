package org.example.DAO;

import org.example.DBConnection.DataBaseCon;
import org.example.DTO.LoginDto;
import org.example.DTO.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public static boolean insert(User user) {

        String sql="insert into user (name,age,username,password,mobile,role)  values (?,?,?,?,?,?)";

        try(
                Connection connection= DataBaseCon.getDataSource().getConnection();
                PreparedStatement statement=connection.prepareStatement(sql)
                ) {
            statement.setString(1,user.getName());
            statement.setInt(2,user.getAge());
            statement.setString(3,user.getUsername());
            statement.setString(4,user.getPassword());
            statement.setString(5,user.getMobile());
            statement.setString(6, user.getRole());
           return statement.executeUpdate() > 0;


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public static LoginDto findByUsername(String username){
        String sql = "select  password,role,name from user where username=? "+"union all "+"select  password,role,name from admin where username=?";
        try (
                Connection connection = DataBaseCon.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, username);
            statement.setString(2, username);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                LoginDto user = new LoginDto();
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setName(rs.getString("name"));
                return user;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static List<User> selectAll(int pageSize, int offset) {

        String sql = "select * from user limit ? offset ?";
        List<User> users = new ArrayList<>();

        try (Connection connection = DataBaseCon.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setMobile(rs.getString("mobile"));
                 users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static int totalRows() {
        String sql = "select count(*) from user";
        int totalRows = 0;
        try (Connection connection = DataBaseCon.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()
        ) {

            if (rs.next()) {
                totalRows = rs.getInt(1);
                return  totalRows;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalRows;
    }



    public static  boolean update(User user){

        return false;
    }



}





