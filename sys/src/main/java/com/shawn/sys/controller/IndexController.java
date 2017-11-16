package com.shawn.sys.controller;

import com.shawn.sys.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 首页
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { URI.INDEX_BASE, URI.INDEX_HOME }, method = RequestMethod.GET)
	public String index(Model model) {
		logger.info("method=>index");
		return "index";
	}
}
