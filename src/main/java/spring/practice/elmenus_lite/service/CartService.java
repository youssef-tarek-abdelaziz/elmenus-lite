package spring.practice.elmenus_lite.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.CartItemResponseDto;
import spring.practice.elmenus_lite.dto.CartResponseDto;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.exception.EntityNotFoundException;
import spring.practice.elmenus_lite.mapper.CartModelDtoMapper;
import spring.practice.elmenus_lite.model.CartItemModel;
import spring.practice.elmenus_lite.model.CartModel;
import spring.practice.elmenus_lite.model.MenuItemModel;
import spring.practice.elmenus_lite.model.UserModel;
import spring.practice.elmenus_lite.repository.CartItemRepository;
import spring.practice.elmenus_lite.repository.CartRepository;
import spring.practice.elmenus_lite.repository.UserRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;
import spring.practice.elmenus_lite.util.CartItemValidator;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CartModelDtoMapper cartModelDtoMapper;
    private final CartItemValidator cartItemValidator;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public CartResponseDto getCartByCustomerId(Integer customerId) {
        UserModel user = userRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getFinalMessage(List.of(customerId))));

        CartModel cartModel = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getFinalMessage(List.of(customerId))));

        List<CartItemResponseDto> items = cartModelDtoMapper.toCartItemResponseDtoList(new ArrayList<>(cartModel.getCartItems()));
        return cartModelDtoMapper.maptoCartResponseDto(cartModel.getId(), items);
    }

    @Transactional(readOnly = true)
    public CartResponseDto getAllItems(Integer cartId) {
        CartModel cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CART_NOT_FOUND.getFinalMessage(List.of(cartId))));

        List<CartItemResponseDto> items = cartModelDtoMapper
                .toCartItemResponseDtoList(new ArrayList<>(cart.getCartItems()));
        return cartModelDtoMapper.maptoCartResponseDto(cartId, items);
    }

    @Transactional
    public void clearCart(Integer cartId) {

        CartModel cartModel = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CART_NOT_FOUND.getFinalMessage(List.of(cartId))));

        if(cartModel.getCartItems().isEmpty()) {
            throw new BadRequestException(ErrorMessage.EMPTY_CART.getFinalMessage(List.of(cartId)));
        }
        List<Integer> itemsIds = cartModel.getCartItems().stream().map(CartItemModel::getId).toList();
        cartItemRepository.deleteAllByIdInBatch(itemsIds);
    }

    @Transactional
    public void addItemsToCart(Integer cartId, List<CartItemDto> cartItemDtos) {
        CartModel cartModel = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CART_NOT_FOUND.getFinalMessage(List.of(cartId))));

        List<MenuItemModel> menuItemModels = cartItemValidator.validateMenuItems(cartItemDtos);
        Set<CartItemModel> existingItems = cartModel.getCartItems();

        for (CartItemDto newItem : cartItemDtos) {
            Optional<CartItemModel> existingModel = existingItems.stream()
                    .filter(existingItem -> existingItem.getId().equals(newItem.getId()) && !existingItem.getQuantity().equals(newItem.getQuantity()))
                    .findFirst();
            if(existingModel.isPresent()) {
                existingItems.remove(existingModel.get());
                existingModel.get().setQuantity(newItem.getQuantity());
                existingItems.add(existingModel.get());
            }
        }

        List<Integer> removedItems = existingItems.stream().map(CartItemModel::getId).filter(existId -> cartItemDtos.stream().noneMatch(newItem -> existId.equals(newItem.getId()))).toList();
        cartItemRepository.deleteAllByIdInBatch(removedItems);

        List<CartItemDto> newItems = cartItemDtos.stream().filter(newItem -> newItem.getId() == null).toList();
        existingItems.addAll(new HashSet<>(cartModelDtoMapper.mapCartItemDtosToModels(newItems, cartModel, menuItemModels)));
        cartItemRepository.saveAll(existingItems);
    }
}
