package org.example.DAO;

import org.example.DBConnection.DataBaseCon;
import org.example.DTO.LoginDto;
import org.example.DTO.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {



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



    public static boolean insert(User user) {

        String sql="insert into user (name,age,username,password,mobile)  values (?,?,?,?,?)";

        try(
                Connection connection= DataBaseCon.getDataSource().getConnection();
                PreparedStatement statement=connection.prepareStatement(sql)
                ) {
            statement.setString(1,user.getName());
            statement.setInt(2,user.getAge());
            statement.setString(3,user.getUsername());
            statement.setString(4,user.getPassword());
            statement.setString(5,user.getMobile());
           return statement.executeUpdate() > 0;


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


    public static boolean updateUserDetail(String SessionUser, User user){

        String sql = "update user set name=?,age=?,username=?,password=?,mobile=? where username=?";

        try(
                Connection connection = DataBaseCon.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)

                ){
            statement.setString(1,user.getName());
            statement.setInt(2,user.getAge());
            statement.setString(3,user.getUsername());
            statement.setString(4,user.getPassword());
            statement.setString(5,user.getMobile());
            statement.setString(6,SessionUser);
            int rs = statement.executeUpdate();

            return rs >0;

        }catch (SQLException e){

            throw new RuntimeException(e);
        }


        
    }

    public static String existingUserCheck(String username,String mobile) {
        String sql = "select 1 from user where username = ? or mobile = ?";

        try (Connection connection = DataBaseCon.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, username);
            statement.setString(2, mobile);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
             return  "Username or mobile number is  exist";

            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static String existingUser(String username, String mobile) {
        String sql = "select 1 from user where (username = ? or mobile = ?) and username !=?";

        try (Connection connection = DataBaseCon.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, username);
            statement.setString(2, mobile);
            statement.setString(3, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return  "Username or mobile number is  exist";

            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean deleteUser(String sessionUsername) {

        try(Connection connection=DataBaseCon.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from user where username=?")
        ) {
            statement.setString(1, sessionUsername);

            int rs = statement.executeUpdate();
            if(rs>0){
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}





