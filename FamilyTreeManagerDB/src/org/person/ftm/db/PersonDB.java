/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.person.ftm.db;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author eubule
 */
public class PersonDB implements Serializable{
     private short id;
    private String name;
    private String middlename;
    private String lastname;
    private String suffix;
    private String gender;
    private String notes;
    private PropertyChangeSupport propChangeSupport = null;
    
    // Property names
    public static final String PROP_FIRST = "name";
    public static final String PROP_MIDDLE = "middlename";
    public static final String PROP_LAST = "lastname";
    public static final String PROP_SUFFIX = "suffix";
    public static final String PROP_GENDER = "gender";
    public static final String PROP_NOTES = "notes";
    

    
    public PersonDB(short id) {
		this("", "","");
		this.id = id;
	    }


    public PersonDB() {
        this("", "", "");
    }

    public PersonDB(String first, String last, String gender) {
        this.name = first;
        this.middlename = "";
        this.lastname = last;
        this.suffix = "";
        this.gender = gender;
       
    }

    public PersonDB(PersonDB person) {
        this.name = person.getName();
        this.middlename = person.getMiddlename();
        this.lastname = person.getLastname();
        this.suffix = person.getSuffix();
        this.gender = person.getGender();
        this.notes = person.getNotes();
        this.id = (short) person.getId();
    }

    private PropertyChangeSupport getPropertyChangeSupport() {
        if (this.propChangeSupport == null) {
            this.propChangeSupport = new PropertyChangeSupport(this);
        }
        return this.propChangeSupport;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        String oldNotes = this.notes;
        this.notes = notes;
        getPropertyChangeSupport().firePropertyChange(PROP_NOTES, oldNotes, notes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldFirst = this.name;
        this.name = name;
        getPropertyChangeSupport().firePropertyChange(PROP_FIRST, oldFirst, name);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        String oldGender = this.gender;
        this.gender = gender;
        getPropertyChangeSupport().firePropertyChange(PROP_GENDER, oldGender, gender);
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        String oldLast = this.lastname;
        this.lastname = lastname;
        getPropertyChangeSupport().firePropertyChange(PROP_LAST, oldLast, lastname);
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        String oldMiddle = this.middlename;
        this.middlename = middlename;
        getPropertyChangeSupport().firePropertyChange(PROP_MIDDLE, oldMiddle, middlename);
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        String oldSuffix = this.suffix;
        this.suffix = suffix;
        getPropertyChangeSupport().firePropertyChange(PROP_SUFFIX, oldSuffix, suffix);
    }

    public short getId() {
        return id;
    }
    public void setId(short id){
        this.id=id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonDB other = (PersonDB) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!name.isEmpty()) {
            sb.append(name);
        }
        if (!middlename.isEmpty()) {
            sb.append(" ").append(middlename);
        }
        if (!lastname.isEmpty()) {
            sb.append(" ").append(lastname);
        }
        if (!suffix.isEmpty()) {
            sb.append(" ").append(suffix);
        }
        return sb.toString();
    }
}
