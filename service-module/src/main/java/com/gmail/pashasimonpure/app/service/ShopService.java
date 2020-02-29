package com.gmail.pashasimonpure.app.service;

import java.util.List;

import com.gmail.pashasimonpure.app.service.model.ShopDTO;

public interface ShopService {

    void addShop(ShopDTO shopDTO);

    List<ShopDTO> findAll();

    void linkItem(Long shopId, Long itemId);

}
