package {PACKAGE};

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codebone.framework.generic.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/{MAPPING_URI}")
public class {TABLE_NAME_CAMELCASE}Controller extends AbstractController{

	@Autowired
	private {TABLE_NAME_CAMELCASE}Service service;
	
	protected String getContextName(){
		return "{MAPPING_URI}";
	}

	@RequestMapping(method = RequestMethod.GET)
	@Secured("ROLE_{TABLE_NAME_UPPERCASE}_READ")
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res, HttpSession session, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page == null) {
			page = 1;
		}
		map.put("data", service.list(page));
		map.put("page", page);

		return getCommonModelAndView(getContextName()+"/list", map, session);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@Secured("ROLE_{TABLE_NAME_UPPERCASE}_READ")
	public ModelAndView search(HttpServletRequest req, HttpServletResponse res, HttpSession session, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();
		String property = req.getParameter("property");
		String keyword = req.getParameter("keyword");
		if (page == null) {
			page = 0;
		}
		map.put("data", service.search(property, keyword, page));
		map.put("page", page);
		return getCommonModelAndView(getContextName()+"/list", map, session);
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@Secured("ROLE_{TABLE_NAME_UPPERCASE}_CREATE")
	public ModelAndView create(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isCreate", "Y");
		return getCommonModelAndView(getContextName()+"/write", map, session);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@Secured("ROLE_{TABLE_NAME_UPPERCASE}_CREATE")
	public RedirectView create_POST(HttpServletRequest req,
			HttpServletResponse res, HttpSession session, @ModelAttribute {TABLE_NAME_CAMELCASE} model) throws ParseException {
		service.create(model);
		return new RedirectView("");
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@Secured("ROLE_{TABLE_NAME_UPPERCASE}_UPDATE")
	public ModelAndView update(HttpServletRequest req, HttpServletResponse res, HttpSession session, String idx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", service.read(idx));
		map.put("id", idx);
		map.put("isCreate", "N");
		return getCommonModelAndView(getContextName()+"/write", map, session);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@Secured("ROLE_{TABLE_NAME_UPPERCASE}_UPDATE")
	public RedirectView update_POST(HttpServletRequest req,
			HttpServletResponse res, HttpSession session, @ModelAttribute {TABLE_NAME_CAMELCASE} model) throws ParseException {
		service.update(model);
		return new RedirectView("");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@Secured("ROLE_{TABLE_NAME_UPPERCASE}_DELETE")
	public RedirectView delete(HttpServletRequest req, HttpServletResponse res, HttpSession session, String idx) {
		{TABLE_NAME_CAMELCASE} model = ({TABLE_NAME_CAMELCASE}) service.read(idx).getData();
		service.delete(model);
		return new RedirectView("");
	}
}