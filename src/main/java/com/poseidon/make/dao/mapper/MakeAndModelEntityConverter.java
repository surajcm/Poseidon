package com.poseidon.make.dao.mapper;

import com.poseidon.make.dao.entities.Make;
import com.poseidon.model.entities.Model;
import com.poseidon.make.domain.MakeAndModelVO;
import org.springframework.stereotype.Component;

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
        return models.stream().map(this::createMakeAndModelVO).toList();
    }

    /**
     * convert list of make to make and model vo list.
     *
     * @param makes list of makes
     * @return list of make and model vos
     */
    public List<MakeAndModelVO> convertMakeToMakeAndModelVOs(final List<Make> makes) {
        return makes.stream().map(this::createMakeAndModelVO).toList();
    }

    private MakeAndModelVO createMakeAndModelVO(final Model model) {
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setId(model.getId());
        makeAndModelVO.setModelId(model.getModelId());
        makeAndModelVO.setModelName(model.getModelName());
        makeAndModelVO.setMakeId(model.getMake().getId());
        makeAndModelVO.setMakeName(model.getMake().getMakeName());
        return makeAndModelVO;
    }

    private MakeAndModelVO createMakeAndModelVO(final Make make) {
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setId(make.getId());
        makeAndModelVO.setMakeId(make.getId());
        makeAndModelVO.setMakeName(make.getMakeName());
        makeAndModelVO.setDescription(make.getDescription());
        makeAndModelVO.setCreatedBy(make.getCreatedBy());
        makeAndModelVO.setModifiedBy(make.getModifiedBy());
        return makeAndModelVO;
    }

    public MakeAndModelVO convertModelToMakeAndModelVO(final Model model) {
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setModelId(model.getModelId());
        makeAndModelVO.setModelName(model.getModelName());
        makeAndModelVO.setMakeId(model.getMake().getId());
        makeAndModelVO.setMakeName(model.getMake().getMakeName());
        return makeAndModelVO;
    }

    public Model convertMakeAndModelVOToModel(final MakeAndModelVO makeAndModelVO) {
        var model = new Model();
        model.setModelName(makeAndModelVO.getModelName());
        model.setMakeId(makeAndModelVO.getMakeId());
        model.setModelId(makeAndModelVO.getMakeId());
        model.setCreatedBy(makeAndModelVO.getCreatedBy());
        model.setModifiedBy(makeAndModelVO.getModifiedBy());
        return model;
    }
}
