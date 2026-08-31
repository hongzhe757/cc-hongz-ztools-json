package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@DisplayName("IssueIAP4GM 测试")
public class IssueIAP4GMTest {
	@Test
    @DisplayName("解析")
	void parse() {
		String res = "{\"uid\":\"asdf\\n\"}";
		final JSONObject entries = JSONUtil.parseObj(res);
		Assertions.assertEquals("asdf\n", entries.getStr("uid"));
	}
}
