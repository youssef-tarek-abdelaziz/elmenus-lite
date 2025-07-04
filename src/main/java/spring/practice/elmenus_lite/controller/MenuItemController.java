package spring.practice.elmenus_lite.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.apiDto.MenuItemApiDto;
import spring.practice.elmenus_lite.apiDto.MenuItemApiRequestDto;
import spring.practice.elmenus_lite.apiDto.UpdatedMenuItemRequest;
import spring.practice.elmenus_lite.dto.MenuItemDto;
import spring.practice.elmenus_lite.mapper.MenuApiDtoMapper;
import spring.practice.elmenus_lite.service.MenuItemService;
import spring.practice.elmenus_lite.statusCode.SuccessStatusCode;
import spring.practice.elmenus_lite.util.ApiResponse;

@RestController
@RequestMapping(path = "api/v1/menu-items")
@AllArgsConstructor
public class MenuItemController {

   private final MenuItemService menuItemService;
   private final MenuApiDtoMapper menuItemApiDtoMapper;

    @GetMapping(path = "/{menuItemId}")
    public ResponseEntity<?> getMenuItemDetails(@PathVariable("menuItemId") Integer menuItemId){
        MenuItemDto menuItemDto = menuItemService.getMenuItemDetails(menuItemId);
        MenuItemApiDto menuItemApiDto = menuItemApiDtoMapper.toMenuItemApiDto(menuItemDto);
        ApiResponse<?> response = new ApiResponse<>(menuItemApiDto);
        return ResponseEntity.ok().body(response.getData());

    }

    @PostMapping
    public ResponseEntity<?> addNewMenuItem(@Valid @RequestBody MenuItemApiRequestDto menuItemApiRequestDto){
       menuItemService.addNewMenuItem(menuItemApiRequestDto);
       ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.MENU_ITEM_ADDED_SUCCESSFULLY.getFinalMessage());
       return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<?> updateMenuItem(@PathVariable("menuItemId") Integer menuItemId , @Valid @RequestBody UpdatedMenuItemRequest menuItemRequest){
        menuItemService.updateMenuItem(menuItemId,menuItemRequest);
        ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.MENU_ITEM_UPDATED_SUCCESSFULLY.getFinalMessage());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable("menuItemId") Integer menuItemId){
        menuItemService.deleteMenuItem(menuItemId);
        ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.MENU_ITEM_DELETED_SUCCESSFULLY.getFinalMessage());
        return ResponseEntity.ok().body(response);
    }

}
