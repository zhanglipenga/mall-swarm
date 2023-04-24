package com.macro.mall.demo.fegin.factory;

import com.macro.mall.demo.fegin.FeignPortalService;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignPortalFactory implements FallbackFactory<FeignPortalService> {

    @Override
    public FeignPortalService create(Throwable cause) {
        try {
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
        } catch (TransactionException e) {
            log.error("分布式事物回滚失败:{}", e);
            log.error("调用fegin接口失败:{}", cause);
        }
        return null;
    }
}
