package ru.cherniak.hibernate.h2;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Product product1 = new Product("Coca-Cola", 50);
        Product product2 = new Product("Sprite", 40);

        Customer customer1 = new Customer("Joric");
        customer1.addProduct(product1);
        customer1.addProduct(product2);
        Customer customer2 = new Customer("Doric");
        customer2.addProduct(product1);
        customer2.addProduct(product2);

        em.persist(product1);
        em.persist(product2);

        em.persist(customer1);
        em.persist(customer2);

        em.getTransaction().commit();

        List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        products.forEach(System.out::println);
        List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        customers.forEach(System.out::println);
        System.out.println("//////////////////////////////////////////////////////");
        product1.getCustomers().forEach(System.out::println);
        product2.getCustomers().forEach(System.out::println);
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        customer1.getProducts().forEach(System.out::println);

        em.getTransaction().begin();
        customer1.getProducts().forEach(p -> p.getCustomers().remove(customer1));
        em.remove(customer1);
        em.getTransaction().commit();

        product1.getCustomers().forEach(System.out::println);

        List<Customer> customers2 = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        customers2.forEach(System.out::println);
        System.out.println();
        System.out.println("Products of customer with id = 2 before remove Product1");
        customer2.getProducts().forEach(System.out::println);

        System.out.println("Удаляем продукт 1");
        Product product = em.find(Product.class, 1L);
        em.getTransaction().begin();
        product.getCustomers().forEach(c -> c.getProducts().remove(product));
        em.remove(product);
        em.getTransaction().commit();

        System.out.println("Products of customer with id = 2 AFTER remove Product1");
        customer2.getProducts().forEach(System.out::println);

        System.out.println("Check of product list");
        List<Product> productAfterRemove = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        productAfterRemove.forEach(System.out::println);

        List<Customer> customers3 = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        customers2.forEach(System.out::println);
    }
}
