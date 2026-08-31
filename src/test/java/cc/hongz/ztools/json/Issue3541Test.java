package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@DisplayName("Issue3541 测试")
public class Issue3541Test {
	@Test
    @DisplayName("long转String")
	public void longToStringTest() {
		Demo demo = new Demo();
		demo.setId(1227690722069581409L);
		demo.setName("hutool");
		String jsonStr = JSONUtil.toJsonStr(demo, JSONConfig.create().setWriteLongAsString(true));
		assertEquals("{\"id\":\"1227690722069581409\",\"name\":\"hutool\"}", jsonStr);
	}

	@Data
	public static class Demo {
		private Long id;
		private String name;
	}
}
