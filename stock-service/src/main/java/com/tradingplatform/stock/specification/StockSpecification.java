package com.tradingplatform.stock.specification;

import org.springframework.data.jpa.domain.Specification;

import com.tradingplatform.stock.model.Stock;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class StockSpecification {

	public static Specification<Stock> hasSector(String sector) {
		return (Root<Stock> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			if (sector == null || sector.isEmpty()) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get("sector"), sector);
		};
	}

	public static Specification<Stock> hasIndustry(String industry) {
		return (Root<Stock> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			if (industry == null || industry.isEmpty()) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get("industry"), industry);
		};
	}
}
