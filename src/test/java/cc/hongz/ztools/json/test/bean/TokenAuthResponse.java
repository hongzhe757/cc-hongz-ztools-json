package cc.hongz.ztools.json.test.bean;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;

@Data
public class TokenAuthResponse {
	private String token;
	private String userId;
}
