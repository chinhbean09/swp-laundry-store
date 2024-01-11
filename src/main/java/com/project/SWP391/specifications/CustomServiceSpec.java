package com.project.SWP391.specifications;


import com.project.SWP391.entities.Cloth;
import com.project.SWP391.entities.Laundry;
import com.project.SWP391.entities.Material;

import com.project.SWP391.requests.SpecialServiceFilterRequest;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomServiceSpec extends SearchSpecification<SpecialServiceFilterRequest, Laundry> {

    private static final long serialVersionUID = 1L;

    public CustomServiceSpec(SpecialServiceFilterRequest search) {
        super(search);
    }


    @Override
    public Predicate toPredicate(Root<Laundry> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, SpecialServiceFilterRequest search) {
        Join<Laundry, Material> material = root.join("materials", JoinType.INNER);
        Join<Laundry, Cloth> cloth = root.join("cloth", JoinType.INNER);
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (StringUtils.isNotEmpty(search.getName())) {
            Predicate likeName = criteriaBuilder.like(root.get("name"), "%" + search.getName() + "%" );
            predicates.add(likeName);
        }

        if (search.getMaterials() != null) {

                Predicate eqMaterials = criteriaBuilder.in(material.get("id")).value(search.getMaterials());
                predicates.add(eqMaterials);
        }

        if (search.getClothId() != null) {

                Predicate eqCloth = criteriaBuilder.equal(cloth.get("id"), search.getClothId());
                predicates.add(eqCloth);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
