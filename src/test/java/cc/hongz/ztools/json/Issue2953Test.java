package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@DisplayName("Issue2953 测试")
public class Issue2953Test {
	@Test
    @DisplayName("解析对象带BigNumber")
	public void parseObjWithBigNumberTest() {
		final String a = "{\"a\": 114793903847679990000000000000000000000}";
		final JSONObject jsonObject = JSONUtil.parseObj(a, JSONConfig.create());
		assertEquals("{\"a\":\"114793903847679990000000000000000000000\"}", jsonObject.toString());
	}
}
