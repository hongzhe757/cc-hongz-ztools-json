package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

@DisplayName("Issue3790 测试")
public class Issue3790Test {
	@Test
    @DisplayName("大小数转String")
	void bigDecimalToStringTest() {
		BigDecimal bigDecimal = new BigDecimal("0.01");
		bigDecimal = bigDecimal.setScale(4, RoundingMode.HALF_UP);

		Dto dto = new Dto();
		dto.remain = bigDecimal;

		final String jsonStr = JSONUtil.toJsonStr(dto, JSONConfig.create().setStripTrailingZeros(false));
		Assertions.assertEquals("{\"remain\":0.0100}", jsonStr);
	}

	static class Dto {
		public BigDecimal remain;
	}
}
