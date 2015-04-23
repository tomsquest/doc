# "doc"

Doc is a typed, recursive structure. It is a disguised, convenient Map of &lt;String, List&lt;Doc&gt;&gt;. 

## Features

* Serializable back and forth to Json without losing the types of the datas (the type is serialized in the json).
* Persistent into Text/Clob using JPA
* Convenient builder

``` java
Doc d = aDoc()
          .with("strings", "abc", "efg")
          .with("children", aDoc().with("foo", "value"), aDoc().with("bar", "value"))
          .build();
```

* Access any level deep using '$'

``` java
myDoc.$("child.childChild.field").values()
```
## Next

* Support LocalDate/Time along java.util.Date
* Xml serialization
* byte[] support
* Deserialize any Json into a Doc
* Optimize json serialization to not output null types
* Path with index for values
  * Ex: $("address.postalCode", 2) --> return Field("address.postalCode", values= [2])
* Runtime register types
  * Ex: for a Link class implementing Docable (T read(Doc), Doc write(T)) can be serialized/deserialized
