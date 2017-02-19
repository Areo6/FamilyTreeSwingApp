/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.person.ftmdb.jpa;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.event.SwingPropertyChangeSupport;
import org.openide.*;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.ServiceProvider;

import org.person.Person;
import org.person.ftm.db.FamilyTreeManagerDB;
import org.person.ftm.db.PersonDB;

/**
 *
 * @author eubule
 */
@Messages({
    "DBServerFailure=Perhaps you forgot to start JavaDB Database Server?",
    "DBServerEntityError=Cannot get EntityManager.",
    "DBError=Error"
})
@ServiceProvider(service = FamilyTreeManagerDB.class)
public class FamilyTreeManagerDBJPA implements FamilyTreeManagerDB {
     private SwingPropertyChangeSupport propChangeSupport = null;
     private static final EntityManagerFactory EMF;
    private static final Logger logger = Logger.getLogger(FamilyTreeManagerDBJPA.class.getName());
    static {
        try {
            EMF = Persistence.createEntityManagerFactory("PersoLibraryPU");
            logger.log(Level.INFO, "Entity Manager Factory Created.");
            // Let's create/close entity manager to make sure JavaDB Server is running
            EntityManager em = EMF.createEntityManager();
            em.close();
        } catch (Throwable ex) {
            logger.log(Level.SEVERE,
                    Bundle.DBServerEntityError(), ex);
            NotifyDescriptor nd = new NotifyDescriptor.Message(
                    Bundle.DBServerFailure(),  NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(nd);
            throw new ExceptionInInitializerError(ex);
        }
    }
    private PropertyChangeSupport getPropertyChangeSupport() {
        if (this.propChangeSupport == null) {
            this.propChangeSupport = new SwingPropertyChangeSupport(this, true);
        }
        return this.propChangeSupport;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }
    private PersonDB buildPerson(Person pe) {
        PersonDB person= new PersonDB(pe.getId());  
        person.setName(pe.getName());
        person.setGender(pe.getGender());
        person.setLastname(pe.getLastname());
        person.setMiddlename(pe.getMiddlename());
        person.setNotes(pe.getNotes());
        person.setSuffix(pe.getSuffix());
        return person;
    }
     @Override
    public void addPerson(final PersonDB newPerson) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Person person = new Person();
            person.setName(newPerson.getName());
            person.setLastname(newPerson.getLastname());
            person.setGender(newPerson.getGender());
            person.setMiddlename(newPerson.getMiddlename());
            person.setSuffix(newPerson.getSuffix());
            person.setNotes(newPerson.getNotes());
            em.persist(person);
            em.getTransaction().commit();
            logger.log(Level.INFO, "New Person: {0} successfully added.", newPerson);
            getPropertyChangeSupport().firePropertyChange(
                    FamilyTreeManagerDB.PROP_PERSON_ADDED, null, buildPerson(person));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
    }
     @Override
    public void updatePerson(final PersonDB p) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Person target = em.find(Person.class, p.getId());
            if (target != null) {
                target.setName(p.getName());
                target.setGender(p.getGender());
                target.setLastname(p.getLastname());
                target.setMiddlename(p.getMiddlename());
                target.setNotes(p.getNotes());
                target.setSuffix(p.getSuffix());
                em.merge(target);
                em.getTransaction().commit();
                logger.log(Level.FINE, "Person {0} successfully updated.", p);
                getPropertyChangeSupport().firePropertyChange(
                        FamilyTreeManagerDB.PROP_PERSON_UPDATED, null, buildPerson(target));
            }
            logger.log(Level.WARNING, "No entity for Person {0}.", p);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
    }
     @Override
    public void deletePerson(PersonDB p) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Person target = em.find(Person.class, p.getId());
            if (target != null) {
                em.remove(target);
                em.getTransaction().commit();
                logger.log(Level.FINE, "Person {0} successfully removed.", p);
                getPropertyChangeSupport().firePropertyChange(
                        FamilyTreeManagerDB.PROP_PERSON_DESTROYED, null, p);
            } else {
                logger.log(Level.WARNING, "No entity for Person {0}.", p);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<PersonDB> getAllPeople() {
        EntityManager em = EMF.createEntityManager();
        try {
            List<PersonDB> people = Collections.synchronizedList(new ArrayList<>());
            em.getTransaction().begin();
            Query q = em.createQuery(
                    "select p from Person p order by p.lastname asc, p.name asc");
            List<Person> results = (List<Person>) q.getResultList();
            if (results != null && results.size() > 0) {
                results.stream().forEach((pe) -> {
                    people.add(buildPerson(pe));
                });
            }
            em.getTransaction().commit();
            return Collections.unmodifiableList(people);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();
        }
    }


    
}
