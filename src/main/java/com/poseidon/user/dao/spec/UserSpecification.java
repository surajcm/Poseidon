package com.poseidon.user.dao.spec;

import com.poseidon.init.specs.SearchCriteria;
import com.poseidon.init.specs.SearchOperation;
import com.poseidon.user.dao.entities.User;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {

    public static final long serialVersionUID = 4328743;
    
    private final List<SearchCriteria> list;

    public UserSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(final SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        //Create a new predicate list
        List<Predicate> predicates = new ArrayList<>();

        //Add criteria to predicates
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(
                        root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
