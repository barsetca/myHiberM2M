package ru.cherniak.hibernate.h2.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "cost")
    private int cost;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne()
    @JoinColumn(name = "id_customer", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
    private Customer customer;

    public Purchase() {
    }

    public Purchase(String name, int cost) {
        this(null, name, cost);
    }

    public Purchase(Long id, String name, int cost) {
        this.id = id;
        this.title = name;
        this.cost = cost;
        this.date = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", date=" + date +
                ", customer=" + customer +
                '}';
    }
}
