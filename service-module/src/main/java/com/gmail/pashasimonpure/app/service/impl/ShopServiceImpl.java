package com.gmail.pashasimonpure.app.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gmail.pashasimonpure.app.repository.ItemShopRepository;
import com.gmail.pashasimonpure.app.repository.model.ItemShop;
import com.gmail.pashasimonpure.app.service.model.ShopDTO;
import com.gmail.pashasimonpure.app.repository.ConnectionRepository;
import com.gmail.pashasimonpure.app.repository.ShopRepository;
import com.gmail.pashasimonpure.app.repository.model.Shop;
import com.gmail.pashasimonpure.app.service.ShopService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final ConnectionRepository connectionRepository;
    private final ShopRepository shopRepository;
    private final ItemShopRepository itemShopRepository;

    public ShopServiceImpl(ConnectionRepository connectionRepository, ShopRepository shopRepository, ItemShopRepository itemShopRepository) {
        this.connectionRepository = connectionRepository;
        this.shopRepository = shopRepository;
        this.itemShopRepository = itemShopRepository;
    }

    @Override
    public List<ShopDTO> findAll() {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                List<ShopDTO> shopsDTO = new ArrayList<>();
                List<Shop> shops = shopRepository.findAll(connection);

                for (Shop shop : shops) {

                    ShopDTO shopDTO = convert(shop);
                    shopsDTO.add(shopDTO);

                }

                connection.commit();
                return shopsDTO;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return Collections.emptyList();

    }

    @Override
    public void linkItem(Long shopId, Long itemId) {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                ItemShop itemShop = new ItemShop();
                itemShop.setShopId(shopId);
                itemShop.setItemId(itemId);
                itemShopRepository.add(connection, itemShop);
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

    }

    @Override
    public void addShop(ShopDTO shopDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Shop shop = convert(shopDTO);
                shopRepository.add(connection, shop);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Shop convert(ShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.setName(shopDTO.getName());
        shop.setLocation(shopDTO.getLocation());
        return shop;
    }

    private ShopDTO convert(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(shop.getId());
        shopDTO.setName(shop.getName());
        shopDTO.setLocation(shop.getLocation());
        return shopDTO;
    }

}