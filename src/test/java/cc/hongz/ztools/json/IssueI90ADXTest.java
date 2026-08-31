package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import lombok.Getter;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@DisplayName("IssueI90ADX 测试")
public class IssueI90ADXTest {

	/**
	 * 测试只有getter方法时是否可以解析
	 */
	@Test
    @DisplayName("解析")
	public void parseTest() {
		final TestBean testBean = new TestBean();
		testBean.name = "aaaa";

		final JSONObject json = JSONUtil.parseObj(testBean);
		assertEquals("{\"name\":\"aaaa\"}", json.toString());
	}

	@Getter
	static class TestBean{
		private String name;
	}

}
