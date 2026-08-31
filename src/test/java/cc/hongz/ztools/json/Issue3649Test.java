package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Issue3649 测试")
public class Issue3649Test {
	@Test
    @DisplayName("转空Bean")
	public void toEmptyBeanTest() {
		final Object bean = JSONUtil.toBean("{}", JSONConfig.create().setIgnoreError(true), EmptyBean.class);
		assertEquals(new EmptyBean(), bean);
	}

	@Data
	public static class EmptyBean {}
}
