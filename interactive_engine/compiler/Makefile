OPT?=poc

CUR_DIR:=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))

ifeq ($(JAVA_HOME),)
    java:=java
else
    java:=$(JAVA_HOME)/bin/java
endif

UNAME_S := $(shell uname -s)
UNAME_M := $(shell uname -m)

graph.schema:=
graph.store:=

rpc.target:=start_rpc_server

target.revision:=0.0.1-SNAPSHOT

QUIET_OPT := --quiet

query:=
physical:=

build:
	cd $(CUR_DIR)/.. && \
		mvn clean install -DskipTests -Drevision=${target.revision} -Pexperimental ${QUIET_OPT} && \
		cd $(CUR_DIR)/../executor/ir/integrated && cargo build --release --bin $(rpc.target)

clean:
	cd $(CUR_DIR)/../executor/engine/pegasus/clients/java/client && mvn clean && \
	cd $(CUR_DIR)/../executor/ir && cargo clean && \
	cd $(CUR_DIR) && mvn clean

test:
	cd $(CUR_DIR)/../executor/ir && cargo test && \
	cd $(CUR_DIR)/../executor/ir && cargo test --features with_v6d && \
	cd $(CUR_DIR)/../executor/ir && cargo test --features with_global_query && \
	cd $(CUR_DIR) && mvn test

# start rpc server
# make run
gremlin_test:
	mvn test -Dtest=com.alibaba.graphscope.integration.standard.IrGremlinTest

submit:
	cd $(CUR_DIR) && $(java) \
	  -cp ".:./target/libs/*:./target/compiler-0.0.1-SNAPSHOT.jar" \
	  -Djna.library.path=../executor/ir/target/release \
	  com.alibaba.graphscope.common.SubmitPlanServiceMain \
	  $(OPT)

run:
	cd $(CUR_DIR) && $(java) \
	  -cp ".:./target/libs/*:./target/compiler-0.0.1-SNAPSHOT.jar" \
	  -Djna.library.path=../executor/ir/target/release \
	  -Dgraph.schema=${graph.schema} \
	  -Dgraph.store=${graph.store} \
	  -Dpegasus.hosts=${pegasus.hosts} \
	  com.alibaba.graphscope.gremlin.service.GraphServiceMain

# make physical_plan query='<query in string>' physical='<path to the physical output file>'
physical_plan:
	cd $(CUR_DIR) && $(java) \
		-cp ".:./target/libs/*:./target/compiler-0.0.1-SNAPSHOT.jar" \
		-Djna.library.path=../executor/ir/target/release \
		-Dgraph.schema=${graph.schema} \
		com.alibaba.graphscope.common.ir.tools.GraphPlanner "$(query)" "$(physical)"

# start rpc server
# make run graph.schema:=../executor/ir/core/resource/ldbc_schema.json 
ldbc_test:
	mvn test -Dtest=com.alibaba.graphscope.integration.ldbc.IrLdbcTest

# start rpc server
# make run graph.schema:=../executor/ir/core/resource/ldbc_schema.json
pattern_test:
	mvn test -Dtest=com.alibaba.graphscope.integration.pattern.IrPatternTest

.PHONY: build run
