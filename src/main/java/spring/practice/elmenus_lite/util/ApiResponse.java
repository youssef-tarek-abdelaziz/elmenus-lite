package spring.practice.elmenus_lite.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {
    private String statusMessage;
    private T data;
    private PageInfo page;

    public ApiResponse(String statusMessage, T data) {
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public ApiResponse(String statusMessage) {
        this(statusMessage, null);
    }

    public ApiResponse(T data) {
        this(null, data);
    }

    public ApiResponse(T data , PageInfo page) {
        this.data = data;
        this.page = page;
    }

}
