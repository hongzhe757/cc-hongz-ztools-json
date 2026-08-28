package cc.hongz.ztools.json;

import cc.hongz.ztools.core.bean.BeanUtil;
import cc.hongz.ztools.core.bean.RecordUtil;
import cc.hongz.ztools.core.collection.ArrayIter;
import cc.hongz.ztools.core.convert.Convert;
import cc.hongz.ztools.core.io.IoUtil;
import cc.hongz.ztools.core.lang.Filter;
import cc.hongz.ztools.core.lang.mutable.Mutable;
import cc.hongz.ztools.core.lang.mutable.MutablePair;
import cc.hongz.ztools.core.util.ArrayUtil;
import cc.hongz.ztools.core.util.StrUtil;
import cc.hongz.ztools.core.util.TypeUtil;
import cc.hongz.ztools.json.serialize.GlobalSerializeMapping;
import cc.hongz.ztools.json.serialize.JSONObjectSerializer;
import cc.hongz.ztools.json.serialize.JSONSerializer;

import java.io.InputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 对象和JSON映射器，用于转换对象为JSON，支持：。
 * <ul>
 *     <li>Map 转 JSONObject，将键值对加入JSON对象</li>
 *     <li>Map.Entry 转 JSONObject</li>
 *     <li>CharSequence 转 JSONObject，使用JSONTokener解析</li>
 *     <li>{@link Reader} 转 JSONObject，使用JSONTokener解析</li>
 *     <li>{@link InputStream} 转 JSONObject，使用JSONTokener解析</li>
 *     <li>JSONTokener 转 JSONObject，直接解析</li>
 *     <li>ResourceBundle 转 JSONObject</li>
 *     <li>Bean 转 JSONObject，调用其getters方法（getXXX或者isXXX）获得值，加入到JSON对象。例如：如果JavaBean对象中有个方法getName()，值为"张三"，获得的键值对为：name:
 * "张三"</li>
 * </ul>
 *
 * @author looly
 * @since 5.8.0
 */
public class ObjectMapper {

    /**
     * 创建ObjectMapper。
     *
     * @param source 来源对象
     * @return ObjectMapper
     */
    public static ObjectMapper of(final Object source) {
        return new ObjectMapper(source);
    }

    private final Object source;

    /**
     * 构造。
     *
     * @param source 来源对象
     */
    public ObjectMapper(final Object source) {
        this.source = source;
    }

