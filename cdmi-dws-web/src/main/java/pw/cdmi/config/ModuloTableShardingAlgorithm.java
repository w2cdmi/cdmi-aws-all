package pw.cdmi.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;

import java.util.Collection;

public class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<String> {

    @Override
    public String doEqualSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        System.out.print(collection);
        return collection.isEmpty() ? null : collection.iterator().next();
    }

    @Override
    public Collection<String> doInSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        return null;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        return null;
    }

}
