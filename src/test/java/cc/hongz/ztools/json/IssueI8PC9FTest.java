package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

@DisplayName("IssueI8PC9F 测试")
public class IssueI8PC9FTest {

	@Test
    @DisplayName("转Bean忽略错误")
	public void toBeanIgnoreErrorTest() {
		final String testJson = "{\"testMap\":\"\"}";
		final TestBean test = JSONUtil.parseObj(testJson, JSONConfig.create().setIgnoreError(true))
			.toBean(TestBean.class);
		assertNotNull(test);
		assertNull(test.getTestMap());
	}

	@Data
	static class TestBean{
		private Map<?, ?> testMap;
	}
}
