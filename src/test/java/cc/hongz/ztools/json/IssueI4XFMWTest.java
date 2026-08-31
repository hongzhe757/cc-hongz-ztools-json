package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.annotation.Alias;
import lombok.Data;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * https://gitee.com/chinabugotech/hutool/issues/I4XFMW
 */
@DisplayName("IssueI4XFMW 测试")
public class IssueI4XFMWTest {

	@Test
    @DisplayName("测试")
	public void test() {
		List<TestEntity> entityList = new ArrayList<>();
		TestEntity entityA = new TestEntity();
		entityA.setId("123");
		entityA.setPassword("456");
		entityList.add(entityA);

		TestEntity entityB = new TestEntity();
		entityB.setId("789");
		entityB.setPassword("098");
		entityList.add(entityB);

		String jsonStr = JSONUtil.toJsonStr(entityList);
		assertEquals("[{\"uid\":\"123\",\"password\":\"456\"},{\"uid\":\"789\",\"password\":\"098\"}]", jsonStr);
		List<TestEntity> testEntities = JSONUtil.toList(jsonStr, TestEntity.class);
		assertEquals("123", testEntities.get(0).getId());
		assertEquals("789", testEntities.get(1).getId());
	}

	@Data
	static class TestEntity {
		@Alias("uid")
		private String id;
		private String password;
	}
}
