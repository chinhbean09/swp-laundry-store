package com.project.SWP391.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public abstract class SearchSpecification<S,T> implements Specification<T> {
    private static final long serialVersionUID = 1L;

    private S search;

    public SearchSpecification(S search) {
        this.search = search;
    }

    public abstract Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, S search);

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return this.toPredicate(root, query, criteriaBuilder, this.search);
    }

}
