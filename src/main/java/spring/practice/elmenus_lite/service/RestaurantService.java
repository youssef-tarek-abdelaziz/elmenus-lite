package spring.practice.elmenus_lite.service;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.CreateResturantDto;
import spring.practice.elmenus_lite.dto.RestaurantResponseDto;
import spring.practice.elmenus_lite.exception.EntityNotFoundException;
import spring.practice.elmenus_lite.mapper.CategoryMapper;
import spring.practice.elmenus_lite.model.Category;
import spring.practice.elmenus_lite.model.RestaurantDetails;
import spring.practice.elmenus_lite.model.RestaurantModel;
import spring.practice.elmenus_lite.repository.CategoryRepository;
import spring.practice.elmenus_lite.repository.RestaurantDetailsRepository;
import spring.practice.elmenus_lite.repository.RestaurantRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantDetailsRepository restaurantDetailsRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public RestaurantResponseDto createRestaurant(CreateResturantDto restaurantDto) {
        Set<Category> categories = validateAndGetCategories(restaurantDto.getCategoryIds());
        Point pointLocation = createPointLocation(restaurantDto.getLongitude(), restaurantDto.getLatitude());

        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setRestaurantName(restaurantDto.getName());
        restaurant.setActive(restaurantDto.getActive());
        restaurant.setCategories(categories);

        restaurant = restaurantRepository.save(restaurant);

        RestaurantDetails restaurantDetails = new RestaurantDetails();
        restaurantDetails.setRestaurant(restaurant);
        restaurantDetails.setDescription(restaurantDto.getDescription());
        restaurantDetails.setPhone(restaurantDto.getPhone());
        restaurantDetails.setEstimatedDeliveryTime(restaurantDto.getEstimatedDeliveryTime());
        restaurantDetails.setOpenTime(restaurantDto.getOpenTime());
        restaurantDetails.setCloseTime(restaurantDto.getCloseTime());
        restaurantDetails.setLocation(pointLocation);

        restaurantDetails = restaurantDetailsRepository.save(restaurantDetails);

        return buildResponseDto(restaurant, restaurantDetails);
    }

    public RestaurantResponseDto updateRestaurant(Integer restaurantId, CreateResturantDto restaurantDto) {
        RestaurantModel restaurant = getRestaurant(restaurantId);
        RestaurantDetails restaurantDetails = restaurantDetailsRepository.findByRestaurant(restaurant)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.RESTAURANT_DETAILS_NOT_EXIST.getFinalMessage()));

        Set<Category> categories = validateAndGetCategories(restaurantDto.getCategoryIds());
        // Update restaurant
        restaurant.setRestaurantName(restaurantDto.getName());
        restaurant.setActive(restaurantDto.getActive());
        restaurant.setCategories(categories);

        // Update restaurant details
        restaurantDetails.setDescription(restaurantDto.getDescription());
        restaurantDetails.setPhone(restaurantDto.getPhone());
        restaurantDetails.setEstimatedDeliveryTime(restaurantDto.getEstimatedDeliveryTime());
        restaurantDetails.setOpenTime(restaurantDto.getOpenTime());
        restaurantDetails.setCloseTime(restaurantDto.getCloseTime());
        Point pointLocation = createPointLocation(restaurantDto.getLongitude(), restaurantDto.getLatitude());
        restaurantDetails.setLocation(pointLocation);

        restaurant = restaurantRepository.save(restaurant);
        restaurantDetails = restaurantDetailsRepository.save(restaurantDetails);

        return buildResponseDto(restaurant, restaurantDetails);
    }

    public RestaurantResponseDto getRestaurantById(Integer restaurantId) {
        RestaurantModel restaurant = getRestaurant(restaurantId);
        RestaurantDetails restaurantDetails = getRestaurantDetails(restaurant);
        return buildResponseDto(restaurant, restaurantDetails);
    }

    public List<RestaurantResponseDto> getAllRestaurants() {
        List<RestaurantModel> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.NO_RESTAURANTS_FOUND.getFinalMessage());
        }

        return restaurants.stream()
                .map(restaurant -> {
                    RestaurantDetails restaurantDetails = getRestaurantDetails(restaurant);
                    return buildResponseDto(restaurant, restaurantDetails);
                }).collect(Collectors.toList());
    }

    public List<RestaurantResponseDto> getActiveRestaurants() {
        List<RestaurantModel> restaurants = restaurantRepository.findByActiveTrue();
        if (restaurants.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.NO_RESTAURANTS_FOUND.getFinalMessage());
        }

        return restaurants.stream()
                .map(restaurant -> {
                    RestaurantDetails restaurantDetails = getRestaurantDetails(restaurant);
                    return buildResponseDto(restaurant, restaurantDetails);
                }).collect(Collectors.toList());
    }

    public void deleteRestaurant(Integer restaurantId) {
        RestaurantModel restaurant = getRestaurant(restaurantId);
        restaurantDetailsRepository.deleteByRestaurant(restaurant);
        restaurantRepository.delete(restaurant);
    }

    private RestaurantModel getRestaurant(Integer restaurantId) {
        return restaurantRepository.findById(restaurantId).
                orElseThrow(() -> new EntityNotFoundException(ErrorMessage.RESTAURANT_IS_NOT_EXIST.getFinalMessage()));
    }

    private RestaurantDetails getRestaurantDetails(RestaurantModel restaurant) {
        return restaurantDetailsRepository.findByRestaurant(restaurant).
                orElseThrow(() -> new EntityNotFoundException(ErrorMessage.RESTAURANT_DETAILS_NOT_EXIST.getFinalMessage()));
    }

    private Point createPointLocation(Double longitude, Double latitude) {
        // Validate coordinate ranges
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }

        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }

        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(4326);
        return point;
    }

    private RestaurantResponseDto buildResponseDto(RestaurantModel restaurant, RestaurantDetails restaurantDetails) {
        return RestaurantResponseDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getRestaurantName())
                .categories(categoryMapper.toCategoriesDtos(restaurant.getCategories()))
                .active(restaurant.isActive())
                .description(restaurantDetails.getDescription())
                .phone(restaurantDetails.getPhone())
                .estimatedDeliveryTime(restaurantDetails.getEstimatedDeliveryTime())
                .openTime(restaurantDetails.getOpenTime())
                .closeTime(restaurantDetails.getCloseTime())
                .longitude(restaurantDetails.getLocation().getX())
                .latitude(restaurantDetails.getLocation().getY())
                .createdAt(restaurantDetails.getCreatedAt())
                .build();
    }

    private Set<Category> validateAndGetCategories(Set<Integer> categoryIds) {
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
        if (categories.size() != categoryIds.size()) {
            throw new IllegalArgumentException("one or more categories are not valid");
        }
        return categories;
    }
}
