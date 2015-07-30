/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.mqm.frame.util.jofc2.model.metadata.Alias;
import com.mqm.frame.util.jofc2.model.metadata.Converter;
import com.mqm.frame.util.jofc2.util.StackKeyConverter;
import com.mqm.frame.util.jofc2.util.StackValueConverter;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class StackedBarChart extends Element {

	private static final long serialVersionUID = -4495162733156231531L;
    
	private List<Key> keys = new ArrayList<Key>();

	/**
	 * 构建器。
	 */
    public StackedBarChart() {
        super("bar_stack");
    }

    /**
     * 返回keys。
     * 
     * @return List<Key>
     */
    public List<Key> getKeys()
    {
        return keys;
    }

    /**
     * Add keys to the chart (var-args version).
     * 
     * @param keys the keys that have not yet been placed into the chart
     * 
     * @return the chart element object being operated on
     */
    public StackedBarChart addKeys(Key... keys) {
        return addKeys(Arrays.asList(keys));
    }

    /**
     * Add keys to the chart (Collections version).
     * 
     * @param keys the keys that have not yet been placed into the chart
     * @return the chart element object being operated on
     */
    public StackedBarChart addKeys(List<Key> keys) {
        getKeys().addAll(keys);
        return this;
    }

    /**
     * Add stacks to the chart (var-args version).
     * @param stacks the stacks that have not yet been placed into the chart
     * @return the chart element object being operated on
     */
    public StackedBarChart addStack(Stack... stacks) {
        return copy(Arrays.asList(stacks));
    }
    
    /**
     * Add stacks to the chart (Collections version).
     * @param stacks the stacks that have not yet been placed into the chart
     * @return the chart element object being operated on
     */
    public StackedBarChart addStack(List<Stack> stacks) {
        return copy(stacks);
    }
    
    /**
     * Create a stack and add it into the chart.  You do not need to
     * pass this Stack object to addStack.
     * @return the stack that has been created in the chart
     */
    public Stack newStack() {
        Stack s = new Stack();
        copy(Arrays.asList(s));
        return s;
    }
    
    /**
     * Find the most recently created stack, or create one if
     * there are none.
     * @return the last stack in the chart
     */
    public Stack lastStack() {
        if (getValues().isEmpty()) {
            return newStack();
        } else {
            return stack(getStackCount() - 1);
        }
    }
    
    /**
     * Find an arbitrary stack by index number. (Starts at 0.)
     * @param index the index of the stack, 0 to getStackCount() - 1.
     * @return the stack at the specified index
     */
    @SuppressWarnings("unchecked")
    public Stack stack(int index) {
        return new Stack((List<Object>) getValues().get(index));
    }
    
    /**
     * The number of stacks in the chart.
     * @return the number of stacks in the chart
     */
    public int getStackCount() {
        return getValues().size();
    }
    
    private StackedBarChart copy(List<Stack> stacks) {
        for (Stack s : stacks) {
            getValues().add(s.getBackingList());
        }
        return this;
    }

    /**
     * Representation of a stack in the chart.  This class allows
     * you to add numbers or complex values with custom data.
     */
    public static class Stack {
    	
        private transient List<Object> values;
        
        /**
         * 构建器。
         */
        public Stack() {
            values = new ArrayList<Object>();
        }
        
        Stack(List<Object> values) {
            this.values = values;
        }
        
        /**
         * 新增StackValue。
         * 
         * @param values StackValue
         * 
         * @return Stack
         */
        public Stack addStackValues(StackValue... values) {
            return doAdd(Arrays.asList(values));
        }
        
        /**
         * 新增List<StackValue>。
         * 
         * @param values List<StackValue>
         *  
         * @return Stack
         */
        public Stack addStackValues(List<StackValue> values) {
            return doAdd(values);
        }
        
        /**
         * 新增Number。
         * 
         * @param numbers Number
         * @return Stack
         */
        public Stack addValues(Number... numbers) {
            return addValues(Arrays.asList(numbers));
        }
        
        /**
         * 新增List<Number>。
         * 
         * @param numbers List<Number>
         * 
         * @return Stack
         */
        public Stack addValues(List<Number> numbers) {
      	  for (Number number: numbers){
      		  if (number != null) {
      		  this.doAdd(Collections.singletonList(new StackValue(number)));
      		  }
      	  }
            return this;
        }
        
        private Stack doAdd(List<? extends Object> values) {
            this.values.addAll(values);
            return this;
        }
        
        List<Object> getBackingList() {
            return this.values;
        }
    }
    
    /**
     * Representation of data in the stacked bar chart. 
     */
    @Converter(StackValueConverter.class)
    public static class StackValue {
        private Number val;
        private String colour;
        
        /**
         * 构建器。
         * 
         * @param value Numer
         */
        public StackValue(Number value) {
            this(value, null);
        }
        
        /**
         * 构建器。
         * 
         * @param value Number
         * @param colour String
         */
        public StackValue(Number value, String colour) {
            setValue(value);
            setColour(colour);
        }
        
        /**
         * 返回val。
         * 
         * @return Number
         */
        public Number getValue() {
            return val;
        }
        
        /**
         * 设置val。
         * 
         * @param val Number
         * 
         * @return StackValue
         */
        public StackValue setValue(Number val) {
            this.val = val;
            return this;
        }
        
        /**
         * 返回colour。
         * 
         * @return String
         */
        public String getColour() {
            return colour;
        }
        
        /**
         * 设置colour。
         * 
         * @param colour String
         * @return StackValue
         */
        public StackValue setColour(String colour) {
            this.colour = colour;
            return this;
        }
    }

    /**
     * Representation of a key in the stacked bar chart.
     */
    @Converter(StackKeyConverter.class)
    public static class Key {
        private String colour;
        private String text;
        @Alias(value = "font-size")
        private Integer fontSize;

        /**
         * 构建器。
         * 
         * @param colour String
         * @param text String
         * @param fontSize Integer
         */
        public Key(String colour, String text, Integer fontSize)
        {
            this.colour = colour;
            this.text = text;
            this.fontSize = fontSize;
        }

        /**
         * 返回colour。
         * 
         * @return String
         */
        public String getColour()
        {
            return colour;
        }

        /**
         * 设置colour。
         * 
         * @param colour String
         */
        public void setColour(String colour)
        {
            this.colour = colour;
        }

        /**
         * 返回text。
         * 
         * @return String
         */
        public String getText()
        {
            return text;
        }

        /**
         * 设置text。
         * 
         * @param text String
         */
        public void setText(String text)
        {
            this.text = text;
        }

        /**
         * 返回fontSize。
         * 
         * @return Integer
         */
        public Integer getFontSize()
        {
            return fontSize;
        }

        /**
         * 设置fontSize。
         * 
         * @param fontSize Integer
         */
        public void setFontSize(Integer fontSize)
        {
            this.fontSize = fontSize;
        }
    }

}
