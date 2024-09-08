package com.intheeast.springframe.servicetest;

import com.intheeast.springframe.sqlservice.UpdatableSqlRegistry;
import com.intheeast.springframe.sqlservice.updatable.ConcurrentHashMapSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		return new ConcurrentHashMapSqlRegistry();
	}
}