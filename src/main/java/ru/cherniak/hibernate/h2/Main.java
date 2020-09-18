package ru.cherniak.hibernate.h2;

import org.hibernate.cfg.Configuration;
import ru.cherniak.hibernate.h2.model.Customer;
import ru.cherniak.hibernate.h2.model.Product;
import ru.cherniak.hibernate.h2.repository.CustomerRepository;
import ru.cherniak.hibernate.h2.repository.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        EntityManager em = factory.createEntityManager();
        CustomerRepository customerRepository = new CustomerRepository(em);
        ProductRepository productRepository = new ProductRepository(em);

        Product product1 = new Product("Coca-Cola", 50);
        Product product2 = new Product("Sprite", 40);
        productRepository.save(product1);
        productRepository.save(product2);

        Customer customer1 = new Customer("Joric");
        Customer customer2 = new Customer("Doric");

        //insert
        System.out.println("insert customers");
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        System.out.println("shopping");
        customer1.addProduct(product1);
        customer1.addProduct(product2);
        customer2.addProduct(product1);
        customer2.addProduct(product2);

        //update
        System.out.println("update customers");
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        productRepository.findAll().forEach(System.out::println);
        System.out.println("update product1");
        product1.setCost(500);
        productRepository.save(product1);
        System.out.println(productRepository.findById(1L));

        productRepository.findAll().forEach(System.out::println);
        productRepository.findCustomers(1L).forEach(System.out::println);
        productRepository.findCustomers(2L).forEach(System.out::println);

        customerRepository.findAll().forEach(System.out::println);
        customerRepository.findProducts(1L).forEach(System.out::println);
        customerRepository.findProducts(2L).forEach(System.out::println);

        productRepository.findCustomers(1L).forEach(System.out::println);
        productRepository.findCustomers(2L).forEach(System.out::println);

        System.out.println("delete customer1");
        customerRepository.deleteById(1L);
        customerRepository.findAll().forEach(System.out::println);

        productRepository.findCustomers(1L).forEach(System.out::println);
        productRepository.findCustomers(2L).forEach(System.out::println);

        //delete product1
        System.out.println("delete product1");
        productRepository.deleteById(1L);
        productRepository.findAll().forEach(System.out::println);
        customerRepository.findProducts(2L).forEach(System.out::println);

        System.out.println("Find by ID");
        System.out.println(productRepository.findById(2L));
        System.out.println(customerRepository.findById(2L));
    }
}
