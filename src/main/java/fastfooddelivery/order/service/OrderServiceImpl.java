package fastfooddelivery.order.service;

import fastfooddelivery.order.dto.OrderDetailsDTO;
import fastfooddelivery.order.model.Dish;
import fastfooddelivery.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import fastfooddelivery.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl extends OrderService {

    @Value("${api-url}")
    private String url;
    private final RestTemplate client;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate client) {
        super(orderRepository);
        this.client = client;
    }

    @Override
    public Order createOrder(Order order) {
        System.out.println(url);
        return client.postForEntity(
                url, order, Order.class
        ).getBody();
    }

    @Override
    public boolean delete(String id) {
        return client.exchange(
                String.format("%s?id=%s", url, id),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        ).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    public Optional<Order> findOrderById(int id) {
        return Optional.ofNullable(client.getForEntity(
                String.format("%s/getById?id=%s", url, id),
                Order.class
        ).getBody());
    }

    public boolean markOrderAsCompleted(int id) {
        ResponseEntity<Void> response = client.exchange(
                String.format("%s/markAsCompleted?id=%s", url, id),
                HttpMethod.PUT,
                null,
                Void.class
        );
        return response.getStatusCode() == HttpStatus.OK;
    }

    public List<Order> getAllOrders() {
        List<Order> body = client.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Order>>() {
                }
        ).getBody();
        return body == null ? Collections.emptyList() : body;
    }

    public OrderDetailsDTO getOrderDetails(Order order) {
        OrderDetailsDTO orderDetails = new OrderDetailsDTO();
        orderDetails.setId(order.getId());
        orderDetails.setTotalPrice(order.getTotalPrice());
        orderDetails.setCompleted(order.isCompleted());
        List<Dish> dishes = new ArrayList<>();
        for (Dish dish : order.getDishes()) {
            Dish newDish = new Dish();
            newDish.setId(dish.getId());
            newDish.setName(dish.getName());
            newDish.setDescription(dish.getDescription());
            newDish.setPrice(dish.getPrice());
            dishes.add(newDish);
        }
        orderDetails.setDishes(dishes);
        return orderDetails;
    }
}
