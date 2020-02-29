package com.gmail.pashasimonpure.app.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.pashasimonpure.app.repository.model.Shop;

public interface ShopRepository extends GeneralRepository<Shop> {

    List<Shop> findAll(Connection connection) throws SQLException;

}