package edu.wisc.portlet.preferred.web;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;
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
        Map<String, String> userInfo =
            (Map<String, String>) request.getAttribute(PortletRequest.USER_INFO);
        final String pvi = PrimaryAttributeUtils.getPrimaryId();

        PreferredName preferredName =
            preferredNameService.getPreferredName(pvi, userInfo.get("sn"));
        String currentFirstName = userInfo.get("wiscedupreferredfirstname");
        String currentMiddleName = userInfo.get("wiscedupreferredmiddlename");
        String currentLastName = userInfo.get("wiscedupreferredlastname");

        if (preferredName != null) {
            //view stuff
            modelMap.addAttribute("firstName", preferredName.getFirstName());
            modelMap.addAttribute("middleName", preferredName.getMiddleName());

            modelMap.addAttribute("lastName", preferredName.getLastName());
        }

        modelMap.addAttribute("pendingStatus", preferredNameService.getStatus(
            new PreferredName(currentFirstName, currentMiddleName, currentLastName, pvi)));
        modelMap.addAttribute("sirName", userInfo.get("sn"));
        modelMap.addAttribute("legalName", userInfo.get("wiscEduSORName"));


        //edit setup
        if (!modelMap.containsKey("preferredName")) {

            if (preferredName != null) {
                modelMap.addAttribute("preferredName", preferredName);
            } else {
                modelMap.addAttribute("preferredName", new PreferredName());
            }
        }

        if (request.getParameter("therewasanerror") != null) {
            modelMap.addAttribute("therewasanerror", "true");
        }

        return "viewPage";
    }

    @RenderMapping(params = "action=edit")
    public String initializeEdit(ModelMap modelMap, RenderRequest request) {
        if (!modelMap.containsKey("preferredName")) {
            final String pvi = PrimaryAttributeUtils.getPrimaryId();
            PreferredName preferredName = preferredNameService.getPreferredName(pvi);

            if (preferredName != null) {
                modelMap.addAttribute("preferredName", preferredName);
            } else {
                modelMap.addAttribute("preferredName", new PreferredName());
            }
        }

        return "viewPage";
    }

    @ActionMapping(params = "action=delete")
    public void submitDelete(ActionResponse response) throws PortletModeException {
        final String pvi = PrimaryAttributeUtils.getPrimaryId();
        preferredNameService.deletePreferredName(pvi);
        response.setPortletMode(PortletMode.VIEW);
    }

    @ActionMapping(params = "action=savePreferredName")
    public void submitEdit(ActionRequest request, ActionResponse response,
        PreferredName preferredName, BindingResult bindingResult) throws PortletModeException {
        final String pvi = PrimaryAttributeUtils.getPrimaryId();
        @SuppressWarnings("unchecked")
        Map<String, String> userInfo =
            (Map<String, String>) request.getAttribute(PortletRequest.USER_INFO);

        PreferredNameExtended pne = new PreferredNameExtended(preferredName, userInfo.get("sn"));

        //validation
        ValidationUtils.invokeValidator(new PreferredNameValidator(), pne, bindingResult);
        if (!bindingResult.hasErrors()) {
            //submit changes to DAO

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
