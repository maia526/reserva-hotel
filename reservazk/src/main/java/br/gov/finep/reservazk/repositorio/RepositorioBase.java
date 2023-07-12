package br.gov.finep.reservazk.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

public class RepositorioBase {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public <T> List<T> list(Class<T> clz) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cri = cb.createQuery(clz);
        cri.from(clz);
        Query query = em.createQuery(cri);
        return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public <T> List<T> list(Class<T> clz, String columnOrder) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cri = cb.createQuery(clz);
        
        if (StringUtils.isNotEmpty(columnOrder)){
            Root<T> classe = cri.from(clz);
            cri.select(classe);
            cri.orderBy(cb.asc(classe.get(columnOrder)));
        }else{
            cri.from(clz);
        }
        Query query = em.createQuery(cri);
        return query.getResultList();
    }

    public <T> List<T> list(Class<T> clz, int index, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cri = cb.createQuery(clz);
        cri.from(clz);

        TypedQuery<T> query = em.createQuery(cri);
        query.setFirstResult(index);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public <T> int sizeOf(Class<T> clz) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cri = cb.createQuery(Long.class);
        cri.select(cb.count(cri.from(clz)));

        TypedQuery<Long> query = em.createQuery(cri);
        return query.getSingleResult().intValue();
    }

    @Transactional
    public <T> T update(T item) {
        item = em.merge(item);
        return item;
    }

    @Transactional
    public <T> T persist(T item) {
        em.persist(item);
        return item;
    }
    
    @Transactional
    public <T> T merge(T formEntity) {
        return em.merge(formEntity);
    }

    @Transactional
    public <T> T refresh(T item) {
        em.refresh(item);
        return item;
    }
    
    @Transactional
    public <T> void remove(T item) {
        em.remove(em.contains(item) ? item : em.merge(item));
    }

    @Transactional
    public <T> T reload(Class<T> clz, Serializable key) {
        if(key == null || clz == null)
            return null;
        return em.find(clz, key);
    }

    public EntityManager getEntityManager() {
        return em;
    }
}