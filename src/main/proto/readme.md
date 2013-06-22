The protocol buffer definition file for rethinkdb was obtained on Saturday, Jun 22 using the command

```wget https://raw.github.com/rethinkdb/rethinkdb/next/src/rdb_protocol/ql2.proto```

Following package definitions were added to the protocol buffer file

```
package rethinkdb;

option java_package = "com.jamaav.jared";
```

The generated sources will live in the folder ''com.jamaav.jared''