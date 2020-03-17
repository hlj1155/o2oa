package com.x.organization.assemble.control.jaxrs.unit;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.container.factory.EntityManagerContainerFactory;
import com.x.base.core.entity.JpaObject;
import com.x.base.core.project.annotation.FieldDescribe;
import com.x.base.core.project.bean.WrapCopier;
import com.x.base.core.project.bean.WrapCopierFactory;
import com.x.base.core.project.cache.ApplicationCache;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.tools.ListTools;
import com.x.organization.assemble.control.Business;
import com.x.organization.core.entity.Identity;
import com.x.organization.core.entity.Identity_;
import com.x.organization.core.entity.Person;
import com.x.organization.core.entity.Unit;
import com.x.organization.core.entity.UnitAttribute;
import com.x.organization.core.entity.UnitAttribute_;
import com.x.organization.core.entity.UnitDuty;
import com.x.organization.core.entity.UnitDuty_;
import com.x.organization.core.entity.Unit_;

import net.sf.ehcache.Element;

class ActionGet extends BaseAction {

	/** 获取一个组织的信息,要列示其直接下级组织和直接下级身份,同时为了显示的需要,要把下级组织的下级组织数量也带上,不然前端就不知道是否能再逐级展开 */
	ActionResult<Wo> execute(EffectivePerson effectivePerson, String flag) throws Exception {
		try (EntityManagerContainer emc = EntityManagerContainerFactory.instance().create()) {
			ActionResult<Wo> result = new ActionResult<>();
			Business business = new Business(emc);
			String cacheKey = ApplicationCache.concreteCacheKey(this.getClass(), flag);
			Element element = business.cache().get(cacheKey);
			if (null != element && (null != element.getObjectValue())) {
				result.setData((Wo) element.getObjectValue());
			} else {
				Wo wo = this.get(effectivePerson, business, flag);
				business.cache().put(new Element(cacheKey, wo));
				result.setData(wo);
			}
			this.updateControl(effectivePerson, business, result.getData());
			return result;
		}
	}

	public static class Wo extends WoAbstractUnit {

		private static final long serialVersionUID = -125007357898871894L;

		@FieldDescribe("直接上级组织对象")
		private Wo woSupDirectUnit;

		@FieldDescribe("直接下级身份对象")
		private List<WoIdentity> woSubDirectIdentityList;

		@FieldDescribe("组织属性对象")
		private List<WoUnitAttribute> woUnitAttributeList;

		@FieldDescribe("组织职务对象")
		private List<WoUnitDuty> woUnitDutyList;

		@FieldDescribe("直接下级组织数量")
		private Long subDirectUnitCount = 0L;

		@FieldDescribe("直接下级身份数量")
		private Long subDirectIdentityCount = 0L;

		static WrapCopier<Unit, Wo> copier = WrapCopierFactory.wo(Unit.class, Wo.class, null,
				JpaObject.FieldsInvisible);

		public List<WoIdentity> getWoSubDirectIdentityList() {
			return woSubDirectIdentityList;
		}

		public void setWoSubDirectIdentityList(List<WoIdentity> woSubDirectIdentityList) {
			this.woSubDirectIdentityList = woSubDirectIdentityList;
		}

		public Wo getWoSupDirectUnit() {
			return woSupDirectUnit;
		}

		public void setWoSupDirectUnit(Wo woSupDirectUnit) {
			this.woSupDirectUnit = woSupDirectUnit;
		}

		public List<WoUnitAttribute> getWoUnitAttributeList() {
			return woUnitAttributeList;
		}

		public void setWoUnitAttributeList(List<WoUnitAttribute> woUnitAttributeList) {
			this.woUnitAttributeList = woUnitAttributeList;
		}

		public List<WoUnitDuty> getWoUnitDutyList() {
			return woUnitDutyList;
		}

		public void setWoUnitDutyList(List<WoUnitDuty> woUnitDutyList) {
			this.woUnitDutyList = woUnitDutyList;
		}

		public Long getSubDirectUnitCount() {
			return subDirectUnitCount;
		}

		public void setSubDirectUnitCount(Long subDirectUnitCount) {
			this.subDirectUnitCount = subDirectUnitCount;
		}

		public Long getSubDirectIdentityCount() {
			return subDirectIdentityCount;
		}

		public void setSubDirectIdentityCount(Long subDirectIdentityCount) {
			this.subDirectIdentityCount = subDirectIdentityCount;
		}

	}

	public static class WoIdentity extends Identity {

		private static final long serialVersionUID = 7096544058621159846L;

		private WoPerson woPerson;

		static WrapCopier<Identity, WoIdentity> copier = WrapCopierFactory.wo(Identity.class, WoIdentity.class, null,
				JpaObject.FieldsInvisible);

		public WoPerson getWoPerson() {
			return woPerson;
		}

		public void setWoPerson(WoPerson woPerson) {
			this.woPerson = woPerson;
		}
	}

	public static class WoPerson extends Person {

		private static final long serialVersionUID = 7096544058621159846L;

		static WrapCopier<Person, WoPerson> copier = WrapCopierFactory.wo(Person.class, WoPerson.class, null,
				ListTools.toList(JpaObject.FieldsInvisible, "password", "icon"));
	}

	public static class WoUnitAttribute extends UnitAttribute {

		private static final long serialVersionUID = -2515911253898058718L;

		static WrapCopier<UnitAttribute, WoUnitAttribute> copier = WrapCopierFactory.wo(UnitAttribute.class,
				WoUnitAttribute.class, null, JpaObject.FieldsInvisible);
	}

	public static class WoUnitDuty extends UnitDuty {

		private static final long serialVersionUID = 7096544058621159846L;

