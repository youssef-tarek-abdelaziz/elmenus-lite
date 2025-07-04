package spring.practice.elmenus_lite.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.CreateResturantDto;
import spring.practice.elmenus_lite.dto.RestaurantResponseDto;
import spring.practice.elmenus_lite.service.RestaurantService;
import spring.practice.elmenus_lite.statusCode.SuccessStatusCode;
import spring.practice.elmenus_lite.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<ApiResponse<RestaurantResponseDto>> createRestaurant(@Valid @RequestBody CreateResturantDto createResturantDto) {
        RestaurantResponseDto restaurantResponseDto = restaurantService.createRestaurant(createResturantDto);
        ApiResponse<RestaurantResponseDto> response = new ApiResponse(SuccessStatusCode.RESTAURANT_CREATED_SUCCESSFULLY.getFinalMessage()
                , restaurantResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantResponseDto>> updateRestaurant(
            @PathVariable("id") Integer id, @Valid @RequestBody CreateResturantDto createResturantDto) {
        RestaurantResponseDto restaurantResponseDto = restaurantService.updateRestaurant(id, createResturantDto);
        ApiResponse<RestaurantResponseDto> response = new ApiResponse<>(SuccessStatusCode.RESTAURANT_UPDATED_SUCCESSFULLY.getFinalMessage()
                , restaurantResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantResponseDto>> getRestaurant(@PathVariable Integer id) {
        RestaurantResponseDto restaurantResponseDto = restaurantService.getRestaurantById(id);
        ApiResponse<RestaurantResponseDto> response = new ApiResponse<>(restaurantResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RestaurantResponseDto>>> getAllRestaurants() {
        List<RestaurantResponseDto> restaurantResponseDtoList = restaurantService.getAllRestaurants();
        ApiResponse<List<RestaurantResponseDto>> response = new ApiResponse<>(restaurantResponseDtoList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<RestaurantResponseDto>>> getActiveRestaurants() {
        List<RestaurantResponseDto> restaurantResponseDtoList = restaurantService.getActiveRestaurants();
        ApiResponse<List<RestaurantResponseDto>> response = new ApiResponse<>(restaurantResponseDtoList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Integer id) {
        restaurantService.deleteRestaurant(id);
        ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.RESTAURANT_DELETED_SUCCESSFULLY.getFinalMessage());
        return ResponseEntity.ok().body(response);
    }
}
