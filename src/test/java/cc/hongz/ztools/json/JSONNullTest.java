package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@DisplayName("JSONNull 测试")
public class JSONNullTest {

	@Test
    @DisplayName("解析空")
	public void parseNullTest(){
		JSONObject bodyjson = JSONUtil.parseObj("{\n" +
				"            \"device_model\": null,\n" +
				"            \"device_status_date\": null,\n" +
				"            \"imsi\": null,\n" +
				"            \"act_date\": \"2021-07-23T06:23:26.000+00:00\"}");
		assertEquals(JSONNull.class, bodyjson.get("device_model").getClass());
		assertEquals(JSONNull.class, bodyjson.get("device_status_date").getClass());
		assertEquals(JSONNull.class, bodyjson.get("imsi").getClass());

		bodyjson.getConfig().setIgnoreNullValue(true);
		assertEquals("{\"act_date\":\"2021-07-23T06:23:26.000+00:00\"}", bodyjson.toString());
	}

	@Test
    @DisplayName("解析空")
	public void parseNullTest2(){
		JSONObject bodyjson = JSONUtil.parseObj("{\n" +
				"            \"device_model\": null,\n" +
				"            \"device_status_date\": null,\n" +
				"            \"imsi\": null,\n" +
				"            \"act_date\": \"2021-07-23T06:23:26.000+00:00\"}", true);
		assertFalse(bodyjson.containsKey("device_model"));
		assertFalse(bodyjson.containsKey("device_status_date"));
		assertFalse(bodyjson.containsKey("imsi"));
	}
}
