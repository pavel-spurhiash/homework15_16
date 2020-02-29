package com.gmail.pashasimonpure.app.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.pashasimonpure.app.repository.model.Item;

public interface ItemRepository extends GeneralRepository<Item> {

    List<Item> findAll(Connection connection) throws SQLException;

}