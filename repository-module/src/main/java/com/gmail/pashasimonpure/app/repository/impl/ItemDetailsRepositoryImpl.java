package com.gmail.pashasimonpure.app.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gmail.pashasimonpure.app.repository.ItemDetailsRepository;
import com.gmail.pashasimonpure.app.repository.model.ItemDetail;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDetailsRepositoryImpl implements ItemDetailsRepository {

    @Override
    public ItemDetail add(Connection connection, ItemDetail itemDetail) throws SQLException {

        String sql = "INSERT INTO item_details (item_id, price) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, itemDetail.getItemId());
            statement.setDouble(2, itemDetail.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating item details failed, no rows affected.");
            }
            return itemDetail;
        }
    }

    @Override
    public void deleteById(Connection connection, Long itemId) throws SQLException {

        String sql = "DELETE FROM item_details WHERE item_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, itemId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting item details failed, no rows affected.");
            }
        }
    }

}
