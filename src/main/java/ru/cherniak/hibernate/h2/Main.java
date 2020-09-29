package ru.cherniak.hibernate.h2;

import org.hibernate.cfg.Configuration;
import ru.cherniak.hibernate.h2.model.Customer;
import ru.cherniak.hibernate.h2.model.Purchase;
import ru.cherniak.hibernate.h2.repository.CustomerRepository;
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
        PurchaseRepository purchaseRepository = new PurchaseRepository(em);

        Purchase product1 = new Purchase("Coca-Cola", 50);
        Purchase product2 = new Purchase("Sprite", 40);
        Purchase product3 = new Purchase("Fanta", 45);


        Customer customer1 = new Customer("Joric");
        Customer customer2 = new Customer("Doric");

        System.out.println("\ninsert customers");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.findAll().forEach(System.out::println);

        System.out.println("\ninsert products");
        purchaseRepository.save(product1, customer1.getId());
        purchaseRepository.save(product2, customer2.getId());
        purchaseRepository.save(product3, customer1.getId());
        purchaseRepository.findAll().forEach(System.out::println);

        System.out.println("\nupdate customers");
        customer1.setName("Duric");
        customer2.setName("Yuric");
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
        System.out.println("purchases");
        purchaseRepository.findAll().forEach(System.out::println);

        System.out.println("\nupdate product2");
        product2.setCost(500);
        purchaseRepository.save(product2, customer2.getId());
        System.out.println(purchaseRepository.findById(product2.getId()));

         System.out.println("\nproducts");
        purchaseRepository.findAll().forEach(System.out::println);
        System.out.println("products of customer1");
        purchaseRepository.findPurchases(1L).forEach(System.out::println);
        System.out.println("products of customer2");
        purchaseRepository.findPurchases(2L).forEach(System.out::println);

        System.out.println("\ndelete customer1");
        customerRepository.deleteById(1L);
        customerRepository.findAll().forEach(System.out::println);

        System.out.println("Find by customerID");
        System.out.println(purchaseRepository.findPurchases(2L));
        System.out.println(customerRepository.findById(2L));

        System.out.println("\nfindPurchases(2L)");
        System.out.println(customerRepository.getPurchasesByCustomerId(2L).getPurchases());
    }
}
