package com.gmail.pashasimonpure.app.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.pashasimonpure.app.repository.model.ItemShop;

public interface ItemShopRepository extends GeneralRepository<ItemShop> {

    List<Long> findLinkedItems(Connection connection) throws SQLException;

}