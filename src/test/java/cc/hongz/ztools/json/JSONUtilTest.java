package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.collection.CollectionUtil;
import cc.hongz.ztools.core.collection.ListUtil;
import cc.hongz.ztools.core.date.DateUtil;
import cc.hongz.ztools.core.lang.Console;
import cc.hongz.ztools.core.lang.TypeReference;
import cc.hongz.ztools.core.map.MapUtil;
import cc.hongz.ztools.core.util.ArrayUtil;
import cc.hongz.ztools.core.util.NumberUtil;
import cc.hongz.ztools.json.test.bean.Price;
import cc.hongz.ztools.json.test.bean.UserA;
import cc.hongz.ztools.json.test.bean.UserC;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JSONUtil 测试")
public class JSONUtilTest {

	/**
	 * 出现语法错误时报错，检查解析\x字符时是否会导致死循环异常
	 */
	@Test
    @DisplayName("解析")
	public void parseTest() {
		assertThrows(JSONException.class, () -> {
			final JSONArray jsonArray = JSONUtil.parseArray("[{\"a\":\"a\\x]");
			Console.log(jsonArray);
		});
	}

	/**
	 * 数字解析为JSONArray报错
	 */
	@Test
    @DisplayName("解析Number转JSON数组")
	public void parseNumberToJSONArrayTest2() {
		final JSONArray json = JSONUtil.parseArray(123L,
			JSONConfig.create().setIgnoreError(true));
		assertNotNull(json);
	}

	/**
	 * 数字解析为JSONArray报错
	 */
	@Test
    @DisplayName("解析Number")
	public void parseNumberTest() {
		assertThrows(JSONException.class, () -> {
			final JSONArray json = JSONUtil.parseArray(123L);
			assertNotNull(json);
		});
	}

	/**
	 * 数字解析为JSONObject忽略
	 */
	@Test
    @DisplayName("解析Number")
	public void parseNumberTest2() {
		final JSONObject json = JSONUtil.parseObj(123L, JSONConfig.create().setIgnoreError(true));
		assertEquals(new JSONObject(), json);
	}

	@Test
    @DisplayName("转JSON字符串")
	public void toJsonStrTest() {
		final UserA a1 = new UserA();
		a1.setA("aaaa");
		a1.setDate(DateUtil.date());
		a1.setName("AAAAName");
		final UserA a2 = new UserA();
		a2.setA("aaaa222");
		a2.setDate(DateUtil.date());
		a2.setName("AAAA222Name");

		final ArrayList<UserA> list = CollectionUtil.newArrayList(a1, a2);
		final HashMap<String, Object> map = MapUtil.newHashMap();
		map.put("total", 13);
		map.put("rows", list);

		final String str = JSONUtil.toJsonPrettyStr(map);
		JSONUtil.parse(str);
		assertNotNull(str);
	}

	@Test
    @DisplayName("转JSON字符串")
	public void toJsonStrTest2() {
		final Map<String, Object> model = new HashMap<>();
		model.put("mobile", "17610836523");
		model.put("type", 1);

		final Map<String, Object> data = new HashMap<>();
		data.put("model", model);
		data.put("model2", model);

		final JSONObject jsonObject = JSONUtil.parseObj(data);

		assertTrue(jsonObject.containsKey("model"));
		assertEquals(1, jsonObject.getJSONObject("model").getInt("type").intValue());
		assertEquals("17610836523", jsonObject.getJSONObject("model").getStr("mobile"));
		// assertEquals("{\"model\":{\"type\":1,\"mobile\":\"17610836523\"}}", jsonObject.toString());
	}

	@Test
    @DisplayName("转JSON字符串")
	public void toJsonStrTest3() {
		// 验证某个字段为JSON字符串时转义是否规范
		final JSONObject object = new JSONObject(true);
		object.set("name", "123123");
		object.set("value", "\\");
		object.set("value2", "</");

		final HashMap<String, String> map = MapUtil.newHashMap();
		map.put("user", object.toString());

		final JSONObject json = JSONUtil.parseObj(map);
		assertEquals("{\"name\":\"123123\",\"value\":\"\\\\\",\"value2\":\"</\"}", json.get("user"));
		assertEquals("{\"user\":\"{\\\"name\\\":\\\"123123\\\",\\\"value\\\":\\\"\\\\\\\\\\\",\\\"value2\\\":\\\"</\\\"}\"}", json.toString());

		final JSONObject json2 = JSONUtil.parseObj(json.toString());
		assertEquals("{\"name\":\"123123\",\"value\":\"\\\\\",\"value2\":\"</\"}", json2.get("user"));
	}

