package org.apache.io.protocol.coder;

import net.sf.cglib.beans.BeanMap;
import org.apache.io.protocol.annotation.AsciiString;
import org.apache.io.protocol.annotation.DateTime;
import org.apache.io.protocol.annotation.Decimal;
import org.apache.io.protocol.annotation.Number;
import org.apache.io.protocol.coder.impl.AsciiStringCoder;
import org.apache.io.protocol.coder.impl.DateTimeCoder;
import org.apache.io.protocol.coder.impl.DecimalCoder;
import org.apache.io.protocol.coder.impl.NumberCoder;
import org.apache.io.protocol.core.BitBuffer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * The coder factory for various datatype <code>encode</code>
 * or <code>decode</code>.
 */
public class CoderFactory implements ICoder {

    /**
     * The coder collection for byte buffer codecs.
     */
    private Map<Class<?>, ICoder> coderHandler = new HashMap<>();

    public CoderFactory() {
        coderHandler.put(AsciiString.class, new AsciiStringCoder());
        coderHandler.put(DateTime.class, new DateTimeCoder());
        coderHandler.put(Number.class, new NumberCoder());
        coderHandler.put(Decimal.class, new DecimalCoder());
    }

    @Override
    public void decode(BitBuffer bitBuffer, BeanMap beanMap, Field field,
                       Annotation annotation) throws Exception {
        coderHandler.get(annotation.annotationType()).decode(bitBuffer, beanMap,
                field, annotation);
    }

    @Override
    public void encode(BitBuffer bitBuffer, BeanMap beanMap, Field field,
                       Annotation annotation) throws Exception {
        coderHandler.get(annotation.annotationType()).encode(bitBuffer, beanMap,
                field, annotation);
    }

    @Override
    public String toPrettyHexString(BitBuffer bitBuffer, BeanMap beanMap, Field field,
                                    Annotation annotation) throws Exception {
        return coderHandler.get(annotation.annotationType()).toPrettyHexString(
                bitBuffer, beanMap, field, annotation);
    }
}
