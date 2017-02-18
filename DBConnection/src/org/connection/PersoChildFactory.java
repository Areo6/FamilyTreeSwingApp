/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.connection;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.logging.Logger;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.person.Person;

/**
 *
 * @author eubule
 */
public class PersoChildFactory extends ChildFactory<Person> {
    private static final Logger logger = Logger.getLogger(PersoChildFactory.class.getName());
    
    List<Person> resultList;
    public PersoChildFactory(List<Person> resultList){
        this.resultList=resultList;
        
    }

 

    @Override
    protected boolean createKeys(List<Person> list) {
        for(Person result:resultList){
            list.add(result);
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(Person key) {
        try{
            PersoNode node = new PersoNode(key);
            return node;
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
        
    }
    
}
