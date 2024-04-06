package com.poseidon.model.web;

import com.poseidon.init.util.CommonUtils;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.make.web.form.MakeForm;
import com.poseidon.model.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@SuppressWarnings("unused")
public class ModelController {
    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);
    private static final String MODEL_LIST_PAGE = "make/ModelList";
    private static final String MAKE_AND_MODEL_VO_IS = " MakeAndModelVO is {}";
    private static final String MAKE_VO_IS = " makeVO is {}";
    private static final String MAKE_FORM = "makeForm";
    private static final String DANGER = "danger";
    private static final String SUCCESS = "success";
    private static final String MAKE_FORM_IS = " makeForm is {}";

    private final MakeService makeService;
    private final ModelService modelService;

    public ModelController(final MakeService makeService, final ModelService modelService) {
        this.makeService = makeService;
        this.modelService = modelService;
    }

    /**
     * .
     * list all models
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping(value = MODEL_LIST_PAGE)
    public String modelListPage(final MakeForm makeForm, final Model model) {
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        logger.info("Inside model List method of MakeController, form details are  {}",
                sanitizedMakeForm);
        var makeAndModelVOs = makeService.listAllMakesAndModels();
        if (!makeAndModelVOs.isEmpty()) {
            makeAndModelVOs.forEach(makeAndModelVO -> logger.info(MAKE_AND_MODEL_VO_IS, makeAndModelVO));
            makeForm.setMakeAndModelVOs(makeAndModelVOs);
        }
        var makeVOs = makeService.fetchMakes();
        if (!makeVOs.isEmpty()) {
            makeVOs.forEach(makeVO -> logger.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeVOs(convertMakeToMakeVO(makeVOs));
        }
        makeForm.setSearchMakeAndModelVO(new MakeAndModelVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        model.addAttribute(MAKE_FORM, makeForm);
        return "model/list";
    }

    @GetMapping("/make/getForEdit")
    public @ResponseBody
    Map<Long, String> getForEdit(@ModelAttribute("id") final String id,
                                 final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        logger.info("getForEdit method of make controller {}}", sanitizedId);
        var makeVO = modelService.getModelFromId(Long.valueOf(id));
        return makeVO.map(vo -> Map.of(vo.getMakeId(), vo.getModelName())).orElse(Collections.emptyMap());
    }

    /**
     * delete model.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/deleteModel")
    @SuppressWarnings("unused")
    public String deleteModel(final MakeForm makeForm, final Model model) {
        logger.debug("DeleteModel method of MakeController ");
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        logger.debug(MAKE_FORM_IS, sanitizedMakeForm);
        try {
            makeService.deleteModel(makeForm.getId());
            makeForm.setStatusMessage("Successfully deleted the selected Model");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            makeForm.setStatusMessage("Error occurred during deletion");
            makeForm.setStatusMessageType(DANGER);
            logger.error(ex.getLocalizedMessage(), ex);
        }
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return modelListPage(makeForm, model);
    }

    private List<MakeVO> convertMakeToMakeVO(final List<Make> makes) {
        return makes.stream().map(this::createMakeVO).toList();
    }

    private MakeVO createMakeVO(final Make make) {
        var makeVO = new MakeVO();
        makeVO.setId(make.getId());
        makeVO.setMakeName(make.getMakeName());
        makeVO.setDescription(make.getDescription());
        makeVO.setCreatedBy(make.getCreatedBy());
        makeVO.setModifiedBy(make.getModifiedBy());
        return makeVO;
    }

}
