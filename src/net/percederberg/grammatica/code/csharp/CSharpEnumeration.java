/*
 * CSharpEnumeration.java
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the BSD license.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * LICENSE.txt file for more details.
 *
 * Copyright (c) 2003-2015 Per Cederberg. All rights reserved.
 */

package net.percederberg.grammatica.code.csharp;

import java.io.PrintWriter;
import net.percederberg.grammatica.code.CodeElement;
import net.percederberg.grammatica.code.CodeStyle;

/**
 * A class generating a C# enumeration declaration.
 *
 * @author   Per Cederberg
 * @version  1.0
 */
public class CSharpEnumeration extends CSharpType {

    /**
     * The public access modifier constant.
     */
    public static final int PUBLIC = CSharpModifier.PUBLIC;

    /**
     * The protected internal access modifier constant. May only be
     * used when declared inside another type.
     */
    public static final int PROTECTED_INTERNAL =
        CSharpModifier.PROTECTED_INTERNAL;

    /**
     * The protected access modifier constant. May only be used when
     * declared inside another type.
     */
    public static final int PROTECTED = CSharpModifier.PROTECTED;

    /**
     * The internal access modifier constant.
     */
    public static final int INTERNAL = CSharpModifier.INTERNAL;

    /**
     * The private access modifier constant. May only be used when
     * declared inside another type.
     */
    public static final int PRIVATE = CSharpModifier.PRIVATE;

    /**
     * The new modifier constant. May only be used when declared
     * inside another type.
     */
    public static final int NEW = CSharpModifier.NEW;

    /**
     * The last enumeration constant added.
     */
    Constant last = null;

    /**
     * Creates a new enumeration code generator with public access.
     *
     * @param name           the enumeration name
     */
    public CSharpEnumeration(String name) {
        this(PUBLIC, name);
    }

    /**
     * Creates a new enumeration code generator with the specified
     * modifiers.
     *
     * @param modifiers      the modifier flag constants
     * @param name           the enumeration name
     */
    public CSharpEnumeration(int modifiers, String name) {
        super(modifiers, name, "");
    }

    /**
     * Returns a numeric category number for the code element. A lower
     * category number implies that the code element should be placed
     * before code elements with a higher category number within a
     * declaration.
     *
     * @return the category number
     */
    public int category() {
        return 3;
    }

    /**
     * Adds a constant to the enumeration.
     *
     * @param name           the constant name
     */
    public void addConstant(String name) {
        addConstant(name, null);
    }

    /**
     * Adds a constant to the enumeration.
     *
     * @param name           the constant name
     * @param value          the constant value
     */
    public void addConstant(String name, String value) {
        addConstant(name, value, null);
    }

    /**
     * Adds a constant to the enumeration.
     *
     * @param name           the constant name
     * @param value          the constant value, or null
     * @param comment        the constant comment
     */
    public void addConstant(String name,
                            String value,
                            CSharpComment comment) {

        Constant  c = new Constant(name, value);

        if (comment != null) {
            c.setComment(comment);
        }
        addElement(c);
        last = c;
    }

    /**
     * Prints the code element to the specified output stream.
     *
     * @param out            the output stream
     * @param style          the code style to use
     * @param indent         the indentation level
     */
    public void print(PrintWriter out, CodeStyle style, int indent) {
        print(out, style, indent, "enum");
    }

    /**
     * Prints the lines separating two elements. By default this
     * method prints a newline before the first element, and between
     * elements with different category numbers.
     *
     * @param out            the output stream
     * @param style          the code style to use
     * @param prev           the previous element, or null if first
     * @param next           the next element, or null if last
     */
    protected void printSeparator(PrintWriter out,
                                  CodeStyle style,
                                  CodeElement prev,
                                  CodeElement next) {
        // Do nothing
    }

    /**
     * A class generating a C# enumeration constant declaration.
     */
    private class Constant extends CodeElement {

        /**
         * The constant name.
         */
        private String name;

        /**
         * The constant value.
         */
        private String value;

        /**
         * The constant comment.
         */
        private CSharpComment comment;

        /**
         * Creates a new constant.
         *
         * @param name           the constant name
         * @param value          the constant value
         */
        public Constant(String name, String value) {
            this.name = name;
            this.value = value;
            this.comment = null;
        }

        /**
         * Returns a numeric category number for the code element. A lower
         * category number implies that the code element should be placed
         * before code elements with a higher category number within a
         * declaration.
         *
         * @return the category number
         */
        public int category() {
            return 0;
        }

        /**
         * Sets the constant comment. This method overwrites any
         * previous comment.
         *
         * @param comment        the new constant comment
         */
        public void setComment(CSharpComment comment) {
            this.comment = comment;
        }

        /**
         * Prints the code element to the specified output stream.
         *
         * @param out            the output stream
         * @param style          the code style to use
         * @param indent         the indentation level
         */
        public void print(PrintWriter out, CodeStyle style, int indent) {
            if (comment != null) {
                out.println();
                comment.print(out, style, indent);
            }
            out.print(style.getIndent(indent));
            out.print(name);
            if (value != null) {
                out.print(" = ");
                out.print(value);
            }
            if (this != last) {
                out.print(",");
            }
            out.println();
        }
    }
}