	@Test
    @DisplayName("转JSON字符串From排序")
	public void toJsonStrFromSortedTest() {
		final SortedMap<Object, Object> sortedMap = new TreeMap<Object, Object>() {
			private static final long serialVersionUID = 1L;

			{
				put("attributes", "a");
				put("b", "b");
				put("c", "c");
			}
		};

		assertEquals("{\"attributes\":\"a\",\"b\":\"b\",\"c\":\"c\"}", JSONUtil.toJsonStr(sortedMap));
	}

	/**
	 * 泛型多层嵌套测试
	 */
	@Test
    @DisplayName("转Bean")
	public void toBeanTest() {
		final String json = "{\"ADT\":[[{\"BookingCode\":[\"N\",\"N\"]}]]}";

		final Price price = JSONUtil.toBean(json, Price.class);
		assertEquals("N", price.getADT().get(0).get(0).getBookingCode().get(0));
	}

	@Test
    @DisplayName("转Bean")
	public void toBeanTest2() {
		// 测试JSONObject转为Bean中字符串字段的情况
		final String json = "{\"id\":123,\"name\":\"张三\",\"prop\":{\"gender\":\"男\", \"age\":18}}";
		final UserC user = JSONUtil.toBean(json, UserC.class);
		assertNotNull(user.getProp());
		final String prop = user.getProp();
		final JSONObject propJson = JSONUtil.parseObj(prop);
		assertEquals("男", propJson.getStr("gender"));
		assertEquals(18, propJson.getInt("age").intValue());
		// assertEquals("{\"age\":18,\"gender\":\"男\"}", user.getProp());
	}

	@Test
    @DisplayName("获取字符串")
	public void getStrTest() {
		final String html = "{\"name\":\"Something must have been changed since you leave\"}";
		final JSONObject jsonObject = JSONUtil.parseObj(html);
		assertEquals("Something must have been changed since you leave", jsonObject.getStr("name"));
	}

	@Test
    @DisplayName("获取字符串")
	public void getStrTest2() {
		final String html = "{\"name\":\"Something\\u00a0must have been changed since you leave\"}";
		final JSONObject jsonObject = JSONUtil.parseObj(html);
		assertEquals("Something\\u00a0must\\u00a0have\\u00a0been\\u00a0changed\\u00a0since\\u00a0you\\u00a0leave", jsonObject.getStrEscaped("name"));
	}

	@Test
    @DisplayName("解析FromXML")
	public void parseFromXmlTest() {
		final String s = "<sfzh>640102197312070614</sfzh><sfz>640102197312070614X</sfz><name>aa</name><gender>1</gender>";
		final JSONObject json = JSONUtil.parseFromXml(s);
		assertEquals(640102197312070614L, json.get("sfzh"));
		assertEquals("640102197312070614X", json.get("sfz"));
		assertEquals("aa", json.get("name"));
		assertEquals(1, json.get("gender"));
	}

	@Test
    @DisplayName("double")
	public void doubleTest() {
		final String json = "{\"test\": 12.00}";
		final JSONObject jsonObject = JSONUtil.parseObj(json);
		//noinspection BigDecimalMethodWithoutRoundingCalled
		assertEquals("12.00", jsonObject.getBigDecimal("test").setScale(2).toString());
	}

	@Test
    @DisplayName("custom值")
	public void customValueTest() {
		final JSONObject jsonObject = JSONUtil.createObj()
			.set("test2", (JSONString) () -> NumberUtil.decimalFormat("#.0", 12.00D));

		assertEquals("{\"test2\":12.0}", jsonObject.toString());
	}

	@Test
    @DisplayName("设置去除末尾零")
	public void setStripTrailingZerosTest() {
		// 默认去除多余的0
		final JSONObject jsonObjectDefault = JSONUtil.createObj()
			.set("test2", 12.00D);
		assertEquals("{\"test2\":12}", jsonObjectDefault.toString());

		// 不去除多余的0
		final JSONObject jsonObject = JSONUtil.createObj(JSONConfig.create().setStripTrailingZeros(false))
			.set("test2", 12.00D);
		assertEquals("{\"test2\":12.0}", jsonObject.toString());

		// 去除多余的0
		jsonObject.getConfig().setStripTrailingZeros(true);
		assertEquals("{\"test2\":12}", jsonObject.toString());
	}

