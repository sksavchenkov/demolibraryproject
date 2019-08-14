package ru.bis.demolibraryproject.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.bis.demolibraryproject.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookSpecificationsBuilder<T> {
    private final List<SearchCriteria> params;

    public BookSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public BookSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<T> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(BookSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
