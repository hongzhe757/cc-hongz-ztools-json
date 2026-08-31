package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.lang.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

@DisplayName("Issue3795 测试")
public class Issue3795Test {
	@Test
    @DisplayName("转Bean")
	void toBeanTest() {
		String fieldMapping = "[{\"lable\":\"id\",\"value\":\"id\"},{\"lable\":\"name\",\"value\":\"name\"},{\"lable\":\"age\",\"value\":\"age\"}]";
		Assertions.assertThrows(UnsupportedOperationException.class, ()->{
			JSONUtil.toBean(fieldMapping, new TypeReference<Map<String, String>>() {}, false);
		});
	}
}
