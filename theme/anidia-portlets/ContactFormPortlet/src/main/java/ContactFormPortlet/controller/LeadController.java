package ContactFormPortlet.controller;

import ContactFormPortlet.dto.LeadDTO;

import com.liferay.portletmvc4spring.bind.annotation.ActionMapping;
import com.liferay.portletmvc4spring.bind.annotation.RenderMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Locale;

import javax.portlet.ActionResponse;
import javax.portlet.MutableRenderParameters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import org.json.JSONException;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import ContactFormPortlet.services.SalesforceService;
/**
 * @author danieldelapena
 */
@Controller
@RequestMapping("VIEW")
public class LeadController {
	private Log log = LogFactoryUtil.getLog(LeadController.class.getName());

	@ModelAttribute("lead")
	public LeadDTO getLeadModelAttribute() {
		return new LeadDTO();
	}

	@RenderMapping
	public String prepareView() {
		return "form";
	}

	@RenderMapping(params = "javax.portlet.action=success")
	public String showData(ModelMap modelMap) throws JSONException {

		SalesforceService salesforceService = new SalesforceService();

		LeadDTO lead = (LeadDTO) modelMap.get("lead");
		log.info("Sending Lead" );
		log.info(" >  firstName=" + lead.getFirstName());
		log.info(" >  lastName=" + lead.getLastName());
		log.info(" >  email=" + lead.getEmail());
		log.info(" >  phoneNumber=" + lead.getPhoneNumber());

		String result = salesforceService.sendLead(lead);

		log.info(" >  Result =" + result);

		return "form";
	}

	@ActionMapping
	public void submitApplicant(
		@ModelAttribute("lead") LeadDTO Lead, BindingResult bindingResult,
		ModelMap modelMap, Locale locale, ActionResponse actionResponse,
		SessionStatus sessionStatus) {
		//_localValidatorFactoryBean.validate(Lead, bindingResult);

		if (!bindingResult.hasErrors()) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("firstName=" + Lead.getFirstName());
				_logger.debug("lastName=" + Lead.getLastName());
				_logger.debug("email=" + Lead.getEmail());
				_logger.debug("phoneNumber=" + Lead.getPhoneNumber());
			}

			MutableRenderParameters mutableRenderParameters =
				actionResponse.getRenderParameters();

			mutableRenderParameters.setValue("javax.portlet.action", "success");

			sessionStatus.setComplete();
		}
		else {
			bindingResult.addError(
				new ObjectError(
					"Lead",
					_messageSource.getMessage(
						"please-correct-the-following-errors", null, locale)));
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LeadController.class);

	@Autowired
	private LocalValidatorFactoryBean _localValidatorFactoryBean;

	@Autowired
	private MessageSource _messageSource;

}