package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * https://github.com/chinabugotech/hutool/issues/3504
 */
@DisplayName("Issue3504 测试")
public class Issue3504Test {

	@Test
    @DisplayName("test3504")
	public void test3504() {
		JsonBean jsonBean = new JsonBean();
		jsonBean.setName("test");
		jsonBean.setClasses(new Class[]{String.class});
		String huToolJsonStr = JSONUtil.toJsonStr(jsonBean);
		final JsonBean bean = JSONUtil.toBean(huToolJsonStr, JsonBean.class);
		assertNotNull(bean);
		assertEquals("test", bean.getName());
	}

	@Data
	public static class JsonBean {
		private String name;
		private Class<?>[] classes;
	}
}
