/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.connection;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.LifecycleManager;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.Lookups;
import org.person.Person;
import org.person.ftm.db.FamilyTreeManagerDB;
import org.person.ftm.db.PersonDB;

/**
 *
 * @author eubule
 */
@NbBundle.Messages({
    "HINT_PersonNode=Person"
})
public class PersoNode extends BeanNode<PersonDB> implements PropertyChangeListener{
    private static final Logger logger = Logger.getLogger(PersoNode.class.getName());
    public PersoNode(PersonDB person) throws IntrospectionException{
        super(person,Children.LEAF,Lookups.singleton(person));
        setIconBaseWithExtension("org/connection/resources/personIcon.png");
       // setName(String.valueOf(person.getId()));
        setDisplayName(person.getName()+" "+person.getMiddlename());
        setShortDescription(Bundle.HINT_PersonNode());
        logger.log(Level.INFO, "Creating new PersonNode for {0}", person);
        
         
        
       
        
        
    }

    @Override
    public String getHtmlDisplayName() {
        Person person = getLookup().lookup(Person.class);
        StringBuilder sb = new StringBuilder();
        if (person == null) {
            return null;
        }
        
         sb.append("<font color='#5588FF'><b>| ");
               
        
        sb.append(person).append("</b></font>");
        return sb.toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        PersonDB person=(PersonDB) evt.getSource();
        logger.log(Level.INFO, "PropChangeListener for {0}", person);
        fireDisplayNameChange(null,getDisplayName());
    }
     private final PropertyChangeListener propListener =
                     (PropertyChangeEvent evt) -> {
        PersonDB person = (PersonDB) evt.getSource();
        FamilyTreeManagerDB ftm = Lookup.getDefault().lookup(
                                    FamilyTreeManagerDB.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        } else {
            ftm.updatePerson(person);
            fireDisplayNameChange(null, getDisplayName());
        }
    };
    
}
