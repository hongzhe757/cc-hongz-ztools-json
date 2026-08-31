package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@DisplayName("Issue3759 测试")
public class Issue3759Test {
	@Test
    @DisplayName("解析")
	void parseTest() {
		String jsonArrayStr = "[null]";
		final JSONArray objects = JSONUtil.parseArray(jsonArrayStr,
			JSONConfig.create().setIgnoreNullValue(true));
		Assertions.assertTrue(objects.isEmpty());
	}
}
