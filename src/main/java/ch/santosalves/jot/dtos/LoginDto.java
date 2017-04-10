package ch.santosalves.jot.dtos;

public class LoginDto {
	private String nextUrl;

	public LoginDto() {
	}

	public LoginDto(String nextUrl) {
		this.nextUrl = nextUrl;
	}

	public String getNextUrl() {
		return nextUrl;
	}

	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}
}