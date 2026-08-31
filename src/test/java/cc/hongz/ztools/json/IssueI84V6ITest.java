package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@DisplayName("IssueI84V6I 测试")
public class IssueI84V6ITest {
	@Test
    @DisplayName("格式化")
	public void formatTest() {
		final String a1 = "{'x':'\\n','y':','}";
		final String formatJsonStr = JSONUtil.formatJsonStr(a1);
//		Console.log(formatJsonStr);
		assertEquals(
			"{\n" +
				"    'x': '\\n',\n" +
				"    'y': ','\n" +
				"}", formatJsonStr);
	}
}
