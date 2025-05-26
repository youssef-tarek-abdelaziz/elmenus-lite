package spring.practice.elmenus_lite.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CartModelDtoMapper cartModelDtoMapper;

    private CartModel cart;
    private MenuItemModel menuItem;
    private CartItemDto cartItemDto;
    private CartItemModel cartItem;

    private List<CartItemDto> cartItemDtos;
    private List<MenuItemModel> menuItemModels;

    @BeforeEach
    void setUp() {
        //Create Cart Model
        cart = new CartModel();
        cart.setId(1);
        cart.setCartItems(new HashSet<>());

        //Create Menu Item
        menuItem = new MenuItemModel();
        menuItem.setId(100);
        menuItem.setMenuItemName("Pizza");
        menuItem.setAvailable(true);
        menuItem.setPrice(30);

        //Create Cart Item Dto (request that is sent in parameter of method)
        cartItemDto = new CartItemDto();
        cartItemDto.setMenuItemId(100);
        cartItemDto.setQuantity(2);

        //Create Cart item model
        cartItem = new CartItemModel();
        cartItem.setCart(cart);
        cartItem.setMenuItem(menuItem);
        cartItem.setQuantity(2);

        cartItemDtos = List.of(cartItemDto);
        menuItemModels = List.of(menuItem);
    }

    @Test
    void addItemsToCart_ShouldThrowBadRequestException_WhenMenuItemIsNotFound() {
        //Arrange
        Mockito.when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        Mockito.when(menuItemRepository.findAllByIdIn(List.of(100))).thenReturn(List.of());

        Assertions.assertThrows(BadRequestException.class,
                () -> cartService.addItemsToCart(1,cartItemDtos));
    }

    @Test
    void addItemsToCart_shouldThrowEntityNotFound_whenCartDoesNotExist(){
        Mockito.when(cartRepository.findById(99)).thenReturn(Optional.empty());
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> cartService.addItemsToCart(99,cartItemDtos));
        Assertions.assertTrue(exception.getMessage().contains("doesn't exist"));
    }

    @Test
    void addItemsToCart_ShouldAddNewCartItem_WhenItemIsNotInCart() {

        Mockito.when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        Mockito.when(menuItemRepository.findAllByIdIn(List.of(100))).thenReturn(menuItemModels);
        Mockito.when(cartModelDtoMapper.mapCartItemDtoToModel(Mockito.any(),Mockito.any(),Mockito.any()))
                .thenReturn(cartItem);

        cartService.addItemsToCart(1, cartItemDtos);
        Mockito.verify(cartRepository).findById(1);
        Mockito.verify(menuItemRepository).findAllByIdIn(List.of(100));
        Mockito.verify(cartModelDtoMapper).mapCartItemDtoToModel(Mockito.any(),Mockito.any(),Mockito.any());
        Mockito.verify(cartItemRepository).saveAll(Mockito.anyList());

    }

    @Test
    void addItemsToCart_ShouldUpdateQuantity_WhenItemExistsInCart() {
        Mockito.when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        Mockito.when(menuItemRepository.findAllByIdIn(List.of(100))).thenReturn(menuItemModels);
        //Create Cart Item to exist in cart
        CartItemModel existingCartItem = new CartItemModel();
        existingCartItem.setMenuItem(menuItem);
        existingCartItem.setQuantity(3);
        existingCartItem.setCart(cart);

        cart.setCartItems(Set.of(existingCartItem));

        cartService.addItemsToCart(1, cartItemDtos);

        Assertions.assertEquals(5,existingCartItem.getQuantity());
        Mockito.verify(cartItemRepository).saveAll(Mockito.anyList());
    }
}