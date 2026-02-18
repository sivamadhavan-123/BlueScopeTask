package org.example.Service;

import org.example.DAO.UserDao;
import org.example.DTO.LoginDto;
import org.example.DTO.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;


public class ServiceLayer {

    public static LoginDto login(String username,String password){


        LoginDto user= UserDao.findByUsername(username);

        if(user==null){
            return  null;
        }
        if(BCrypt.checkpw(password,user.getPassword())){

            return  user;
        }
        return  null;
    }


    public static List<User> pagenation(int pageSize, int pageNumber){
        int offset = (pageNumber - 1) * pageSize;
        List<User> user=UserDao.selectAll(pageSize,offset);
        return user;

    }


}
