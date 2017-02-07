/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.manager.impl;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author eubule
 */
@ServiceProvider(service = FamilyTreeManager.class)
public class FamilyTreeManagerImpl implements FamilyTreeManager{
    
    private final Map<Long, Person> personMap = new HashMap<>();
    private PropertyChangeSupport propChangeSupport = null;
    
    private PropertyChangeSupport getPropertyChangeSupport() {
        if (this.propChangeSupport == null) {
            this.propChangeSupport = new PropertyChangeSupport(this);
        }
        return this.propChangeSupport;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
       getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addPerson(Person p) {
         Person person = new Person(p);
        personMap.put(person.getId(), person);
        getPropertyChangeSupport().firePropertyChange(
                FamilyTreeManager.PROP_PERSON_ADDED, null, person); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updatePerson(Person p) {
         Person person = new Person(p);
        personMap.put(person.getId(), person);
        getPropertyChangeSupport().firePropertyChange(
                FamilyTreeManager.PROP_PERSON_UPDATED, null, person);//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletePerson(Person p) {
       Person person = personMap.remove(p.getId());
        if (person != null) {
            getPropertyChangeSupport().firePropertyChange(
                    FamilyTreeManager.PROP_PERSON_DESTROYED, null, person);
        }//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Person> getAllPeople() {
         List<Person> copyList = new ArrayList<>();
        personMap.values().stream().forEach((p) -> {
            copyList.add(new Person(p));
        });
        return copyList;
    };

    
    
}
