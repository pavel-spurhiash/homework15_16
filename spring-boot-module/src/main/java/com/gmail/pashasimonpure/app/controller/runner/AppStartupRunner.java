package com.gmail.pashasimonpure.app.controller.runner;

import java.lang.invoke.MethodHandles;
import java.util.List;

import com.gmail.pashasimonpure.app.service.ItemService;
import com.gmail.pashasimonpure.app.service.ShopService;
import com.gmail.pashasimonpure.app.service.model.ItemDTO;
import com.gmail.pashasimonpure.app.service.model.ShopDTO;
import com.gmail.pashasimonpure.app.service.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {

    //GENERATE OPTIONS:
    private static final int ITEMS_COUNT = 10;
    private static final int SHOPS_COUNT = 3;
    private static final int SHOP_MAX_ITEMS = 5;
    private static final double ITEM_MAX_PRICE = 20.;

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final ItemService itemService;
    private final ShopService shopService;

    public AppStartupRunner(ItemService itemService, ShopService shopService) {
        this.itemService = itemService;
        this.shopService = shopService;
    }

    @Override
    public void run(ApplicationArguments args) {

        logger.info("APPLICATION STARTED");

        generateItems(ITEMS_COUNT);
        generateShops(SHOPS_COUNT);
        List<ItemDTO> items = itemService.findAll();
        List<ShopDTO> shops = shopService.findAll();

        for (ShopDTO shop : shops) {

            for (int i = 0; i < SHOP_MAX_ITEMS; i++) {

                int randomIndex = (int) (Math.random() * items.size());
                ItemDTO item = items.get(randomIndex);
                shopService.linkItem(shop.getId(), item.getId());

            }
        }

        itemService.deleteLinkedItems();

    }

    private void generateItems(int count) {
        for (int i = 1; i <= count; i++) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setName("item#" + i);
            itemDTO.setDescription("itemDescription#" + RandomUtil.getRandomIntAbs());
            itemDTO.setPrice(Math.random() * ITEM_MAX_PRICE);
            itemService.addItem(itemDTO);
        }
    }

    private void generateShops(int count) {
        for (int i = 1; i <= count; i++) {
            ShopDTO shopDTO = new ShopDTO();
            shopDTO.setName("shopName#" + i);
            shopDTO.setLocation("shopLocation#" + RandomUtil.getRandomIntAbs());
            shopService.addShop(shopDTO);
        }
    }

}