package org.openmrs.module.resourcesapp.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.module.hospitalcore.model.TriagePatientData;
import org.openmrs.module.resourcesapp.api.db.TriageDAO;

public class HibernateTriageDAO implements TriageDAO {

	@Override
	public List<TriagePatientData> getPatientTriageData(Patient patient) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TriagePatientData.class);
		criteria.add(Restrictions.eq("patient", patient));
		return criteria.list();
	}
	public TriagePatientData getPatientTriageData(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TriagePatientData.class);
		criteria.add(Restrictions.eq("id", id));
		return (TriagePatientData) criteria.list().get(0);
	}
	private SessionFactory sessionFactory;
   
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
