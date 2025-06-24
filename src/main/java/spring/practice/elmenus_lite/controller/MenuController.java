package spring.practice.elmenus_lite.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.apiDto.MenuAPiRequestDto;
import spring.practice.elmenus_lite.apiDto.MenuApiDto;
import spring.practice.elmenus_lite.apiDto.OrderResponseApiDto;
import spring.practice.elmenus_lite.apiDto.UpdatedMenuNameRequest;
import spring.practice.elmenus_lite.dto.MenuDto;
import spring.practice.elmenus_lite.dto.MenuItemDto;
import spring.practice.elmenus_lite.mapper.MenuApiDtoMapper;
import spring.practice.elmenus_lite.repository.MenuRepository;
import spring.practice.elmenus_lite.service.MenuService;
import spring.practice.elmenus_lite.statusCode.SuccessStatusCode;
import spring.practice.elmenus_lite.util.ApiResponse;
import spring.practice.elmenus_lite.util.PageInfo;

@RestController
@RequestMapping("api/v1/menus")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final MenuApiDtoMapper menuApiDtoMapper;


    // without pagination
//    @GetMapping(path = "/{menuId}")
//    public ResponseEntity<?> getMenuDetails(@PathVariable("menuId") Integer menuId){
//        MenuDto menuDto = menuService.getMenuDetails(menuId);
//        MenuApiDto menuApiDto = menuApiDtoMapper.toMenuApiDto(menuDto);
//        ApiResponse<?> response = new ApiResponse<>(menuApiDto);
//        return ResponseEntity.ok().body(response.getData());
//    }

    // with pagination

//    @GetMapping("/{menuId}")
//    public ResponseEntity<?> getMenuDetails(
//            @PathVariable("menuId") Integer menuId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        MenuDto menuDto = menuService.getMenuDetails(menuId, pageable);
//
//        return ResponseEntity.ok().body(menuDto);
//    }

    @GetMapping("/{menuId}")
    public ResponseEntity<?> getMenuDetails(
            @PathVariable Integer menuId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(required = false) Integer size) {

        int pageSize = (size == null) ? menuService.countMenuSize(menuId) : size;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<MenuDto> menuItemsPage = menuService.getMenuDetails(menuId, pageable);

        PageInfo pageInfo = new PageInfo(
                menuItemsPage.getSize(),
                menuItemsPage.getNumber(),
                (int) menuItemsPage.getTotalElements(),
                menuItemsPage.getTotalPages()
        );

        ApiResponse<?> response = new ApiResponse<>(menuItemsPage.getContent(), pageInfo);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping(path = "/restaurant/{restaurantId}/all-menus")
    public ResponseEntity<?> getAllMenusForRestaurant(
            @PathVariable("restaurantId") Integer restaurantId,
            @RequestParam(defaultValue = "0" , required = false) int page,
            @RequestParam(required = false) Integer size){
        int pageSize = (size == null) ? menuService.countRestaurantSize(restaurantId) : size;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<MenuDto> menuDto = menuService.getAllMenusForRestaurant(restaurantId, pageable);
        PageInfo pageInfo = new PageInfo(
                menuDto.getSize(),
                menuDto.getNumber(),
                (int) menuDto.getTotalElements(),
                menuDto.getTotalPages()
        );
        Page<MenuApiDto> menusApiDtoPage = menuApiDtoMapper.mapMenuApiDtoToPage(menuDto);
        ApiResponse<?> response = new ApiResponse<>(menusApiDtoPage.getContent(), pageInfo);
        return ResponseEntity.ok().body(response);

    }

    @PostMapping
    public ResponseEntity<?> addNewMenu(@Valid @RequestBody MenuAPiRequestDto menuAPiRequestDto){
        menuService.addNewMenu(menuAPiRequestDto);
        ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.MENU_ADDED_SUCCESSFULLY.getFinalMessage());
        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/{menuId}")
    public ResponseEntity<?> updateMenuName(@PathVariable("menuId") Integer menuId, @RequestBody UpdatedMenuNameRequest menuName){
        menuService.updateMenuName(menuId,menuName.getMenuName());
        ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.MENU_NAME_UPDATED_SUCCESSFULLY.getFinalMessage());
        return ResponseEntity.ok().body(response);

    }



    @DeleteMapping("/{menuId}")
    public ResponseEntity<?> deleteMenu(@PathVariable("menuId") Integer menuId){
        menuService.deleteMenu(menuId);
        ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.MENU_DELETED_SUCCESSFULLY.getFinalMessage());
        return ResponseEntity.ok().body(response);

    }

}
