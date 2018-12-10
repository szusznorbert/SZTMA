package ro.sapientia2015.story.controller;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ro.sapientia2015.story.dto.SprintDTO;
import ro.sapientia2015.story.dto.StoryDTO;
import ro.sapientia2015.story.exception.NotFoundException;
import ro.sapientia2015.story.model.Sprint;
import ro.sapientia2015.story.model.Story;
import ro.sapientia2015.story.service.SprintService;

@Controller
public class SprintController {

	@Resource
	private SprintService service;
	
	    protected static final String FEEDBACK_MESSAGE_KEY_ADDED = "feedback.message.sprint.added";
	    protected static final String FEEDBACK_MESSAGE_KEY_UPDATED = "feedback.message.sprint.updated";
	    protected static final String FEEDBACK_MESSAGE_KEY_DELETED = "feedback.message.sprint.deleted";

	    protected static final String FLASH_MESSAGE_KEY_ERROR = "errorMessage";
	    protected static final String FLASH_MESSAGE_KEY_FEEDBACK = "feedbackMessage";

	    //protected static final String MODEL_ATTRIBUTE = "story";
	    //protected static final String MODEL_ATTRIBUTE_LIST = "stories";

	    protected static final String SPRINT_PARAMETER_ID = "id";

	    //protected static final String REQUEST_MAPPING_LIST = "/";
	    protected static final String REQUEST_MAPPING_VIEW = "/sprint/{id}";

	    protected static final String SPRINT_ADD = "sprint/add";
	    protected static final String SPRINT_LIST = "/sprint/list";
	    protected static final String SPRINT_UPDATE = "sprint/update";
	    protected static final String SPRINT_VIEW = "sprint/view";
	
	    @Resource
	    private MessageSource messageSource;

	private static final String MODEL_ATTRIBUTE = "sprint";
	
	 private String getMessage(String messageCode, Object... messageParameters) {
	        Locale current = LocaleContextHolder.getLocale();
	        return messageSource.getMessage(messageCode, messageParameters, current);
	    }
	
	  private SprintDTO constructFormObjectForUpdateForm(Sprint updated) {
		  SprintDTO dto = new SprintDTO();

	        dto.setId(updated.getId());
	        dto.setDescription(updated.getDescription());
	        dto.setTitle(updated.getTitle());

	        return dto;
	    }
	  private void addFeedbackMessage(RedirectAttributes attributes, String messageCode, Object... messageParameters) {
	        String localizedFeedbackMessage = getMessage(messageCode, messageParameters);
	        attributes.addFlashAttribute(FLASH_MESSAGE_KEY_FEEDBACK, localizedFeedbackMessage);
	    }

	@RequestMapping(value = "/sprint/list", method = RequestMethod.GET)
	public String listSprints(Model model) {

		List<Sprint> sprints = service.findAll();
		model.addAttribute("sprints", sprints);
		return SPRINT_LIST;
	}
	
    private String createRedirectViewPath(String requestMapping) {
        StringBuilder redirectViewPath = new StringBuilder();
        redirectViewPath.append("redirect:");
        redirectViewPath.append(requestMapping);
        return redirectViewPath.toString();
    }
    
	@RequestMapping(value = "/sprint/add", method = RequestMethod.GET)
	public String showForm(Model model) {

		SprintDTO sprint = new SprintDTO();
		model.addAttribute("sprint", sprint);
		return SPRINT_ADD;
	}
   //new function to delete the sprints
	@RequestMapping(value = "/sprint/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute(MODEL_ATTRIBUTE) SprintDTO dto, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()){
			return SPRINT_ADD;
		}
		
		service.add(dto);
		
		return createRedirectViewPath(SPRINT_LIST);
	}
	//new function to open the newly added SPRINT
	
	@RequestMapping(value = REQUEST_MAPPING_VIEW, method = RequestMethod.GET)
    public String findById(@PathVariable("id") Long id, Model model) throws NotFoundException {
        Sprint found = service.findById(id);
        model.addAttribute(MODEL_ATTRIBUTE, found);
        return SPRINT_VIEW;
    }
	
	@RequestMapping(value = "/sprint/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable("id") Long id, RedirectAttributes attributes) throws NotFoundException {
        Sprint deleted = service.deleteById(id);
        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_DELETED, deleted.getTitle());
        return createRedirectViewPath(SPRINT_LIST);
    }
	
	  @RequestMapping(value = "/sprint/update/{id}", method = RequestMethod.GET)
	    public String showUpdateForm(@PathVariable("id") Long id, Model model) throws NotFoundException {
	        Sprint updated = service.findById(id);
	        SprintDTO formObject = constructFormObjectForUpdateForm(updated);
	        model.addAttribute(MODEL_ATTRIBUTE, formObject);

	        return SPRINT_UPDATE;
	    }
	
	//new functions to update the sprints
	@RequestMapping(value = "/sprint/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(MODEL_ATTRIBUTE) SprintDTO dto, BindingResult result, RedirectAttributes attributes) throws NotFoundException {
        if (result.hasErrors()) {
            return SPRINT_UPDATE;
        }

        Sprint updated = service.update(dto);
        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_UPDATED, updated.getTitle());
        attributes.addAttribute(SPRINT_PARAMETER_ID, updated.getId());

        return createRedirectViewPath(REQUEST_MAPPING_VIEW);
    }
}
