package com.gcats.cats.service;

import com.gcats.cats.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.ArrayList;

public class EntityDao<Bean>
{
    protected final Class<Bean> typeParameterClass;

    public EntityDao(Class<Bean> typeParameterClass)
    {
        this.typeParameterClass = typeParameterClass;
    }

    public void delete(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Bean del = (Bean) session.get(typeParameterClass, id);
        session.delete(del);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public ArrayList<Bean> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("from %s",typeParameterClass.getCanonicalName());
        Query SQLQuery = session.createQuery(hql);
        ArrayList<Bean> result = (ArrayList<Bean>) SQLQuery.list();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public Bean getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Bean result = (Bean) session.get(typeParameterClass, id);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public void update(Bean object) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public void add(Bean object) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public void clear()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("delete from %s",typeParameterClass.getCanonicalName());
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }
}

