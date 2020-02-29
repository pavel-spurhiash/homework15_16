package com.gmail.pashasimonpure.app.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gmail.pashasimonpure.app.service.model.ItemDTO;
import com.gmail.pashasimonpure.app.repository.ConnectionRepository;
import com.gmail.pashasimonpure.app.repository.ItemDetailsRepository;
import com.gmail.pashasimonpure.app.repository.ItemRepository;
import com.gmail.pashasimonpure.app.repository.ItemShopRepository;
import com.gmail.pashasimonpure.app.repository.model.Item;
import com.gmail.pashasimonpure.app.repository.model.ItemDetail;
import com.gmail.pashasimonpure.app.service.ItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final ConnectionRepository connectionRepository;
    private final ItemRepository itemRepository;
    private final ItemDetailsRepository itemDetailsRepository;
    private final ItemShopRepository itemShopRepository;

    public ItemServiceImpl(
            ConnectionRepository connectionRepository,
            ItemRepository itemRepository,
            ItemDetailsRepository itemDetailsRepository,
            ItemShopRepository itemShopRepository) {
        this.connectionRepository = connectionRepository;
        this.itemRepository = itemRepository;
        this.itemDetailsRepository = itemDetailsRepository;
        this.itemShopRepository = itemShopRepository;
    }

    @Override
    public List<ItemDTO> findAll(){

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                List<ItemDTO> itemsDTO = new ArrayList<>();
                List<Item> items = itemRepository.findAll(connection);

                for (Item item : items) {

                    ItemDTO itemDTO = convert(item);
                    itemsDTO.add(itemDTO);
                }

                connection.commit();
                return itemsDTO;

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
    public void addItem(ItemDTO itemDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = new Item();
                item.setName(itemDTO.getName());
                item.setDescription(itemDTO.getDescription());
                //returns item with id:
                item = itemRepository.add(connection, item);

                ItemDetail itemDetail = new ItemDetail();
                itemDetail.setItemId(item.getId());
                itemDetail.setPrice(itemDTO.getPrice());
                itemDetailsRepository.add(connection, itemDetail);

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
    public void deleteLinkedItems() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Long> linkedItemsId = itemShopRepository.findLinkedItems(connection);
                for (Long itemId : linkedItemsId) {
                    deleteItem(connection, itemId);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void deleteItem(Connection connection, Long itemId) throws SQLException {
        itemShopRepository.deleteById(connection, itemId);
        itemDetailsRepository.deleteById(connection, itemId);
        itemRepository.deleteById(connection, itemId);
    }

    private ItemDTO convert(Item item){
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setPrice(item.getPrice());

        return itemDTO;
    }

}