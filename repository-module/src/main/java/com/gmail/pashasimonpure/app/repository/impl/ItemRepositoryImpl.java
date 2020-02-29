package com.gmail.pashasimonpure.app.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.pashasimonpure.app.repository.ItemRepository;
import com.gmail.pashasimonpure.app.repository.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    @Override
    public Item add(Connection connection, Item item) throws SQLException {

        String sql = "INSERT INTO item (name, description) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating item failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    item.setId(id);
                    return item;
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
        }

    }

    @Override
    public List<Item> findAll(Connection connection) throws SQLException {

        String sql = "SELECT i.id, i.name, i.description, d.price FROM item i JOIN item_details d ON ( i.id = d.item_id )";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Item> items = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();

                    item.setId(rs.getLong("id"));
                    item.setName(rs.getString("name"));
                    item.setDescription(rs.getString("description"));
                    item.setPrice(rs.getDouble("price"));

                    items.add(item);
                }
                return items;
            }
        }

    }

    @Override
    public void deleteById(Connection connection, Long itemId) throws SQLException {

        String sql = "DELETE FROM item WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, itemId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting item failed, no rows affected.");
            }
        }
    }

}