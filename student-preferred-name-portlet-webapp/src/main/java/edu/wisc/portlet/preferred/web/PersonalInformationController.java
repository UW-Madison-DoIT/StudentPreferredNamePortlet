package edu.wisc.portlet.preferred.web;

import java.util.Map;

import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.validation.Valid;

import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.validator.PreferredNameValidator;
import edu.wisc.portlet.preferred.service.PreferredNameService;

@Controller
@RequestMapping("VIEW")
public class PersonalInformationController {
	
	private PreferredNameService preferredNameService;
	
	@Autowired
	public void setPreferredNameService(PreferredNameService pns) {
		this.preferredNameService = pns;
	}
	
	@RenderMapping
	public String initializeView(ModelMap modelMap, RenderRequest request) {
		
		//view setup
		@SuppressWarnings("unchecked")
		Map<String, String> userInfo = (Map <String, String>) request.getAttribute(PortletRequest.USER_INFO);
		final String pvi = PrimaryAttributeUtils.getPrimaryId();
		
		PreferredName preferredName = preferredNameService.getPreferredName(pvi);
		String currentFirstName = userInfo.get("wiscedupreferredfirstname");
		String currentMiddleName = userInfo.get("wiscedupreferredmiddlename");
		
		if(preferredName != null) {
			//view stuff
			modelMap.addAttribute("firstName", preferredName.getFirstName());
			modelMap.addAttribute("middleName", preferredName.getMiddleName());
		}
		
		modelMap.addAttribute("pendingStatus",preferredNameService.getStatus(new PreferredName(currentFirstName, currentMiddleName,pvi)));
		modelMap.addAttribute("sirName",userInfo.get("sn"));
		modelMap.addAttribute("displayName",userInfo.get("displayName"));
		
		
		//edit setup
		if(!modelMap.containsKey("preferredName")) {
		
			if(preferredName != null) {
				modelMap.addAttribute("preferredName", preferredName);
			} else {
				modelMap.addAttribute("preferredName", new PreferredName());
			}
		}
		
		if(request.getParameter("therewasanerror") != null) {
			modelMap.addAttribute("therewasanerror","true");
		}
		
		return "viewPage";
	}
	
	@RenderMapping(params="action=edit")
	public String initializeEdit(ModelMap modelMap, RenderRequest request) {
		if(!modelMap.containsKey("preferredName")) {
			final String pvi = PrimaryAttributeUtils.getPrimaryId();
			PreferredName preferredName = preferredNameService.getPreferredName(pvi);
			
			if(preferredName != null) {
				modelMap.addAttribute("preferredName", preferredName);
			} else {
				modelMap.addAttribute("preferredName", new PreferredName());
			}
		}
		
		return "viewPage";
	}
	
	@ActionMapping(params="action=savePreferredName")
	public void submitEdit(ActionResponse response, PreferredName preferredName, BindingResult bindingResult) throws PortletModeException {
		//validation
		ValidationUtils.invokeValidator(new PreferredNameValidator(), preferredName, bindingResult);
		if(!bindingResult.hasErrors()) {
			//submit changes to DAO
			final String pvi = PrimaryAttributeUtils.getPrimaryId();
			preferredName.setPvi(pvi);
			
			preferredNameService.setPreferredName(preferredName);
			//redirect to view page on success
			response.setPortletMode(PortletMode.VIEW);
		} else {
			//fail back to edit mode with flag set
			response.setRenderParameter("therewasanerror", "true");
			response.setPortletMode(PortletMode.VIEW);
		}
	}
}
