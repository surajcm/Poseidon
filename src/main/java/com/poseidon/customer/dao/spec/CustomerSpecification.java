package com.poseidon.customer.dao.spec;

import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.init.specs.SearchCriteria;
import com.poseidon.init.specs.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification  implements Specification<Customer> {
    private final List<SearchCriteria> list;

    public CustomerSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(final SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(final Root<Customer> root,
                                 final CriteriaQuery<?> query,
                                 final CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            }
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
