package com.rhahn.myworldtrip.Enum;

/**
 * Enum used to set the CardView layout
 *
 * @author Robin Hahn
 */
public enum AttributeType {
    //normal textview
    TEXTVIEW {
        @Override
        public String toString() {
            return "textview";
        }
    },
    //2 columns texts
    TEXTLIST {
        @Override
        public String toString() {
            return "textlist";
        }
    },
    //normal textinput
    TEXTINPUT,
    //2 column, first text, second checkbox
    CHECKLIST,
    //2 column, first text, second numberinput
    NUMBERINPUT,
    //multiline Textview
    MULTILINE {
        @Override
        public String toString() {
            return "multiline";
        }
    }
}
