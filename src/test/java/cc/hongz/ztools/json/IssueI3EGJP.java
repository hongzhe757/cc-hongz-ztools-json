package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.bean.BeanUtil;
import lombok.Data;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IssueI3EGJP {

	@Test
    @DisplayName("hutoolMap转Bean")
	public void hutoolMapToBean() {
		JSONObject paramJson = new JSONObject();
		paramJson.set("is_booleana", "1");
		paramJson.set("is_booleanb", true);
		ConvertDO convertDO = BeanUtil.toBean(paramJson, ConvertDO.class);

		assertTrue(convertDO.isBooleana());
		assertTrue(convertDO.getIsBooleanb());
	}

	@Data
	public static class ConvertDO {
		private boolean isBooleana;
		private Boolean isBooleanb;
	}
}
