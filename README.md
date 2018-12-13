# Data Versioning in Hazelcast

This demo app shows how to use [Google Protocol Buffers](https://developers.google.com/protocol-buffers/) to create & 
use backward/forward compatible serialized models & store/access them to a Hazelcast cluster.


## Running the examples

 - Either run  [HzMember](cluster/src/main/java/com/test/cluster/HzMember.java) class multiple times or start a Hazelcast
cluster manually using distribution.
 - [data-v1](data-v1), [data-v2](data-v2) and [data-v3](data-v3) modules contains different versions of a sample `Person`
 object.
 - Run test cases on each version. First `loadData` & `checkData` methods, then `checkData` from a newer or older version
 to verify that you can see newer classes from a client which has older version or vice versa.
 - Then use `updateData` to update data & see even if the updater don't know the new fields, it won't overwrite the 
 unmapped fields since it's handled by Protocol Buffers.