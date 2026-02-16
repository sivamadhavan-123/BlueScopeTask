package org.example.DAO;

import org.example.DBCon.DataBaseCon;
import org.example.DTO.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public static boolean insert(User user) {

        String sql="insert into user (name,age,username,password,mobile,role)  values (?,?,?,?,?,?)";

        try(
                Connection connection= DataBaseCon.getDataSource().getConnection();
                PreparedStatement statement=connection.prepareStatement(sql);
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


    public static User login(String username){

        String sql = "select  password,role,name from user where username=?";
        try (
                Connection connection = DataBaseCon.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
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
}
