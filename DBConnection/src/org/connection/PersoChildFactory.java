/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.connection;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.LifecycleManager;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.WeakListeners;
import org.person.Person;
import org.person.ftm.db.FamilyTreeManagerDB;
import org.person.ftm.db.PersonDB;

/**
 *
 * @author eubule
 */
public class PersoChildFactory extends ChildFactory<PersonDB> {
     private final FamilyTreeManagerDB ftm;
    private static final Logger logger = Logger.getLogger(PersoChildFactory.class.getName());
    
    //List<PersonDB> resultList;
    public PersoChildFactory(List<PersonDB> resultList){
       this.ftm = Lookup.getDefault().lookup(FamilyTreeManagerDB.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        ftm.addPropertyChangeListener(familyTreeListener);
    }

 

    @Override
    protected boolean createKeys(List<PersonDB> list) {
        list.addAll(ftm.getAllPeople());
        logger.log(Level.FINER, "createKeys called: {0}", ftm.getAllPeople());
        return true;
    }

    @Override
    protected Node createNodeForKey(PersonDB key) {
        logger.log(Level.FINER, "createNodeForKey: {0}", key);
         PersoNode node = null;
        try{
            node=new PersoNode(key);
            key.addPropertyChangeListener(WeakListeners.propertyChange(node, key));
            return node;
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
        
    }
    private final PropertyChangeListener familyTreeListener = (PropertyChangeEvent evt) -> {
        if (evt.getPropertyName().equals(FamilyTreeManagerDB.PROP_PERSON_UPDATED)
                && evt.getNewValue() != null) {
            Person personChange = (Person) evt.getNewValue();
            logger.log(Level.INFO, "Person updated: {0}", personChange);
            this.refresh(true);
        }
    };

    
}
