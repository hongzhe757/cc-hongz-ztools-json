package cc.hongz.ztools.json.test.bean;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;

import java.util.Map;

@Data
public class UserWithMap {
	private Map<String, String> data;
}
