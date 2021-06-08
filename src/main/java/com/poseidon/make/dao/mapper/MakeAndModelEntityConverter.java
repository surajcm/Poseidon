package com.poseidon.make.dao.mapper;

import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.domain.MakeAndModelVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MakeAndModelEntityConverter {
    /**
     * convert models to make and model vos.
     *
     * @param models list of model entity objects
     * @return list of make and model vos
     */
    public List<MakeAndModelVO> convertModelsToMakeAndModelVOs(final List<Model> models) {
        List<MakeAndModelVO> makeAndModelVOS = new ArrayList<>();
        for (Model model : models) {
            MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
            makeAndModelVO.setId(model.getId());
            makeAndModelVO.setModelId(model.getModelId());
            makeAndModelVO.setModelName(model.getModelName());
            makeAndModelVO.setMakeId(model.getMake().getMakeId());
            makeAndModelVO.setMakeName(model.getMake().getMakeName());
            makeAndModelVOS.add(makeAndModelVO);
        }
        return makeAndModelVOS;
    }

    /**
     * convert list of make to make and model vo list.
     *
     * @param makes list of makes
     * @return list of make and model vos
     */
    public List<MakeAndModelVO> convertMakeToMakeAndModelVOs(final List<Make> makes) {
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

    /**
     * convert make and model vo to make.
     *
     * @param currentMakeVO make and model vo
     * @return make
     */
    public Make convertToMake(final MakeAndModelVO currentMakeVO) {
        Make make = new Make();
        make.setMakeId(currentMakeVO.getMakeId());
        make.setMakeName(currentMakeVO.getMakeName());
        make.setDescription(currentMakeVO.getDescription());
        make.setCreatedBy(currentMakeVO.getCreatedBy());
        make.setModifiedBy(currentMakeVO.getModifiedBy());
        return make;
    }

    /**
     * get make vo from make entity.
     *
     * @param make make
     * @return make and model vo
     */
    public MakeAndModelVO getMakeVOFromMake(final Make make) {
        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setMakeId(make.getMakeId());
        makeAndModelVO.setMakeName(make.getMakeName());
        makeAndModelVO.setDescription(make.getDescription());
        return makeAndModelVO;
    }

    public MakeAndModelVO convertModelToMakeAndModelVO(final Model model) {
        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setModelId(model.getModelId());
        makeAndModelVO.setModelName(model.getModelName());
        makeAndModelVO.setMakeId(model.getMake().getMakeId());
        makeAndModelVO.setMakeName(model.getMake().getMakeName());
        return makeAndModelVO;
    }

    public Model convertMakeAndModelVOToModel(final MakeAndModelVO makeAndModelVO) {
        Model model = new Model();
        model.setModelName(makeAndModelVO.getModelName());
        model.setMakeId(makeAndModelVO.getMakeId());
        model.setCreatedBy(makeAndModelVO.getCreatedBy());
        model.setModifiedBy(makeAndModelVO.getModifiedBy());
        return model;
    }
}