		@FieldDescribe("身份对象")
		private List<WoIdentity> woIdentityList;

		static WrapCopier<UnitDuty, WoUnitDuty> copier = WrapCopierFactory.wo(UnitDuty.class, WoUnitDuty.class, null,
				JpaObject.FieldsInvisible);

		public List<WoIdentity> getWoIdentityList() {
			return woIdentityList;
		}

		public void setWoIdentityList(List<WoIdentity> woIdentityList) {
			this.woIdentityList = woIdentityList;
		}
	}

	private Wo get(EffectivePerson effectivePerson, Business business, String flag) throws Exception {
		Unit o = business.unit().pick(flag);
		if (null == o) {
			throw new ExceptionUnitNotExist(flag);
		}
		Wo wo = Wo.copier.copy(o);
		this.referenceUnitAttribute(business, wo);
		this.referenceUnitDuty(business, wo);
		this.referenceSubDirectIdentity(business, wo);
		this.referenceSupDirectUnit(business, wo);
		wo.setSubDirectIdentityCount(this.countSubDirectIdentity(business, wo));
		wo.setSubDirectUnitCount(this.countSubDirectUnit(business, wo));
		return wo;
	}

	private void referenceSupDirectUnit(Business business, Wo wo) throws Exception {
		if (StringUtils.isNotEmpty(wo.getSuperior())) {
			Unit o = business.unit().pick(wo.getSuperior());
			if (null == o) {
				throw new ExceptionSuperiorNotExist(wo.getName(), wo.getSuperior());
			}
			Wo woSuperior = Wo.copier.copy(o);
			wo.setWoSupDirectUnit(woSuperior);
		}
	}

	private void referenceSubDirectIdentity(Business business, Wo wo) throws Exception {
		EntityManager em = business.entityManagerContainer().get(Identity.class);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Identity> cq = cb.createQuery(Identity.class);
		Root<Identity> root = cq.from(Identity.class);
		Predicate p = cb.equal(root.get(Identity_.unit), wo.getId());
		List<Identity> os = em.createQuery(cq.select(root).where(p)).getResultList();
		List<WoIdentity> wos = WoIdentity.copier.copy(os);
		for (WoIdentity woIdentity : wos) {
			this.referencePerson(business, woIdentity);
		}
		wos = business.identity().sort(wos);
		wo.setWoSubDirectIdentityList(wos);
	}

	private void referenceUnitAttribute(Business business, Wo wo) throws Exception {
		EntityManager em = business.entityManagerContainer().get(UnitAttribute.class);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UnitAttribute> cq = cb.createQuery(UnitAttribute.class);
		Root<UnitAttribute> root = cq.from(UnitAttribute.class);
		Predicate p = cb.equal(root.get(UnitAttribute_.unit), wo.getId());
		List<UnitAttribute> os = em.createQuery(cq.select(root).where(p)).getResultList();
		List<WoUnitAttribute> wos = WoUnitAttribute.copier.copy(os);
		wos = business.unitAttribute().sort(wos);
		wo.setWoUnitAttributeList(wos);
	}

	private void referenceUnitDuty(Business business, Wo wo) throws Exception {
		EntityManager em = business.entityManagerContainer().get(UnitDuty.class);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UnitDuty> cq = cb.createQuery(UnitDuty.class);
		Root<UnitDuty> root = cq.from(UnitDuty.class);
		Predicate p = cb.equal(root.get(UnitDuty_.unit), wo.getId());
		List<UnitDuty> os = em.createQuery(cq.select(root).where(p)).getResultList();
		List<WoUnitDuty> wos = WoUnitDuty.copier.copy(os);
		for (WoUnitDuty woUnitDuty : wos) {
			this.referenceIdentity(business, woUnitDuty);
		}
		wos = business.unitDuty().sort(wos);
		wo.setWoUnitDutyList(wos);
	}

	private void referenceIdentity(Business business, WoUnitDuty woUnitDuty) throws Exception {
		EntityManager em = business.entityManagerContainer().get(Identity.class);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Identity> cq = cb.createQuery(Identity.class);
		Root<Identity> root = cq.from(Identity.class);
		Predicate p = root.get(Identity_.id).in(woUnitDuty.getIdentityList());
		List<Identity> os = em.createQuery(cq.select(root).where(p).distinct(true)).getResultList();
		List<WoIdentity> wos = WoIdentity.copier.copy(os);
		for (WoIdentity woIdentity : wos) {
			this.referencePerson(business, woIdentity);
		}
		wos = business.identity().sort(wos);
		woUnitDuty.setWoIdentityList(wos);
	}

	private void referencePerson(Business business, WoIdentity woIdentity) throws Exception {
		Person person = business.person().pick(woIdentity.getPerson());
		if (null == person) {
			throw new ExceptionPersonNotExist(woIdentity.getPerson());
		}
		WoPerson woPerson = WoPerson.copier.copy(person);
		woIdentity.setWoPerson(woPerson);
	}

	private Long countSubDirectUnit(Business business, Wo wo) throws Exception {
		EntityManager em = business.entityManagerContainer().get(Unit.class);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Unit> root = cq.from(Unit.class);
		Predicate p = cb.equal(root.get(Unit_.superior), wo.getId());
		Long count = em.createQuery(cq.select(cb.count(root)).where(p)).getSingleResult();
		return count;
	}

	private Long countSubDirectIdentity(Business business, Wo wo) throws Exception {
		EntityManager em = business.entityManagerContainer().get(Identity.class);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Identity> root = cq.from(Identity.class);
		Predicate p = cb.equal(root.get(Identity_.unit), wo.getId());
		Long count = em.createQuery(cq.select(cb.count(root)).where(p)).getSingleResult();
		return count;
	}

}
