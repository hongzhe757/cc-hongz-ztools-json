package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.map.MapUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

@DisplayName("IssueID61QR 测试")
public class IssueID61QRTest {
	@Test
    @DisplayName("名称")
	public void testName() {
		JSONObject map1 = JSONUtil.createObj(new JSONConfig().setDateFormat("yyyy"));
		// JSONObject map1 = JSONUtil.createObj();
		map1.set("a", 3);
		map1.set("b", 5);
		map1.set("c", 5432);
		Assertions.assertEquals("{c=5432, b=5, a=3}", MapUtil.sortByValue(JSONUtil.toBean(map1, Map.class), true).toString());
	}
}
