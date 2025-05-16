package spring.practice.elmenus_lite.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.model.*;
import spring.practice.elmenus_lite.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartInitializerService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final AddressRepository addressRepository;

    @PostConstruct
    private void init() {

        //Create Cart
        CartModel cartModel = new CartModel();
        cartModel.setCustomer(getTempCustomer());
        cartRepository.save(cartModel);
//        cartModel.setCartItemModelList(getTempCustomerItems(cartModel));
//        CartItemModel cartItemModel = new CartItemModel();
//        cartItemModel.setCart(cartModel);
//        cartItemModel.setQuantity(1);
//        cartModel.getCartItemModelList().add(cartItemModel);
//        cartRepository.save(cartModel);
    }
    private CustomerModel getTempCustomer() {
        //Get User Type
        UserTypeModel userTypeModel = userTypeRepository.findById(1L).get();
        System.out.println(userTypeModel);

        //Create User
        UserModel userModel = UserModel.builder().email("test"+Math.random()).password("test").firstName("test")
                .lastName("test").fullName("test").userType(userTypeModel).build();
        userRepository.save(userModel);
        System.out.println("userModel: " + userModel);

        //Create Customer
        CustomerModel customerModel = new CustomerModel();
        customerModel.setPhone("01147558964");
        customerModel.setGender(1);
        customerModel.setUser(userModel);

        customerRepository.save(customerModel);

        //Create Address
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(),4326);
        Point point = geometryFactory.createPoint(new Coordinate(31.2357,30.0444));


        AddressModel address = AddressModel.builder().customer(customerModel).label("l")
                .street("s").city("c").floor("1").apartment("1")
                .direction("dir").state("st").country("co").zipCode("zip")
                .isDefault(true).location(point).build();
        addressRepository.save(address);
        System.out.println(address);

        return customerModel;
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
