package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.json.serialize.JSONObjectSerializer;
import lombok.ToString;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

@DisplayName("CustomSerialize 测试")
public class CustomSerializeTest {

	@Test
    @DisplayName("序列化")
	public void serializeTest() {
		JSONUtil.putSerializer(CustomBean.class, (JSONObjectSerializer<CustomBean>) (json, bean) -> json.set("customName", bean.name));

		CustomBean customBean = new CustomBean();
		customBean.name = "testName";

		JSONObject obj = JSONUtil.parseObj(customBean);
		assertEquals("testName", obj.getStr("customName"));
	}

	@Test
    @DisplayName("deserialize")
	public void deserializeTest() {
		JSONUtil.putDeserializer(CustomBean.class, json -> {
			CustomBean customBean = new CustomBean();
			customBean.name = ((JSONObject)json).getStr("customName");
			return customBean;
		});

		String jsonStr = "{\"customName\":\"testName\"}";
		CustomBean bean = JSONUtil.parseObj(jsonStr).toBean(CustomBean.class);
		assertEquals("testName", bean.name);
	}

	@ToString
	public static class CustomBean {
		public String name;
		public String b;
		public Date date;
	}
}
