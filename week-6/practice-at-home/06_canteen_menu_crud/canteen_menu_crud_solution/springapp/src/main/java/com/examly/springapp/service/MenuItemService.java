package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.dto.MenuItemRequestDTO;
import com.examly.springapp.model.MenuItem;

public interface MenuItemService {

    MenuItem saveMenuItem(MenuItem menuItem);

    List<MenuItem> getAllMenuItems();

    Optional<MenuItem> getMenuItemById(Long id);

    Optional<MenuItem> updateMenuItem(Long id, MenuItemRequestDTO request);

    boolean deleteMenuItem(Long id);
}
