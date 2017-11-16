package com.shawn.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: WordBookController
 * @Description: 系统字典维护
 * @author liuzyhn
 * @date 2016年2月24日 下午2:36:19
 */
//@Controller
public class WordBookController {
	private static final Logger LOG = LoggerFactory.getLogger(WordBookController.class);
//	@Autowired
//	private WordBookService service;
//
//	/**
//	 * 为需要与库中数据merge而服务（如修改,其他地方不可使用）
//	 * @param id
//	 * @param model
//	 */
//	@ModelAttribute
//	public void getWordBook(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
//		if(id > 0) {
//			model.addAttribute("init_wordBook", service.findById(id));
//		}
//	}
//
//	/**
//	 * 获取wordbookMap
//	 * @return
//	 */
//	@RequestMapping(value = URI.WORDBOOK_MAP, method = RequestMethod.GET)
//	public Object getWordBookMapJsp(Model model) {
//		// TODO  缓存
//		Map<String, List<WordBook>> wordbookMap = new HashMap<>();
//		List<WordBook> wordbooks = service.findWordBooksMap();
//		for(WordBook wordbook : wordbooks) {
//			wordbookMap.put(wordbook.getCode(), wordbook.getChildrens());
//		}
//		model.addAttribute("wordbookMap", wordbookMap);
//		return VIEW.ADMIN_WORDBOOK_MAP;
//	}
//
//	/**
//	 * 系统字典列表
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = URI.WORDBOOK_HOME, method = RequestMethod.GET)
//	public String index(Model model) {
//		LOG.debug("method=>wrodbook=>index");
//		service.findWordBookPageContext(null, null, null).addAttributeTo(model);
//		return VIEW.ADMIN_WORDBOOK;
//	}
//
//	/**
//	 * 系统字典项分页查询
//	 *
//	 * @param pageNumber
//	 * @param pageSize
//	 * @param criteria
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = URI.WORDBOOK_PAGE, method = RequestMethod.GET)
//	@ResponseBody
//	public Object page(@PathVariable(value = "pageNumber") Integer pageNumber,
//			@RequestParam(value = "pageSize", required = false) Integer pageSize, WordBook criteria, Model model) {
//		LOG.debug("pageNumber={}", pageNumber);
//		PageContext<WordBook> pageContext = service.findWordBookPageContext(pageNumber, pageSize, criteria);
//		return pageContext;
//	}
//
//	/**
//	 * 判断字典是否存在
//	 * @param wordBook
//	 * @return false：存在，true：不存在
//	 */
//	@RequestMapping(value = URI.WORDBOOK_ISEXISTS, method = RequestMethod.POST)
//	@ResponseBody
//	public boolean isExists(@ModelAttribute("wordBook") WordBook wordBook) {
//		LOG.debug("wordbook[name={},code={}] isExists", wordBook.getName(), wordBook.getCode());
//		return !service.exists(wordBook);
//	}
//
//	/**
//	 * 加载字典数据数据
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value = URI.WORDBOOK_LOAD, method = RequestMethod.GET)
//	@ResponseBody
//	public WordBook load(@PathVariable("id") Long id) {
//		LOG.debug("load wordbook[id={}]", id);
//		return service.findById(id);
//	}
//
//	/**
//	 * 加载字典数据数据
//	 * @param code 编号
//	 * @return
//	 */
//	@RequestMapping(value = URI.WORDBOOK_LOAD_CODE, method = RequestMethod.POST)
//	@ResponseBody
//	public WordBook loadByCode(@PathVariable("code") String code) {
//		LOG.debug("load wordbook[code={}]", code);
//		return service.findByCode(code);
//	}
//
//	/**
//	 * 字典项（内容）页面
//	 * @param id
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = URI.WORDBOOK_ITEMS, method = RequestMethod.GET)
//	public String items(@PathVariable("id") Long id, Model model) {
//		LOG.debug("字典项页面[id={}]", id);
//		model.addAttribute("wordbook", service.findById(id));
//		return VIEW.ADMIN_WORDBOOK_ITEMS;
//	}
//
//	/**
//	 * 新增/修改
//	 * @param wordBook
//	 * @return
//	 */
//	@RequestMapping(value = URI.WORDBOOK_SAVE, method = RequestMethod.POST)
//	@ResponseBody
//	public WordBook save(@ModelAttribute("init_wordBook") WordBook wordBook) {
//		LOG.debug("save wordbook");
//		if(wordBook.getId() == null && wordBook.getParentId() != null) {  //新增字典项
//			WordBook parent = service.findById(wordBook.getParentId());
//			wordBook.setParent(parent);
//			wordBook.setLevel(parent.getLevel()+1);
//		}
//		// TODO 清楚缓存
////		wordBookServiceSupport.clearWordBooksMap();
//		return service.save(wordBook);
//	}
//
//	/**
//	 * 删除
//	 * @param wordBook
//	 * @return
//	 */
//	@RequestMapping(value = URI.WORDBOOK_DELETE, method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseMessage delete(@ModelAttribute("init_wordBook") WordBook wordBook) {
//		LOG.debug("delete wordbook");
//		ResponseMessage response = new ResponseMessage();
//		try {
//			service.delete(wordBook);
//			response.setStatusCode(1);
//		} catch (Exception e) {
//			LOG.error(e.getMessage(), e);
//			response.fail(e.getMessage());
//		}
////		wordBookServiceSupport.clearWordBooksMap();
//		return response;
//	}

}
