package ch.santosalves.jot.dtos;

import java.time.LocalDateTime;
import java.time.ZoneId;

import ch.santosalves.jot.entities.LogSession;

public class LoggedSession {
	private String ip;
	private Integer level;
	private String email;
	private LocalDateTime date;

	public String getIp() {
		return ip;
	}

	public Integer getLevel() {
		return level;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public static LoggedSession convertEntityToDto(LogSession ls) {
		LoggedSession lss = new LoggedSession();
		lss.email = ls.getEmail();
		lss.ip = ls.getIp();
		lss.level = ls.getLevel();
		lss.date = LocalDateTime.ofInstant(ls.getDate().toInstant(), ZoneId.of("Z"));
		return lss;
	}
}
