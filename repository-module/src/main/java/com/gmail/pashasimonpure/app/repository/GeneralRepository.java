package com.gmail.pashasimonpure.app.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface GeneralRepository<T> {

    T add(Connection connection, T t) throws SQLException;

    void deleteById(Connection connection, Long itemId) throws SQLException;

}