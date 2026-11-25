package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.CustomerOrderRequestDTO;
import com.examly.springapp.dto.CustomerOrderResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.dto.OrderItemRequestDTO;
import com.examly.springapp.dto.OrderItemResponseDTO;
import com.examly.springapp.model.CustomerOrder;
import com.examly.springapp.model.OrderItem;
import com.examly.springapp.service.CustomerOrderService;

@RestController
@RequestMapping("/api/orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomerOrder(@RequestBody CustomerOrderRequestDTO request) {
        if (request.getCustomerName() == null || request.getCustomerName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Customer name is required"));
        }
        if (request.getOrderDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Order date is required"));
        }
        if (request.getItems() != null) {
            for (OrderItemRequestDTO item : request.getItems()) {
            if (item.getProductName() == null || item.getProductName().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Item product name is required"));
            }
            if (item.getQuantity() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Item quantity is required"));
            }
            if (item.getPrice() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Item price is required"));
            }
            }
        }
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomerName(request.getCustomerName());
        customerOrder.setOrderDate(request.getOrderDate());
        if (request.getStatus() != null) {
            customerOrder.setStatus(request.getStatus());
        }
        if (request.getItems() != null) {
            for (OrderItemRequestDTO item : request.getItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductName(item.getProductName());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(item.getPrice());
                customerOrder.addOrderItem(orderItem);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(toCustomerOrderResponse(customerOrderService.saveCustomerOrder(customerOrder)));
    }

    @GetMapping
    public ResponseEntity<List<CustomerOrderResponseDTO>> getAllOrders() {
        List<CustomerOrderResponseDTO> result = new ArrayList<>();
        for (CustomerOrder x : customerOrderService.getAllOrders()) {
            result.add(toCustomerOrderResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerOrderById(@PathVariable Long id) {
        Optional<CustomerOrder> customerOrder = customerOrderService.getCustomerOrderById(id);
        if (customerOrder.isPresent()) {
            return ResponseEntity.ok(toCustomerOrderResponse(customerOrder.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "CustomerOrder not found with id " + id));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<?> addOrderItemToCustomerOrder(@PathVariable Long id, @RequestBody OrderItemRequestDTO item) {
        if (item.getProductName() == null || item.getProductName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Product name is required"));
        }
        if (item.getQuantity() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Quantity is required"));
        }
        if (item.getPrice() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Price is required"));
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setProductName(item.getProductName());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setPrice(item.getPrice());
        Optional<OrderItem> saved = customerOrderService.addOrderItem(id, orderItem);
        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(toOrderItemResponse(saved.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "CustomerOrder not found with id " + id));
    }

    @DeleteMapping("/{id}/items/{orderItemId}")
    public ResponseEntity<?> removeOrderItemFromCustomerOrder(@PathVariable Long id, @PathVariable Long orderItemId) {
        if (customerOrderService.getCustomerOrderById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "CustomerOrder not found with id " + id));
        }
        if (customerOrderService.removeOrderItem(id, orderItemId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "OrderItem not found with id " + orderItemId + " in CustomerOrder " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerOrder(@PathVariable Long id) {
        if (customerOrderService.getCustomerOrderById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "CustomerOrder not found with id " + id));
        }
        customerOrderService.deleteCustomerOrder(id);
        return ResponseEntity.noContent().build();
    }

    private OrderItemResponseDTO toOrderItemResponse(OrderItem x) {
        return new OrderItemResponseDTO(x.getId(), x.getProductName(), x.getQuantity(), x.getPrice());
    }

    private CustomerOrderResponseDTO toCustomerOrderResponse(CustomerOrder x) {
        List<OrderItemResponseDTO> items = new ArrayList<>();
        for (OrderItem y : x.getItems()) {
            items.add(toOrderItemResponse(y));
        }
        items.sort(Comparator.comparing(OrderItemResponseDTO::getId));
        return new CustomerOrderResponseDTO(x.getId(), x.getCustomerName(), x.getOrderDate(), x.getStatus(), items);
    }
}
