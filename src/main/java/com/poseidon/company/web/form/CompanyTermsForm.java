package com.poseidon.company.web.form;

import com.poseidon.company.domain.CompanyTermsVO;

import java.util.StringJoiner;

public class CompanyTermsForm {

    private CompanyTermsVO currentCompanyTermsVO;

    public CompanyTermsVO getCurrentCompanyTermsVO() {
        return currentCompanyTermsVO;
    }

    public void setCurrentCompanyTermsVO(final CompanyTermsVO currentCompanyTermsVO) {
        this.currentCompanyTermsVO = currentCompanyTermsVO;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CompanyTermsForm.class.getSimpleName() + "[", "]")
                .add("currentCompanyTermsVO=" + currentCompanyTermsVO)
                .toString();
    }
}
