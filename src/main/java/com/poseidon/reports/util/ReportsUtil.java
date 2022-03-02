package com.poseidon.reports.util;

import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.transaction.domain.TransactionVO;
import org.springframework.stereotype.Component;

@Component
public class ReportsUtil {
    public TransactionVO getSearchTransaction() {
        var searchVO = new TransactionVO();
        searchVO.setModelId(0L);
        searchVO.setMakeId(0L);
        return searchVO;
    }

    public MakeAndModelVO getSearchMakeAndModelVO() {
        var searchVO = new MakeAndModelVO();
        searchVO.setMakeId(0L);
        searchVO.setModelId(0L);
        return searchVO;
    }
}
