package ru.cherniak.hibernate.h2;

import org.hibernate.cfg.Configuration;
import ru.cherniak.hibernate.h2.model.Customer;
import ru.cherniak.hibernate.h2.model.Product;
import ru.cherniak.hibernate.h2.repository.CustomerRepository;
import ru.cherniak.hibernate.h2.repository.ProductRepository;
import ru.cherniak.hibernate.h2.repository.PurchaseRepository;

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
        PurchaseRepository purchaseRepository = new PurchaseRepository(em);

        Product product1 = new Product("Coca-Cola", 50);
        Product product2 = new Product("Sprite", 40);
        System.out.println("\ninsert products");
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.findAll().forEach(System.out::println);

        Customer customer1 = new Customer("Joric");
        Customer customer2 = new Customer("Doric");

        System.out.println("\ninsert customers");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.findAll().forEach(System.out::println);

        System.out.println("\nshopping");
        customerRepository.addProduct(product1, customer1.getId());
        customerRepository.addProduct(product2, customer1.getId());
        customerRepository.addProduct(product1, customer2.getId());
        customerRepository.addProduct(product2, customer2.getId());

        System.out.println("\nupdate customers");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.findAll().forEach(System.out::println);

        System.out.println("\npurchases");
        purchaseRepository.findAll().forEach(System.out::println);
        System.out.println("\npurchases of customer1");
        customerRepository.findPurchases(customer1.getId()).forEach(System.out::println);
        System.out.println("\npurchases of customer2");
        customerRepository.findPurchases(customer2.getId()).forEach(System.out::println);

        System.out.println("\ndelete purchase id = 1");
        purchaseRepository.deleteById(1L);
        System.out.println("purchases of customer1");
        customerRepository.findPurchases(customer1.getId()).forEach(System.out::println);

        System.out.println("\nupdate product1");
        product1.setCost(500);
        productRepository.save(product1);
        System.out.println(productRepository.findById(1L));

        System.out.println("\n addCustomer2 update product1");
        customerRepository.addProduct(product1, customer2.getId());
        System.out.println("History of purchases customer2");
        customerRepository.findPurchases(customer2.getId()).forEach(System.out::println);

        System.out.println("\nproducts");
        productRepository.findAll().forEach(System.out::println);
        System.out.println("customers of product1");
        productRepository.findCustomers(1L).forEach(System.out::println);
        System.out.println("customers of product2");
        productRepository.findCustomers(2L).forEach(System.out::println);


        System.out.println("\ncustomers");
        customerRepository.findAll().forEach(System.out::println);
        System.out.println("products of customer1");
        customerRepository.findProducts(1L).forEach(System.out::println);
        System.out.println("products of customer2");
        customerRepository.findProducts(2L).forEach(System.out::println);

        productRepository.findCustomers(1L).forEach(System.out::println);
        productRepository.findCustomers(2L).forEach(System.out::println);

        System.out.println("\ndelete customer1");
        customerRepository.deleteById(1L);
        customerRepository.findAll().forEach(System.out::println);


        System.out.println("\ndelete product1");
        productRepository.deleteById(1L);
        productRepository.findAll().forEach(System.out::println);
        customerRepository.findProducts(2L).forEach(System.out::println);

        System.out.println("Find by ID");
        System.out.println(productRepository.findById(2L));
        System.out.println(customerRepository.findById(2L));

        System.out.println("\nfindPurchases(2L)");
        customerRepository.findPurchases(2L).forEach(System.out::println);
    }

}
