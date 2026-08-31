package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("Issue4197 测试")
public class Issue4197Test {
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static
	class TestDTO {

		private BigDecimal h;
	}

	@Test
    @DisplayName("转Bean")
	void toBeanTest() {
		final TestDTO bean = JSONUtil.toBean("{\"h\":\"123，456，789\"}", TestDTO.class);
		Assertions.assertEquals(new BigDecimal("123456789"), bean.getH());
	}
}
