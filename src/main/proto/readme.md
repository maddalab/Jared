Notes
=====
This folder contains the protocol buffer definition for rethinkdb's wire protocol. It was obtained on *Saturday, Jun 2013*.

You can view the latest version of the protocol definition [here](https://github.com/rethinkdb/rethinkdb/blob/next/src/rdb_protocol/ql2.proto).

### Modification
We have added the following customizations to the *proto* file

```
package rethinkdb;

option java_package = "com.jamaav.jared";
```

### Updating
To update the protocol definition file on a linux system, fetch the latest file using

```wget https://raw.github.com/rethinkdb/rethinkdb/next/src/rdb_protocol/ql2.proto```

Add the package definitions as above to the top of the file.


The generated sources will live in the folder ''com.jamaav.jared''