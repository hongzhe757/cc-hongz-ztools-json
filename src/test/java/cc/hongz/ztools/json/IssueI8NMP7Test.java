package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;
import lombok.ToString;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

@DisplayName("IssueI8NMP7 测试")
public class IssueI8NMP7Test {
	@Test
    @DisplayName("转Bean")
	public void toBeanTest() {
		final String jsonString = "{\"enableTime\":\"1702262524444\"}";
		final DemoModel bean = JSONUtil.toBean(jsonString, JSONConfig.create(), DemoModel.class);
		assertNotNull(bean.getEnableTime());
	}

	@Data
	@ToString
	static class DemoModel{
		private Date enableTime;
	}
}
