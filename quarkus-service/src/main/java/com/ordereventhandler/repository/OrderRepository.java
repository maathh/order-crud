package com.ordereventhandler.repository;


import com.ordereventhandler.entity.Order;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public Order findById(Long id) {
        return find("id", id).firstResult();
    }

    public void save(Order order) {
        persist(order);
    }

    public void update(Order order) {
        persist(order);
    }

    public void delete(Long id) {
        delete("id", id);
    }
}
