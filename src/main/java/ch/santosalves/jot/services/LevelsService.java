package ch.santosalves.jot.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;

import ch.santosalves.jot.dtos.Level;
import ch.santosalves.jot.repositories.LevelsRepository;

@Service
@EntityScan(basePackageClasses = { ch.santosalves.jot.entities.Level.class, LevelsRepository.class })
public class LevelsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LevelsService.class);

	@Autowired
	LevelsRepository lr;

	public List<Level> getLevelsList() {
		Iterable<ch.santosalves.jot.entities.Level> levels = lr.findAll();
		List<Level> levels2 = new ArrayList<>();

		LOGGER.info("Going to get list of levels");

		for (ch.santosalves.jot.entities.Level level : levels) {
			levels2.add(Level.convertEntityToDto(level));
		}

		LOGGER.info("json levels = {}", levels2);

		return levels2;
	}

	public Level getLevelById(Integer id) {
		return Level.convertEntityToDto(lr.findOne(id));
	}
}
