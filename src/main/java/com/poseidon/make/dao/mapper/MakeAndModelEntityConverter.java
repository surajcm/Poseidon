package com.poseidon.make.dao.mapper;

import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.domain.MakeAndModelVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MakeAndModelEntityConverter {
    public List<MakeAndModelVO> convertModelsToMakeAndModelVOs(List<Model> models) {
        List<MakeAndModelVO> makeAndModelVOS = new ArrayList<>();
        for (Model model:models) {
            MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
            makeAndModelVO.setModelId(model.getId().longValue());
            makeAndModelVO.setModelName(model.getModelName());
            makeAndModelVO.setMakeId(model.getMake().getId().longValue());
            makeAndModelVO.setMakeName(model.getMake().getMakeName());
            makeAndModelVOS.add(makeAndModelVO);
        }
        return makeAndModelVOS;
    }

    public List<MakeAndModelVO> convertMakeToMakeAndModelVOs(List<Make> makes) {
        List<MakeAndModelVO> makeAndModelVOS = new ArrayList<>();
        makes.forEach(make -> {
            MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
            makeAndModelVO.setMakeId(Long.valueOf(make.getId()));
            makeAndModelVO.setMakeName(make.getMakeName());
            makeAndModelVO.setDescription(make.getDescription());
            makeAndModelVO.setCreatedBy(make.getCreatedBy());
            makeAndModelVO.setCreatedDate(make.getCreatedOn());
            makeAndModelVO.setModifiedBy(make.getModifiedBy());
            makeAndModelVO.setModifiedDate(make.getModifiedOn());
            makeAndModelVOS.add(makeAndModelVO);
        });
        return makeAndModelVOS;
    }

    public Make convertToMake(MakeAndModelVO currentMakeVO) {
        Make make = new Make();
        make.setMakeName(currentMakeVO.getMakeName());
        make.setDescription(currentMakeVO.getDescription());
        make.setCreatedBy(currentMakeVO.getCreatedBy());
        make.setCreatedOn(currentMakeVO.getCreatedDate());
        make.setModifiedBy(currentMakeVO.getModifiedBy());
        make.setModifiedOn(currentMakeVO.getModifiedDate());
        return make;
    }

    public MakeAndModelVO getMakeVOFromMake(Make make) {
        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setMakeId(Long.valueOf(make.getId()));
        makeAndModelVO.setMakeName(make.getMakeName());
        makeAndModelVO.setDescription(make.getDescription());
        return makeAndModelVO;
    }
}
