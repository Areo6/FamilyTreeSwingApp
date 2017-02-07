/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.genderViewer;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle;


/**
 *
 * @author eubule
 */
@NbBundle.Messages({
    "HINT_RootNode=Show all people",
    "LBL_RootNode=People"
})
public class RootNode extends AbstractNode{
    
    public RootNode() {
         super(Children.create(new PersonChildFactory(), false));
        setIconBaseWithExtension("com/asgteach/familytree/genderViewer/resources/personIcon.png");
        setDisplayName(Bundle.LBL_RootNode());
        setShortDescription(Bundle.HINT_RootNode());
    }
    
}
