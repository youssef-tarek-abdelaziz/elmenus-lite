package spring.practice.elmenus_lite.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.model.MenuItemModel;
import spring.practice.elmenus_lite.repository.MenuItemRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuItemValidator {

    private final MenuItemRepository menuItemRepository;

    public List<MenuItemModel> validateMenuItems(List<Integer> menuItemsIds) {
        List<MenuItemModel> menuItemModels = menuItemRepository.findByIdIn(menuItemsIds);
        List<Integer> menusIdsNotExist = menuItemModels.isEmpty() ? menuItemsIds : menuItemsIds.stream()
                .mapToInt(Integer::intValue)
                .filter(menuItemsId -> menuItemModels.stream()
                        .noneMatch(menuItemModel -> menuItemModel.getId().equals(menuItemsId)))
                .boxed().toList();
        if(!menusIdsNotExist.isEmpty()) {
            throw new BadRequestException(ErrorMessage.MENU_ITEM_IS_NOT_EXIST.getFinalMessage(menuItemsIds));
        }

        return menuItemModels;
    }
    public void verifyMenuItemsRestaurantMatch(List<MenuItemModel> menuItems) {
        long restaurantsCount = menuItems.stream()
                .mapToInt(menuItem -> menuItem.getMenuModel().getRestaurantModel().getId())
                .distinct().count();
        if(restaurantsCount > 1) {
            throw new BadRequestException(ErrorMessage.NOT_SAME_RESTAURANT.getFinalMessage());
        }
    }
}
