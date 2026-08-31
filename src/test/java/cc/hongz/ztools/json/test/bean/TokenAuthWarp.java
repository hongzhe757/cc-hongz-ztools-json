package cc.hongz.ztools.json.test.bean;

import org.junit.jupiter.api.DisplayName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenAuthWarp extends UUMap<TokenAuthResponse> {
	private static final long serialVersionUID = 1L;

	private String targetUrl;
	private String success;
}
