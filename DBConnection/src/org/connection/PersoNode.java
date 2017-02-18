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
import org.openide.nodes.AbstractNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle;
import org.openide.util.lookup.Lookups;
import org.person.Person;

/**
 *
 * @author eubule
 */
@NbBundle.Messages({
    "HINT_PersonNode=Person"
})
public class PersoNode extends BeanNode implements PropertyChangeListener{
    private static final Logger logger = Logger.getLogger(PersoNode.class.getName());
    public PersoNode(Person person) throws IntrospectionException{
        super(Children.LEAF);
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
        Person person=(Person) evt.getSource();
        logger.log(Level.INFO, "PropChangeListener for {0}", person);
        fireDisplayNameChange(null,getDisplayName());
    }
    
}
