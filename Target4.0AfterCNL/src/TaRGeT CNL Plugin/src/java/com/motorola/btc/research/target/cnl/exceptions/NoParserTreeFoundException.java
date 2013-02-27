package com.motorola.btc.research.target.cnl.exceptions;

import com.motorola.btc.research.cnlframework.exceptions.CNLException;
import com.motorola.btc.research.cnlframework.postagger.TaggedSentence;
import com.motorola.btc.research.target.cnl.controller.TestCaseTextType;

@SuppressWarnings("serial")
/**
 * This class represents an exception thrown while processing the text.
 */
public class NoParserTreeFoundException extends CNLException
{

    private TaggedSentence taggedSentence;

    private TestCaseTextType field;

    /**
     * Class constructor.
     * @param taggedSentence the tagged sentence where the problem occurred.
     * @param field the field that indicates the grammar that will be matched to the sentence.
     */
    public NoParserTreeFoundException(TaggedSentence taggedSentence, TestCaseTextType field)
    {

        this.taggedSentence = taggedSentence;
        this.field = field;
    }


    /**
     * Returns the error message.
     */
    public String getMessage()
    {
        return "The following sentence violates the grammar: \""
                + this.taggedSentence.getTokenSentence().getActualSentence() + "\"";
    }

    /**
     * 
     * @return the tagged sentence where the problem occurred.
     */
    public TaggedSentence getTaggedSentence()
    {
        return this.taggedSentence;
    }
    
    /**
     * 
     * @return the field that indicates the grammar that will be matched to the sentence.
     */
    public TestCaseTextType getField()
    {
        return field;
    }

}
