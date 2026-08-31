package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.lang.TypeReference;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

@DisplayName("IssuesI4V14N 测试")
public class IssuesI4V14NTest {

	@Test
    @DisplayName("解析")
	public void parseTest(){
		String str = "{\"A\" : \"A\\nb\"}";
		final JSONObject jsonObject = JSONUtil.parseObj(str);
		assertEquals("A\nb", jsonObject.getStr("A"));

		final Map<String, String> map = jsonObject.toBean(new TypeReference<Map<String, String>>() {});
		assertEquals("A\nb", map.get("A"));
	}
}
