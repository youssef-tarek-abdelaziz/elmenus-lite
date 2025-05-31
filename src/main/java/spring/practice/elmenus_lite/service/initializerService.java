package spring.practice.elmenus_lite.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.model.*;
import spring.practice.elmenus_lite.repository.*;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class initializerService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderTrackingRepository orderTrackingRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final UserTypeRepository userTypeRepository;
    private final AddressRepository addressRepository;

    @PostConstruct
    private void init() {
        cartRepository.deleteAll();
        CartModel cartModel = new CartModel();
        cartModel.setCustomer(getTempCustomer());
        cartModel.setCartItems(getTempCustomerItems(cartModel));
        cartRepository.save(cartModel);
    }
    private CustomerModel getTempCustomer() {
        UserTypeModel userType = new UserTypeModel();
        userType.setUserTypeName("customer" + Math.random());
        userTypeRepository.save(userType);

        UserModel user = new UserModel();
        user.setFirstName("Ali");
        user.setLastName("Sami");
        user.setFullName("Ali Sami");
        user.setEmail("customer" + Math.random() + "@email.com");
        user.setEnabled(true);
        user.setPassword("temppassword");
        user.setUserType(userType);
        userRepository.save(user);

        CustomerModel customerModel = new CustomerModel();
        customerModel.setPhone("01147558964");
        customerModel.setGender(1);
        customerModel.setUser(user);
        return customerRepository.save(customerModel);
    }
    private Set<CartItemModel> getTempCustomerItems(CartModel cartModel) {
        Set<CartItemModel> cartItems = new HashSet<>();
        MenuModel menuModel = getMenuModel();
        for(int i = 0; i < 5; i++) {
            CartItemModel cartItemModel = new CartItemModel();
            cartItemModel.setCart(cartModel);
            cartItemModel.setQuantity(1);
            cartItems.add(cartItemModel);
            cartItemModel.setMenuItem(getMenuItemModel("item-" + (i + 1), menuModel));
        }
        return cartItems;
    }

    private MenuItemModel getMenuItemModel(String itemName, MenuModel menuModel) {
        MenuItemModel menuItemModel = new MenuItemModel();
        menuItemModel.setMenuItemName(itemName);
        menuItemModel.setAvailable(true);
        menuItemModel.setPrice(10);
        menuItemModel.setMenuModel(menuModel);
        return menuItemModel;
    }

    private MenuModel getMenuModel() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setRestaurantName("Restaurant - 1");
        restaurantModel.setActive(true);

        MenuModel menuModel = new MenuModel();
        menuModel.setMenuName("Main Menu");
        menuModel.setRestaurantModel(restaurantModel);

        return menuModel;
    }
}
