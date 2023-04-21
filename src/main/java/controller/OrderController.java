package controller;

import dto.OrderDetailsDTO;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import service.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order save(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/all")
    public List<Order> getAll() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable int id) {
        Optional<Order> person = orderService.findOrderById(id);
        return new ResponseEntity<Order>(
                person.orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order with this id wasn't found. Please, check the input.")),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Void> markOrderAsCompleted(@PathVariable int id) {
        Optional<Order> orderById = orderService.findOrderById(id);
        if (orderById.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderService.markOrderAsCompleted(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestParam int id) {
        Optional<Order> personsById = orderService.findOrderById(id);
        if (personsById.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Order order = new Order();
        order.setId(id);
        this.orderService.delete(order);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(@PathVariable int id) {
        Optional<Order> order = orderService.findOrderById(id);
        if (order.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        OrderDetailsDTO orderDetails = orderService.getOrderDetails(order.get().getId());
        return ResponseEntity.ok(orderDetails);
    }
}
