package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.lang.Console;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@DisplayName("Issue3619 测试")
public class Issue3619Test {
	@Test
    @DisplayName("解析对象")
	public void parseObjTest() {
		String json = "{\"@timestamp\":\"2024-06-14T00:02:06.438Z\",\"@version\":\"1\",\"int_arr\":[-4]}";
		JSONConfig jsonConfig = JSONConfig.create().setKeyComparator(String.CASE_INSENSITIVE_ORDER);
		JSONObject jsonObject = JSONUtil.parseObj(json, jsonConfig);

		final String jsonStr = jsonObject.toJSONString(0, pair -> {
			final Object key = pair.getKey();
			if(key instanceof String){
				// 只有key为String时才检查并过滤，其它类型的key，如int类型的key跳过
				return key.toString().equals("int_arr");
			}else{
				return true;
			}
		});

		assertEquals("{\"int_arr\":[-4]}", jsonStr);
	}
}
