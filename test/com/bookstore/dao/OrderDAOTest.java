package com.bookstore.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Book;
import com.bookstore.entity.BookOrder;
import com.bookstore.entity.Customer;
import com.bookstore.entity.OrderDetail;

import static org.junit.Assert.*;

public class OrderDAOTest {

    private static OrderDAO orderDAO;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        orderDAO = new OrderDAO();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        orderDAO.close();
    }

    @Test
    public void testCreateBookOrder2() {
        BookOrder order = new BookOrder();
        Customer customer = new Customer();
        customer.setCustomerId(1);

        order.setCustomer(customer);
        order.setFirstname("Nam");
        order.setLastname("Ha Minh");
        order.setPhone("123456789");
        order.setAddressLine1("123 South Street");
        order.setAddressLine1("Cliffton Park");
        order.setCity("New York");
        order.setState("New York");
        order.setCountry("US");
        order.setPaymentMethod("paypal");
        order.setZipcode("12345");

        Set<OrderDetail> orderDetails = new HashSet<>();
        OrderDetail orderDetail1 = new OrderDetail();

        Book book1 = new Book(34);
        orderDetail1.setBook(book1);
        orderDetail1.setQuantity(2);
        orderDetail1.setSubtotal(80f);
        orderDetail1.setBookOrder(order);

        orderDetails.add(orderDetail1);

        Book book2 = new Book(35);
        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setBook(book2);
        orderDetail2.setQuantity(1);
        orderDetail2.setSubtotal(36.72f);
        orderDetail2.setBookOrder(order);

        orderDetails.add(orderDetail2);

        order.setOrderDetails(orderDetails);
        order.setTax(6.5f);
        order.setShippingFee(1.0f);
        order.setTotal(76.8f);

        orderDAO.create(order);

        assertTrue(order.getOrderId() > 0 && order.getOrderDetails().size() == 2);

    }


    @Test
    public void testUpdateBookOrderShippingAddress() {
        Integer orderId = 22;
        BookOrder order = orderDAO.get(orderId);
        order.setAddressLine1("New Shipping Address");

        orderDAO.update(order);

        BookOrder updatedOrder = orderDAO.get(orderId);

        assertEquals(order.getAddressLine1(), updatedOrder.getAddressLine1());

    }

    @Test
    public void testUpdateBookOrderDetail() {
        Integer orderId = 22;
        BookOrder order = orderDAO.get(orderId);

        Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();

        while (iterator.hasNext()) {
            OrderDetail orderDetail = iterator.next();
            if (orderDetail.getBook().getBookId() == 35) {
                orderDetail.setQuantity(3);
                orderDetail.setSubtotal(120);
            }
        }


        orderDAO.update(order);

        BookOrder updatedOrder = orderDAO.get(orderId);

        iterator = order.getOrderDetails().iterator();

        int expectedQuantity = 3;
        float expectedSubtotal = 120;
        int actualQuantity = 0;
        float actualSubtotal = 0;

        while (iterator.hasNext()) {
            OrderDetail orderDetail = iterator.next();
            if (orderDetail.getBook().getBookId() == 35) {
                actualQuantity = orderDetail.getQuantity();
                actualSubtotal = orderDetail.getSubtotal();
            }
        }

        assertEquals(expectedQuantity, actualQuantity);
        assertEquals(expectedSubtotal, actualSubtotal, 0.0f);

    }

    @Test
    public void testGet() {
        Integer orderId = 23;
        BookOrder order = orderDAO.get(orderId);
        System.out.println(order.getFirstname());
        System.out.println(order.getPhone());
        System.out.println(order.getAddressLine1());
        System.out.println(order.getAddressLine2());
        System.out.println(order.getStatus());
        System.out.println(order.getTotal());
        System.out.println(order.getPaymentMethod());


        assertEquals(1, order.getOrderDetails().size());
    }

    @Test
    public void testGetByIdAndCustomerNull() {
        Integer orderId = 10;
        Integer customerId = 99;

        BookOrder order = orderDAO.get(orderId, customerId);

        assertNull(order);
    }

    @Test
    public void testGetByIdAndCustomerNotNull() {
        Integer orderId = 10;
        Integer customerId = 8;

        BookOrder order = orderDAO.get(orderId, customerId);

        assertNotNull(order);
    }

    @Test
    public void testDeleteOrder() {
        int orderId = 22;
        orderDAO.delete(orderId);

        BookOrder order = orderDAO.get(orderId);

        assertNull(order);
    }

    @Test
    public void testListAll() {
        List<BookOrder> listOrders = orderDAO.listAll();

        for (BookOrder order : listOrders) {
            System.out.println(order.getOrderId() + " - " + order.getCustomer().getFirstname()
                    + " - " + order.getTotal() + " - " + order.getStatus());
            for (OrderDetail detail : order.getOrderDetails()) {
                Book book = detail.getBook();
                int quantity = detail.getQuantity();
                float subtotal = detail.getSubtotal();
                System.out.println("\t" + book.getTitle() + " - " + quantity + " - " + subtotal);
            }
        }

        assertFalse(listOrders.isEmpty());
    }

    @Test
    public void testListByCustomerNoOrders() {
        Integer customerId = 99;
        List<BookOrder> listOrders = orderDAO.listByCustomer(customerId);

        assertTrue(listOrders.isEmpty());
    }

    @Test
    public void testListByCustomerHaveOrders() {
        Integer customerId = 1;
        List<BookOrder> listOrders = orderDAO.listByCustomer(customerId);

        assertTrue(listOrders.isEmpty());
    }

    @Test
    public void testCount() {
        long totalOrders = orderDAO.count();
        assertEquals(0, totalOrders);
    }

    @Test
    public void testListMostRecentSales() {
        List<BookOrder> recentOrders = orderDAO.listMostRecentSales();
        assertEquals(2, recentOrders.size());
    }
}
