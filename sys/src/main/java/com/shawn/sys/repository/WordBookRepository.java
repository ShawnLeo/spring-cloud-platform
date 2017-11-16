package com.shawn.sys.repository;

public interface WordBookRepository /*extends JpaRepository<WordBook, Long>, JpaSpecificationExecutor<WordBook>*/ {

//	class DelegatingWordBookSpecificationExecutor {
//		private WordBookRepository repository;
//
//		public DelegatingWordBookSpecificationExecutor(WordBookRepository repository) {
//			this.repository = repository;
//		}
//
//		public Page<WordBook> findAll(final WordBook filter, Pageable pageable) {
//			return repository.findAll(new Specification<WordBook>() {
//				@Override
//				public Predicate toPredicate(Root<WordBook> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//					if (filter == null) {
//						return cb.and(merge(eq(cb, root.get(WordBook_.level), 1)));
//					}
//
//					return cb.and(merge(
//
//					eq(cb, root.get(WordBook_.level), 1),
//					eq(cb, root.get(WordBook_.id), filter.getId()),
//					eq(cb, root.get(WordBook_.code), filter.getCode()),
//					like(cb, root.get(WordBook_.name), filter.getName())
//
//					));
//				}
//			}, pageable);
//		}
//
//		public WordBook findWordBook(final WordBook filter) {
//			return repository.findOne(new Specification<WordBook>() {
//				@Override
//				public Predicate toPredicate(Root<WordBook> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//					if (filter == null) {
//						return cb.conjunction();
//					}
//
//					return cb.and(merge(
//
//					eq(cb, root.get(WordBook_.code), filter.getCode()),
//					eq(cb, root.join(WordBook_.parent).get(WordBook_.id), filter.getParentId()),
//					eq(cb, root.join(WordBook_.parent).get(WordBook_.code), filter.getParentCode())
//
//					));
//				}
//			});
//		}
//	}
//
//	WordBook findByCode(String code);
//
//	List<WordBook> findByLevelOrderByDispOrderAsc(Integer level);
//
//	/**
//	 * 设置父节点为NULL
//	 *
//	 * @return
//	 */
//	@Modifying
//	@Query("UPDATE WordBook o SET o.parent = null")
//	int setParentToNull();
}
