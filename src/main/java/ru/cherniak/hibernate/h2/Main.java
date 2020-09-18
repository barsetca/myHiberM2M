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





//        em.getTransaction().begin();
//
//
//
//
//        em.persist(product1);
//        em.persist(product2);
//
//        em.persist(customer1);
//        em.persist(customer2);
//
//        em.getTransaction().commit();
//
//        List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
//        products.forEach(System.out::println);
//        List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
//        customers.forEach(System.out::println);
//        System.out.println("//////////////////////////////////////////////////////");
//        product1.getCustomers().forEach(System.out::println);
//        product2.getCustomers().forEach(System.out::println);
//        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
//        customer1.getProducts().forEach(System.out::println);
//
//        em.getTransaction().begin();
//        customer1.getProducts().forEach(p -> p.getCustomers().remove(customer1));
//        em.remove(customer1);
//        em.getTransaction().commit();
//
//        product1.getCustomers().forEach(System.out::println);
//
//        List<Customer> customers2 = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
//        customers2.forEach(System.out::println);
//        System.out.println();
//        System.out.println("Products of customer with id = 2 before remove Product1");
//        customer2.getProducts().forEach(System.out::println);
//
//        System.out.println("Удаляем продукт 1");
//        Product product = em.find(Product.class, 1L);
//        em.getTransaction().begin();
//        product.getCustomers().forEach(c -> c.getProducts().remove(product));
//        em.remove(product);
//        em.getTransaction().commit();
//
//        System.out.println("Products of customer with id = 2 AFTER remove Product1");
//        customer2.getProducts().forEach(System.out::println);
//
//        System.out.println("Check of product list");
//        List<Product> productAfterRemove = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
//        productAfterRemove.forEach(System.out::println);
//
//        List<Customer> customers3 = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
//        customers2.forEach(System.out::println);
    }
}
