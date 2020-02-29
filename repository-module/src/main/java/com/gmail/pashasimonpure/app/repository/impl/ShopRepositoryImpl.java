package com.gmail.pashasimonpure.app.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.pashasimonpure.app.repository.ShopRepository;
import com.gmail.pashasimonpure.app.repository.model.Shop;
import org.springframework.stereotype.Repository;

@Repository
public class ShopRepositoryImpl implements ShopRepository {

    @Override
    public Shop add(Connection connection, Shop shop) throws SQLException {

        String sql = "INSERT INTO shop (name, location) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, shop.getName());
            statement.setString(2, shop.getLocation());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating shop failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    shop.setId(id);
                    return shop;
                } else {
                    throw new SQLException("Creating shop failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<Shop> findAll(Connection connection) throws SQLException {

        String sql = "SELECT id, name, location FROM shop";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Shop> shops = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    Shop shop = new Shop();

                    shop.setId(rs.getLong("id"));
                    shop.setName(rs.getString("name"));
                    shop.setLocation(rs.getString("location"));

                    shops.add(shop);
                }
                return shops;
            }
        }

    }

    @Override
    public void deleteById(Connection connection, Long itemId) {
        throw new UnsupportedOperationException("Deleting action for shop is not implemented.");
    }

}
