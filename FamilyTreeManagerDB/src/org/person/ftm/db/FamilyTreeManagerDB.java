/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.person.ftm.db;

import java.beans.PropertyChangeListener;
import java.util.List;
import org.person.*;

/**
 *
 * @author eubule
 */
public interface FamilyTreeManagerDB {
     public static final String PROP_PERSON_DESTROYED = "removePerson";
    public static final String PROP_PERSON_ADDED = "addPerson";
    public static final String PROP_PERSON_UPDATED = "updatePerson";
    

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);
    
    public void addPerson(PersonDB p);

    public void updatePerson(PersonDB p);

    public void deletePerson(PersonDB p);
    
    public List<PersonDB> getAllPeople();
}
