package com.ke.service;

import com.ke.component.discount.DiscountApplicable;
import com.ke.exception.NoItemsInCartException;
import com.ke.model.Buyer;
import com.ke.model.Cart;
import com.ke.model.Order;
import com.ke.model.OrderItem;
import com.ke.repository.CartRepository;
import com.ke.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private List<? extends DiscountApplicable> discountApplicables;

    public Order createOrder() {
        Buyer buyer = buyerService.getCurrentBuyer();
        List<Cart> cartItems = cartRepository.findByBuyer(buyer);

        if (CollectionUtils.isEmpty(cartItems)) {
            throw new NoItemsInCartException();
        }
        Order order = prepareOrderWithCart(cartItems);
        order.setBuyer(buyer);
        return order;
    }

    private Order prepareOrderWithCart(List<Cart> cartItems) {
        Order order = new Order();

        for (Cart cart : cartItems) {
            OrderItem orderItem = orderItemService.createOrderItem(cart);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        BigDecimal totalPrice = order.getOrderItems()
                .stream()
                .map(o -> o.totalPriceWithQuantity())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);
        applyDiscounts(order);
        order.setFinalPrice(totalPrice.subtract(order.getDiscountDetail().getTotalDiscount()));
        return order;
    }

    private void applyDiscounts(Order order) {
        for (DiscountApplicable discountApplicable : discountApplicables) {
            discountApplicable.apply(order);
        }
    }

    @Transactional
    public void completeOrder(Order order) {
        orderRepository.save(order);
        cartRepository.deleteByBuyer(order.getBuyer());
    }

    public List query(Long productId, String discountType) {
        return orderRepository.query(productId, discountType);
    }

    public void setDiscountApplicables(List<? extends DiscountApplicable> discountApplicables) {
        this.discountApplicables = discountApplicables;
    }
}
