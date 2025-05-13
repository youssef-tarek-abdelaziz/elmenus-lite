package spring.practice.elmenus_lite.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.model.CartItemModel;
import spring.practice.elmenus_lite.model.CartModel;
import spring.practice.elmenus_lite.model.CustomerModel;
import spring.practice.elmenus_lite.repository.CartRepository;
import spring.practice.elmenus_lite.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartInitializerService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;

    @PostConstruct
    private void init() {

        CartModel cartModel = new CartModel();
        cartModel.setCustomer(getTempCustomer());
        cartModel.setCartItemModelList(getTempCustomerItems(cartModel));
        cartRepository.save(cartModel);
    }
    private CustomerModel getTempCustomer() {

        CustomerModel customerModel = new CustomerModel();
        customerModel.setPhone("01147558964");
        customerModel.setGender(1);
        return customerRepository.save(customerModel);
    }
    private List<CartItemModel> getTempCustomerItems(CartModel cartModel) {
        List<CartItemModel> cartItems = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            CartItemModel cartItemModel = new CartItemModel();
            cartItemModel.setCart(cartModel);
            cartItemModel.setQuantity(1);
            cartItems.add(cartItemModel);
        }
        return cartItems;
    }
}
