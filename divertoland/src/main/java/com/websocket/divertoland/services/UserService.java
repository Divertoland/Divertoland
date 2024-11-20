package com.websocket.divertoland.services;

import com.websocket.divertoland.utils.DatabaseUtil;
import com.websocket.divertoland.domain.Role;
import com.websocket.divertoland.domain.User;
import com.websocket.divertoland.domain.Visitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public void createVisitorAccount(User user) {
        String sql = "insert into user (name,email,password,role) values (?,?,?,?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, Role.VISITOR.name());
            stmt.executeUpdate();
            System.out.println("Usuário criado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User login(String inputName, String inputPassword) {
        String sql = "Select * from user where email = ? and password = ?";
        User user = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, inputName);
            stmt.setString(2, inputPassword);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String role = rs.getString("role");

                    user = new User(id, name, email, Role.valueOf(role));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
