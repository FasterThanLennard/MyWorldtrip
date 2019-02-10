package com.rhahn.myworldtrip.Data;

import com.rhahn.myworldtrip.Enum.AttributeType;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Dataobject for attributedata
 *
 * @author Robin Hahn
 */
public class AttributeData implements Serializable {
    private static final long serialVersionUID = -5668595998423094632L;
    //Viewtyp of attribute
    private AttributeType type;
    //name of attribute ex: weather
    private String name;
    //values of attribute, ex: min temp, 2°C and max temp, 5°C
    private HashMap<String, String> values;
    //is View editable
    private boolean editable;

    public AttributeData(AttributeType type, String name, HashMap<String, String> values, boolean editable) {
        this.type = type;
        this.name = name;
        this.values = values;
        this.editable = editable;
    }

    public AttributeType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getValues() {
        return values;
    }

    public void setValues(HashMap<String, String> values) {
        this.values = values;
    }

    public boolean isEditable() {
        return editable;
    }

}
