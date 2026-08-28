# cc-hongz-ztools-json

Hongz 平台 **JSON 序列化模块**（`jar`），对应 Hutool **`hutool-json`**。

## 特性

- `JSONObject` / `JSONArray` / `JSONUtil` / `JSONConfig`
- JSON ↔ Bean 转换、JSON ↔ XML 互转、自定义序列化
- 对齐 hutool-json（33 个主类）

## 模块关系

| Hutool | Hongz |
|---|---|
| `hutool-json` | **`cc-hongz-ztools-json`**（本模块） |

## 构建

```bash
mvn -f cc-hongz-ztools-json/pom.xml clean test
```
