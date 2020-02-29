package com.gmail.pashasimonpure.app.service;

import java.util.List;

import com.gmail.pashasimonpure.app.service.model.ItemDTO;

public interface ItemService {

    void addItem(ItemDTO itemDTO);

    List<ItemDTO> findAll();

    void deleteLinkedItems();

}
