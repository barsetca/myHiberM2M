package ru.cherniak.hibernate.h2.repository;

import ru.cherniak.hibernate.h2.exception.ResourceNotFoundException;
import ru.cherniak.hibernate.h2.model.Customer;
import ru.cherniak.hibernate.h2.model.Purchase;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PurchaseRepository {
    private EntityManager em;

    public PurchaseRepository(EntityManager em) {
        this.em = em;
    }

    public Purchase save(Purchase purchase, long customerId) {
        em.getTransaction().begin();
        Customer customer = em.getReference(Customer.class, customerId);
//        List<Purchase> purchases = customer.getPurchases();
        purchase.setCustomer(customer);
        if (purchase.getId() == null) {
            em.persist(purchase);
//            purchases.add(purchase);
        } else {
            purchase = em.merge(purchase);
            long id = purchase.getId();
//            purchases.removeIf(p -> p.getId().equals(id));
//            purchases.add(purchase);
        }

        em.getTransaction().commit();
        return purchase;
    }

    public List<Purchase> findAll() {
        return Collections.unmodifiableList(em.createQuery("SELECT p FROM Purchase p", Purchase.class).getResultList());
    }

    public Purchase findById(long id) {
        Purchase purchase = em.find(Purchase.class, id);
        if (purchase == null) {
            throw new ResourceNotFoundException("Покупка с id = " + id + " не найдена");
        }
        return purchase;
    }

    public void deleteById(long purchaseId) {
        Purchase purchase = em.find(Purchase.class, purchaseId);

        if (purchase == null) {
            throw new ResourceNotFoundException("Покупка с id = " + purchaseId + " не найдена");
        }
        //Customer customer = purchase.getCustomer();
        em.getTransaction().begin();
        //customer.getPurchases().remove(purchase);
        em.remove(purchase);
        em.getTransaction().commit();
    }

    public void deleteByCustomerId(long customerId) {
        em.getTransaction().begin();
        //em.createNativeQuery("DELETE FROM purshases WHERE id_customer=:id").setParameter("id", customerId);
        em.createQuery("DELETE FROM Purchase p WHERE p.customer.id=:id").setParameter("id", customerId);
        em.getTransaction().commit();
    }

    public List<Purchase> findPurchases(long customerId) {
        return em.createQuery("SELECT p FROM Purchase p WHERE p.customer.id=:customerId", Purchase.class)
                .setParameter("customerId", customerId).getResultList();
    }
}
