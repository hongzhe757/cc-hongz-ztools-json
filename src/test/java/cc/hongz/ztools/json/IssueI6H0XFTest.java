package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@DisplayName("IssueI6H0XF 测试")
public class IssueI6H0XFTest {
	@Test
    @DisplayName("转Bean")
	public void toBeanTest(){
		final Demo demo = JSONUtil.toBean("{\"biz\":\"A\",\"isBiz\":true}", Demo.class);
		assertEquals("A", demo.getBiz());
		assertEquals("{\"biz\":\"A\"}", JSONUtil.toJsonStr(demo));
	}

	@Data
	static class Demo {
		String biz;
	}
}
