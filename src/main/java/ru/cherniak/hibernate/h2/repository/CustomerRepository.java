package ru.cherniak.hibernate.h2.repository;

import ru.cherniak.hibernate.h2.exception.ResourceNotFoundException;
import ru.cherniak.hibernate.h2.model.Customer;
import ru.cherniak.hibernate.h2.model.Purchase;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

public class CustomerRepository {
    private EntityManager em;
    PurchaseRepository purchaseRepository;

    public CustomerRepository(EntityManager em) {
        this.em = em;
        purchaseRepository = new PurchaseRepository(em);
    }

    public Customer save(Customer customer) {
        em.getTransaction().begin();
        if (customer.getId() == null) {
            em.persist(customer);
        } else {
            customer = em.merge(customer);
        }
        em.getTransaction().commit();
        return customer;
    }

    public List<Customer> findAll() {
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList());
    }

    public Customer findById(long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            throw new ResourceNotFoundException("Клиент с id = " + id + " не найден");
        }
        return customer;
    }

    public void deleteById(long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            throw new ResourceNotFoundException("Клиент с id = " + id + " не найден");
        }
        em.getTransaction().begin();
        em.remove(customer);
        em.getTransaction().commit();
    }

    public List<Purchase> findPurchases(long customerId) {
        return Collections.unmodifiableList(em.find(Customer.class, customerId).getPurchases());
    }

public Customer getPurchasesByCustomerId(long customerId){
        em.getTransaction().begin();
        Customer customer = em.createQuery("SELECT DISTINCT c FROM Customer c INNER JOIN FETCH c.purchases WHERE c.id=:customerId",Customer.class)
                .setParameter("customerId", customerId).getSingleResult();
        em.getTransaction().commit();
        return customer;
}
}
