package com.college.erp.collegemanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PagablePage<T> {
    private int currentPage;
    private List<Integer> pageList = new ArrayList<>();
    private int lastpage;
    private long totalResults;
    private List<T> objects = new ArrayList<>();
    private T object;

    public static <T> PagablePage<T> from(Page<T> page) {
        PagablePage<T> pagablePage = new PagablePage<>();
        pagablePage.setCurrentPage(page.getNumber() + 1);
        pagablePage.setLastpage(page.getTotalPages());
        pagablePage.setTotalResults(page.getTotalElements());
        pagablePage.setObjects(page.getContent());
        pagablePage.setPageList(buildPageList(page.getNumber() + 1, page.getTotalPages()));
        return pagablePage;
    }

    public static int normalizePage(Integer page) {
        return page == null || page < 1 ? 1 : page;
    }

    public static int normalizeSize(Integer size) {
        if (size == null || size < 1) {
            return 10;
        }
        return Math.min(size, 100);
    }

    private static List<Integer> buildPageList(int currentPage, int lastPage) {
        List<Integer> pages = new ArrayList<>();
        if (lastPage < 1) {
            return pages;
        }
        int start = Math.max(1, currentPage - 2);
        int end = Math.min(lastPage, currentPage + 2);
        for (int index = start; index <= end; index++) {
            pages.add(index);
        }
        return pages;
    }
}
