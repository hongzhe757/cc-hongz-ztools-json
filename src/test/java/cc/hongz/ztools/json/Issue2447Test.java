package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@DisplayName("Issue2447 测试")
public class Issue2447Test {

	@Test
    @DisplayName("addInteger")
	public void addInteger() {
		Time time = new Time();
		time.setTime(LocalDateTime.of(1970, 1, 2, 10, 0, 1, 0));
		String timeStr = JSONUtil.toJsonStr(time);
		assertEquals("{\"time\":93601000}", timeStr);
		assertEquals(JSONUtil.toBean(timeStr, Time.class).getTime(), time.getTime());
	}

	static class Time {
		private LocalDateTime time;

		public LocalDateTime getTime() {
			return time;
		}

		public void setTime(LocalDateTime time) {
			this.time = time;
		}

		@Override
		public String toString() {
			return time.toString();
		}
	}

}