	@Test
    @DisplayName("解析对象")
	public void parseObjTest() {
		// 测试转义
		final JSONObject jsonObject = JSONUtil.parseObj("{\n" +
			"    \"test\": \"\\\\地库地库\",\n" +
			"}");

		assertEquals("\\地库地库", jsonObject.getObj("test"));
	}

	@Test
    @DisplayName("sqlException")
	public void sqlExceptionTest() {
		//https://github.com/chinabugotech/hutool/issues/1399
		// SQLException实现了Iterable接口，默认是遍历之，会栈溢出，修正后只返回string
		final JSONObject set = JSONUtil.createObj().set("test", new SQLException("test"));
		assertEquals("{\"test\":\"java.sql.SQLException: test\"}", set.toString());
	}

	@Test
    @DisplayName("解析BigNumber")
	public void parseBigNumberTest() {
		// 科学计数法使用BigDecimal处理，默认输出非科学计数形式
		final String str = "{\"test\":100000054128897953e4}";
		assertEquals("{\"test\":1000000541288979530000}", JSONUtil.parseObj(str).toString());
	}

	@Test
    @DisplayName("转XML")
	public void toXmlTest() {
		final JSONObject obj = JSONUtil.createObj();
		obj.set("key1", "v1")
			.set("key2", ListUtil.of("a", "b", "c"));
		final String xmlStr = JSONUtil.toXmlStr(obj);
		assertEquals("<key1>v1</key1><key2>a</key2><key2>b</key2><key2>c</key2>", xmlStr);
	}

	@Test
    @DisplayName("duplicateKeyFalse")
	public void duplicateKeyFalseTest() {
		final String str = "{id:123, name:\"张三\", name:\"李四\"}";

		final JSONObject jsonObject = JSONUtil.parseObj(str, JSONConfig.create().setCheckDuplicate(false));
		assertEquals("{\"id\":123,\"name\":\"李四\"}", jsonObject.toString());
	}

	@Test
    @DisplayName("duplicateKeyTrue")
	public void duplicateKeyTrueTest() {
		assertThrows(JSONException.class, () -> {
			final String str = "{id:123, name:\"张三\", name:\"李四\"}";

			final JSONObject jsonObject = JSONUtil.parseObj(str, JSONConfig.create().setCheckDuplicate(true));
			assertEquals("{\"id\":123,\"name\":\"李四\"}", jsonObject.toString());
		});
	}

	/**
	 * 测试普通数组转JSONArray时是否异常, 尤其是byte[]数组, 可能是普通的byte[]数组, 也可能是二进制流
	 */
	@Test
    @DisplayName("数组Entity")
	public void testArrayEntity() {
		final String jsonStr = JSONUtil.toJsonStr(new ArrayEntity());
		assertEquals("{\"a\":[],\"b\":[0],\"c\":[],\"d\":[],\"e\":[]}", jsonStr);
	}

	@Data
	static class ArrayEntity {
		private byte[] a = new byte[0];
		private byte[] b = new byte[1];
		private int[] c = new int[0];
		private Byte[] d = new Byte[0];
		private Byte[] e = new Byte[1];
	}

	@Test
    @DisplayName("issue3540")
	public void issue3540Test() {
		Long userId = 10101010L;
		final String jsonStr = JSONUtil.toJsonStr(userId);
		assertEquals("10101010", jsonStr);
	}

	/**
	 * 类型引用数组泛型丢失
	 */
	@Test
    @DisplayName("issue3873")
	public void issue3873Test() {
		String json = "{\"results\":[{\"uid\":\"1\"}],\"offset\":0,\"limit\":20,\"total\":0}";
		Results<Index> deserialize = JSONUtil.toBean(json, (new TypeReference<Results<Index>>() {
		}), false);

		assertEquals(Results.class, deserialize.getClass());
		assertEquals(ArrayUtil.getArrayType(Index.class), deserialize.results.getClass());
	}

	@Data
	public static class Results<T> {
		public T[] results;
	}

	@Data
	public static class Index {
		public String uid;
	}
}