    /**
     * 将给定对象转换为{@link JSONObject}。
     *
     * @param jsonObject 目标{@link JSONObject}
     * @param filter     键值对过滤编辑器，可以通过实现此接口，完成解析前对键值对的过滤和修改操作
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void map(final JSONObject jsonObject, final Filter<MutablePair<String, Object>> filter) {
        final Object localSource = this.source;
        if (null == localSource) {
            return;
        }

        // 自定义序列化
        final JSONSerializer serializer = GlobalSerializeMapping.getSerializer(localSource.getClass());
        if (serializer instanceof JSONObjectSerializer) {
            serializer.serialize(jsonObject, localSource);
            return;
        }

        if (localSource instanceof JSONArray) {
            // 不支持集合类型转换为JSONObject
            throw new JSONException("Unsupported type [{}] to JSONObject!", localSource.getClass());
        }

        if (localSource instanceof Map) {
            // Map
            for (final Map.Entry<?, ?> e : ((Map<?, ?>) localSource).entrySet()) {
                jsonObject.set(Convert.toStr(e.getKey()), e.getValue(), filter,
                        jsonObject.getConfig().isCheckDuplicate());
            }
        } else if (localSource instanceof Map.Entry) {
            final Map.Entry entry = (Map.Entry) localSource;
            jsonObject.set(Convert.toStr(entry.getKey()), entry.getValue(), filter,
                    jsonObject.getConfig().isCheckDuplicate());
        } else if (localSource instanceof CharSequence) {
            // 可能为JSON字符串
            mapFromStr((CharSequence) localSource, jsonObject, filter);
        } else if (localSource instanceof Reader) {
            mapFromTokener(new JSONTokener((Reader) localSource, jsonObject.getConfig()), jsonObject, filter);
        } else if (localSource instanceof InputStream) {
            mapFromTokener(new JSONTokener((InputStream) localSource, jsonObject.getConfig()), jsonObject, filter);
        } else if (localSource instanceof byte[]) {
            mapFromTokener(new JSONTokener(IoUtil.toStream((byte[]) localSource), jsonObject.getConfig()),
                    jsonObject, filter);
        } else if (localSource instanceof JSONTokener) {
            // JSONTokener
            mapFromTokener((JSONTokener) localSource, jsonObject, filter);
        } else if (localSource instanceof ResourceBundle) {
            // JSONTokener
            mapFromResourceBundle((ResourceBundle) localSource, jsonObject, filter);
        } else if (RecordUtil.isRecord(localSource.getClass())) {
            mapFromBean(localSource, jsonObject, filter);
        } else if (BeanUtil.isReadableBean(localSource.getClass())) {
            // 普通Bean
            mapFromBean(localSource, jsonObject, filter);
        }

        // 跳过空对象
    }

    /**
     * 初始化。
     *
     * @param jsonArray 目标{@link JSONArray}
     * @param filter    键值对过滤编辑器，可以通过实现此接口，完成解析前对值的过滤和修改操作，{@code null}表示不过滤
     * @throws JSONException 非数组或集合
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void map(final JSONArray jsonArray, final Filter<Mutable<Object>> filter) throws JSONException {
        final Object localSource = this.source;
        if (null == localSource) {
            return;
        }

        final JSONSerializer serializer = GlobalSerializeMapping.getSerializer(localSource.getClass());
        if (null != serializer && JSONArray.class.equals(TypeUtil.getTypeArgument(serializer.getClass()))) {
            // 自定义序列化
            serializer.serialize(jsonArray, localSource);
        } else if (localSource instanceof CharSequence) {
            // JSON字符串
            mapFromStr((CharSequence) localSource, jsonArray, filter);
        } else if (localSource instanceof Reader) {
            mapFromTokener(new JSONTokener((Reader) localSource, jsonArray.getConfig()), jsonArray, filter);
        } else if (localSource instanceof InputStream) {
            mapFromTokener(new JSONTokener((InputStream) localSource, jsonArray.getConfig()), jsonArray, filter);
        } else if (localSource instanceof byte[]) {
            final byte[] bytesSource = (byte[]) localSource;
            // 如果是普通的的byte[], 要避免下标越界
            if (bytesSource.length > 1 && '[' == bytesSource[0] && ']' == bytesSource[bytesSource.length - 1]) {
                mapFromTokener(new JSONTokener(IoUtil.toStream(bytesSource), jsonArray.getConfig()), jsonArray, filter);
            } else {
                // https://github.com/chinabugotech/hutool/issues/2369
                // 非标准的二进制流，则按照普通数组对待
                for (final byte b : bytesSource) {
                    jsonArray.add(b);
                }
            }
        } else if (localSource instanceof JSONTokener) {
            mapFromTokener((JSONTokener) localSource, jsonArray, filter);
        } else {
            final Iterator<?> iter;
            if (ArrayUtil.isArray(localSource)) { // 数组
                iter = new ArrayIter<>(localSource);
            } else if (localSource instanceof Iterator<?>) { // Iterator
                iter = ((Iterator<?>) localSource);
            } else if (localSource instanceof Iterable<?>) { // Iterable
                iter = ((Iterable<?>) localSource).iterator();
            } else {
                if (false == jsonArray.getConfig().isIgnoreError()) {
                    throw new JSONException("JSONArray initial value should be a string or collection or array.");
                }
                // 如果用户选择跳过异常，则跳过此值转换
                return;
            }

            final JSONConfig config = jsonArray.getConfig();
            Object next;
            while (iter.hasNext()) {
                next = iter.next();
                // 检查循环引用
                if (next != localSource) {
                    jsonArray.addRaw(JSONUtil.wrap(next, config), filter);
                }
            }
        }
    }

    /**
     * 从{@link ResourceBundle}转换。
     *
     * @param bundle     ResourceBundle
     * @param jsonObject {@link JSONObject}
     * @param filter     键值对过滤编辑器，可以通过实现此接口，完成解析前对键值对的过滤和修改操作，{@code null}表示不过滤
     * @since 5.3.1
     */
    private static void mapFromResourceBundle(final ResourceBundle bundle, final JSONObject jsonObject, final
            Filter<MutablePair<String, Object>> filter) {
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key != null) {
                InternalJSONUtil.propertyPut(jsonObject, key, bundle.getString(key), filter);
            }
        }
    }

    /**
     * 从字符串转换。
     *
     * @param source     JSON字符串
     * @param jsonObject {@link JSONObject}
     * @param filter     键值对过滤编辑器，可以通过实现此接口，完成解析前对键值对的过滤和修改操作，{@code null}表示不过滤
     */
    private static void mapFromStr(final CharSequence source, final JSONObject jsonObject, final
            Filter<MutablePair<String, Object>> filter) {
        final String jsonStr = StrUtil.trim(source);
        if (StrUtil.startWith(jsonStr, '<')) {
            // 可能为XML
            XML.toJSONObject(jsonObject, jsonStr, false);
            return;
        }
        mapFromTokener(new JSONTokener(jsonStr, jsonObject.getConfig()), jsonObject, filter);
    }

    /**
     * 初始化。
     *
     * @param source    JSON字符串
     * @param jsonArray {@link JSONArray}
     * @param filter    值过滤编辑器，可以通过实现此接口，完成解析前对值的过滤和修改操作，{@code null}表示不过滤
     */
    private void mapFromStr(final CharSequence src, final JSONArray jsonArray, final Filter<Mutable<Object>> filter) {
        if (null != src) {
            mapFromTokener(new JSONTokener(StrUtil.trim(src), jsonArray.getConfig()), jsonArray, filter);
        }
    }

    /**
     * 从{@link JSONTokener}转换。
     *
     * @param x          JSONTokener
     * @param jsonObject {@link JSONObject}
     * @param filter     键值对过滤编辑器，可以通过实现此接口，完成解析前对键值对的过滤和修改操作
     */
    private static void mapFromTokener(final JSONTokener x, final JSONObject jsonObject, final
            Filter<MutablePair<String, Object>> filter) {
        JSONParser.of(x).parseTo(jsonObject, filter);
    }

    /**
     * 初始化。
     *
     * @param x         {@link JSONTokener}
     * @param jsonArray {@link JSONArray}
     * @param filter    值过滤编辑器，可以通过实现此接口，完成解析前对值的过滤和修改操作，{@code null}表示不过滤
     */
    private static void mapFromTokener(final JSONTokener x, final JSONArray jsonArray, final
            Filter<Mutable<Object>> filter) {
        JSONParser.of(x).parseTo(jsonArray, filter);
    }

    /**
     * 从Bean转换。
     *
     * @param bean       Bean对象
     * @param jsonObject {@link JSONObject}
     */
    @Deprecated
    private static void mapFromBean(final Object bean, final JSONObject jsonObject) {
        BeanUtil.beanToMap(bean, jsonObject, InternalJSONUtil.toCopyOptions(jsonObject.getConfig()));
    }

    /**
     * 从Bean转换（可添加过滤器）。
     * @param bean Bean对象
     * @param jsonObject {@link JSONObject}
     * @param filter {@link Filter}
     */
    private static void mapFromBean(final Object bean, final JSONObject jsonObject, final
            Filter<MutablePair<String, Object>> filter) {
        BeanUtil.beanToMap(bean, jsonObject, InternalJSONUtil.toCopyOptions(jsonObject.getConfig(), filter));
    }
}
