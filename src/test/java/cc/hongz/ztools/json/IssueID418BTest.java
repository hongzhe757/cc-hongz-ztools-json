package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@DisplayName("IssueID418B 测试")
public class IssueID418BTest {

	@Test
    @DisplayName("布尔转JSON")
	void booleanToJsonTest() {
		Boolean dd = true;
		String jsonStr = JSONUtil.toJsonStr(dd);
		Assertions.assertEquals("true", jsonStr);
	}
}
