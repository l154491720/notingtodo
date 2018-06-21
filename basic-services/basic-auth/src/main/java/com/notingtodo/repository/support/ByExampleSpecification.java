package com.notingtodo.repository.support;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by qilin.liu on 2018/6/20.
 */
public class ByExampleSpecification<E> implements Specification<E> {

    private final Example<E> example;

    public ByExampleSpecification(Example example) {
        Assert.notNull(example,"Example must not be null!");
        this.example = example;
    }

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return QueryByExamplePredicateBuilder.getPredicate(root,criteriaBuilder,example);
    }
}
