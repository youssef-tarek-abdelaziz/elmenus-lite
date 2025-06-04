package spring.practice.elmenus_lite.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageInfo {

    private int pageSize;
    private int pageNumber;
    private int totalElements;
    private int totalPages;


}
