package spring.practice.elmenus_lite.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.exception.EntityNotFoundException;
import spring.practice.elmenus_lite.mapper.CartModelDtoMapper;
import spring.practice.elmenus_lite.model.CartItemModel;
import spring.practice.elmenus_lite.model.CartModel;
import spring.practice.elmenus_lite.model.MenuItemModel;
import spring.practice.elmenus_lite.repository.CartItemRepository;
import spring.practice.elmenus_lite.repository.CartRepository;
import spring.practice.elmenus_lite.repository.MenuItemRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final MenuItemRepository menuItemRepository;

    private final CartModelDtoMapper cartModelDtoMapper;

    @Transactional(readOnly = true)
    public Set<CartItemModel> getAllItems(Integer cartId) {
        CartModel cartModel = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CART_NOT_FOUND.getFinalMessage(List.of(cartId))));
        return cartModel.getCartItems();
    }

    public List<CartItemModel> updateCartItems(Integer cartId, List<CartItemModel> cartItems) {
//        Optional<CartModel> cartModelOptional = cartRepository.findById(cartId);
//        CartModel cartModel = cartModelOptional.orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CART_NOT_FOUND.getMessage()));

//        Set<CartItemModel> oldItems = cartModel.getCartItems();
//        oldItems.addAll(cartItems);
//        oldItems.removeAll(oldItems.stream().filter(oldItem -> oldItem.get))
//        oldItems.stream()
//                .filter(oldItem -> cartItems
//                        .stream()
//                        .noneMatch(newItem -> newItem.getMenuItem().equals(oldItem.getMenuItem())
//                        && oldItem.getQuantity().equals(newItem.getQuantity())))
//                .collect(Collectors.toSet());
        return null;
    }

    @Transactional
    public void deleteCart(Integer cartId){

        CartModel cartModel = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CART_NOT_FOUND.getFinalMessage(List.of(cartId))));
//        if(!cartModel.getCartItems().isEmpty()) {
//            cartItemRepository.deleteAll(cartModel.getCartItems());
//        }
        cartRepository.delete(cartModel);
    }

    public void clearCart(Integer cartId) {

        CartModel cartModel = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CART_NOT_FOUND.getFinalMessage(List.of(cartId))));
        if(cartModel.getCartItems().isEmpty()) {
            throw new BadRequestException(ErrorMessage.EMPTY_CART.getFinalMessage(List.of(cartId)));
        }
        cartItemRepository.deleteAll(cartModel.getCartItems());
    }

    @Transactional
    public void addItemsToCart(Integer cartId, List<CartItemDto> cartItemDtos) {
        CartModel cartModel = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CART_NOT_FOUND.getFinalMessage(List.of(cartId))));
        List<Integer> menuItemsIds = cartItemDtos.stream().map(CartItemDto::getMenuItemId).toList();
        List<MenuItemModel> menuItemModels = menuItemRepository.findAllByIdIn(menuItemsIds);
        List<Integer> menusIdsNotExist = menuItemsIds.stream()
                .mapToInt(Integer::intValue)
                .filter(menuItemsId -> menuItemModels.stream()
                        .noneMatch(menuItemModel -> menuItemModel.getId().equals(menuItemsId)))
                .boxed().toList();
        if(!menusIdsNotExist.isEmpty()) {
            throw new BadRequestException(ErrorMessage.MENU_ITEM_IS_NOT_EXIST.getFinalMessage(menusIdsNotExist));
        }
        List<CartItemModel> cartItemModels = cartModelDtoMapper.mapCartItemDtosToModels(cartItemDtos, cartModel, menuItemModels);
        cartItemRepository.saveAll(cartItemModels);
    }
}
