package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.MenuItemRequestDTO;
import com.examly.springapp.model.MenuItem;
import com.examly.springapp.repository.MenuItemRepository;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public MenuItem saveMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public Optional<MenuItem> getMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    @Override
    public Optional<MenuItem> updateMenuItem(Long id, MenuItemRequestDTO request) {
        Optional<MenuItem> existing = menuItemRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        MenuItem menuItem = existing.get();
        menuItem.setName(request.getName());
        menuItem.setCategory(request.getCategory());
        menuItem.setPrice(request.getPrice());
        menuItem.setAvailable(request.getAvailable());
        menuItem.setPreparationMinutes(request.getPreparationMinutes());
        return Optional.of(menuItemRepository.save(menuItem));
    }

    @Override
    public boolean deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            return false;
        }
        menuItemRepository.deleteById(id);
        return true;
    }
}
