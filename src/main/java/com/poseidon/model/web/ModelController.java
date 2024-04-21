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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.poseidon.init.Constants.PAGE_SIZE;

@Controller
@SuppressWarnings("unused")
public class ModelController {
    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);
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
    @PostMapping(value = "make/ModelList")
    public String modelListPage(final MakeForm makeForm, final Model model) {
        return modelByPage(1, model);
    }

    @GetMapping(value = "model/page/{pageNumber}")
    public String modelByPage(final @PathVariable(name = "pageNumber") int pageNumber,
                              final Model model) {
        logger.info("ListByPage method of user controller ");
        var page = modelService.listModels(pageNumber);
        var startCount = (pageNumber - 1) * PAGE_SIZE + 1;
        long endCount = (long) startCount + PAGE_SIZE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("models", page.getContent());
        var makes = makeService.fetchAllMakes();
        model.addAttribute("makes", makes);
        MakeForm makeForm = new MakeForm();
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
            modelService.deleteModel(makeForm.getId());
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

    /**
     * search for a model.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/model/searchModel")
    public String searchModel(final MakeForm makeForm, final Model model) {
        return searchModelByPage(1, makeForm, model);
    }

    @GetMapping(value = "model/SearchPage/{pageNumber}")
    public String searchModelByPage(final @PathVariable(name = "pageNumber") int pageNumber,
                                    final MakeForm makeForm,
                                    final Model model) {
        logger.info("ListByPage method of user controller ");
        loggingFromSearch(makeForm);
        var makeVOs = makeService.searchModels(makeForm.getSearchMakeAndModelVO());
        makeForm.setStatusMessage("Found " + makeVOs.size() + " Models");
        makeForm.setStatusMessageType("info");
        if (!makeVOs.isEmpty()) {
            makeVOs.forEach(makeVO -> logger.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        var searchMakeVOs = convertMakeToMakeVO(makeService.fetchAllMakes());
        if (searchMakeVOs != null) {
            searchMakeVOs.forEach(searchMakeVO -> logger.debug("SearchMakeVO is {}", searchMakeVO));
            makeForm.setMakeVOs(searchMakeVOs);
        }
        var page = modelService.listModels(pageNumber);
        var startCount = (pageNumber - 1) * PAGE_SIZE + 1;
        long endCount = (long) startCount + PAGE_SIZE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("models", page.getContent());
        var makes = makeService.fetchAllMakes();
        model.addAttribute("makes", makes);
        //MakeForm makeForm = new MakeForm();
        //makeForm.setSearchMakeAndModelVO(new MakeAndModelVO());
        //makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        //makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        model.addAttribute(MAKE_FORM, makeForm);
        return "model/list";
    }

    /**
     * saveModel.
     *
     * @param selectMakeId    selectMakeId
     * @param selectModelName selectModelName
     * @param result          result
     * @return json string
     */
    @PostMapping("/make/saveModel")
    public @ResponseBody
    List<MakeAndModelVO> saveModel(@ModelAttribute("selectMakeId") final Long selectMakeId,
                                   @ModelAttribute("selectModelName") final String selectModelName,
                                   final BindingResult result) {
        logger.info("SaveModel method of MakeController ");
        if (!result.hasErrors()) {
            makeService.addNewModel(populateModelVO(selectMakeId, selectModelName));
        } else {
            logger.info("errors {}", result);
        }
        return modelService.listAllMakesAndModels();
    }

    @PutMapping("/make/updateModel")
    public @ResponseBody
    List<MakeAndModelVO> updateModel(@ModelAttribute("id") final Long id,
                                     @ModelAttribute("modalMakeName") final Long makeId,
                                     @ModelAttribute("modalModelName") final String modalModelName,
                                     final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id.toString());
        var sanitizedMakeId = CommonUtils.sanitizedString(makeId.toString());
        var sanitizedModelName = CommonUtils.sanitizedString(modalModelName);
        logger.info("UpdateModel method of make controller with id {}, makeId {}, modalModelName {}",
                sanitizedId, sanitizedMakeId, sanitizedModelName);
        makeService.updateModel(id, makeId, modalModelName);
        return modelService.listAllMakesAndModels();
    }

    private void loggingFromSearch(final MakeForm makeForm) {
        logger.debug("SearchModel method of MakeController ");
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        logger.debug("MakeForm instance to search {}", sanitizedMakeForm);
        if (makeForm.getSearchMakeAndModelVO() != null) {
            var sanitizedSearchModel = CommonUtils.sanitizedString(
                    makeForm.getSearchMakeAndModelVO().toString());
            logger.debug("SearchVO instance to search {}", sanitizedSearchModel);
        }
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

    public String findLoggedInUsername() {
        var username = "";
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            username = auth.getName();
        }
        return username;
    }

    private MakeAndModelVO populateModelVO(final Long selectMakeId, final String selectModelName) {
        var sanitizedSelectMakeId = CommonUtils.sanitizedString(selectMakeId.toString());
        var sanitizedSelectModelName = CommonUtils.sanitizedString(selectModelName);
        logger.info("selectMakeId : {}", sanitizedSelectMakeId);
        logger.info("selectModelName : {}", sanitizedSelectModelName);
        var userName = findLoggedInUsername();
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setMakeId(selectMakeId);
        makeAndModelVO.setModelName(selectModelName);
        makeAndModelVO.setCreatedBy(userName);
        makeAndModelVO.setModifiedBy(userName);
        return makeAndModelVO;
    }

}
