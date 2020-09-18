package ru.cherniak.hibernate.h2.repository;

import ru.cherniak.hibernate.h2.exception.ResourceNotFoundException;
import ru.cherniak.hibernate.h2.model.Customer;
import ru.cherniak.hibernate.h2.model.Product;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

public class ProductRepository {
    private EntityManager em;

    public ProductRepository(EntityManager em) {
        this.em = em;
    }

    public Product save(Product product) {
        em.getTransaction().begin();
        if (product.getId() == null) {
            em.persist(product);
        } else {
            product = em.merge(product);
        }
        em.getTransaction().commit();
        return product;
    }

    public List<Product> findAll() {
        return Collections.unmodifiableList(em.createQuery("SELECT p FROM Product p", Product.class).getResultList());
    }

    public Product findById(long id) {
        Product product = em.find(Product.class, id);
        if (product == null) {
            throw new ResourceNotFoundException("Продукт с id = " + id + " не найден");
        }
        return product;
    }

    public void deleteById(long id) {
        Product product = em.find(Product.class, id);
        if (product == null) {
            throw new ResourceNotFoundException("Продукт с id = " + id + " не найден");
        }
        em.getTransaction().begin();
        product.getCustomers().forEach(c -> c.getProducts().remove(product));
        em.remove(product);
        em.getTransaction().commit();
    }

    public List<Customer> findCustomers(long productId) {
        return Collections.unmodifiableList(em.find(Product.class, productId).getCustomers());
    }
}
