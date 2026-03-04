package org.example.DAO;

import org.example.DBConnection.DataBaseCon;
import org.example.DTO.LoginDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDao {

    public static LoginDto findByUsername(String username){
        String sql = "select  password,role,name,username from user where username=? "+"union all "+"select  password,role,name,username from admin where username=?";
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
                user.setUsername(rs.getString("username"));
                return user;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
