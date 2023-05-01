package fastfooddelivery.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import fastfooddelivery.order.model.Dish;
import fastfooddelivery.order.model.Order;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {

    private int id;
    @Size(min = 10, message = "The minimum cost of order should be 10 USD.")
    private double totalPrice;
    private boolean isCompleted;
    private List<Dish> dishes;

    public void setOrder(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.isCompleted = order.isCompleted();
        this.dishes = order.getDishes().stream()
                .map(d -> new Dish(d.getId(), d.getName(), d.getDescription(), d.getPrice()))
                .collect(Collectors.toList());
    }
}
