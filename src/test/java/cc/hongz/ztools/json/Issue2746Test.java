package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.util.StrUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Issue2746 测试")
public class Issue2746Test {
	@Test
    @DisplayName("解析对象")
	public void parseObjTest() {
		final String str = StrUtil.repeat("{", 1500) + StrUtil.repeat("}", 1500);
		try{
			JSONUtil.parseObj(str);
		} catch (final JSONException e){
			assertTrue(e.getMessage().startsWith("A JSONObject can not directly nest another JSONObject or JSONArray"));
		}
	}

	@Test
    @DisplayName("解析")
	public void parseTest() {
		assertThrows(JSONException.class, () -> {
			final String str = StrUtil.repeat("[", 1500) + StrUtil.repeat("]", 1500);
			JSONUtil.parseArray(str);
		});
	}
}
