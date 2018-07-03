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
            makeAndModelVO.setModelId(model.getModelId());
            makeAndModelVO.setModelName(model.getModelName());
            makeAndModelVO.setMakeId(model.getMake().getMakeId());
            makeAndModelVO.setMakeName(model.getMake().getMakeName());
            makeAndModelVOS.add(makeAndModelVO);
        }
        return makeAndModelVOS;
    }

    public List<MakeAndModelVO> convertMakeToMakeAndModelVOs(List<Make> makes) {
        List<MakeAndModelVO> makeAndModelVOS = new ArrayList<>();
        makes.forEach(make -> {
            MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
            makeAndModelVO.setMakeId(make.getMakeId());
            makeAndModelVO.setMakeName(make.getMakeName());
            makeAndModelVO.setDescription(make.getDescription());
            makeAndModelVO.setCreatedBy(make.getCreatedBy());
            makeAndModelVO.setModifiedBy(make.getModifiedBy());
            makeAndModelVOS.add(makeAndModelVO);
        });
        return makeAndModelVOS;
    }

    public Make convertToMake(MakeAndModelVO currentMakeVO) {
        Make make = new Make();
        make.setMakeName(currentMakeVO.getMakeName());
        make.setDescription(currentMakeVO.getDescription());
        make.setCreatedBy(currentMakeVO.getCreatedBy());
        make.setModifiedBy(currentMakeVO.getModifiedBy());
        return make;
    }

    public MakeAndModelVO getMakeVOFromMake(Make make) {
        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setMakeId(make.getMakeId());
        makeAndModelVO.setMakeName(make.getMakeName());
        makeAndModelVO.setDescription(make.getDescription());
        return makeAndModelVO;
    }
}
