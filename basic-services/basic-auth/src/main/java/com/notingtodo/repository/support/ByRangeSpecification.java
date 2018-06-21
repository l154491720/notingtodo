package com.notingtodo.repository.support;



import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static com.google.common.collect.Iterables.toArray;

/**
 * Created by qilin.liu on 2018/6/20.
 */
public class ByRangeSpecification<E> implements Specification<E> {

    private final List<Range<E>> ranges;

    public  ByRangeSpecification(List<Range<E>> ranges) {
        this.ranges = ranges;
    }

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList();

        for (Range<E> range : ranges) {
            if (range.isSet()) {
                Predicate rangePredicate = buildRangePredicate(range, root, builder);

                if (rangePredicate != null) {
                    if (!range.isIncludeNullSet() || range.getIncludeNull() == FALSE) {
                        predicates.add(rangePredicate);
                    } else {
                        predicates.add(builder.or(rangePredicate, builder.isNull(root.get(range.getField()))));
                    }
                }

                if (TRUE == range.getIncludeNull()) {
                    predicates.add(builder.isNull(root.get(range.getField())));
                } else if (FALSE == range.getIncludeNull()) {
                    predicates.add(builder.isNotNull(root.get(range.getField())));
                }
            }
        }
        return predicates.isEmpty() ? builder.conjunction() : builder.and(toArray(predicates, Predicate.class));
    }

    private Predicate buildRangePredicate(Range<E> range, Root<E> root, CriteriaBuilder builder) {
        if (range.isBetween()) {
            return builder.between(root.get(range.getField()), range.getFrom(), range.getTo());
        } else if (range.isFromSet()) {
            return builder.greaterThanOrEqualTo(root.get(range.getField()), range.getFrom());
        } else if (range.isToSet()) {
            return builder.lessThanOrEqualTo(root.get(range.getField()), range.getTo());
        }
        return null;
    }

}
