package ru.cherniak.hibernate.h2.repository;

import ru.cherniak.hibernate.h2.exception.ResourceNotFoundException;
import ru.cherniak.hibernate.h2.model.Customer;
import ru.cherniak.hibernate.h2.model.Product;
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

    public void addProduct(Product product, long customerId) {
        Customer customer = em.find(Customer.class, customerId);
        customer.getProducts().add(product);
        product.getCustomers().add(customer);
        purchaseRepository.save(new Purchase(product.getTitle(), product.getCost()), customerId);
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
        customer.getProducts().forEach(p -> p.getCustomers().remove(customer));
        em.remove(customer);
        em.getTransaction().commit();
    }

    public List<Product> findProducts(long customerId) {
        return Collections.unmodifiableList(em.find(Customer.class, customerId).getProducts());
    }

    public List<Purchase> findPurchases(long customerId) {
        return Collections.unmodifiableList(em.find(Customer.class, customerId).getPurchases());
    }

public List<Purchase> getPurchasesByCustomerId(long customerId){
        em.getTransaction().begin();
        List<Purchase> purchases = em.createQuery("SELECT DISTINCT c FROM Customer c INNER JOIN FETCH c.purchases WHERE c.id=:customerId")
                .setParameter("customerId", customerId).getResultList();
        em.getTransaction().commit();
        return purchases;
}
}
