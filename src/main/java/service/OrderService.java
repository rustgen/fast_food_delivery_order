package service;

import dto.OrderDetailsDTO;
import lombok.AllArgsConstructor;
import model.Dish;
import model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public abstract class OrderService {

    OrderRepository orderRepository;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public abstract boolean delete(String id);

    public Optional<Order> findOrderById(int id) {
        return orderRepository.findById(id);
    }

    public boolean markOrderAsCompleted(int id) {
        return orderRepository.markOrderAsCompleted(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public OrderDetailsDTO getOrderDetails(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
            orderDetailsDTO.setOrder(order.get());

            List<Dish> dishes = order.get().getDishes();
            orderDetailsDTO.setDishes(dishes);

            return orderDetailsDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with this id wasn't found. Please, check the input.");
        }
    }
}
