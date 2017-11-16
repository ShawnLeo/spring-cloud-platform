package com.shawn.sys.service;

//@Transactional(readOnly = true)
//@Service
public class WordBookService {
//	private static final Logger LOG = LoggerFactory.getLogger(WordBookService.class);
//
//	@Autowired
//	private PageService pageService;
//	@Autowired
//	private WordBookRepository repos;
//	@Autowired
//	private BeanFactory beanFactory;
//
//	public final String CACHE_WORDBOOKS_MAP = "CACHE_WORDBOOKS_MAP";
//
//	public PageContext<WordBook> findWordBookPageContext(Integer pageNumber, Integer pageSize, final WordBook criteria) {
//		LOG.debug("分页条件筛选字典项");
//		DelegatingWordBookSpecificationExecutor executor = new DelegatingWordBookSpecificationExecutor(repos);
//		Sort sort = new Sort(new Order(Sort.Direction.DESC, WordBook_.dispOrder.getName()));
//		Pageable pageable = pageService.getPageable(pageNumber, pageSize, sort);
//		return pageService.buildPageContext(executor.findAll(criteria, pageable));
//	}
//
//	public List<WordBook> findWordBooksMap() {
//		List<WordBook> wordBooks = repos.findByLevelOrderByDispOrderAsc(1);
//		peekWordBooksMap(wordBooks);// 缓存 JPA懒加载对象 Session关闭
//		return wordBooks;
//	}
//
//	private void peekWordBooksMap(List<WordBook> wordBooks) {
//		for (WordBook wordbook : wordBooks) {
//			if (wordbook.getChildrens() != null)
//				peekWordBooksMap(wordbook.getChildrens());
//		}
//	}
//
//	public WordBook findByCode(String code) {
//		LOG.debug("findByCode wordBook[code={}]", code);
//		return repos.findByCode(code);
//	}
//
//	public WordBook findWordBookItem(String parentCode, String itemCode) {
//		final WordBook criteria = new WordBook();
//		criteria.setParentCode(parentCode);
//		criteria.setCode(itemCode);
//		return findByWordBook(criteria);
//	}
//
//	public WordBook findById(Long id) {
//		LOG.debug("findById wordBook[id={}]", id);
//		return repos.findOne(id);
//	}
//
//	@Transactional
//	public WordBook save(WordBook wordBook) {
//		LOG.debug("WordBook > save");
//		return repos.save(wordBook);
//	}
//
//	@Transactional
//	public void delete(WordBook wordBook) {
//		LOG.info("wordBook[name={},code={}] > del", wordBook.getName(), wordBook.getCode());
//		repos.delete(wordBook);
//	}
//
//	public boolean exists(WordBook wordBook) {
//		if (wordBook == null) {
//			return false;
//		}
//		WordBook wordBook_ = findByWordBook(wordBook);
//		if (wordBook_ != null && wordBook_.getId() != null && !wordBook_.getId().equals(wordBook.getId())) {
//			return true;
//		}
//		return false;
//	}
//
//	private WordBook findByWordBook(final WordBook criteria) {
//		DelegatingWordBookSpecificationExecutor executor = new DelegatingWordBookSpecificationExecutor(repos);
//		return executor.findWordBook(criteria);
//	}
//
//	public Map<String, String> getName(List<WordBook> list) {
//		Map<String, String> map = new HashMap<String, String>();
//		if (list != null && list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				WordBook book = list.get(i);
//				map.put(book.getCode(), book.getName());
//			}
//		}
//		return map;
//	}

}
