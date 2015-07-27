package com.techstack.component.jpa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.techstack.component.jpa.SearchFilter.Logic;


public class DynamicSpecifications {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> clazz) {
		return new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (!filters.isEmpty()) {
					Predicate predicates = null;
					for (SearchFilter filter : filters) {
						Predicate predicate = null;
						String[] names = StringUtils.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						
						// the one-to-many search， 但是还未实现多级
						if(expression.getJavaType().equals(List.class)){
							Join namesJoin = root.join(names[0]);
							expression = namesJoin.get(names[1]);
							for (int i = 2; i < names.length; i++) {
								expression = expression.get(names[i]);
							}
							
						}else{
							// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
							for (int i = 1; i < names.length; i++) {
								expression = expression.get(names[i]);
							}
						}
						
						
						Object value = filter.value;
						
						if(Date.class.equals(expression.getJavaType())){
							try {
								value = dateFormat.parse(value.toString());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								value = filter.value;
							}
						}
						
						
						// logic operator
						switch (filter.operator) {
							case EQ:
								predicate = builder.equal(expression, value);
								break;
							case NEQ:
								predicate = builder.notEqual(expression, value);
								break;
							case LIKE:
								predicate = builder.like(builder.upper(expression),"%" + value.toString().toUpperCase() + "%");
								break;
							case GT:
								predicate = builder.greaterThan(expression, (Comparable) value);
								break;
							case LT:
								predicate = builder.lessThan(expression, (Comparable) value);
								break;
							case GTE:
								predicate = builder.greaterThanOrEqualTo(expression, (Comparable) value);
								break;
							case LTE:
								predicate = builder.lessThanOrEqualTo(expression, (Comparable) value);
								break;
							case IN:
						        In<Object> in = builder.in(expression);
						        for(String val : value.toString().split(",")){
						        	in.value((Comparable) val);
						        }
						        predicate = in;
								break;
						}
						
						if(predicate!=null){
							if(filter.logic == Logic.OR){
								if(predicates == null){
									predicates = builder.or(predicate);
								}else{
									predicates = builder.or(predicates,predicate);
								}
							}else{
								if(predicates == null){
									predicates = builder.and(predicate);
								}else{
									predicates = builder.and(predicates,predicate);
								}
							}
						}
					}

					return predicates;
				}

				return builder.conjunction();
			}
		};
	}
}
