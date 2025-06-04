//package spring.practice.elmenus_lite.service;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import spring.practice.elmenus_lite.model.*;
//import spring.practice.elmenus_lite.repository.*;
//
//import java.math.BigDecimal;
//import java.time.Duration;
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//@RequiredArgsConstructor
//public class initializerService {
//    private final OrderRepository orderRepository;
//    private final OrderStatusRepository orderStatusRepository;
//    private final OrderTrackingRepository orderTrackingRepository;
//    private final CartRepository cartRepository;
//    private final UserRepository userRepository;
//    private final CustomerRepository customerRepository;
//    private final UserTypeRepository userTypeRepository;
//    private final AddressRepository addressRepository;
//
//    @PostConstruct
//    private void init() {
//        orderRepository.deleteAll();
//        addressRepository.deleteAll();
//        orderTrackingRepository.deleteAll();
//        cartRepository.deleteAll();
//        CartModel cartModel = new CartModel();
//        CustomerModel customerModel = getTempCustomer();
//        cartModel.setCustomer(customerModel);
//        cartModel.setCartItems(getTempCustomerItems(cartModel));
//        cartRepository.save(cartModel);
//
//        createOrder(customerModel);
//    }
//
//    private void createOrder(CustomerModel customerModel) {
//        AddressModel addressModel = AddressModel
//                .builder()
//                .customer(customerModel)
//                .label("label")
//                .street("street")
//                .city("city")
//                .floor("floor")
//                .apartment("apartment")
//                .additionalDirection("direction")
//                .state("state")
//                .country("country")
//                .zipCode("zipCode")
//                .isDefault(true)
//                .location(null)
//                .build();
//
//        addressRepository.save(addressModel);
//
//        OrderStatusModel orderStatusModel = orderStatusRepository.findByOrderStatusName("PENDING").orElse(null);
//
//        OrderTrackingModel orderTracking = OrderTrackingModel
//                .builder()
//                .currentLocation(null)
//                .estimatedTime(Duration.ofMinutes(30))
//                .build();
//        orderTrackingRepository.save(orderTracking);
//
//        OrderModel orderModel = OrderModel
//                .builder()
//                .customer(customerModel)
//                .address(addressModel)
//                .orderStatus(orderStatusModel)
//                .orderTracking(orderTracking)
//                .promotion(null)
//                .discountAmount(BigDecimal.ZERO)
//                .subtotal(BigDecimal.ZERO)
//                .total(BigDecimal.ZERO)
//                .build();
//
//        orderRepository.save(orderModel);
//
//    }
//
//
//    private CustomerModel getTempCustomer() {
//        UserTypeModel userType = new UserTypeModel();
//        userType.setUserTypeName("customer" + Math.random());
//        userTypeRepository.save(userType);
//
//        UserModel user = new UserModel();
//        user.setFirstName("Ali");
//        user.setLastName("Sami");
//        user.setFullName("Ali Sami");
//        user.setEmail("customer" + Math.random() + "@email.com");
//        user.setEnabled(true);
//        user.setPassword("temppassword");
//        user.setUserType(userType);
//        userRepository.save(user);
//
//        CustomerModel customerModel = new CustomerModel();
//        customerModel.setPhone("01147558964");
//        customerModel.setGender(1);
//        customerModel.setUser(user);
//        return customerRepository.save(customerModel);
//    }
//    private Set<CartItemModel> getTempCustomerItems(CartModel cartModel) {
//        Set<CartItemModel> cartItems = new HashSet<>();
//        MenuModel menuModel = getMenuModel();
//        for(int i = 0; i < 5; i++) {
//            CartItemModel cartItemModel = new CartItemModel();
//            cartItemModel.setCart(cartModel);
//            cartItemModel.setQuantity(1);
//            cartItems.add(cartItemModel);
//            cartItemModel.setMenuItem(getMenuItemModel("item-" + (i + 1), menuModel));
//        }
//        return cartItems;
//    }
//
//    private MenuItemModel getMenuItemModel(String itemName, MenuModel menuModel) {
//        MenuItemModel menuItemModel = new MenuItemModel();
//        menuItemModel.setMenuItemName(itemName);
//        menuItemModel.setAvailable(true);
//        menuItemModel.setPrice(10);
//        menuItemModel.setMenuModel(menuModel);
//        return menuItemModel;
//    }
//
//    private MenuModel getMenuModel() {
//        RestaurantModel restaurantModel = new RestaurantModel();
//        restaurantModel.setRestaurantName("Restaurant - 1");
//        restaurantModel.setActive(true);
//
//        MenuModel menuModel = new MenuModel();
//        menuModel.setMenuName("Main Menu");
//        menuModel.setRestaurantModel(restaurantModel);
//
//        return menuModel;
//    }
//}
