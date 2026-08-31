package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 验证忽略大小写匹配的同时，遍历保留原始 key 大小写。
 */
@DisplayName("CaseInsensitiveKey 测试")
public class CaseInsensitiveKeyTest {

	@Test
	@DisplayName("忽略大小写获取，遍历保留原始大小写")
	public void ignoreCaseAndKeepOriginalKeyTest() {
		final JSONObject json = JSONUtil.parseObj("{\"userName\":\"张三\"}",
				JSONConfig.create().setIgnoreCase(true));

		// 任意大小写形式都能获取
		assertEquals("张三", json.getStr("username"));
		assertEquals("张三", json.getStr("USERNAME"));
		assertEquals("张三", json.getStr("UserName"));
		assertEquals("张三", json.getStr("userName"));
		// 驼峰里大小写混着的极端形式也能获取
		assertEquals("张三", json.getStr("UserNAME"));
		assertEquals("张三", json.getStr("uSerName"));
		// 下划线形式也能匹配驼峰
		assertEquals("张三", json.getStr("user_name"));
		assertEquals("张三", json.getStr("USER_NAME"));

		// 遍历保留第一次写入的原始大小写
		assertTrue(json.keySet().contains("userName"));
		assertFalse(json.keySet().contains("username"));
		assertEquals(1, json.keySet().size());
	}

	@Test
	@DisplayName("多人协作多种大小写写入，统一匹配且保留首个原始 key")
	public void multiWriterMixedCaseTest() {
		final JSONObject json = new JSONObject(JSONConfig.create().setIgnoreCase(true));
		json.set("userName", "张三");
		// 另一种大小写写入应覆盖，而非新增
		json.set("UserName", "李四");

		assertEquals("李四", json.getStr("username"));
		assertEquals(1, json.size());
		// 保留第一个写入的原始大小写 userName
		assertEquals("userName", json.keySet().iterator().next());
	}

	@Test
	@DisplayName("驼峰、下划线、无分隔三种形式统一匹配")
	public void underlineAndCamelUnifiedTest() {
		final JSONObject json = new JSONObject(JSONConfig.create().setIgnoreCase(true));
		json.set("userName", "张三");

		// 下划线、驼峰、无分隔、全大写统一匹配
		assertEquals("张三", json.getStr("user_name"));
		assertEquals("张三", json.getStr("userName"));
		assertEquals("张三", json.getStr("username"));
		assertEquals("张三", json.getStr("USER_NAME"));

		// 下划线形式写入覆盖同一 key，而非新增
		json.set("user_name", "李四");
		assertEquals("李四", json.getStr("userName"));
		assertEquals(1, json.size());
		// 保留第一个写入的原始 key userName
		assertEquals("userName", json.keySet().iterator().next());
	}
}
