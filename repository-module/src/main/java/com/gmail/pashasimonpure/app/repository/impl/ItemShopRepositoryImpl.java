package com.gmail.pashasimonpure.app.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gmail.pashasimonpure.app.repository.ItemShopRepository;
import com.gmail.pashasimonpure.app.repository.model.ItemShop;
import org.springframework.stereotype.Repository;

@Repository
public class ItemShopRepositoryImpl implements ItemShopRepository {

    @Override
    public ItemShop add(Connection connection, ItemShop itemShop) throws SQLException {

        String sql = "INSERT INTO item_shop (item_id, shop_id) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, itemShop.getItemId());
            statement.setLong(2, itemShop.getShopId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("CREATING ITEM-SHOP LINK FAILED, NO ROWS AFFECTED.");
            }

            return itemShop;
        }

    }

    @Override
    public List<Long> findLinkedItems(Connection connection) throws SQLException {

        String sql = "SELECT DISTINCT item_id FROM item_shop";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Long> itemsId = new ArrayList<>();
                while (resultSet.next()) {
                    Long itemId = resultSet.getLong("item_id");
                    itemsId.add(itemId);
                }
                return itemsId;
            }
        }
    }

    @Override
    public void deleteById(Connection connection, Long itemId) throws SQLException {

        String sql = "DELETE FROM item_shop WHERE item_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, itemId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting item-shop link failed, no rows affected.");
            }
        }
    }

}