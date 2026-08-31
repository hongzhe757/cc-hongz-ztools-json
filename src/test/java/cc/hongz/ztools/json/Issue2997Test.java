package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Issue2997 测试")
public class Issue2997Test {
	@Test
    @DisplayName("转Bean")
	public void toBeanTest() {
		// https://github.com/chinabugotech/hutool/issues/2997
		final Object o = JSONUtil.toBean("{}", Object.class);
		assertEquals(JSONObject.class, o.getClass());
	}
}
