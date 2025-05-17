package spring.practice.elmenus_lite.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {
    private String statusMessage;
    private T data;

    public ApiResponse(String statusMessage, T data) {
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public ApiResponse(String statusMessage) {
        this(statusMessage, null);
    }
}
