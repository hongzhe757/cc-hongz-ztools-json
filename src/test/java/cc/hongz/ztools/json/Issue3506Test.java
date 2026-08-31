package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * https://github.com/chinabugotech/hutool/issues/3506
 */
@DisplayName("Issue3506 测试")
public class Issue3506Test {

	@Test
    @DisplayName("test3506")
	public void test3506() {
		Languages languages = new Languages();
		languages.setLanguageType(Java.class);
		String hutoolJSONString = JSONUtil.toJsonStr(languages);
		final Languages bean = JSONUtil.toBean(hutoolJSONString, Languages.class);
		assertNotNull(bean);
		assertEquals(bean.getLanguageType(), Java.class);
	}

	@Data
	public static class Languages {
		private Class<? extends Language> languageType;
	}

	public interface Language {
	}

	public static class Java implements Language {
	}
}
