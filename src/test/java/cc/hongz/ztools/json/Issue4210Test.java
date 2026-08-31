package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.exceptions.InvocationTargetRuntimeException;
import cc.hongz.ztools.core.lang.Console;
import cc.hongz.ztools.core.lang.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

@DisplayName("Issue4210 测试")
public class Issue4210Test {

	@Test
    @DisplayName("设置值")
	void setValueTest(){
		final JSONObject jsonObject = new JSONObject();

		final TestEntity entity = new TestEntity("张三", "社畜");
		Assertions.assertThrows(InvocationTargetRuntimeException.class, () -> jsonObject.set("entity",entity));
	}

	@Data
	public static class BaseEntity implements Serializable {

		/**
		 * 实体唯一标识符
		 */
		private String _id;
		private ObjectId id;

		public String get_id() {
			return _id == null ? id.toString() : _id;
		}
	}

	@EqualsAndHashCode(callSuper = true)
	@Data
	@AllArgsConstructor
	public class TestEntity extends BaseEntity{
		private String name;
		private String desc;
	}
}
