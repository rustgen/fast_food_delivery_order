package fastfooddelivery.order.repository;

import fastfooddelivery.order.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    boolean markOrderAsCompleted(int id);

    List<Order> findAll();
}
