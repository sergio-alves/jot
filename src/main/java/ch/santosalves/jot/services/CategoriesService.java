package ch.santosalves.jot.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;

import ch.santosalves.jot.repositories.CategoriesRepository;

@Service
@EntityScan(basePackageClasses = { ch.santosalves.jot.entities.Category.class, CategoriesRepository.class })
public class CategoriesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoriesService.class);

	@Autowired
	CategoriesRepository cr;

	public Map<Integer, String> getCategories() {
		Iterable<ch.santosalves.jot.entities.Category> cats = cr.findAll();
		Map<Integer, String> categories = new HashMap<>();

		cats.forEach(c -> categories.put(c.getId(), c.getResource().getContent()));

		return categories;
	}
}
