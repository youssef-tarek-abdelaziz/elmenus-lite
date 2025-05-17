package spring.practice.elmenus_lite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.model.CartItemModel;
import spring.practice.elmenus_lite.model.CartModel;
import spring.practice.elmenus_lite.repository.CartItemRepository;
import spring.practice.elmenus_lite.repository.CartRepository;

import java.util.List;

@Service
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }


    public List<CartItemModel> getAllItems(Long id){
        return  cartItemRepository.findByCartId(id);
    }


    public void deleteCart(Long id){

        CartModel cartModel = cartRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cart with id " + id + " not found"));

        Integer itemsCount = cartItemRepository.countByCart(cartModel);
        if(itemsCount > 0 ){
            throw new IllegalStateException("Can't delete Cart. It have items!");
        }

        cartRepository.delete(cartModel);

    }

    public List<CartModel> getAllCarts(){
        return cartRepository.findAll();
    }
}
