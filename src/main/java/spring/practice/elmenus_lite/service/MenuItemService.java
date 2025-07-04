package spring.practice.elmenus_lite.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.practice.elmenus_lite.apiDto.MenuItemApiRequestDto;
import spring.practice.elmenus_lite.apiDto.UpdatedMenuItemRequest;
import spring.practice.elmenus_lite.dto.MenuItemDto;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.mapper.MenuApiDtoMapper;
import spring.practice.elmenus_lite.model.MenuItemModel;
import spring.practice.elmenus_lite.model.OrderItemModel;
import spring.practice.elmenus_lite.repository.MenuItemRepository;
import spring.practice.elmenus_lite.repository.MenuRepository;
import spring.practice.elmenus_lite.repository.OrderItemRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuApiDtoMapper menuItemApiDtoMapper;
    private final MenuRepository menuRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public MenuItemDto getMenuItemDetails(Integer menuItemId){
        MenuItemModel menuItemModel = menuItemRepository.findById(menuItemId)
                .orElseThrow(()-> new BadRequestException(ErrorMessage.MENU_ITEM_IS_NOT_EXIST.getFinalMessage(List.of(menuItemId))));
        return menuItemApiDtoMapper.toMenuItemDto(menuItemModel);

    }

    @Transactional
    public void addNewMenuItem(MenuItemApiRequestDto menuItemApiRequestDto){
        MenuItemModel menuItemModel = menuItemApiDtoMapper.toMenuItemModelFromRequest(menuItemApiRequestDto , true , menuRepository);
        menuItemRepository.save(menuItemModel);
    }

    @Transactional
    public void updateMenuItem(Integer menuItemId,UpdatedMenuItemRequest menuItemRequest){
        MenuItemModel menuItemModel = menuItemRepository.findById(menuItemId)
                .orElseThrow(()-> new BadRequestException(ErrorMessage.MENU_ITEM_IS_NOT_EXIST.getFinalMessage(List.of(menuItemId))));
        menuItemModel.setMenuItemName(menuItemRequest.getItemName());
        menuItemModel.setAvailable(menuItemRequest.getAvailable());
        menuItemModel.setPrice(menuItemRequest.getPrice());
    }

    @Transactional
    public void deleteMenuItem(Integer menuItemId){
        MenuItemModel menuItemModel = menuItemRepository.findById(menuItemId)
                .orElseThrow(()-> new BadRequestException(ErrorMessage.MENU_ITEM_IS_NOT_EXIST.getFinalMessage(List.of(menuItemId))));
        boolean isRelatedToOrder = orderItemRepository.existsByMenuItemId(menuItemId);
        if(isRelatedToOrder){
        OrderItemModel orderItem = orderItemRepository.findByMenuItemId(menuItemId);
        orderItemRepository.deleteById(orderItem.getId());
        }
        menuItemRepository.deleteById(menuItemId);
    }
}
